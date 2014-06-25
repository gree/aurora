package net.gree.aurora.domain.clustergroup;

/**
 * {@link ClusterGroupId}のためのファクトリ。
 */
public final class ClusterGroupIdFactory {

    private ClusterGroupIdFactory() {

    }

    /**
     * {@link ClusterGroupId}を生成する。
     *
     * @param value 識別子の値
     * @return {@link ClusterGroupId}
     */
    public static ClusterGroupId create(String value) {
        return new ClusterGroupIdImpl(value);
    }

}
