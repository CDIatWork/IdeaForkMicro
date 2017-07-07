package at.irian.cdiatwork.ideafork.ui;

import org.apache.deltaspike.core.api.config.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.logging.Logger;

public class StartupLogger {
    private static final Logger LOG = Logger.getLogger(StartupLogger.class.getName());

    @Inject
    @ConfigProperty(name = "serviceName")
    private String serviceName;

    @Inject
    @ConfigProperty(name = "serviceVersion")
    private String serviceVersion;

    @Inject
    @ConfigProperty(name = "serviceRoot")
    private String serviceRoot;

    @Inject
    @ConfigProperty(name = "httpPort")
    private Integer httpPort;

    public void onStartup(@Observes @Initialized(ApplicationScoped.class) Object startupEvent) {
        LOG.info("service '" + serviceName + "' started in version " + serviceVersion + "");

        if (serviceRoot.startsWith("/")) {
            LOG.info("info-page at http://localhost:" + httpPort + serviceRoot);
        } else {
            LOG.info("info-page at http://localhost:" + httpPort + "/" + serviceRoot);
        }
    }
}
