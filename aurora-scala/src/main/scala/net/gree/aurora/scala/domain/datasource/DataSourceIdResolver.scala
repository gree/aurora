package net.gree.aurora.scala.domain.datasource

import scala.language.implicitConversions
import net.gree.aurora.domain.datasource.{DataSourceIdResolver => JDataSourceIdResolver}

/**
 * [[net.gree.aurora.domain.datasource.DataSourceIdResolver]]のScala版。
 *
 * @tparam T ヒントの型
 */
trait DataSourceIdResolver[T] extends (Option[T] => DataSourceId) {

  def apply(value: Option[T] = None): DataSourceId

}

/**
 * コンパニオンオブジェクト。
 */
object DataSourceIdResolver {

  /**
   * ScalaオブジェクトをJavaオブジェクトに変換する。
   *
   * @param dataSourceIdResolver [[net.gree.aurora.scala.domain.datasource.DataSourceIdResolver]]
   * @tparam T ヒントの型
   * @return [[net.gree.aurora.domain.datasource.DataSourceIdResolver]]
   */
  implicit def toJava[T](dataSourceIdResolver: DataSourceIdResolver[T]) = {
    new JDataSourceIdResolver[T] {
      def apply(hint: T) = dataSourceIdResolver(Option(hint))
    }
  }

}
