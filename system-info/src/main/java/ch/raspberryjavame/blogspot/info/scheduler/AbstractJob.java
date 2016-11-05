package ch.raspberryjavame.blogspot.info.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.quartz.Job;

public abstract class AbstractJob implements Job {

	protected static final Marker JOB_MARKER = MarkerManager.getMarker("JOB");
	protected final Logger logger;

	protected AbstractJob(Class<?> clazz) {
		super();
		logger = LogManager.getLogger(clazz);
	}
}
