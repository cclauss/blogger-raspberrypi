package ch.raspberryjavame.blogspot.info.scheduler;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

@WebListener
public class InfoQuartzListener extends QuartzInitializerListener {

	private final Logger LOGGER = LogManager.getLogger(InfoQuartzListener.class);
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
			LOGGER.error("There was an error scheduling the job.", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			if (scheduler != null) {
				scheduler.shutdown();
				Thread.sleep(2000); // wait for shutdown
				LOGGER.info("Quartz Scheduler shutdown complete.");
			}
		} catch (Exception e) {
			LOGGER.error("There was an error shutting down the job!", e);
		}

	}
}
