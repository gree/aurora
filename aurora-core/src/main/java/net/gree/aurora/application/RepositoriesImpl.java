package net.gree.aurora.application;

import net.gree.aurora.domain.datasource.DataSourceRepository;
import net.gree.aurora.domain.shardingconfig.ShardingConfigRepository;

final class RepositoriesImpl implements Repositories {

    private final DataSourceRepository dataSourceRepository;
    private final ShardingConfigRepository shardingConfigRepository;

    RepositoriesImpl(DataSourceRepository dataSourceRepository, ShardingConfigRepository shardingConfigRepository) {
        this.dataSourceRepository = dataSourceRepository;
        this.shardingConfigRepository = shardingConfigRepository;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepositoriesImpl that = (RepositoriesImpl) o;

        if (!dataSourceRepository.equals(that.dataSourceRepository)) return false;
        if (!shardingConfigRepository.equals(that.shardingConfigRepository)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dataSourceRepository.hashCode();
        result = 31 * result + shardingConfigRepository.hashCode();
        return result;
    }

    @Override
    public DataSourceRepository getDataSourceRepository() {
        return dataSourceRepository;
    }

    @Override
    public ShardingConfigRepository getShardingConfigRepository() {
        return shardingConfigRepository;
    }

}
