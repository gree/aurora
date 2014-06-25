package net.gree.aurora.scala.domain.cluster

import net.gree.aurora.domain.cluster.{Cluster => JCluster}
import net.gree.aurora.domain.cluster.{ClusterIdFactory, ClusterFactory}
import net.gree.aurora.domain.datasource.DataSourceIdFactory
import net.gree.aurora.scala.domain.datasource._
import org.sisioh.dddbase.core.lifecycle.EntityIOContext
import org.sisioh.dddbase.core.model.Entity
import scala.collection.JavaConverters._
import scala.language.implicitConversions
import scala.util.Try

/**
 * [[net.gree.aurora.domain.cluster.Cluster]]のScalaラッパー。
 */
trait Cluster extends Entity[ClusterId] {

  /**
   * マスター用[[net.gree.aurora.scala.domain.datasource.DataSource]]への識別子。
   */
  val masterDataSourceId: DataSourceId

  /**
   * スレーブ用[[net.gree.aurora.scala.domain.datasource.DataSource]]への識別子の集合。
   */
  val slaveDataSourceIds: Seq[DataSourceId]

  /**
   * マスターデータソースを取得する。
   *
   * @param dataSourceRepository [[net.gree.aurora.scala.domain.datasource.DataSourceRepository]]
   * @param ctx [[org.sisioh.dddbase.core.lifecycle.EntityIOContext]]
   * @return Tryでラップされた[[net.gree.aurora.scala.domain.datasource.DataSource]]
   */
  def getMasterDataSource(implicit dataSourceRepository: DataSourceRepository, ctx: EntityIOContext[Try]): Try[DataSource]

  /**
   * JDBC用マスターデータソースを取得する。
   *
   * @param dataSourceRepository [[net.gree.aurora.scala.domain.datasource.DataSourceRepository]]
   * @param ctx [[org.sisioh.dddbase.core.lifecycle.EntityIOContext]]
   * @return Tryでラップされた[[net.gree.aurora.scala.domain.datasource.DataSource]]
   */
  def getMasterDataSourceAsJDBC(implicit dataSourceRepository: DataSourceRepository, ctx: EntityIOContext[Try]): Try[JDBCDataSource]

  /**
   * Redis用マスターデータソースを取得する。
   *
   * @param dataSourceRepository [[net.gree.aurora.scala.domain.datasource.DataSourceRepository]]
   * @param ctx [[org.sisioh.dddbase.core.lifecycle.EntityIOContext]]
   * @return Tryでラップされた[[net.gree.aurora.scala.domain.datasource.DataSource]]
   */
  def getMasterDataSourceAsRedis(implicit dataSourceRepository: DataSourceRepository, ctx: EntityIOContext[Try]): Try[RedisDataSource]

  /**
   * スレーブデータソースの集合を取得する。
   *
   * @param dataSourceRepository [[net.gree.aurora.scala.domain.datasource.DataSourceRepository]]
   * @param ctx [[org.sisioh.dddbase.core.lifecycle.EntityIOContext]]
   * @return Tryでラップされた[[net.gree.aurora.scala.domain.datasource.DataSource]]
   */
  def getSlaveDataSources(implicit dataSourceRepository: DataSourceRepository, ctx: EntityIOContext[Try]): Try[Seq[DataSource]]

  /**
   * JDBC用スレーブデータソースの集合を取得する。
   *
   * @param dataSourceRepository [[net.gree.aurora.scala.domain.datasource.DataSourceRepository]]
   * @param ctx [[org.sisioh.dddbase.core.lifecycle.EntityIOContext]]
   * @return Tryでラップされた[[net.gree.aurora.scala.domain.datasource.DataSource]]
   */
  def getSlaveDataSourcesAsJDBC(implicit dataSourceRepository: DataSourceRepository, ctx: EntityIOContext[Try]): Try[Seq[DataSource]]

  /**
   * Redis用スレーブデータソースの集合を取得する。
   *
   * @param dataSourceRepository [[net.gree.aurora.scala.domain.datasource.DataSourceRepository]]
   * @param ctx [[org.sisioh.dddbase.core.lifecycle.EntityIOContext]]
   * @return Tryでラップされた[[net.gree.aurora.scala.domain.datasource.DataSource]]
   */
  def getSlaveDataSourcesAsRedis(implicit dataSourceRepository: DataSourceRepository, ctx: EntityIOContext[Try]): Try[Seq[DataSource]]

  /**
   * [[net.gree.aurora.scala.domain.datasource.DataSourceSelector]]を生成する。
   *
   * @param dataSourceIdResolver [[net.gree.aurora.scala.domain.datasource.DataSourceIdResolver]]
   * @param dataSourceRepository [[net.gree.aurora.scala.domain.datasource.DataSourceRepository]]
   * @tparam T ヒントの型
   * @return [[net.gree.aurora.scala.domain.datasource.DataSourceSelector]]
   */
  def createDataSourceSelector[T](dataSourceIdResolver: DataSourceIdResolver[T])(implicit dataSourceRepository: DataSourceRepository): DataSourceSelector[T]

  /**
   * 乱数による[[net.gree.aurora.scala.domain.datasource.DataSourceSelector]]を生成する。
   *
   * @param dataSourceRepository [[net.gree.aurora.scala.domain.datasource.DataSourceRepository]]
   */
  def createDataSourceSelectorAsRandom(implicit dataSourceRepository: DataSourceRepository): DataSourceSelector[Int]

  override def toString =
    Seq(
      s"identity = $identity",
      s"masterDataSourceId = $masterDataSourceId",
      s"slaveDataSourceIds = $slaveDataSourceIds"
    ).mkString("Cluster(", ", ", ")")

}

/**
 * コンパニオンオブジェクト。
 */
object Cluster {

  private[scala] def apply(underlying: JCluster): Cluster =
    new ClusterImpl(underlying)

  /**
   * ファクトリメソッド。
   *
   * @param identity 識別子
   * @param masterDataSourceId マスターデータソースの識別子
   * @param slaveDataSourceIds スレーブデータソースの識別子の集合
   * @return [[net.gree.aurora.scala.domain.cluster.Cluster]]
   */
  def apply(identity: ClusterId, masterDataSourceId: DataSourceId, slaveDataSourceIds: Seq[DataSourceId]): Cluster =
    apply(
      ClusterFactory.create(
        ClusterIdFactory.create(identity.value),
        DataSourceIdFactory.create(masterDataSourceId.value),
        slaveDataSourceIds.map(e => DataSourceIdFactory.create(e.value)).asJava
      )
    )

  /**
   * ScalaオブジェクトからJavaオブジェクトに変換する。
   *
   * @param self [[net.gree.aurora.scala.domain.cluster.Cluster]]
   * @return [[net.gree.aurora.domain.cluster.Cluster]]
   */
  implicit def toJava(self: Cluster) = self match {
    case s: ClusterImpl => s.underlying
  }

}
