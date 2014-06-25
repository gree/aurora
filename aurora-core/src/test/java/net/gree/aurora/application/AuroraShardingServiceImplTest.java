package net.gree.aurora.application;

import net.gree.aurora.domain.cluster.*;
import net.gree.aurora.domain.clustergroup.ClusterGroupIdFactory;
import net.gree.aurora.domain.datasource.DataSource;
import net.gree.aurora.domain.datasource.DataSourceId;
import net.gree.aurora.domain.datasource.DataSourceSelector;
import net.gree.aurora.domain.hint.Hint;
import net.gree.aurora.domain.hint.HintFactory;
import net.gree.aurora.domain.shardingconfig.ShardingConfigIdFactory;
import org.junit.Test;
import org.sisioh.dddbase.utils.Try;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.assertThat;

public class AuroraShardingServiceImplTest {

    private <T> void assertMasterDataSource(
            AuroraShardingService<T> ass,
            Cluster cluster,
            DataSourceId dataSourceId
    ) {
        DataSource ds1 = ass.resolveDataSourceById(dataSourceId).get();
        assertThat(ds1.getIdentity(), is(dataSourceId));

        DataSource ds2 = cluster.getMasterDataSource(DataSource.class, ass.getDataSourceRepository().get()).get();
        assertThat(ds2.getIdentity(), is(ds1.getIdentity()));
    }

    private <T> void assertSlaveDataSource(
            AuroraShardingService<T> ass,
            Cluster cluster,
            DataSourceId dataSourceId
    ) {
        DataSource ds1 = ass.resolveDataSourceById(dataSourceId).get();
        assertThat(ds1.getIdentity(), is(dataSourceId));
        List<DataSource> dataSources = cluster.getSlaveDataSources(DataSource.class, ass.getDataSourceRepository().get()).get();
        List<DataSourceId> ids = new ArrayList<>();
        for (DataSource ds : dataSources) {
            ids.add(ds.getIdentity());
        }
        assertThat(ds1.getIdentity(), isIn(ids));
    }

    @Test
    public void test_設定ファイルを読み込んだ状態でヒントを与えてClusterを解決する() {
        AbstractClusterIdResolver<Integer> clusterIdResolver = new AbstractClusterIdResolver<Integer>("cluster") {
            @Override
            protected String getSuffixName(Hint<Integer> userIdHint, int clusterSize) {
                return Integer.toString(userIdHint.getValue() % clusterSize);
            }
        };
        final AuroraShardingService<Integer> ass = AuroraShardingServiceFactory.create(clusterIdResolver, new File("./conf/application.conf"));

        int userId = 1;
        Try<Cluster> clusterTry = ass.resolveClusterByHint(
                ShardingConfigIdFactory.create("database"),
                ClusterGroupIdFactory.create("main"),
                HintFactory.create(userId, String.class)
        );

        Cluster cluster = clusterTry.get();

        assertThat(cluster.getIdentity().getValue(), is("cluster1"));

        assertMasterDataSource(ass, cluster, cluster.getMasterDataSourceId());
        for (DataSourceId id : cluster.getSlaveDataSourceIds()) {
            assertSlaveDataSource(ass, cluster, id);
        }

        DataSourceSelector<Integer> selector = cluster.createDataSourceSelectorAsRandom(ass.getDataSourceRepository().get());
        Try<DataSource> dataSourceTry = selector.selectDataSource(null);
        System.out.println(dataSourceTry);

    }


    @Test
    public void test_設定ファイルを読み込んだ状態でヒントを与えてClusterを解決する_タグで検索する() {
        AbstractClusterIdResolver<Integer> clusterIdResolver = new AbstractClusterIdResolver<Integer>("cluster") {
            @Override
            protected String getSuffixName(Hint<Integer> userIdHint, int clusterSize) {
                return Integer.toString(userIdHint.getValue() % clusterSize);
            }
        };

        TaggedClusterIdResolver<Integer> taggedClusterIdResolver =
                TaggedClusterIdResolverFactory.create(TaggedClusterIdResolverIdFactory.create("hoge"), clusterIdResolver);

        Set<TaggedClusterIdResolver<Integer>> set = new HashSet<>();
        set.add(taggedClusterIdResolver);

        final AuroraShardingService<Integer> ass = AuroraShardingServiceFactory.create(set, new File("./conf/application.conf"));

        int userId = 1;
        Try<Cluster> clusterTry = ass.resolveClusterByHint(
                ShardingConfigIdFactory.create("database"),
                ClusterGroupIdFactory.create("main"),
                HintFactory.create(userId, "hoge")
        );

        Cluster cluster = clusterTry.get();

        assertThat(cluster.getIdentity().getValue(), is("cluster1"));

        assertMasterDataSource(ass, cluster, cluster.getMasterDataSourceId());
        for (DataSourceId id : cluster.getSlaveDataSourceIds()) {
            assertSlaveDataSource(ass, cluster, id);
        }

        DataSourceSelector<Integer> selector = cluster.createDataSourceSelectorAsRandom(ass.getDataSourceRepository().get());
        Try<DataSource> dataSourceTry = selector.selectDataSource(null);
        System.out.println(dataSourceTry);

    }


