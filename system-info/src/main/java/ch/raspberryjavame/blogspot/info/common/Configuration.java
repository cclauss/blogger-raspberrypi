package ch.raspberryjavame.blogspot.info.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.raspberryjavame.blogspot.info.system.SystemInfoException;

public class Configuration {

	private final Logger LOGGER = LogManager.getLogger(Configuration.class);

	private static Configuration instance = null;
	private Properties applicationProperties;
	private final String userHome = System.getProperty("user.home");

	private Configuration() {
		super();
	}

	public static Configuration getInstance() {
		if (instance == null) {
			instance = new Configuration();
		}
		return instance;
	}

	public void init() throws SystemInfoException {
		applicationProperties = getSystemProperties();
	}

	public Properties getApplicationProperties() {
		return applicationProperties;
	}

	public String getUserHome() {
		return userHome;
	}

	private Properties getSystemProperties() throws SystemInfoException {
		Properties prop = new Properties();
		try (InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties")) {
			if (in != null) {
				prop.load(in);
				return prop;
			} else {
				LOGGER.error("Fatal! Property file not found check project configuration.");
				throw new SystemInfoException("Fatal! Property file not found check project configuration.");
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw new SystemInfoException(e.getMessage(), e);
		}
	}
}
