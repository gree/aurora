package net.gree.aurora.domain.datasource;

import org.sisioh.dddbase.utils.Option;

import java.util.concurrent.atomic.AtomicLong;

/**
 * {@link DataSource}のためのファクトリ。
 */
public final class DataSourceFactory {

    private static final AtomicLong order = new AtomicLong(1L);

    private DataSourceFactory() {

    }

    /**
     * {@link DataSource}を生成する。
     *
     * @param identity       {@link DataSourceId}
     * @param dataSourceType {@link DataSourceType}
     * @param host           ホスト名
     * @param port           ポート番号
     * @return {@link DataSource`}
     */
    public static DataSource create(DataSourceId identity,
                                    DataSourceType dataSourceType,
                                    String host,
                                    Option<Integer> port) {
        return new DataSourceImpl(identity, dataSourceType, host, port, order.getAndIncrement());
    }

    /**
     * {@link JDBCDataSource}を生成する。
     *
     * @param identity        {@link DataSourceId}
     * @param dataSourceType  {@link DataSourceType}
     * @param driverClassName ドライバークラス名
     * @param host            ホスト名
     * @param port            ポート番号
     * @param url             URL
     * @param userName        ユーザ名
     * @param password        パスワード
     * @return {@link JDBCDataSource}
     */
    public static JDBCDataSource createAsJDBC(DataSourceId identity,
                                              DataSourceType dataSourceType,
                                              String driverClassName,
                                              String host,
                                              Option<Integer> port,
                                              String url,
                                              String userName,
                                              String password) {
        return new JDBCDataSourceImpl(identity, dataSourceType, driverClassName, host, port, url, userName, password, order.getAndIncrement());
    }

    /**
     * {@link RedisDataSource}を生成する。
     *
     * @param identity       {@link DataSourceId}
     * @param dataSourceType {@link DataSourceType}
     * @param host           ホスト名
     * @param port           ポート番号
     * @param databaseNumber データベース番号
     * @param password       パスワード
     * @return {@link RedisDataSource}
     */
    public static RedisDataSource createAsRedis(DataSourceId identity,
                                                DataSourceType dataSourceType,
                                                String host,
                                                Option<Integer> port,
                                                Integer databaseNumber,
                                                String password) {
        return new RedisDataSourceImpl(
                identity,
                dataSourceType,
                host,
                port,
                databaseNumber,
                password,
                order.getAndIncrement()
        );
    }

}