    @Test
    public void test_設定ファイルを読み込んだ状態でヒントを与えてClusterを解決する_複数インスタンス版() {
        AbstractClusterIdResolver<Integer> clusterIdResolver1 = new AbstractClusterIdResolver<Integer>("cluster") {
            @Override
            protected String getSuffixName(Hint<Integer> userIdHint, int clusterSize) {
                return Integer.toString(userIdHint.getValue() % clusterSize);
            }
        };
        AbstractClusterIdResolver<String> clusterIdResolver2 = new AbstractClusterIdResolver<String>("cluster") {
            @Override
            protected String getSuffixName(Hint<String> userIdHint, int clusterSize) {
                return Integer.toString(Integer.valueOf(userIdHint.getValue()) % clusterSize);
            }
        };

        AuroraShardingConfigLoadService ascls = AuroraShardingConfigLoadServiceFactory.create();
        Repositories repositories = ascls.loadFromConfigFile(new File("./conf/application.conf")).get();

        final AuroraShardingService<Integer> ass1 = AuroraShardingServiceFactory.create(
                clusterIdResolver1, repositories
        );
        final AuroraShardingService<String> ass2 = AuroraShardingServiceFactory.create(
                clusterIdResolver2, repositories
        );

        int userId1 = 1;
        Cluster cluster1 = ass1.resolveClusterByHint(
                ShardingConfigIdFactory.create("database"),
                ClusterGroupIdFactory.create("main"),
                HintFactory.create(userId1)
        ).get();

        assertThat(cluster1.getIdentity().getValue(), is("cluster1"));
        assertMasterDataSource(ass1, cluster1, cluster1.getMasterDataSourceId());
        for (DataSourceId id : cluster1.getSlaveDataSourceIds()) {
            assertSlaveDataSource(ass1, cluster1, id);
        }

        DataSourceSelector<Integer> selector = cluster1.createDataSourceSelectorAsRandom(ass1.getDataSourceRepository().get());
        Try<DataSource> dataSourceTry = selector.selectDataSource(null);
        System.out.println(dataSourceTry);

        String userId2 = "1";
        Cluster cluster2 = ass2.resolveClusterByHint(
                ShardingConfigIdFactory.create("database"),
                ClusterGroupIdFactory.create("main"),
                HintFactory.create(userId2)
        ).get();

        assertThat(cluster1.getIdentity().getValue(), is("cluster1"));
        assertMasterDataSource(ass2, cluster2, cluster2.getMasterDataSourceId());
        for (DataSourceId id : cluster2.getSlaveDataSourceIds()) {
            assertSlaveDataSource(ass2, cluster2, id);
        }
    }


    @Test
    public void test_設定ファイルを読み込んだ状態でヒントなしでClusterを解決する() {
        final AuroraShardingService<Integer> ass = AuroraShardingServiceFactory.create(new AbstractClusterIdResolver<Integer>("cluster") {
            @Override
            protected String getSuffixName(Hint<Integer> userIdHint, int clusterSize) {
                return Integer.toString(userIdHint.getValue() % clusterSize);
            }
        }, new File("./conf/application.conf"));

        Try<Cluster> clusterTry = ass.resolveCluster(
                ShardingConfigIdFactory.create("redis"),
                ClusterGroupIdFactory.create("main")
        );

        Cluster cluster = clusterTry.get();

        assertThat(cluster.getIdentity().getValue(), is("cluster1"));

        assertMasterDataSource(ass, cluster, cluster.getMasterDataSourceId());
        for (DataSourceId id : cluster.getSlaveDataSourceIds()) {
            assertSlaveDataSource(ass, cluster, id);
        }

        DataSourceSelector<Integer> selector = cluster.createDataSourceSelectorAsRandom(ass.getDataSourceRepository().get());
        Try<DataSource> dataSourceTry = selector.selectDataSource(null);
        System.out.println(dataSourceTry);

    }

}
