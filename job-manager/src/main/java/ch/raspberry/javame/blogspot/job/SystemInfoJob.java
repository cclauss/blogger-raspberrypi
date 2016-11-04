package ch.raspberry.javame.blogspot.job;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SystemInfoJob extends AbstractJob {

	public SystemInfoJob() {
		super(SystemInfoJob.class);
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LocalDateTime date = LocalDateTime.now();
		logger.info(JOB_MARKER,
				String.format("Java web application :: %s", date.format(DateTimeFormatter.ISO_DATE_TIME)));
	}
}
