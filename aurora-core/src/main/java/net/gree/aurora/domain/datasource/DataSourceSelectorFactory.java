package net.gree.aurora.domain.datasource;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link DataSOurceSelector}のためのファクトリ。
 */
public final class DataSourceSelectorFactory {

    private DataSourceSelectorFactory() {

    }

    /**
     * {@link DataSourceSelector}を生成する。
     *
     * @param dataSourceRepository {@link net.gree.aurora.domain.datasource.DataSourceRepository}
     * @param dataSourceResolver   {@link DataSourceResolver}
     * @param targetIds            想定する{@link net.gree.aurora.domain.datasource.DataSourceId}のリスト
     * @return {@link DataSourceSelector}
     */
    public static <T> DataSourceSelector<T> create(
            DataSourceRepository dataSourceRepository,
            DataSourceIdResolver<T> dataSourceResolver, List<DataSourceId> targetIds
    ) {
        return new DataSourceSelectorImpl<>(dataSourceRepository, targetIds, dataSourceResolver);
    }

    /**
     * {@link DataSourceSelector}を生成する。
     *
     * @param dataSourceRepository {@link DataSourceRepository}
     * @param dataSourceResolver   {@link DataSourceResolver}
     * @param <T>                  ヒントの型
     * @return {@link DataSourceSelector}
     */
    public static <T> DataSourceSelector<T> create(
            DataSourceRepository dataSourceRepository,
            DataSourceIdResolver<T> dataSourceResolver
    ) {
        return new DataSourceSelectorImpl<>(dataSourceRepository, dataSourceResolver);
    }

    /**
     * 乱数に基づく{@link DataSourceSelector}を生成する。
     *
     * @param dataSourceRepository {@link DataSourceRepository}
     * @return {@link DataSourceSelector}
     */
    public static DataSourceSelector<Integer> createAsRandom(DataSourceRepository dataSourceRepository) {
        List<DataSource> dataSources = dataSourceRepository.toList();
        List<DataSourceId> dataSourceIds = new ArrayList<>();
        for (DataSource dataSource : dataSources) {
            dataSourceIds.add(dataSource.getIdentity());
        }
        return createAsRandom(dataSourceRepository, dataSourceIds);
    }

    /**
     * 乱数に基づく{@link DataSourceSelector}を生成する。
     *
     * @param dataSourceRepository {@link DataSourceRepository}
     * @param targetIds            想定する{@link net.gree.aurora.domain.datasource.DataSourceId}のリスト
     * @return {@link DataSourceSelector}
     */
    public static DataSourceSelector<Integer> createAsRandom(DataSourceRepository dataSourceRepository, List<DataSourceId> targetIds) {
        return create(dataSourceRepository, new DataSourceIdRandomResolver(targetIds), targetIds);
    }

}
