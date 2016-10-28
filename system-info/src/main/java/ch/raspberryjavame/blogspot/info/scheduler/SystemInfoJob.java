package ch.raspberryjavame.blogspot.info.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SystemInfoJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Java web application + Quartz 2.2.2");
	}
}
