package net.gree.aurora.domain.cluster;

import net.gree.aurora.domain.hint.Hint;
import org.apache.commons.lang.Validate;

/**
 * {@link ClusterIdResolver}の骨格実装。
 *
 * @param <T> ヒントの型
 */
public abstract class AbstractClusterIdResolver<T> implements ClusterIdResolver<T> {

    protected final String idBaseName;

    /**
     * インスタンスを生成する。
     *
     * @param idBaseName 識別子のベース名
     */
    protected AbstractClusterIdResolver(String idBaseName) {
        Validate.notNull(idBaseName);
        this.idBaseName = idBaseName;
    }

    /**
     * ヒントからサフィックス名を取得する。
     *
     * @param hint ヒント
     * @return サフィックス名
     */
    protected abstract String getSuffixName(Hint<T> hint, int clusterSize);


    @Override
    public ClusterId apply(Hint<T> hint, int clusterSize) {
        Validate.notNull(hint);
        return ClusterIdFactory.create(idBaseName + getSuffixName(hint, clusterSize));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractClusterIdResolver that = (AbstractClusterIdResolver) o;

        if (!idBaseName.equals(that.idBaseName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return 31 * idBaseName.hashCode();
    }

}
