package net.gree.aurora.domain.shardingconfig;

/**
 * {@link ShardingConfigRepository}のためのファクトリ。
 */
public final class ShardingConfigRepositoryFactory {

    private ShardingConfigRepositoryFactory() {

    }

    /**
     * {@link ShardingConfigRepository}を生成する。
     *
     * @return {@link ShardingConfigRepository}
     */
    public static ShardingConfigRepository create() {
        return new ShardingConfigRepositoryImpl();
    }

}
