package net.gree.aurora.application.config.clusterconfig;

/**
 * {@link AuroraClusterConfig}のためのファクトリ。
 *
 * @param <T> ソースの型
 */
public interface AuroraClusterConfigFactory<T> {

    AuroraClusterConfig create(T source);

}
