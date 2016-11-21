package ch.raspberryjavame.blogspot.info.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.raspberryjavame.blogspot.info.common.Configuration;
import ch.raspberryjavame.blogspot.info.system.SystemInfoException;

public class ScriptManager {

	private final Logger LOGGER = LogManager.getLogger(ScriptManager.class);
	private static ScriptManager instance = null;
	private String pythonPath = "";

	private ScriptManager() {
		super();
	}

	public static ScriptManager getInstance() {
		if (instance == null) {
			instance = new ScriptManager();
		}
		return instance;
	}

	public synchronized void updateScriptConfiguration() throws SystemInfoException {
		Configuration config = Configuration.getInstance();
		updatePythonScriptStructure(config.getUserHome());
	}

	public ScriptResult executePythonScript(String script) throws SystemInfoException {
		String command = String.format("python3 %s%s", this.pythonPath, script);
		ScriptResult scriptResult = new ScriptResult();
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command);
			if (p.getErrorStream() != null) {
				BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				String strError = IOUtils.toString(error);
				scriptResult.setError(strError);
				// tomcat has no tty (nologin) so top: failed tty get\n
				// is just a warning but anything else is an error
				if (!StringUtils.equalsIgnoreCase(strError, "top: failed tty get\n")) {
					scriptResult.setSuccess(false);
				}
			}
			if (p.getInputStream() != null) {
				BufferedReader result = new BufferedReader(new InputStreamReader(p.getInputStream()));
				scriptResult.setResult(IOUtils.toString(result));
			}
			if (p.getOutputStream() != null) {
				byte[] array = new byte[0];
				p.getOutputStream().write(array);
				scriptResult.setOut(new String(array));
			}

			return scriptResult;
		} catch (IOException e) {
			String message = String.format("Executing python 3 script %s failed! command was '%s'", script, command);
			LOGGER.error(message);
			throw new SystemInfoException(message, e);
		} finally {
			if (p != null) {
				p.destroy();
			}
		}
	}

	private synchronized void updatePythonScriptStructure(String root) throws SystemInfoException {
		try {
			String path = validateScriptStructure(root);
			File file = new File(path);
			file.mkdirs();
			List<String> files = new ArrayList<>();
			files.add("system.py");
			files.add("system_info.py");
			for (String f : files) {
				InputStream in = readPythonScriptFromResource(f);
				File targetFile = new File(String.format("%s/%s", path, f));
				java.nio.file.Files.copy(in, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				IOUtils.closeQuietly(in);
			}
			this.pythonPath = path;
		} catch (IOException e) {
			LOGGER.error("Unable to copy file in script target directory, check access rights!");
			throw new SystemInfoException("Unable to copy file in script target directory, check access rights!");
		}

	}

	private synchronized String validateScriptStructure(String root) throws SystemInfoException {
		Configuration config = Configuration.getInstance();
		Properties prop = config.getApplicationProperties();
		String path = String.format("%s/%s/%s", root, prop.getProperty("application.name"),
				prop.getProperty("python.path"));

		File home = new File(root);
		if (home.exists() && home.isDirectory()) {
			File scriptDir = new File(path);
			removeDirectory(scriptDir);
		} else {
			LOGGER.error("Unable to remove script directory, check access rights!");
			throw new SystemInfoException("Unable to remove script directory, check access rights!");
		}
		return path;
	}

	private void removeDirectory(File dir) throws SystemInfoException {
		try {
			FileUtils.deleteDirectory(dir);
		} catch (IOException e) {
			LOGGER.error("Unable to remove directory, check access rights!");
			throw new SystemInfoException("Unable to remove directory, check access rights!");
		} catch (IllegalArgumentException e) {
			LOGGER.info("Directory does not exists, nothing to do here.");
		}
	}

	private InputStream readPythonScriptFromResource(String name) {
		return getClass().getClassLoader().getResourceAsStream("/python/" + name);
	}

	public class ScriptResult {

		private boolean success = true;
		private String result;
		private String error;
		private String out;

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public String getOut() {
			return out;
		}

		public void setOut(String out) {
			this.out = out;
		}
	}
}
