package ch.raspberry.javame.blogspot.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import ch.raspberry.javame.blogspot.job.SystemInfoJob;

public class InfoQuartzListener implements ServletContextListener {

	private final Logger LOG = LogManager.getLogger(InfoQuartzListener.class);
	private Scheduler scheduler;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			JobDetail jobDetail = JobBuilder.newJob(SystemInfoJob.class).build();
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simple")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * 1/1 * ? *")).startNow().build();
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
		} catch (Exception e) {
			LOG.error("There was an error scheduling the job.", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		try {
			if (scheduler != null) {
				scheduler.shutdown();
				Thread.sleep(2000); // wait for shutdown
				LOG.info("Quartz Scheduler shutdown complete.");
			}
		} catch (Exception e) {
			LOG.error("There was an error shutting down the job!", e);
		}

	}
}
