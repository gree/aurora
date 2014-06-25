package net.gree.aurora.scala.domain.datasource

import net.gree.aurora.domain.datasource.{JDBCDataSource => JJDBCDataSource, DataSourceType, DataSourceIdFactory, DataSourceFactory}
import org.sisioh.dddbase.utils.{Option => JOption}
import scala.language.implicitConversions

/**
 * JDBC用[[net.gree.aurora.scala.domain.datasource.DataSource]]。
 */
trait JDBCDataSource extends DataSource {

  /**
   * ドライバークラス名
   */
  val driverClassName: String

  /**
   * JDBC URL
   */
  val url: String

  /**
   * ユーザ名
   */
  val userName: String

  /**
   * パスワード
   */
  val password: String

  override def toString = Seq(
    s"identity = $identity",
    s"dataSourceType = $dataSourceType",
    s"host = $host",
    s"port = $port",
    s"driverClassName = $driverClassName",
    s"url = $url",
    s"userName = $userName",
    s"password = $password"
  ).mkString("JDBCDataSource(", ", ", ")")

}

/**
 * コンパニオンオブジェクト。
 */
object JDBCDataSource {

  /**
   * JavaオブジェクトからScalaオブジェクトを生成する。
   *
   * @param underlying [[net.gree.aurora.domain.datasource.JDBCDataSource]]
   * @return [[net.gree.aurora.scala.domain.datasource.JDBCDataSource]]
   */
  private[scala] def apply(underlying: JJDBCDataSource): JDBCDataSource =
    new JDBCDataSourceImpl(underlying)

  /**
   * ファクトリメソッド。
   *
   * @param identity 識別子
   * @param dataSourceType [[net.gree.aurora.domain.datasource.DataSourceType]]
   * @param driverClassName ドライバークラス名
   * @param host ホスト名
   * @param port ポート番号
   * @param url URL
   * @param userName ユーザ名
   * @param password パスワード
   * @return [[net.gree.aurora.scala.domain.datasource.JDBCDataSource]]
   */
  def apply(identity: DataSourceId,
            dataSourceType: DataSourceType,
            driverClassName: String,
            host: String,
            port: Option[Int],
            url: String,
            userName: String,
            password: String): JDBCDataSource =
    apply(
      DataSourceFactory.createAsJDBC(
        DataSourceIdFactory.create(identity.value),
        dataSourceType,
        driverClassName,
        host,
        if (port.isDefined) JOption.ofSome(port.get) else JOption.ofNone[Integer],
        url,
        userName,
        password
      )
    )

  /**
   * ScalaオブジェクトをJavaオブジェクトに変換する。
   *
   * @param self [[net.gree.aurora.scala.domain.datasource.JDBCDataSource]]
   * @return [[net.gree.aurora.domain.datasource.JDBCDataSource]]
   */
  implicit def toJava(self: JDBCDataSource) = self match {
    case s: JDBCDataSourceImpl => s.underlying
  }

  /**
   * エクストラクタメソッド。
   *
   * @param self [[net.gree.aurora.scala.domain.datasource.JDBCDataSource]]
   * @return 構成要素
   */
  def unapply(self: JDBCDataSource): Option[(DataSourceId, DataSourceType, String, String, Option[Int], String, String, String)] =
    Some(
      self.identity,
      self.dataSourceType,
      self.driverClassName,
      self.host,
      self.port,
      self.url,
      self.userName,
      self.password
    )

}
