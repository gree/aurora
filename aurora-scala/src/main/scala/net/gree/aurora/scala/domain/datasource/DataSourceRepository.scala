package net.gree.aurora.scala.domain.datasource

import net.gree.aurora.domain.datasource.{DataSourceRepository => JDataSourceRepository, DataSourceRepositoryFactory}
import org.sisioh.dddbase.core.lifecycle.sync.{SyncEntityReadableByIterable, SyncRepository}
import scala.language.implicitConversions

/**
 * [[net.gree.aurora.scala.domain.datasource.DataSource]]のためのリポジトリ。
 */
trait DataSourceRepository
  extends SyncRepository[DataSourceId, DataSource]
  with SyncEntityReadableByIterable[DataSourceId, DataSource]

/**
 * コンパニオンオブジェクト。
 */
object DataSourceRepository {

  /**
   * JavaオブジェクトからScalaオブジェクトを生成する。
   *
   * @param underlying [[net.gree.aurora.domain.datasource.DataSourceRepository]]
   * @return [[net.gree.aurora.scala.domain.datasource.DataSourceRepository]]
   */
  private[scala] def apply(underlying: JDataSourceRepository): DataSourceRepository =
    new DataSourceRepositoryImpl(underlying)

  /**
   * ファクトリメソッド。
   *
   * @return [[net.gree.aurora.scala.domain.datasource.DataSourceRepository]]
   */
  def apply(): DataSourceRepository =
    apply(DataSourceRepositoryFactory.create())

  /**
   * ScalaオブジェクトからJavaオブジェクトに変換する。
   *
   * @param self [[net.gree.aurora.scala.domain.datasource.DataSourceRepository]]
   * @return [[net.gree.aurora.domain.datasource.DataSourceRepository]]
   */
  implicit def toJava(self: DataSourceRepository) = self match {
    case s: DataSourceRepositoryImpl => s.underlying
  }

}
