package net.gree.aurora.scala.domain.datasource

import net.gree.aurora.domain.datasource.{RedisDataSource => JRedisDataSource, DataSourceType, DataSourceIdFactory, DataSourceFactory}
import scala.language.implicitConversions

/**
 * Redisに対応した[[net.gree.aurora.scala.domain.datasource.DataSource]]。
 */
trait RedisDataSource extends DataSource {

  /**
   * データベース番号
   */
  val databaseNumber: Option[Int]

  /**
   * パスワード
   */
  val password: Option[String]

  override def toString = Seq(
    s"identity = $identity",
    s"dataSourceType = $dataSourceType",
    s"host = $host",
    s"port = $port",
    s"databaseNumber = $databaseNumber",
    s"password = $password"
  ).mkString("RedisDataSource(", ", ", ")")

}

/**
 * コンパニオンオブジェクト。
 */
object RedisDataSource {

  /**
   * JavaオブジェクトからScalaオブジェクトを生成する。
   *
   * @param underlying [[net.gree.aurora.domain.datasource.RedisDataSource]]
   * @return [[net.gree.aurora.scala.domain.datasource.RedisDataSource]]
   */
  private[scala] def apply(underlying: JRedisDataSource): RedisDataSource =
    new RedisDataSourceImpl(underlying)

  import org.sisioh.dddbase.utils.{Option => JOption}

  /**
   * ファクトリメソッド。
   *
   * @param identity 識別子
   * @param dataSourceType [[net.gree.aurora.domain.datasource.DataSourceType]]
   * @param host ホスト名
   * @param port ポート番号
   * @param databaseNumber データベース番号
   * @param password パスワード
   * @return [[net.gree.aurora.scala.domain.datasource.RedisDataSource]]
   */
  def apply(identity: DataSourceId,
            dataSourceType: DataSourceType,
            host: String,
            port: Option[Int],
            databaseNumber: Int,
            password: String): RedisDataSource =
    apply(
      DataSourceFactory.createAsRedis(
        DataSourceIdFactory.create(identity.value),
        dataSourceType,
        host,
        if (port.isDefined) JOption.ofSome(port.get) else JOption.ofNone[Integer],
        databaseNumber,
        password
      )
    )

  /**
   * ScalaオブジェクトをJavaオブジェクトに変換する。
   *
   * @param self [[net.gree.aurora.scala.domain.datasource.RedisDataSource]]
   * @return [[net.gree.aurora.domain.datasource.RedisDataSource]]
   */
  implicit def toJava(self: RedisDataSource) = self match {
    case s: RedisDataSourceImpl => s.underlying
  }

  /**
   * エクストラクタメソッド。
   *
   * @param self [[net.gree.aurora.scala.domain.datasource.RedisDataSource]]
   * @return 構成要素
   */
  def unapply(self: RedisDataSource): Option[(DataSourceId, DataSourceType, String, Option[Int], Option[Int], Option[String])] =
    Some(
      self.identity,
      self.dataSourceType,
      self.host,
      self.port,
      self.databaseNumber,
      self.password
    )

}
