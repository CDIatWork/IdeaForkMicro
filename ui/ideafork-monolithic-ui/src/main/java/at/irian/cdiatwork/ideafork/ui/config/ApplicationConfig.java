package at.irian.cdiatwork.ideafork.ui.config;

import at.irian.cdiatwork.ideafork.config.client.TypedConfig;

import javax.enterprise.inject.Produces;

@TypedConfig
public interface ApplicationConfig {
    @Produces
    ApplicationVersion version();
}
