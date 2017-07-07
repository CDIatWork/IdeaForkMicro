package at.irian.cdiatwork.ideafork.dev;

import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.apache.meecrowave.Meecrowave;

public class DevNotificationServerStarter {
    public static void main(String[] args) {
        Meecrowave.Builder builder = new Meecrowave.Builder();
        String configuredPort = ConfigResolver.getPropertyValue("notification-service.http.port");
        builder.setHttpPort(Integer.parseInt(configuredPort));

        new Meecrowave(builder).bake().await();
    }
}
