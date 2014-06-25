package net.gree.aurora.domain.datasource;

import org.sisioh.dddbase.utils.Try;

/**
 * 複数の{@link DataSource}から一つの{@link DataSource}を選択するためのサービス。
 *
 * @param <T> ヒントの型
 */
public interface DataSourceSelector<T> {

    /**
     * ヒントを元に{@link DataSource}の派生型を選択する。
     *
     * @param clazz {@link DataSource}の派生クラス
     * @param hint  ヒント
     * @param <A>   派生型
     * @return Tryでラップされた{@link DataSource}
     */
    <A extends DataSource> Try<A> selectDataSourceAs(Class<A> clazz, T hint);

    /**
     * ヒントを元に{@link DataSource}を選択する。
     *
     * @param hint ヒント
     * @return Tryでラップされた{@link DataSource}
     */
    Try<DataSource> selectDataSource(T hint);

    /**
     * ヒントを元に{@link JDBCDataSource}を選択する。
     *
     * @param hint ヒント
     * @return Tryでラップされた{@link JDBCDataSource}
     */
    Try<JDBCDataSource> selectDataSourceAsJDBC(T hint);

    /**
     * ヒントを元に{@link RedisDataSource}を選択する。
     *
     * @param hint ヒント
     * @return Tryでラップされた{@link RedisDataSource}
     */
    Try<RedisDataSource> selectDataSourceAsRedis(T hint);

}
