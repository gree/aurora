package net.gree.aurora.domain.cluster;

import net.gree.aurora.domain.datasource.*;
import org.sisioh.dddbase.model.Entity;
import org.sisioh.dddbase.utils.Try;

import java.util.List;

/**
 * クラスタを表すエンティティ。
 * <p>
 * クラスタとは、マスタと複数のスレーブで構成される{@link DataSource}の集合を意味する。
 * </p>
 */
public interface Cluster extends Entity<ClusterId>, Comparable<Cluster> {

    /**
     * マスターデータソースIDを取得する。
     *
     * @return {@link DataSourceId マスターデータソースID}
     */
    DataSourceId getMasterDataSourceId();

    /**
     * スレーブデータソースIDの集合を取得する。
     *
     * @return {@link DataSourceId}
     */
    List<DataSourceId> getSlaveDataSourceIds();

    /**
     * マスターデータソースを取得する。DataSource版。
     *
     * @param dataSourceRepository {@link DataSourceRepository}
     * @return {@link DataSource マスターデータソース}
     */
    Try<DataSource> getMasterDataSource(DataSourceRepository dataSourceRepository);

    /**
     * マスターデータソースを取得する。JDBCDataSource版。
     *
     * @param dataSourceRepository {@link DataSourceRepository}
     * @return {@link DataSource マスターデータソース}
     */
    Try<JDBCDataSource> getMasterDataSourceAsJDBC(DataSourceRepository dataSourceRepository);

    /**
     * マスターデータソースを取得する。RedisDataSource版。
     *
     * @param dataSourceRepository {@link DataSourceRepository}
     * @return {@link DataSource マスターデータソース}
     */
    Try<RedisDataSource> getMasterDataSourceAsRedis(DataSourceRepository dataSourceRepository);

    /**
     * マスターデータソースを取得する。
     *
     * @param clazz                キャストする{@link DataSource}のクラス
     * @param dataSourceRepository {@link DataSourceRepository}
     * @return {@link DataSource マスターデータソース}
     */
    <T extends DataSource> Try<T> getMasterDataSource(Class<T> clazz, DataSourceRepository dataSourceRepository);

    /**
     * マスターデータソースを取得する。
     *
     * @param dataSourceRepository {@link DataSourceRepository}
     * @return {@link DataSource マスターデータソース}
     */
    Try<List<DataSource>> getSlaveDataSources(DataSourceRepository dataSourceRepository);

    /**
     * マスターデータソースを取得する。JDBCDataSource版。
     *
     * @param dataSourceRepository {@link DataSourceRepository}
     * @return {@link  DataSource マスターデータソース}
     */
    Try<List<JDBCDataSource>> getSlaveDataSourcesAsJDBC(DataSourceRepository dataSourceRepository);

    /**
     * マスターデータソースを取得する。RedisDataSource版。
     *
     * @param dataSourceRepository {@link DataSourceRepository}
     * @return {@link  DataSource マスターデータソース}
     */
    Try<List<RedisDataSource>> getSlaveDataSourcesAsRedis(DataSourceRepository dataSourceRepository);

    /**
     * スレーブデータソースの集合を取得する。
     *
     * @param dataSourceRepository {@link DataSourceRepository}
     * @return
     */
    <T extends DataSource> Try<List<T>> getSlaveDataSources(Class<T> clazz, DataSourceRepository dataSourceRepository);

    /**
     * {@link DataSourceSelector}を生成する。
     *
     * @param dataSourceRepository {@link DataSourceRepository}
     * @return {@link DataSourceSelector}
     */
    DataSourceSelector<Integer> createDataSourceSelectorAsRandom(DataSourceRepository dataSourceRepository);

    /**
     * {@link DataSourceSelector}を生成する。
     *
     * @param dataSourceRepository {@link DataSourceSelector}
     * @param dataSourceIdResolver {@link DataSourceIdResolver}
     * @param <T>
     * @return
     */
    <T> DataSourceSelector<T> createDataSourceSelector(
            DataSourceRepository dataSourceRepository,
            DataSourceIdResolver<T> dataSourceIdResolver
    );

    /**
     * オーダを取得する。
     *
     * @return オーダ
     */
    Long getOrder();

    /**
     * インスタンスの複製を返す。
     *
     * @return {@link Cluster}
     */
    Cluster clone();

}
