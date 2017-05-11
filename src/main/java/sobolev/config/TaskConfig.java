package sobolev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import sobolev.mechanics.Mechanics;
import sobolev.mechanics.UpdatesDownloadingTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.Executors;


@EnableScheduling
@Configuration
public class TaskConfig implements SchedulingConfigurer {

    private final UpdatesDownloadingTask updatesDownloadingTask;
    private final Mechanics mechanics;

    @Autowired
    public TaskConfig(UpdatesDownloadingTask updatesDownloadingTask, Mechanics mechanics) {
        this.updatesDownloadingTask = updatesDownloadingTask;
        this.mechanics = mechanics;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        System.out.println("TaskConfig.configureTasks");
        scheduledTaskRegistrar.setScheduler(new ConcurrentTaskScheduler() {{
            setConcurrentExecutor(Executors.newFixedThreadPool(3));
        }});

        scheduledTaskRegistrar.addFixedDelayTask(updatesDownloadingTask::getUpdates, 200);
        scheduledTaskRegistrar.addFixedDelayTask(mechanics::step, 200);
    }

}
