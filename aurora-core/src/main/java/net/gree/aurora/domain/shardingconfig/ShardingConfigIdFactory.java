package net.gree.aurora.domain.shardingconfig;

/**
 * {@link ShardingConfigId}のためのファクトリ。
 */
public final class ShardingConfigIdFactory {

    private ShardingConfigIdFactory() {

    }

    /**
     * {@link ShardingConfigId}を生成する。
     *
     * @param value 識別子の値
     * @return {@link ShardingConfigId}
     */
    public static ShardingConfigId create(String value) {
        return new ShardingConfigIdImpl(value);
    }

}
