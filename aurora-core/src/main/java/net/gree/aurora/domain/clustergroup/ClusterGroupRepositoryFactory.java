package net.gree.aurora.domain.clustergroup;

/**
 * {@link ClusterGroupRepository}のためのファクトリ。
 */
public final class ClusterGroupRepositoryFactory {

    private ClusterGroupRepositoryFactory() {

    }

    /**
     * {@link ClusterGroupRepository}を生成する。
     *
     * @return {@link ClusterGroupRepository}
     */
    public static ClusterGroupRepository create() {
        return new ClusterGroupRepositoryImpl();
    }

}
