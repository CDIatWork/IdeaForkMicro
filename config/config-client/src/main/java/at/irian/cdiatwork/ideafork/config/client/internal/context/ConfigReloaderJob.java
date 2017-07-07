package at.irian.cdiatwork.ideafork.config.client.internal.context;

import org.apache.deltaspike.scheduler.api.Scheduled;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@Scheduled(cronExpression = "0 0/10 * * * ?")
public class ConfigReloaderJob implements Job {
    @Inject
    private ConfigReloader configReloader;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        configReloader.reloadConfig();
    }
}
