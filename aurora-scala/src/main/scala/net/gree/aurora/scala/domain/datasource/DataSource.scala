package net.gree.aurora.scala.domain.datasource

import net.gree.aurora.domain.datasource.{DataSource => JDataSource, DataSourceType, DataSourceFactory}
import org.sisioh.dddbase.core.model.Entity
import org.sisioh.dddbase.utils.{Option => JOption}
import scala.language.implicitConversions

/**
 * [[net.gree.aurora.domain.datasource.DataSource]]のScala版。
 */
trait DataSource extends Entity[DataSourceId] {

  /**
   * データソースの種類
   */
  val dataSourceType: DataSourceType

  /**
   * ホスト名
   */
  val host: String

  /**
   * ポート番号
   */
  val port: Option[Int]

  override def toString = Seq(
    s"identity = $identity",
    s"dataSourceType = $dataSourceType",
    s"host = $host",
    s"port = $port"
  ).mkString("DataSource(", ", ", ")")

}

/**
 * コンパニオンオブジェクト。
 */
object DataSource {

  /**
   * JavaオブジェクトからScalaオブジェクトを生成する。
   *
   * @param underlying [[net.gree.aurora.domain.datasource.DataSource]]
   * @return [[net.gree.aurora.scala.domain.datasource.DataSource]]
   */
  private[scala] def apply(underlying: JDataSource): DataSource =
    new DataSourceImpl(underlying)

  /**
   * ファクトリメソッド。
   *
   * @param identity [[net.gree.aurora.scala.domain.datasource.DataSourceId]]
   * @param dataSourceType [[net.gree.aurora.domain.datasource.DataSourceType]]
   * @param host ホスト名
   * @param port ポート番号
   * @return [[net.gree.aurora.scala.domain.datasource.DataSource]]
   */
  def apply(identity: DataSourceId, dataSourceType: DataSourceType, host: String, port: Option[Int]): DataSource =
    apply(DataSourceFactory.create(identity, dataSourceType, host, if (port.isDefined) JOption.ofSome(port.get) else JOption.ofNone[Integer]))

  /**
   * ScalaオブジェクトからJavaオブジェクトに変換する。
   *
   * @param self [[net.gree.aurora.scala.domain.datasource.DataSource]]
   * @return [[net.gree.aurora.domain.datasource.DataSource]]
   */
  implicit def toJava(self: DataSource) = self match {
    case s: DataSourceImpl => s.underlying
    case s: JDBCDataSourceImpl => s.underlying
    case s: RedisDataSourceImpl => s.underlying
  }

  /**
   * エクストラクタメソッド。
   *
   * @param self [[net.gree.aurora.scala.domain.datasource.DataSource]]
   * @return 構成要素
   */
  def unapply(self: DataSource): Option[(DataSourceId, DataSourceType, String, Option[Int])] =
    Some(self.identity, self.dataSourceType, self.host, self.port)

}
