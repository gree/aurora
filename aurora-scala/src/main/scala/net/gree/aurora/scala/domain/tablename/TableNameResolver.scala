package net.gree.aurora.scala.domain.tablename

import net.gree.aurora.domain.tablename.{TableNameResolver => JTableNameResolver}
import scala.language.implicitConversions

/**
 * [[net.gree.aurora.domain.tablename.TableNameResolver]]のScala版。
 *
 * @tparam T ヒントの型
 */
trait TableNameResolver[T] extends (T => TableName) {

  def apply(value: T): TableName

}

/**
 * コンパニオンオブジェクト。
 */
object TableNameResolver {

  /**
   * ScalaオブジェクトからJavaオブジェクトに変換する。
   *
   * @param self [[net.gree.aurora.scala.domain.tablename.TableNameResolver]]
   * @tparam T ヒントの型
   * @return [[net.gree.aurora.domain.tablename.TableNameResolver]]
   */
  implicit def toJava[T](self: TableNameResolver[T]) =
    new JTableNameResolver[T] {
      def apply(hint: T) = self(hint)
    }

}
