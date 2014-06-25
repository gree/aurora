package net.gree.aurora.application;

/**
 * {@link AuroraShardingConfigLoadService}を生成するためのファクトリ。
 */
public final class AuroraShardingConfigLoadServiceFactory {

    private AuroraShardingConfigLoadServiceFactory() {
    }

    /**
     * {@link AuroraShardingConfigLoadService}を生成する。
     *
     * @return {@link AuroraShardingConfigLoadService}
     */
    public static AuroraShardingConfigLoadService create() {
        return new AuroraShardingConfigLoadServiceImpl();
    }

}
