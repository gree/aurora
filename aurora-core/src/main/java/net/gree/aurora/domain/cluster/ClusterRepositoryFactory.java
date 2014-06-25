package net.gree.aurora.domain.cluster;

/**
 * {@link ClusterRepository}のためのファクトリ。
 */
public final class ClusterRepositoryFactory {

    private ClusterRepositoryFactory() {

    }

    /**
     * {@link ClusterRepository}を生成する。
     *
     * @return {@link ClusterRepository}
     */
    public static ClusterRepository create() {
        return new ClusterRepositoryImpl();
    }

}
