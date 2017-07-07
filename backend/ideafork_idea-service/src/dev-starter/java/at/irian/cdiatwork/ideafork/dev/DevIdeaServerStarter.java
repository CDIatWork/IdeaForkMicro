package at.irian.cdiatwork.ideafork.dev;

import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.apache.deltaspike.core.api.projectstage.ProjectStage;
import org.apache.meecrowave.Meecrowave;

public class DevIdeaServerStarter {
    public static void main(String[] args) {
        System.setProperty("org.apache.deltaspike.ProjectStage", ProjectStage.Development.getClass().getSimpleName());

        Meecrowave.Builder builder = new Meecrowave.Builder();
        String configuredPort = ConfigResolver.getPropertyValue("idea-service.http.port");
        builder.setHttpPort(Integer.parseInt(configuredPort));

        new Meecrowave(builder).bake().await();
    }
}
