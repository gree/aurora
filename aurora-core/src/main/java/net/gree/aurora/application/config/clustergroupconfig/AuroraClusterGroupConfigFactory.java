package net.gree.aurora.application.config.clustergroupconfig;

/**
 * {@link AuroraClusterGroupConfig}のためのファクトリ。
 *
 * @param <T> ソースの型
 */
public interface AuroraClusterGroupConfigFactory<T> {

    /**
     * {@link AuroraClusterGroupConfig}を生成する。
     *
     * @param source ソース
     * @return {@link AuroraClusterGroupConfig}
     */
    AuroraClusterGroupConfig create(T source);

}
