package ch.raspberryjavame.blogspot.info.script;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
		String userHome = System.getProperty("user.home");
		updatePythonScriptStructure(userHome);
	}

	private synchronized void updatePythonScriptStructure(String root) throws SystemInfoException {
		try {
			String path = validateScriptStructure(root);
			File file = new File(path);
			file.mkdirs();
			List<String> files = new ArrayList<>();
			files.add("main.py");
			files.add("hello.py");
			for (String f : files) {
				InputStream in = readPythonScriptFromResource(f);
				File targetFile = new File(String.format("%s/%s", path, f));
				java.nio.file.Files.copy(in, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				IOUtils.closeQuietly(in);
			}
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
			LOGGER.info("Directory does not exists, nothing left to do here.");
		}
	}

	private InputStream readPythonScriptFromResource(String name) {
		return getClass().getClassLoader().getResourceAsStream("/python/" + name);
	}
}
