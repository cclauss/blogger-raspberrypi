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

	public String executePythonScript(String script) throws SystemInfoException {
		String command = String.format("python3 %s%s", this.pythonPath, script);
		BufferedReader result = null;
		BufferedReader error = null;
		try {
			Process p = Runtime.getRuntime().exec(command);

			if (p.getErrorStream() != null) {
				error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			}

			if (p.getInputStream() != null) {
				result = new BufferedReader(new InputStreamReader(p.getInputStream()));
			}

			String out = result != null ? IOUtils.toString(result) : "N/E";
			p.destroy();
			return formatExecutionResultString(out);
		} catch (IOException e) {
			String message = String.format("Executing python 3 script %s failed! command was '%s'", script, command);
			LOGGER.error(message);
			throw new SystemInfoException(message, e);
		} finally {
			if (result != null) {
				IOUtils.closeQuietly(result);
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

	private String formatExecutionResultString(String raw) {
		if (raw.length() == 0) {
			raw = "empty";
		}
		return raw.replace("\n", "").replace("\r", "");
	}

	private InputStream readPythonScriptFromResource(String name) {
		return getClass().getClassLoader().getResourceAsStream("/python/" + name);
	}
}
