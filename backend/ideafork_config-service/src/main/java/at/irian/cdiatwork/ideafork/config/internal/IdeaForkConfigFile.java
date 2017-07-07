package at.irian.cdiatwork.ideafork.config.internal;

import org.apache.deltaspike.core.api.config.PropertyFileConfig;

public class IdeaForkConfigFile implements PropertyFileConfig {
    @Override
    public String getPropertyFileName() {
        return "app-config.properties";
    }

    @Override
    public boolean isOptional() {
        return false;
    }
}
