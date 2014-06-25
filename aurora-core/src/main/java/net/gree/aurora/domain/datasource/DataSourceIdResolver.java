package net.gree.aurora.domain.datasource;

/**
 * ヒントから{@link DataSourceId}を解決するためのインターフェイス
 *
 * @param <T> ヒントの型
 */
public interface DataSourceIdResolver<T> {

    /**
     * 関数にヒントを適用して戻り値を得る。
     *
     * @param hint ヒント
     * @return {@link DataSourceId}
     */
    DataSourceId apply(T hint);

}
