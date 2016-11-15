package ch.raspberryjavame.blogspot.info.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.raspberryjavame.blogspot.info.common.Configuration;
import ch.raspberryjavame.blogspot.info.system.SystemInfoException;

@WebListener
public class ApplicationListener implements ServletContextListener {

	private final Logger LOGGER = LogManager.getLogger(ApplicationListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			LOGGER.info("Initializing application...");
			Configuration config = Configuration.getInstance();
			config.init();
			LOGGER.info("Application initialized.");
		} catch (SystemInfoException e) {
			LOGGER.error("Fatal application start failed!");
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// nothing to do here at the moment
	}
}
