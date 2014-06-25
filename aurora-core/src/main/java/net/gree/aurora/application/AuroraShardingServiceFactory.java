package net.gree.aurora.application;

import com.typesafe.config.Config;
import net.gree.aurora.domain.cluster.ClusterIdResolver;
import net.gree.aurora.domain.cluster.TaggedClusterIdResolver;
import net.gree.aurora.domain.datasource.DataSourceRepository;
import net.gree.aurora.domain.shardingconfig.ShardingConfigRepository;

import java.io.File;
import java.util.Set;

/**
 * {@link AuroraShardingService}のためのファクトリ。
 */
public final class AuroraShardingServiceFactory {

    /**
     * {@link AuroraShardingService}を生成する。
     *
     * @param clusterIdResolver {@link ClusterIdResolver}
     * @param configFile        {@link File}
     * @param <T>               ヒントの型
     * @return {@link AuroraShardingService}
     */
    public static <T> AuroraShardingService<T> create(ClusterIdResolver<T> clusterIdResolver, File configFile) {
        return new AuroraShardingServiceImpl<>(clusterIdResolver, configFile);
    }

    /**
     * {@link AuroraShardingService}を生成する。
     *
     * @param clusterIdResolver {@link ClusterIdResolver}
     * @param config            {@link Config}
     * @param <T>               ヒントの型
     * @return {@link AuroraShardingService}
     */
    public static <T> AuroraShardingService<T> create(ClusterIdResolver<T> clusterIdResolver, Config config) {
        return new AuroraShardingServiceImpl<>(clusterIdResolver, config);
    }

    /**
     * {@link AuroraShardingService}を生成する。
     *
     * @param clusterIdResolver        {@link ClusterIdResolver}
     * @param shardingConfigRepository {@link ShardingConfigRepository}
     * @param dataSourceRepository     {@link DataSourceRepository}
     * @param <T>                      ヒントの型
     * @return {@link AuroraShardingService}
     */
    public static <T> AuroraShardingService<T> create(
            ClusterIdResolver<T> clusterIdResolver,
            ShardingConfigRepository shardingConfigRepository,
            DataSourceRepository dataSourceRepository) {
        return new AuroraShardingServiceImpl<>(clusterIdResolver, shardingConfigRepository, dataSourceRepository);
    }

    /**
     * {@link AuroraShardingService}を生成する。
     *
     * @param clusterIdResolver {@link ClusterIdResolver}
     * @param repositories      {@link Repositories}
     * @param <T>               ヒントの型
     * @return {@link AuroraShardingService}
     */
    public static <T> AuroraShardingService<T> create(
            ClusterIdResolver<T> clusterIdResolver,
            Repositories repositories
    ) {
        return create(clusterIdResolver, repositories.getShardingConfigRepository(), repositories.getDataSourceRepository());
    }

    /**
     * {@link AuroraShardingService}を生成する。
     *
     * @param taggedClusterIdResolvers {@link TaggedClusterIdResolver}
     * @param configFile               設定ファイル
     * @param <T>                      ヒントの型
     * @return {@link AuroraShardingService}
     */
    public static <T> AuroraShardingService<T> create(
            Set<TaggedClusterIdResolver<T>> taggedClusterIdResolvers,
            File configFile
    ) {
        return new AuroraShardingServiceImpl<>(taggedClusterIdResolvers, configFile);
    }

    /**
     * {@link AuroraShardingService}を生成する。
     *
     * @param taggedClusterIdResolvers {@link TaggedClusterIdResolver}
     * @param config                   設定ファイル
     * @param <T>                      ヒントの型
     * @return {@link AuroraShardingService}
     */
    public static <T> AuroraShardingService<T> create(
            Set<TaggedClusterIdResolver<T>> taggedClusterIdResolvers,
            Config config
    ) {
        return new AuroraShardingServiceImpl<>(taggedClusterIdResolvers, config);
    }
}
