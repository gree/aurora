package net.gree.aurora.scala.domain.cluster

import java.util.UUID
import net.gree.aurora.domain.datasource.DataSourceType
import net.gree.aurora.scala.domain.datasource._
import org.sisioh.dddbase.core.lifecycle.sync.SyncEntityIOContext
import org.specs2.mutable.Specification
import org.specs2.specification.Scope


class ClusterSpec extends Specification {

  private def withDataSources(size: Int) = {
    val masterDataSourceId = DataSourceId(UUID.randomUUID())
    val masterDataSource = DataSource(masterDataSourceId, DataSourceType.MASTER, "master_localhost", None)
    val slaveDataSources = for {
      i <- 1 to size
    } yield {
      val slaveDataSourceId = DataSourceId(UUID.randomUUID())
      val slaveDataSource = DataSource(slaveDataSourceId, DataSourceType.SLAVE, "slave_localhost", Some(i))
      slaveDataSource
    }
    (masterDataSource, slaveDataSources)
  }

  private def withDataSourcesAsJDBC(size: Int): (DataSource, Seq[DataSource]) = {
    val masterDataSourceId = DataSourceId(UUID.randomUUID())
    val masterDataSource = JDBCDataSource(
      identity = masterDataSourceId,
      dataSourceType = DataSourceType.MASTER,
      driverClassName = "",
      host = "master_host",
      port = Some(10),
      url = "hoehoge",
      userName = "",
      password = ""
    )
    val slaveDataSources = for {
      i <- 1 to size
    } yield {
      val slaveDataSourceId = DataSourceId(UUID.randomUUID())
      val slaveDataSource = JDBCDataSource(
        identity = slaveDataSourceId,
        dataSourceType = DataSourceType.SLAVE,
        driverClassName = "",
        host = "slave_host",
        port = Some(10),
        url = "hoehoge",
        userName = "",
        password = ""
      )
      slaveDataSource
    }
    (masterDataSource, slaveDataSources)
  }


  private def createCluster
  (clusterId: ClusterId,
   masterDataSourceId: DataSourceId,
   slaveDataSourceIds: Seq[DataSourceId]) = {
    Cluster(clusterId, masterDataSourceId, slaveDataSourceIds)
  }

  class Setup(f: => (DataSource, Seq[DataSource])) extends Scope {
    implicit val dataSourceRespository = DataSourceRepository()
    implicit val ctx = SyncEntityIOContext
    val clusterId = ClusterId("test")
    val (masterDataSource, slaveDataSources) = f
    dataSourceRespository.store(Seq(masterDataSource) ++ slaveDataSources)
    val cluster = createCluster(
      clusterId,
      masterDataSource.identity,
      slaveDataSources.map(_.identity)
    )
  }

  "cluster" should {
    "be able to get DataSources" in new Setup(withDataSources(2)) {
      cluster.getMasterDataSource.get must_== masterDataSource
      cluster.getSlaveDataSources.get must_== slaveDataSources
    }
    "be able to get JDBCDataSources" in new Setup(withDataSourcesAsJDBC(2)) {
      cluster.masterDataSourceId must_== masterDataSource.identity
      cluster.slaveDataSourceIds must_== slaveDataSources.map(_.identity)
      cluster.getMasterDataSourceAsJDBC.get must_== masterDataSource
      cluster.getSlaveDataSourcesAsJDBC.get must_== slaveDataSources
    }
    "be able to select a DataSource" in new Setup(withDataSources(2)) {
      val selector = cluster.createDataSourceSelectorAsRandom
      for {
        i <- 1 to 10
      } yield {
        val dataSourceTry = selector.selectDataSource()
        val f = dataSourceRespository.contains(dataSourceTry.get)
        f.get must_== true
        dataSourceTry.get must_!= cluster.getMasterDataSource
      }
    }

  }

}
