package net.gree.aurora.domain.datasource;

import org.apache.commons.lang.Validate;
import org.sisioh.dddbase.utils.Function1;
import org.sisioh.dddbase.utils.Try;

import java.util.ArrayList;
import java.util.List;

final class DataSourceSelectorImpl<T> implements DataSourceSelector<T> {

    private final List<DataSourceId> targetIds;
    private final DataSourceRepository dataSourceRepository;
    private final DataSourceIdResolver<T> dataSourceResolver;

    DataSourceSelectorImpl(
            DataSourceRepository dataSourceRepository,
            DataSourceIdResolver<T> dataSourceResolver
    ) {
        Validate.notNull(dataSourceRepository);
        Validate.notNull(dataSourceResolver);
        this.dataSourceRepository = dataSourceRepository;
        targetIds = new ArrayList<>();
        for (DataSource ds : dataSourceRepository.toList()) {
            targetIds.add(ds.getIdentity());
        }
        this.dataSourceResolver = dataSourceResolver;

    }

    DataSourceSelectorImpl(
            DataSourceRepository dataSourceRepository,
            List<DataSourceId> targetIds,
            DataSourceIdResolver<T> dataSourceResolver
    ) {
        Validate.notNull(dataSourceRepository);
        Validate.notNull(targetIds);
        Validate.notNull(dataSourceResolver);
        this.dataSourceRepository = dataSourceRepository;
        this.targetIds = targetIds;
        this.dataSourceResolver = dataSourceResolver;
    }

    @Override
    public <A extends DataSource> Try<A> selectDataSourceAs(final Class<A> clazz, T hint) {
        DataSourceId dataSourceId = dataSourceResolver.apply(hint);
        if (!targetIds.contains(dataSourceId)) {
            throw new IllegalArgumentException();
        }
        return dataSourceRepository.resolve(dataSourceId).map(new Function1<DataSource, A>() {
            @Override
            public A apply(DataSource value) {
                return clazz.cast(value);
            }
        });
    }

    @Override
    public Try<DataSource> selectDataSource(T hint) {
        return selectDataSourceAs(DataSource.class, hint);
    }

    @Override
    public Try<JDBCDataSource> selectDataSourceAsJDBC(T hint) {
        return selectDataSourceAs(JDBCDataSource.class, hint);
    }

    @Override
    public Try<RedisDataSource> selectDataSourceAsRedis(T hint) {
        return selectDataSourceAs(RedisDataSource.class, hint);
    }

}
