package betBot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import betBot.mechanics.Mechanics;
import betBot.mechanics.UpdatesDownloadingTask;

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

        scheduledTaskRegistrar.addFixedDelayTask(updatesDownloadingTask::getUpdates, 500);
        scheduledTaskRegistrar.addFixedDelayTask(mechanics::step, 1000);
    }

}
