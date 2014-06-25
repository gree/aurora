package net.gree.aurora.application.config;

import net.gree.aurora.application.config.clusterconfig.AuroraClusterConfigFactories;
import net.gree.aurora.application.config.clustergroupconfig.AuroraClusterGroupConfig;
import net.gree.aurora.application.config.clustergroupconfig.AuroraClusterGroupConfigFactories;
import net.gree.aurora.application.config.defaultconfig.AuroraDefaultConfig;
import net.gree.aurora.application.config.defaultconfig.AuroraDefaultConfigFactories;
import net.gree.aurora.application.config.clusterconfig.AuroraClusterConfig;
import net.gree.aurora.application.config.clusterconfig.AuroraClusterJDBCConfig;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AuroraConfigLoaderImplTest {

    public List<AuroraClusterConfig> getShardsInSub() {
        List<AuroraClusterConfig> shards = new ArrayList<>();

        {
            String id = "cluster1";
            String masterHost = "192.168.3.2";
            List<String> slaveHosts = Arrays.asList("192.168.3.3", "192.168.3.4");
            String standByHost = "192.168.3.254";
            String driverClassName = null;
            String databaseName = "test_a";
            String userName = null;
            String password = null;

            AuroraClusterJDBCConfig cluster1 = AuroraClusterConfigFactories.createForJDBC(
                    id,
                    masterHost,
                    slaveHosts,
                    standByHost,
                    driverClassName,
                    databaseName,
                    userName,
                    password
            );

            shards.add(cluster1);
        }
        {
            String id = "cluster2";
            String masterHost = "192.168.4.2";
            List<String> slaveHosts = Arrays.asList("192.168.4.3", "192.168.4.4");
            String standByHost = "192.168.4.254";
            String driverClassName = null;
            String databaseName = "test_b";
            String userName = null;
            String password = null;

            AuroraClusterJDBCConfig cluster2 = AuroraClusterConfigFactories.createForJDBC(
                    id,
                    masterHost,
                    slaveHosts,
                    standByHost,
                    driverClassName,
                    databaseName,
                    userName,
                    password
            );

            shards.add(cluster2);
        }


        return shards;
    }

    public List<AuroraClusterConfig> getShardsInMain() {
        List<AuroraClusterConfig> clusters = new ArrayList<>();

        {
            String id = "cluster1";
            String masterHost = "192.168.1.2";
            List<String> slaveHosts = Arrays.asList("192.168.1.3", "192.168.1.4");
            String standByHost = "192.168.1.254";
            String driverClassName = null;
            String databaseName = "test_a";
            String userName = null;
            String password = null;

            AuroraClusterJDBCConfig cluster1 = AuroraClusterConfigFactories.createForJDBC(
                    id,
                    masterHost,
                    slaveHosts,
                    standByHost,
                    driverClassName,
                    databaseName,
                    userName,
                    password
            );

            clusters.add(cluster1);
        }
        {
            String id = "cluster2";
            String masterHost = "192.168.2.2";
            List<String> slaveHosts = Arrays.asList("192.168.2.3", "192.168.2.4");
            String standByHost = "192.168.2.254";
            String driverClassName = null;
            String databaseName = "test_b";
            String userName = null;
            String password = null;

            AuroraClusterJDBCConfig cluster2 = AuroraClusterConfigFactories.createForJDBC(
                    id,
                    masterHost,
                    slaveHosts,
                    standByHost,
                    driverClassName,
                    databaseName,
                    userName,
                    password
            );

            clusters.add(cluster2);
        }


        return clusters;
    }

    @Test
    public void test_() {
        AuroraShardingConfigLoaderImpl auroraConfigLoader = new AuroraShardingConfigLoaderImpl();
        Set<AuroraShardingConfig> configs = auroraConfigLoader.load(new File("./conf/application.json"));

        {
            String driverClassName = "org.mysql.Driver";
            String prefixUrl = "jdbc:mysql://";
            String masterHost = null;
            String slaveHost = null;
            Integer port = 2456;
            String userName = "user1";
            String password = "user1pass";
            String readOnlyUserName = "user2";
            String readOnlyPassword = "user2pass";

            AuroraDefaultConfig defaultConfig = AuroraDefaultConfigFactories.createAuroraDefaultJDBCConfig(
                    driverClassName,
                    prefixUrl,
                    masterHost,
                    slaveHost,
                    port,
                    userName,
                    password,
                    readOnlyUserName,
                    readOnlyPassword
            );

            Set<AuroraClusterGroupConfig> clusterGroups = new HashSet<>();
            AuroraClusterGroupConfig clusterGroupInMain = AuroraClusterGroupConfigFactories.create("main", getShardsInMain());
            AuroraClusterGroupConfig clusterGroupInSub = AuroraClusterGroupConfigFactories.create("sub", getShardsInSub());
            clusterGroups.add(clusterGroupInMain);
            clusterGroups.add(clusterGroupInSub);

            AuroraShardingConfig expectedDatabase = AuroraShardingConfigFactories.create(
                    "database",
                    AuroraShardingConfigType.JDBC_TYPE,
                    defaultConfig,
                    clusterGroups
            );

            for (AuroraShardingConfig config : configs) {
                if (config.getId().equals("database")) {
                    String actualId = config.getId();
                    String expectedId = expectedDatabase.getId();
                    assertThat(actualId, is(expectedId));
                    AuroraDefaultConfig actualDefaultConfig = config.getDefaultConfig();
                    AuroraDefaultConfig expectedDefaultConfig = expectedDatabase.getDefaultConfig();
                    assertThat(actualDefaultConfig, is(expectedDefaultConfig));
                    AuroraClusterGroupConfig actualClusterGroups = config.getClusterGroupsAsMap().get("main");
                    AuroraClusterGroupConfig expectedClusterGroups = expectedDatabase.getClusterGroupsAsMap().get("main");
                    assertThat(actualClusterGroups, is(expectedClusterGroups));
                    assertThat(config, is(expectedDatabase));
                    return;
                }
            }
        }
    }
}
