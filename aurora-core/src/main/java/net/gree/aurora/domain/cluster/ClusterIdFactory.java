package net.gree.aurora.domain.cluster;

/**
 * {@link ClusterId}のためのファクトリ。
 */
public final class ClusterIdFactory {

    private ClusterIdFactory() {
    }

    /**
     * {@link ClusterId}を生成する。
     *
     * @param value 識別子の値
     * @return {@link ClusterId}
     */
    public static ClusterId create(String value) {
        return new ClusterIdImpl(value);
    }

}
