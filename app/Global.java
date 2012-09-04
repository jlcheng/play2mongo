import global.GlobalContext;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import demo.Person;

import play.Application;
import play.GlobalSettings;
import play.Logger;

public class Global extends GlobalSettings {

	private Scheduler scheduler;

	@Override
	public void onStart(Application app) {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();

			JobDetail job = JobBuilder.newJob(Worker.class)
					.withIdentity("job1", "group1").build();

			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity("trigger1", "group1")
					.startNow()
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
									.withIntervalInSeconds(10).withRepeatCount(3))
					.build();
			scheduler.scheduleJob(job, trigger);
			Logger.info("Quartz scheduler started.");
		} catch ( Exception e ) {
			Logger.error(e.getMessage());
		}

		try {
			GlobalContext.appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
			Person p = GlobalContext.appContext.getBean(Person.class);
			if ( p == null || p.getFavoriteAnimal() == null ) {
				throw new RuntimeException("Spring not initalized as expected.");
			}
			Logger.info("Spring applicationContext started.");
		} catch ( Exception e ) {
			Logger.error(e.getMessage());
		}
	}

	@Override
	public void onStop(Application app) {
		try {
			if (scheduler != null) {
				scheduler.shutdown();
				scheduler = null;
				Logger.info("Quartz scheduler shutdown.");
			}
		} catch ( Exception e ) {
			Logger.error(e.getMessage());
		}
		
		if ( GlobalContext.appContext != null ) {
			try {
				GlobalContext.appContext.stop();
				Logger.debug("Spring applicationContext stopped.");
			} catch ( Exception e ) {
				Logger.error(e.getMessage());
			}
		}
	}
	
	public static class Worker implements Job {
		public void execute(JobExecutionContext ctx) {
			Logger.debug("Scheduled Job triggered at: " + new java.util.Date());
		}
	}

}
