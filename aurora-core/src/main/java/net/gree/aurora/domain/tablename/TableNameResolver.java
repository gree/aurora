package net.gree.aurora.domain.tablename;

/**
 * ヒントから{@link TableName}を解決するためのインターフェイス。
 *
 * @param <T> ヒントの型
 */
public interface TableNameResolver<T> {

    /**
     * ヒントから{@link TableName}を解決する。
     *
     * @param hint ヒント
     * @return {@link TableName}
     */
    TableName apply(T hint);

}
