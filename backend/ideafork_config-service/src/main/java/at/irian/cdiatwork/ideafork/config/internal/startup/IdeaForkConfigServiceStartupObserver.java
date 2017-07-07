package at.irian.cdiatwork.ideafork.config.internal.startup;

import at.irian.cdiatwork.ideafork.config.internal.DataBaseAwareConfigSource;
import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.apache.deltaspike.core.spi.config.ConfigSource;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import java.util.Arrays;

@ApplicationScoped
public class IdeaForkConfigServiceStartupObserver {
    protected void onStartup(@Observes @Initialized(ApplicationScoped.class) Object ideaForkStartedEvent,
                             DataBaseAwareConfigSource configSource) {

        ConfigResolver.addConfigSources(Arrays.<ConfigSource>asList(configSource));
    }
}
