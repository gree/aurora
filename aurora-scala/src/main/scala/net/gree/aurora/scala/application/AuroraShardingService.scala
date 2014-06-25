package net.gree.aurora.scala.application

import java.io.File
import net.gree.aurora.application.{AuroraShardingService => JAuroraShardingService, AuroraShardingServiceFactory}
import net.gree.aurora.scala.domain.cluster.{TaggedClusterIdResolver, ClusterIdResolver, Cluster}
import net.gree.aurora.scala.domain.clustergroup.ClusterGroupId
import net.gree.aurora.scala.domain.datasource.{DataSourceRepository, DataSourceId, DataSource}
import net.gree.aurora.scala.domain.hint.Hint
import net.gree.aurora.scala.domain.shardingconfig.{ShardingConfigRepository, ShardingConfigId}
import org.sisioh.config.Configuration
import scala.util.Try
import scala.collection.JavaConverters._

/**
 * [[net.gree.aurora.application.AuroraShardingService]]のScalaラッパー。
 *
 * @tparam T ヒントの型
 */
trait AuroraShardingService[T] {

  /**
   * [[net.gree.aurora.scala.domain.datasource.DataSourceRepository]]を取得する。
   *
   * @return [[net.gree.aurora.scala.domain.datasource.DataSourceRepository]]
   */
  def dataSourceRepository: Try[DataSourceRepository]

  /**
   * [[net.gree.aurora.scala.domain.shardingconfig.ShardingConfigRepository]]を取得する。
   *
   * @return [[net.gree.aurora.scala.domain.shardingconfig.ShardingConfigRepository]]
   */
  def shardingConfigRepository: Try[ShardingConfigRepository]

  /**
   * [[net.gree.aurora.scala.domain.datasource.DataSourceId]]から[[net.gree.aurora.scala.domain.datasource.DataSource]]を解決する。
   *
   * @param id [[net.gree.aurora.scala.domain.datasource.DataSourceId]]
   * @return [[net.gree.aurora.scala.domain.datasource.DataSource]]
   */
  def resolveDataSourceById(id: DataSourceId): Try[DataSource]


  /**
   * [[net.gree.aurora.scala.domain.datasource.DataSourceId]]の集合から[[net.gree.aurora.scala.domain.datasource.DataSource]]の集合を解決する。
   *
   * @param ids [[net.gree.aurora.scala.domain.datasource.DataSourceId]]の集合
   * @return [[net.gree.aurora.scala.domain.datasource.DataSource]]の集合
   */
  def resolveDataSourcesByIds(ids: Seq[DataSourceId]): Try[Seq[DataSource]]

  /**
   * ヒントを指定しないで[net.gree.aurora.domain.cluster.Cluster]]を解決する。
   *
   * @param shardingConfigId [[net.gree.aurora.scala.domain.shardingconfig.ShardingConfigId]]
   * @param clusterGroupId [[net.gree.aurora.scala.domain.clustergroup.ClusterGroupId]]
   * @return Tryでラップされた[[net.gree.aurora.scala.domain.cluster.Cluster]]
   */
  def resolveCluster(shardingConfigId: ShardingConfigId, clusterGroupId: ClusterGroupId): Try[Cluster]

  /**
   * ヒントを指定しないで[[net.gree.aurora.domain.cluster.Cluster]]を解決する。
   *
   * @param shardingConfigId [[net.gree.aurora.scala.domain.shardingconfig.ShardingConfigId]]の文字列表現
   * @param clusterGroupId [[net.gree.aurora.scala.domain.clustergroup.ClusterGroupId]]の文字列表現
   * @return Tryでラップされた[[net.gree.aurora.scala.domain.cluster.Cluster]]
   */
  def resolveCluster(shardingConfigId: String, clusterGroupId: String): Try[Cluster]

  /**
   * ヒントから[[net.gree.aurora.domain.cluster.Cluster]]を解決する。
   *
   * @param shardingConfigId [[net.gree.aurora.scala.domain.shardingconfig.ShardingConfigId]]
   * @param clusterGroupId [[net.gree.aurora.scala.domain.clustergroup.ClusterGroupId]]
   * @param hint ヒント
   * @return Tryでラップされた[[net.gree.aurora.scala.domain.cluster.Cluster]]
   */
  def resolveClusterByHint(shardingConfigId: ShardingConfigId, clusterGroupId: ClusterGroupId, hint: Hint[T]): Try[Cluster]

