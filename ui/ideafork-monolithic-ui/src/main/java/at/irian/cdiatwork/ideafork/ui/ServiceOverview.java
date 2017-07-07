package at.irian.cdiatwork.ideafork.ui;

import org.apache.deltaspike.core.api.config.ConfigResolver;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ServiceOverview {
    public String config(String key) {
        return ConfigResolver.getProjectStageAwarePropertyValue(key, "???" + key + "???");
    }
}
