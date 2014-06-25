package net.gree.aurora.scala.domain.datasource

import net.gree.aurora.domain.datasource.{DataSource => JDataSource}
import net.gree.aurora.domain.datasource.{DataSourceSelector => JDataSourceSelector, DataSourceSelectorFactory}
import scala.util.Try

/**
 * [[net.gree.aurora.domain.datasource.DataSource]]のScala版。
 *
 * @tparam T ヒントの型
 */
trait DataSourceSelector[T] {

  /**
   * ヒントから[[net.gree.aurora.scala.domain.datasource.DataSource]]を選択する。
   *
   * @param hint ヒント
   * @return Tryでラップされた[[net.gree.aurora.scala.domain.datasource.DataSource]]
   */
  def selectDataSource(hint: Option[T] = None): Try[DataSource]

  /**
   * ヒントから[[net.gree.aurora.scala.domain.datasource.JDBCDataSource]]を選択する。
   *
   * @param hint ヒント
   * @return Tryでラップされた[[net.gree.aurora.scala.domain.datasource.JDBCDataSource]]
   */
  def selectDataSourceAsJDBC(hint: Option[T] = None): Try[JDBCDataSource]

  /**
   * ヒントから[[net.gree.aurora.scala.domain.datasource.RedisDataSource]]を選択する。
   *
   * @param hint ヒント
   * @return Tryでラップされた[[net.gree.aurora.scala.domain.datasource.RedisDataSource]]
   */
  def selectDataSourceAsRedis(hint: Option[T] = None): Try[RedisDataSource]

}

/**
 * コンパニオンオブジェクト。
 */
object DataSourceSelector {

  /**
   * JavaオブジェクトからScalaオブジェクトを生成する。
   *
   * @param underlying [[net.gree.aurora.domain.datasource.DataSourceSelector]]
   * @tparam T ヒントの型
   * @return [[net.gree.aurora.scala.domain.datasource.DataSourceSelector]]
   */
  private[scala] def apply[T](underlying: JDataSourceSelector[T]): DataSourceSelector[T] =
    new DataSourceSelectorImpl[T](underlying)

  /**
   * ファクトリメソッド。
   *
   * @param dataSourceRepository [[net.gree.aurora.scala.domain.datasource.DataSourceRepository]]
   * @param dataSourceIdResolver [[net.gree.aurora.scala.domain.datasource.DataSourceIdResolver]]
   * @tparam T ヒントの型
   * @return [[net.gree.aurora.scala.domain.datasource.DataSourceSelector]]
   */
  def apply[T](dataSourceRepository: DataSourceRepository, dataSourceIdResolver: DataSourceIdResolver[T]): DataSourceSelector[T] =
    apply(DataSourceSelectorFactory.create(dataSourceRepository, dataSourceIdResolver))

}
