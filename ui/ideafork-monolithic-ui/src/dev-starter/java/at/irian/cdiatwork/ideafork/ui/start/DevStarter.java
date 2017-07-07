package at.irian.cdiatwork.ideafork.ui.start;

import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.undertow.WARArchive;

/**
 * tested with intellij (by opening ui/ideafork-monolithic-ui/pom.xml after building ui/pom.xml)
 */
public class DevStarter {
    public static void main(String[] args) throws Exception {
        System.setProperty("faces.PROJECT_STAGE", "Development"); //will be picked up by deltaspike -> mojarra will use it as well (since deltaspike v1.6.0)

        Swarm container = new Swarm(); //org.wildfly.swarm.container.Container is deprecated now
        //every access of logging (in-/directly) needs to be after the creation of the container
        System.setProperty("swarm.http.port", ConfigResolver.getProjectStageAwarePropertyValue("httpPort"));

        container.start();

        String context = ConfigResolver.getProjectStageAwarePropertyValue("serviceRoot");
        WARArchive warArchive = container.createDefaultDeployment().as(WARArchive.class).setContextRoot(context);

        container.deploy(warArchive);
    }
}
