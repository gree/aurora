package net.gree.aurora.domain.cluster;

import net.gree.aurora.domain.datasource.*;
import org.junit.Test;
import org.sisioh.dddbase.utils.Option;
import org.sisioh.dddbase.utils.Try;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ClusterTest {

    DataSourceRepository dataSourceRepository = DataSourceRepositoryFactory.create();

    private Cluster createCluster(String clusterId, UUID masterDataSourceIdValue, List<UUID> slaveDataSourceIdValues) {
        ClusterId id = ClusterIdFactory.create(clusterId);
        DataSourceId masterDataSourceId = DataSourceIdFactory.create(masterDataSourceIdValue);
        DataSource masterDataSource = DataSourceFactory.create(masterDataSourceId, DataSourceType.MASTER, "localhost", Option.ofSome(1234));
        dataSourceRepository.store(masterDataSource);

        List<DataSourceId> slaveDataSourceIds = new ArrayList<>();
        for (UUID uuid : slaveDataSourceIdValues) {
            DataSourceId slaveDataSourceId = DataSourceIdFactory.create(uuid);
            DataSource slaveDataSource = DataSourceFactory.create(slaveDataSourceId, DataSourceType.SLAVE, "localhost", Option.ofSome(1234));
            dataSourceRepository.store(slaveDataSource);
            slaveDataSourceIds.add(slaveDataSourceId);
        }
        return ClusterFactory.create(id, masterDataSourceId, slaveDataSourceIds);
    }

    private Cluster createClusterWithJDBCDataSource(String clusterId, UUID masterDataSourceIdValue, List<UUID> slaveDataSourceIdValues) {
        ClusterId id = ClusterIdFactory.create(clusterId);
        DataSourceId masterDataSourceId = DataSourceIdFactory.create(masterDataSourceIdValue);

        JDBCDataSource masterDataSource = DataSourceFactory.createAsJDBC(
                masterDataSourceId,
                DataSourceType.MASTER,
                "com.mysql.Driver",
                "localhost",
                Option.ofSome(1234),
                "jdbc:mysql://",
                "user1",
                "user1pass"
        );

        dataSourceRepository.store(masterDataSource);

        List<DataSourceId> slaveDataSourceIds = new ArrayList<>();
        for (UUID uuid : slaveDataSourceIdValues) {
            DataSourceId slaveDataSourceId = DataSourceIdFactory.create(uuid);
            JDBCDataSource slaveDataSource = DataSourceFactory.createAsJDBC(
                    slaveDataSourceId,
                    DataSourceType.SLAVE,
                    "com.mysql.Driver",
                    "localhost",
                    Option.ofSome(1234),
                    "jdbc:mysql://",
                    "user1",
                    "user1pass"
            );
            dataSourceRepository.store(slaveDataSource);
            slaveDataSourceIds.add(slaveDataSourceId);
        }
        return ClusterFactory.create(id, masterDataSourceId, slaveDataSourceIds);
    }

    private Cluster createClusterWithRedisDataSource(String clusterId, UUID masterDataSourceIdValue, List<UUID> slaveDataSourceIdValues) {
        ClusterId id = ClusterIdFactory.create(clusterId);
        DataSourceId masterDataSourceId = DataSourceIdFactory.create(masterDataSourceIdValue);

        RedisDataSource masterDataSource = DataSourceFactory.createAsRedis(
                masterDataSourceId,
                DataSourceType.MASTER,
                "localhost",
                Option.ofSome(1234),
                0,
                "user1pass"
        );

        dataSourceRepository.store(masterDataSource);

        List<DataSourceId> slaveDataSourceIds = new ArrayList<>();
        for (UUID uuid : slaveDataSourceIdValues) {
            DataSourceId slaveDataSourceId = DataSourceIdFactory.create(uuid);
            RedisDataSource slaveDataSource = DataSourceFactory.createAsRedis(
                    slaveDataSourceId,
                    DataSourceType.SLAVE,
                    "localhost",
                    Option.ofSome(1234),
                    0,
                    "user1pass"
            );
            dataSourceRepository.store(slaveDataSource);
            slaveDataSourceIds.add(slaveDataSourceId);
        }
        return ClusterFactory.create(id, masterDataSourceId, slaveDataSourceIds);
    }

    @Test
    public void test_文字列表現が取得できる() {
        UUID masterDataSourceIdValue = UUID.randomUUID();
        UUID slaveDataSourceIdValue = UUID.randomUUID();

        Cluster cluster = createCluster("test1", masterDataSourceIdValue, Arrays.asList(slaveDataSourceIdValue));

        assertThat(cluster.toString(), is(String.format("Cluster(identity = ClusterId(value = test1), masterDataSourceId = DataSourceId(value = %s), slaveDataSourceIds = [DataSourceId(value = %s)])", masterDataSourceIdValue, slaveDataSourceIdValue)));
    }


    @Test
    public void test_識別子が取得できる() {
        UUID masterDataSourceIdValue = UUID.randomUUID();
        UUID slaveDataSourceIdValue = UUID.randomUUID();

        Cluster cluster = createCluster("test1", masterDataSourceIdValue, Arrays.asList(slaveDataSourceIdValue));

        assertThat(cluster.getIdentity().getValue(), is("test1"));
    }

    @Test
    public void test_マスターデータソースの識別子が取得できる() {
        UUID masterDataSourceIdValue = UUID.randomUUID();
        UUID slaveDataSourceIdValue = UUID.randomUUID();

        Cluster cluster = createCluster("test1", masterDataSourceIdValue, Arrays.asList(slaveDataSourceIdValue));

        assertThat(cluster.getMasterDataSourceId().getValue(), is(masterDataSourceIdValue));
    }

    @Test
    public void test_スレーブデータソースの識別子が取得できる() {
        UUID masterDataSourceIdValue = UUID.randomUUID();
        UUID slaveDataSourceIdValue = UUID.randomUUID();

        Cluster cluster = createCluster("test1", masterDataSourceIdValue, Arrays.asList(slaveDataSourceIdValue));

        assertThat(cluster.getSlaveDataSourceIds().get(0).getValue(), is(slaveDataSourceIdValue));
    }

    @Test
    public void test_マスターデータソースを取得できる() {
        UUID masterDataSourceIdValue = UUID.randomUUID();
        UUID slaveDataSourceIdValue = UUID.randomUUID();

        Cluster cluster = createCluster("test1", masterDataSourceIdValue, Arrays.asList(slaveDataSourceIdValue));

        Try<DataSource> masterDataSource = cluster.getMasterDataSource(dataSourceRepository);

        assertThat(masterDataSource.isSuccess(), is(true));
        assertThat(masterDataSource.get().getIdentity().getValue(), is(masterDataSourceIdValue));
    }

    @Test
    public void test_マスターデータソースを取得できない_JDBC() {
        UUID masterDataSourceIdValue = UUID.randomUUID();
        UUID slaveDataSourceIdValue = UUID.randomUUID();

        Cluster cluster = createClusterWithRedisDataSource("test1", masterDataSourceIdValue, Arrays.asList(slaveDataSourceIdValue));

        Try<JDBCDataSource> masterDataSource = cluster.getMasterDataSourceAsJDBC(dataSourceRepository);

        assertThat(masterDataSource.isSuccess(), is(false));
        //assertThat(masterDataSource.getCause(), is(ClassCastException.class));
    }

    @Test
    public void test_マスターデータソースを取得できる_JDBC() {
        UUID masterDataSourceIdValue = UUID.randomUUID();
        UUID slaveDataSourceIdValue = UUID.randomUUID();

        Cluster cluster = createClusterWithJDBCDataSource("test1", masterDataSourceIdValue, Arrays.asList(slaveDataSourceIdValue));

        Try<JDBCDataSource> masterDataSource = cluster.getMasterDataSourceAsJDBC(dataSourceRepository);

        assertThat(masterDataSource.isSuccess(), is(true));
        assertThat(masterDataSource.get().getIdentity().getValue(), is(masterDataSourceIdValue));
    }

    @Test
    public void test_マスターデータソースを取得できる_Redis() {
        UUID masterDataSourceIdValue = UUID.randomUUID();
        UUID slaveDataSourceIdValue = UUID.randomUUID();

        Cluster cluster = createClusterWithRedisDataSource("test1", masterDataSourceIdValue, Arrays.asList(slaveDataSourceIdValue));

        Try<RedisDataSource> masterDataSource = cluster.getMasterDataSourceAsRedis(dataSourceRepository);

        assertThat(masterDataSource.isSuccess(), is(true));
        assertThat(masterDataSource.get().getIdentity().getValue(), is(masterDataSourceIdValue));
    }


    @Test
    public void test_スレーブデータソースを取得できる() {
        UUID masterDataSourceIdValue = UUID.randomUUID();
        UUID slaveDataSourceIdValue = UUID.randomUUID();

        Cluster cluster = createCluster("test1", masterDataSourceIdValue, Arrays.asList(slaveDataSourceIdValue));

        Try<List<DataSource>> slaveDataSources = cluster.getSlaveDataSources(dataSourceRepository);

        assertThat(slaveDataSources.isSuccess(), is(true));
        assertThat(slaveDataSources.get().get(0).getIdentity().getValue(), is(slaveDataSourceIdValue));
    }

    @Test
    public void test_スレーブデータソースを取得できない_JDBC() {
        UUID masterDataSourceIdValue = UUID.randomUUID();
        UUID slaveDataSourceIdValue = UUID.randomUUID();

        Cluster cluster = createClusterWithRedisDataSource("test1", masterDataSourceIdValue, Arrays.asList(slaveDataSourceIdValue));

        Try<List<JDBCDataSource>> slaveDataSources = cluster.getSlaveDataSourcesAsJDBC(dataSourceRepository);

        assertThat(slaveDataSources.isSuccess(), is(false));
        //assertThat(slaveDataSources.getCause(), is(ClassCastException.class));
    }

    @Test
    public void test_スレーブデータソースを取得できる_JDBC() {
        UUID masterDataSourceIdValue = UUID.randomUUID();
        UUID slaveDataSourceIdValue = UUID.randomUUID();

        Cluster cluster = createClusterWithJDBCDataSource("test1", masterDataSourceIdValue, Arrays.asList(slaveDataSourceIdValue));

        Try<List<JDBCDataSource>> slaveDataSources = cluster.getSlaveDataSourcesAsJDBC(dataSourceRepository);

        assertThat(slaveDataSources.isSuccess(), is(true));
        assertThat(slaveDataSources.get().get(0).getIdentity().getValue(), is(slaveDataSourceIdValue));
    }

    @Test
    public void test_スレーブデータソースを取得できる_Redis() {
        UUID masterDataSourceIdValue = UUID.randomUUID();
        UUID slaveDataSourceIdValue = UUID.randomUUID();

        Cluster cluster = createClusterWithRedisDataSource("test1", masterDataSourceIdValue, Arrays.asList(slaveDataSourceIdValue));

        Try<List<RedisDataSource>> slaveDataSources = cluster.getSlaveDataSourcesAsRedis(dataSourceRepository);

        assertThat(slaveDataSources.isSuccess(), is(true));
        assertThat(slaveDataSources.get().get(0).getIdentity().getValue(), is(slaveDataSourceIdValue));
    }

    @Test
    public void test_ランダムなDataSourceSelectorを生成できること() {
        UUID masterDataSourceIdValue = UUID.randomUUID();
        UUID slaveDataSourceIdValue = UUID.randomUUID();

        Cluster cluster = createCluster("test1", masterDataSourceIdValue, Arrays.asList(slaveDataSourceIdValue));

        DataSourceSelector<Integer> selector = cluster.createDataSourceSelectorAsRandom(dataSourceRepository);

        assertThat(selector, is(notNullValue()));
    }

    @Test
    public void test_DataSourceSelectorを生成できること() {
        UUID masterDataSourceIdValue = UUID.randomUUID();
        final UUID slaveDataSourceIdValue = UUID.randomUUID();

        Cluster cluster = createCluster("test1", masterDataSourceIdValue, Arrays.asList(slaveDataSourceIdValue));

        final DataSourceSelector<Integer> selector = cluster.createDataSourceSelector(dataSourceRepository, new DataSourceIdResolver<Integer>() {
            @Override
            public DataSourceId apply(Integer hint) {
                return DataSourceIdFactory.create(slaveDataSourceIdValue);
            }
        });

        assertThat(selector, is(notNullValue()));
    }

}
