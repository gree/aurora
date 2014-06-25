package net.gree.aurora.application.config;

import com.typesafe.config.Config;
import org.apache.commons.lang.Validate;

import java.util.HashSet;
import java.util.List;

final class AuroraTableNamesConfigFactoryByConfig implements AuroraTableNamesConfigFactory<Config> {

    @Override
    public AuroraTableNamesConfig create(Config source) {
        Validate.notNull(source);
        List<String> patterns = source.getStringList("patterns");
        return new AuroraTableNamesConfigImpl(new HashSet<>(patterns));
    }
}
