package net.gree.aurora.application.config;

/**
 * {@link AuroraShardingConfigLoader}のためのファクトリ。
 */
public final class AuroraShardingConfigLoaderFactory {

    private AuroraShardingConfigLoaderFactory() {

    }

    /**
     * {@link AuroraShardingConfigLoader}を生成する。
     *
     * @return {@link AuroraShardingConfigLoader}
     */
    public static AuroraShardingConfigLoader create() {
        return new AuroraShardingConfigLoaderImpl();
    }

}
