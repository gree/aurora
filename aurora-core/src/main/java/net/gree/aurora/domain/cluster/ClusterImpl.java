package net.gree.aurora.domain.cluster;

import net.gree.aurora.domain.datasource.*;
import org.apache.commons.lang.Validate;
import org.sisioh.dddbase.model.impl.AbstractEntity;
import org.sisioh.dddbase.utils.Function1;
import org.sisioh.dddbase.utils.Try;

import java.util.ArrayList;
import java.util.List;

final class ClusterImpl extends AbstractEntity<ClusterId> implements Cluster {

    private final DataSourceId masterDataSourceId;
    private final List<DataSourceId> slaveDataSourceIds;
    private final Long order;

    ClusterImpl(ClusterId identity,
                DataSourceId masterDataSourceId,
                List<DataSourceId> slaveDataSourceIds,
                Long order) {
        super(identity);
        Validate.notNull(masterDataSourceId);
        Validate.notNull(slaveDataSourceIds);
        Validate.notNull(order);
        this.masterDataSourceId = masterDataSourceId;
        this.slaveDataSourceIds = slaveDataSourceIds;
        this.order = order;
    }

    @Override
    public DataSourceId getMasterDataSourceId() {
        return masterDataSourceId;
    }

    @Override
    public <T extends DataSource> Try<T> getMasterDataSource(final Class<T> clazz, DataSourceRepository repository) {
        Validate.notNull(clazz);
        Validate.notNull(repository);
        return repository.resolve(masterDataSourceId).map(new Function1<DataSource, T>() {
            @Override
            public T apply(DataSource value) {
                return clazz.cast(value);
            }
        });
    }

    @Override
    public Try<List<DataSource>> getSlaveDataSources(DataSourceRepository dataSourceRepository) {
        return getSlaveDataSources(DataSource.class, dataSourceRepository);
    }

    @Override
    public Try<List<JDBCDataSource>> getSlaveDataSourcesAsJDBC(DataSourceRepository dataSourceRepository) {
        return getSlaveDataSources(JDBCDataSource.class, dataSourceRepository);
    }

    @Override
    public Try<List<RedisDataSource>> getSlaveDataSourcesAsRedis(DataSourceRepository dataSourceRepository) {
        return getSlaveDataSources(RedisDataSource.class, dataSourceRepository);
    }

    @Override
    public List<DataSourceId> getSlaveDataSourceIds() {
        return slaveDataSourceIds;
    }

    @Override
    public Try<DataSource> getMasterDataSource(DataSourceRepository dataSourceRepository) {
        return getMasterDataSource(DataSource.class, dataSourceRepository);
    }

    @Override
    public Try<JDBCDataSource> getMasterDataSourceAsJDBC(DataSourceRepository dataSourceRepository) {
        return getMasterDataSource(JDBCDataSource.class, dataSourceRepository);
    }

    @Override
    public Try<RedisDataSource> getMasterDataSourceAsRedis(DataSourceRepository dataSourceRepository) {
        return getMasterDataSource(RedisDataSource.class, dataSourceRepository);
    }

    @Override
    public <T> DataSourceSelector<T> createDataSourceSelector(
            DataSourceRepository dataSourceRepository,
            DataSourceIdResolver<T> dataSourceIdResolver
    ) {
        return DataSourceSelectorFactory.create(dataSourceRepository, dataSourceIdResolver, slaveDataSourceIds);
    }

    @Override
    public DataSourceSelector<Integer> createDataSourceSelectorAsRandom(DataSourceRepository dataSourceRepository) {
        return DataSourceSelectorFactory.createAsRandom(dataSourceRepository, slaveDataSourceIds);
    }

    @Override
    public <T extends DataSource> Try<List<T>> getSlaveDataSources(final Class<T> clazz, DataSourceRepository repository) {
        Validate.notNull(clazz);
        Validate.notNull(repository);
        try {
            List<T> dataSources = new ArrayList<>();
            for (DataSourceId id : slaveDataSourceIds) {
                Try<DataSource> resolve = repository.resolve(id);
                if (resolve.isFailure()) {
                    return Try.ofFailure(resolve.getCause());
                }
                dataSources.add(clazz.cast(resolve.get()));
            }
            return Try.ofSuccess(dataSources);
        } catch (RuntimeException e) {
            return Try.ofFailure(e);
        }
    }

    @Override
    public Long getOrder() {
        return order;
    }

    @Override
    public int compareTo(Cluster o) {
        return order.compareTo(o.getOrder());
    }

    @Override
    public ClusterImpl clone() {
        return (ClusterImpl) super.clone();
    }

    @Override
    public String toString() {
        return String.format(
                "Cluster(identity = %s, masterDataSourceId = %s, slaveDataSourceIds = %s)",
                getIdentity(), masterDataSourceId, slaveDataSourceIds
        );
    }

}
