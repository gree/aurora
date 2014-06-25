package net.gree.aurora;

import net.gree.aurora.application.AuroraShardingService;
import net.gree.aurora.application.AuroraShardingServiceFactory;
import net.gree.aurora.domain.cluster.AbstractClusterIdResolver;
import net.gree.aurora.domain.cluster.Cluster;
import net.gree.aurora.domain.cluster.ClusterIdResolver;
import net.gree.aurora.domain.datasource.DataSourceRepository;
import net.gree.aurora.domain.datasource.DataSourceSelector;
import net.gree.aurora.domain.datasource.JDBCDataSource;
import net.gree.aurora.domain.hint.Hint;
import net.gree.aurora.domain.hint.HintFactory;
import org.sisioh.dddbase.core.lifecycle.EntityNotFoundException;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Main {

    private static class User {
        private final int id;
        private final String name;

        User(int id, String name){
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    // リゾルバを定義する
    private final ClusterIdResolver<Integer> clusterIdResolver = new AbstractClusterIdResolver<Integer>("cluster") {

        @Override
        protected String getSuffixName(Hint<Integer> userIdHint, int clusterSize) {
            return Integer.toString(((userIdHint.getValue() +1) % clusterSize) + 1);
        }
    };

    // AuroraShardingServiceを初期化する
    private final AuroraShardingService<Integer> auroraShardingService = AuroraShardingServiceFactory.create(clusterIdResolver, new File("./conf/example.conf"));

    private final DataSourceRepository dataSourceRepository = auroraShardingService.getDataSourceRepository().get();
    private User findByUserId(int userId) throws SQLException, ClassNotFoundException, IOException {
        // sharding-config-idとcluster-group-idとヒントを引数に与えてclusterを解決する
        Cluster cluster = auroraShardingService.resolveClusterByHint("database", "main", HintFactory.create(userId)).get();

        // スレーブはランダム選択
        DataSourceSelector<Integer> selector = cluster.createDataSourceSelectorAsRandom(dataSourceRepository);
        // スレーブデータソースを選択する
        JDBCDataSource dataSource = selector.selectDataSourceAsJDBC(null).get();
        // 書き込み用途でマスターを利用する場合は、以下。
        // JDBCDataSource dataSource = cluster.masterDataSource(dataSourceRepository).get();

        System.out.println(dataSource);

        // データソースからコネクションを取得する
        Connection connection = getConnection(dataSource);

        // コネクションを使ってSQLを発行する
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM user WHERE id = " + userId);
        if (rs.next()) {
            return convertToModel(rs);
        }else {
            throw new IOException("entity is not found.");
        }
    }

    private Connection getConnection(JDBCDataSource dataSource) throws ClassNotFoundException, SQLException {
        Class.forName(dataSource.getDriverClassName());
        return DriverManager.getConnection(dataSource.getUrl(), dataSource.getUserName(), dataSource.getPassword());
    }

    private User convertToModel(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt("id"), resultSet.getString("name"));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        User user = new Main().findByUserId(1);
        System.out.println(user);
    }

}
