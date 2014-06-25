package net.gree.aurora.application.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import net.gree.aurora.application.ConfigUtil;
import org.apache.commons.lang.Validate;

import java.io.File;
import java.util.Arrays;

final class AuroraTableNamesConfigLoaderImpl implements AuroraTableNamesConfigLoader {

    private final AuroraTableNamesConfigFactory<Config> factory = new AuroraTableNamesConfigFactoryByConfig();

    @Override
    public AuroraTableNamesConfig load(File configFile) {
        Validate.notNull(configFile);
        Config config = ConfigFactory.parseFile(configFile);
        Config auroraConfig = config.getConfig("aurora");
        Config tableNameConfig = ConfigUtil.getConfig(auroraConfig, Arrays.asList("tableNameConfigs", "table-name-configs"));
        return load(tableNameConfig);
    }

    @Override
    public AuroraTableNamesConfig load(Config config) {
        return factory.create(config);
    }

}