  /**
   * ヒントから[[net.gree.aurora.domain.cluster.Cluster]]を解決する。
   *
   * @param shardingConfigId [[net.gree.aurora.scala.domain.shardingconfig.ShardingConfigId]]の文字列表現
   * @param clusterGroupId [[net.gree.aurora.scala.domain.clustergroup.ClusterGroupId]]の文字列表現
   * @param hint ヒント
   * @return Tryでラップされた[[net.gree.aurora.scala.domain.cluster.Cluster]]
   */
  def resolveClusterByHint(shardingConfigId: String, clusterGroupId: String, hint: Hint[T]): Try[Cluster]
}

/**
 * コンパニオンオブジェクト。
 */
object AuroraShardingService {

  private[scala] def apply[T](underlying: JAuroraShardingService[T]): AuroraShardingService[T] =
    new AuroraShardingServiceImpl[T](underlying)

  /**
   * ファクトリメソッド。
   *
   * @param clusterIdResolver [[net.gree.aurora.scala.domain.cluster.ClusterIdResolver]]
   * @param configFile 設定ファイル
   * @tparam T ヒントの型
   * @return [[net.gree.aurora.scala.application.AuroraShardingService]]
   */
  def apply[T](clusterIdResolver: ClusterIdResolver[T], configFile: File): AuroraShardingService[T] =
    apply(AuroraShardingServiceFactory.create(clusterIdResolver, configFile))

  /**
   * ファクトリメソッド。
   *
   * @param clusterIdResolver [[net.gree.aurora.scala.domain.cluster.ClusterIdResolver]]
   * @param config [[com.typesafe.config.Config]]
   * @tparam T ヒントの型
   * @return [[net.gree.aurora.scala.application.AuroraShardingService]]
   */
  def apply[T](clusterIdResolver: ClusterIdResolver[T], config: Configuration): AuroraShardingService[T] =
    apply(AuroraShardingServiceFactory.create(clusterIdResolver, config.underlying))

  /**
   * ファクトリメソッド。
   *
   * @param clusterIdResolver [[net.gree.aurora.scala.domain.cluster.ClusterIdResolver]]
   * @param shardingConfigRepository [[net.gree.aurora.scala.domain.shardingconfig.ShardingConfigRepository]]
   * @param dataSourceRepository [[net.gree.aurora.scala.domain.datasource.DataSourceRepository]]
   * @tparam T ヒントの型
   * @return [[net.gree.aurora.scala.application.AuroraShardingService]]
   */
  def apply[T](clusterIdResolver: ClusterIdResolver[T],
               shardingConfigRepository: ShardingConfigRepository,
               dataSourceRepository: DataSourceRepository): AuroraShardingService[T] =
    apply(AuroraShardingServiceFactory.create(clusterIdResolver, shardingConfigRepository, dataSourceRepository))

  /**
   * ファクトリメソッド。
   *
   * @param clusterIdResolver [[net.gree.aurora.scala.domain.cluster.ClusterIdResolver]]
   * @param repositories [[net.gree.aurora.scala.application.Repositories]]
   * @tparam T ヒントの型
   * @return [[net.gree.aurora.scala.application.AuroraShardingService]]
   */
  def apply[T](clusterIdResolver: ClusterIdResolver[T],
               repositories: Repositories): AuroraShardingService[T] =
    apply(clusterIdResolver, repositories.shardingConfigRepository, repositories.dataSourceRepository)

  def apply[T](taggedClusterIdResolvers: Set[TaggedClusterIdResolver[T]], config: Configuration): AuroraShardingService[T] ={
    val tcirs = taggedClusterIdResolvers.map(e => TaggedClusterIdResolver.toJava[T](e)).asJava
    val r: JAuroraShardingService[T] = AuroraShardingServiceFactory.create(tcirs, config.underlying)
    apply(r)
  }

}
