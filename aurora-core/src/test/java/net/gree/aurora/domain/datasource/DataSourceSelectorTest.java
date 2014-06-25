package net.gree.aurora.domain.datasource;

import org.junit.Test;
import org.sisioh.dddbase.utils.Option;
import org.sisioh.dddbase.utils.Try;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DataSourceSelectorTest {

    @Test
    public void test_データソースをランダムに選択できる() throws Exception {
        final List<UUID> uuids = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        DataSourceRepository repository = DataSourceRepositoryFactory.create();
        for (UUID uuid : uuids) {
            Option<Integer> port = Option.ofNone();
            DataSource dataSource = DataSourceFactory.create(DataSourceIdFactory.create(uuid), DataSourceType.MASTER, "localhost", port);
            repository.store(dataSource);
        }

        DataSourceSelector<Integer> dataSourceSelector = DataSourceSelectorFactory.createAsRandom(repository);
        Try<DataSource> dataSourceTry = dataSourceSelector.selectDataSource(1);

        assertThat(dataSourceTry.isSuccess(), is(true));
        assertThat(dataSourceTry.get().getIdentity().getValue(), anyOf(is(uuids.get(0)), is(uuids.get(1))));
    }

    @Test
    public void test_データソースを選択できる() throws Exception {
        final List<UUID> uuids = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        DataSourceRepository repository = DataSourceRepositoryFactory.create();
        for (UUID uuid : uuids) {
            Option<Integer> port = Option.ofNone();
            DataSource dataSource = DataSourceFactory.create(DataSourceIdFactory.create(uuid), DataSourceType.MASTER, "localhost", port);
            repository.store(dataSource);
        }
        DataSourceIdResolver dataSourceIdResolver = new DataSourceIdResolver<Integer>() {
            @Override
            public DataSourceId apply(Integer hint) {
                return DataSourceIdFactory.create(uuids.get(hint));
            }
        };

        DataSourceSelector<Integer> dataSourceSelector = DataSourceSelectorFactory.create(repository, dataSourceIdResolver);
        Try<DataSource> dataSourceTry = dataSourceSelector.selectDataSource(1);

        assertThat(dataSourceTry.isSuccess(), is(true));
        assertThat(dataSourceTry.get().getIdentity().getValue(), is(uuids.get(1)));
    }

    @Test
    public void test_JDBCデータソースを選択できる() throws Exception {
        final List<UUID> uuids = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        DataSourceRepository repository = DataSourceRepositoryFactory.create();
        for (UUID uuid : uuids) {
            Option<Integer> port = Option.ofNone();
            JDBCDataSource dataSource = DataSourceFactory.createAsJDBC(
                    DataSourceIdFactory.create(uuid),
                    DataSourceType.MASTER,
                    "A",
                    "localhost",
                    port,
                    "url",
                    "userA",
                    "pass"
            );
            repository.store(dataSource);
        }
        DataSourceIdResolver dataSourceIdResolver = new DataSourceIdResolver<Integer>() {
            @Override
            public DataSourceId apply(Integer hint) {
                return DataSourceIdFactory.create(uuids.get(hint));
            }
        };

        DataSourceSelector<Integer> dataSourceSelector = DataSourceSelectorFactory.create(repository, dataSourceIdResolver);
        Try<JDBCDataSource> dataSourceTry = dataSourceSelector.selectDataSourceAsJDBC(1);

        assertThat(dataSourceTry.isSuccess(), is(true));
        assertThat(dataSourceTry.get().getIdentity().getValue(), is(uuids.get(1)));

    }

    @Test
    public void test_Redisデータソースを選択できる() throws Exception {
        final List<UUID> uuids = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        DataSourceRepository repository = DataSourceRepositoryFactory.create();
        for (UUID uuid : uuids) {
            Option<Integer> port = Option.ofNone();
            RedisDataSource dataSource = DataSourceFactory.createAsRedis(
                    DataSourceIdFactory.create(uuid),
                    DataSourceType.MASTER,
                    "localhost",
                    port,
                    0,
                    "pass"
            );
            repository.store(dataSource);
        }
        DataSourceIdResolver dataSourceIdResolver = new DataSourceIdResolver<Integer>() {
            @Override
            public DataSourceId apply(Integer hint) {
                return DataSourceIdFactory.create(uuids.get(hint));
            }
        };

        DataSourceSelector<Integer> dataSourceSelector = DataSourceSelectorFactory.create(repository, dataSourceIdResolver);
        Try<RedisDataSource> dataSourceTry = dataSourceSelector.selectDataSourceAsRedis(1);

        assertThat(dataSourceTry.isSuccess(), is(true));
        assertThat(dataSourceTry.get().getIdentity().getValue(), is(uuids.get(1)));
    }
}
