package net.gree.aurora.application.config.clusterconfig;

/**
 * JDBCに対応した{@link AuroraClusterConfigFactory}。
 *
 * @param <T> ソースの型
 */
public interface AuroraClusterJDBCConfigFactory<T> extends AuroraClusterConfigFactory<T> {

    /**
     * {@link AuroraClusterJDBCConfig}を生成する。
     *
     * @param source ソース
     * @return {@link AuroraClusterJDBCConfig}
     */
    AuroraClusterJDBCConfig create(T source);

}
