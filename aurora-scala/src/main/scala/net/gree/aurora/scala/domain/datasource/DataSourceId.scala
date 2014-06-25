package net.gree.aurora.scala.domain.datasource

import java.util.UUID
import net.gree.aurora.domain.datasource.{DataSourceId => JDataSourceId, DataSourceIdFactory}
import org.sisioh.dddbase.core.model.Identity
import scala.language.implicitConversions

/**
 * [[net.gree.aurora.scala.domain.datasource.DataSource]]のための識別子。
 */
trait DataSourceId extends Identity[UUID]

/**
 * コンパニオンオブジェクト。
 */
object DataSourceId {

  /**
   * JavaオブジェクトからScalaオブジェクトを生成する。
   *
   * @param underlying [[net.gree.aurora.scala.domain.datasource.DataSourceId]]
   * @return
   */
  private[scala] def apply(underlying: JDataSourceId): DataSourceId =
    new DataSourceIdImpl(underlying)

  /**
   * ファクトリメソッド。
   *
   * @param value [[java.util.UUID]]
   * @return [[net.gree.aurora.scala.domain.datasource.DataSourceId]]
   */
  def apply(value: UUID): DataSourceId =
    apply(DataSourceIdFactory.create(value))

  /**
   * ScalaオブジェクトからJavaオブジェクトに変換する。
   *
   * @param self [[net.gree.aurora.scala.domain.datasource.DataSourceId]]
   * @return [[net.gree.aurora.domain.datasource.DataSourceId]]
   */
  implicit def toJava(self: DataSourceId) = self match {
    case s: DataSourceIdImpl => s.underlying
  }

}
