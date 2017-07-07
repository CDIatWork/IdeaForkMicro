package at.irian.cdiatwork.ideafork.idea.config;

import at.irian.cdiatwork.ideafork.config.client.TypedConfig;

@TypedConfig(remote = true)
public interface IdeaConfig {
    Integer maxNumberOfHighestRatedCategories();
}
