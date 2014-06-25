package net.gree.aurora.application.config;

/**
 * {@link AuroraShardingConfig}のためのファクトリ。
 *
 * @param <T> ソースの型
 */
public interface AuroraShardingConfigFactory<T> {

    /**
     * ソースから{@link AuroraShardingConfig}を生成する。
     *
     * @param source ソース
     * @return {@link AuroraShardingConfig}
     */
    AuroraShardingConfig create(T source);

}
