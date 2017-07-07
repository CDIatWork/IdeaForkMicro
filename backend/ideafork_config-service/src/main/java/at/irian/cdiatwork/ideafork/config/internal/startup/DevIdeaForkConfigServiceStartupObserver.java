package at.irian.cdiatwork.ideafork.config.internal.startup;

import at.irian.cdiatwork.ideafork.config.domain.ConfigEntry;
import at.irian.cdiatwork.ideafork.config.repository.ConfigRepository;
import org.apache.deltaspike.core.api.exclude.Exclude;
import org.apache.deltaspike.core.api.projectstage.ProjectStage;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

@Exclude(exceptIfProjectStage = ProjectStage.Development.class)
@ApplicationScoped
public class DevIdeaForkConfigServiceStartupObserver {
    protected void onStartup(@Observes @Initialized(ApplicationScoped.class) Object ideaForkStartedEvent,
                             ConfigRepository configRepository) {

        configRepository.save(new ConfigEntry("maxNumberOfHighestRatedCategories", "10"));
    }
}
