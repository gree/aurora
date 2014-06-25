package net.gree.aurora.application.config.defaultconfig;

import com.typesafe.config.Config;

/**
 * {@link AuroraDefaultRedisConfig}のためのファクトリ。
 */
public interface AuroraDefaultRedisConfigFactory extends AuroraDefaultConfigFactory<Config> {

    /**
     * {@link AuroraDefaultRedisConfig}を生成する。
     *
     * @param source ソース
     * @return {@link AuroraDefaultRedisConfig}
     */
    AuroraDefaultRedisConfig create(Config source);

}
