package net.gree.aurora.domain.cluster;

import net.gree.aurora.domain.hint.Hint;

/**
 * {@link ClusterId}をヒントから解決するためのインターフェイス。
 *
 * @param <T> ヒントの型
 */
public interface ClusterIdResolver<T> {

    /**
     * ヒントから{@link ClusterId}を解決する。
     *
     * @param hint ヒント
     * @return {@link ClusterId}
     */
    ClusterId apply(Hint<T> hint, int clusterSize);

}

