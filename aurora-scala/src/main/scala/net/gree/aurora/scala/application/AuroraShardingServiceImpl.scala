package net.gree.aurora.scala.application

import java.util.{List => JList}
import net.gree.aurora.application.{AuroraShardingService => JAuroraShardingService}
import net.gree.aurora.domain.cluster.{Cluster => JCluster}
import net.gree.aurora.domain.datasource.{DataSourceRepository => JDataSourceRepository, DataSource => JDataSource}
import net.gree.aurora.domain.shardingconfig.{ShardingConfigRepository => JShardingConfigRepository}
import net.gree.aurora.scala.domain.cluster.Cluster
import net.gree.aurora.scala.domain.clustergroup.ClusterGroupId
import net.gree.aurora.scala.domain.datasource.{DataSourceRepository, DataSourceId, DataSource}
import net.gree.aurora.scala.domain.shardingconfig.{ShardingConfigRepository, ShardingConfigId}
import org.sisioh.dddbase.utils.{Try => JTry}
import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}
import net.gree.aurora.scala.domain.hint.Hint

private[application]
class AuroraShardingServiceImpl[T](underlying: JAuroraShardingService[T]) extends AuroraShardingService[T] {

  def dataSourceRepository: Try[DataSourceRepository] = {
    val result = underlying.getDataSourceRepository
    result match {
      case success: JTry.Success[_] =>
        val dataSourceRepository = success.get().asInstanceOf[JDataSourceRepository]
        Success(DataSourceRepository(dataSourceRepository))
      case failure: JTry.Failure[_] =>
        Failure(failure.getCause)
    }
  }

  def shardingConfigRepository: Try[ShardingConfigRepository] = {
    val result = underlying.getShardingConfigRepository
    result match {
      case success: JTry.Success[_] =>
        val shardingConfigRepository = success.get().asInstanceOf[JShardingConfigRepository]
        Success(ShardingConfigRepository(shardingConfigRepository))
      case failure: JTry.Failure[_] =>
        Failure(failure.getCause)

    }
  }

  def resolveDataSourceById(id: DataSourceId): Try[DataSource] = {
    val result = underlying.resolveDataSourceById(id)
    result match {
      case success: JTry.Success[_] =>
        val dataSource = success.get().asInstanceOf[JDataSource]
        Success(DataSource(dataSource))
      case failure: JTry.Failure[_] =>
        Failure(failure.getCause)
    }
  }

  def resolveDataSourcesByIds(ids: Seq[DataSourceId]): Try[Seq[DataSource]] = {
    val result = underlying.resolveDataSourcesByIds(ids.map(DataSourceId.toJava).asJava)
    result match {
      case success: JTry.Success[_] =>
        val dataSources = success.get().asInstanceOf[JList[JDataSource]]
        Success(dataSources.asScala.map(DataSource(_)).toSeq)
      case failure: JTry.Failure[_] =>
        Failure(failure.getCause)
    }

  }

  def resolveCluster(shardingConfigId: ShardingConfigId, clusterGroupId: ClusterGroupId): Try[Cluster] = {
    val result = underlying.resolveCluster(shardingConfigId, clusterGroupId)
    result match {
      case success: JTry.Success[_] =>
        val cluster = success.get.asInstanceOf[JCluster]
        Success(Cluster(cluster))
      case failure: JTry.Failure[_] =>
        Failure(failure.getCause)
    }
  }

  def resolveClusterByHint(shardingConfigId: ShardingConfigId, clusterGroupId: ClusterGroupId, hint: Hint[T]): Try[Cluster] = {
    val result = underlying.resolveClusterByHint(shardingConfigId, clusterGroupId, hint)
    result match {
      case success: JTry.Success[_] =>
        val cluster = success.get.asInstanceOf[JCluster]
        Success(Cluster(cluster))
      case failure: JTry.Failure[_] =>
        Failure(failure.getCause)
    }
  }

  def resolveClusterByHint(shardingConfigId: String, clusterGroupId: String, hint: Hint[T]): Try[Cluster] =
    resolveClusterByHint(ShardingConfigId(shardingConfigId), ClusterGroupId(clusterGroupId), hint)

  def resolveCluster(shardingConfigId: String, clusterGroupId: String): Try[Cluster] =
    resolveCluster(ShardingConfigId(shardingConfigId), ClusterGroupId(clusterGroupId))

}
