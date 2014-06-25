package net.gree.aurora.scala.domain.tablename

import net.gree.aurora.domain.tablename.TableNameFactory
import net.gree.aurora.domain.tablename.{TableName => JTableName}
import scala.language.implicitConversions

/**
 * [[net.gree.aurora.domain.tablename.TableName]]のScala版。
 */
trait TableName {

  /**
   * 名前
   */
  val name: String

}

/**
 * コンパニオンオブジェクト。
 */
object TableName {

  /**
   * JavaオブジェクトからScalaオブジェクトを生成する。
   *
   * @param underlying [[net.gree.aurora.domain.tablename.TableName]]
   * @return [[net.gree.aurora.scala.domain.tablename.TableName]]
   */
  private[scala] def apply(underlying: JTableName): TableName =
    new TableNameImpl(underlying)

  /**
   * ファクトリメソッド。
   *
   * @param value 識別子の値
   * @return [[net.gree.aurora.scala.domain.tablename.TableName]]
   */
  def apply(value: String): TableName =
    apply(TableNameFactory.create(value))

  /**
   * ScalaオブジェクトからJavaオブジェクトに変換する。
   *
   * @param self [[net.gree.aurora.scala.domain.tablename.TableName]]
   * @return [[net.gree.aurora.domain.tablename.TableName]]
   */
  implicit def toJava(self: TableName) = self match {
    case s: TableNameImpl => s.underlying
  }

}
