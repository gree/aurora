package net.gree.aurora.scala.application

import java.io.File
import net.gree.aurora.application.{AuroraTableNameService => JAuroraTableNameService, AuroraTableNameServiceFactory}
import net.gree.aurora.scala.domain.tablename.{TableNameResolver, TableName}
import org.sisioh.config.Configuration
import scala.collection.JavaConverters._
import scala.util.Try

/**
 * [[net.gree.aurora.application.AuroraTableNameService]]に対応するトレイト。
 *
 * @tparam T ヒントの型
 */
trait AuroraTableNameService[T] {

  /**
   * ヒントから[[net.gree.aurora.scala.domain.tablename.TableName]]を解決する。
   *
   * @param hint ヒント
   * @return [[net.gree.aurora.scala.domain.tablename.TableName]]
   */
  def resolveByHint(hint: T): Try[TableName]

}

/**
 * コンパニオンオブジェクト。
 */
object AuroraTableNameService {

  /**
   * JavaオブジェクトからScalaオブジェクトを生成する。
   *
   * @param underlying [[net.gree.aurora.scala.application.AuroraTableNameService]]
   * @tparam T ヒントの型
   * @return [[net.gree.aurora.scala.application.AuroraTableNameService]]
   */
  private[scala] def apply[T](underlying: JAuroraTableNameService[T]): AuroraTableNameService[T] =
    new AuroraTableNameServiceImpl(underlying)

  /**
   * ファクトリメソッド。
   *
   * @param tableNameResolver [[net.gree.aurora.scala.domain.tablename.TableNameResolver]]
   * @param tableNames [[net.gree.aurora.scala.domain.tablename.TableName]]の集合
   * @tparam T ヒントの型
   * @return [[net.gree.aurora.scala.application.AuroraTableNameService]]
   */
  def apply[T](tableNameResolver: TableNameResolver[T], tableNames: Set[TableName]): AuroraTableNameService[T] =
    apply(AuroraTableNameServiceFactory.create(tableNameResolver, tableNames.map(TableName.toJava).asJava))

  /**
   * ファクトリメソッド。
   *
   * @param tableNameResolver [[net.gree.aurora.scala.domain.tablename.TableNameResolver]]
   * @param configFile 設定ファイル
   * @tparam T ヒントの型
   * @return [[net.gree.aurora.scala.application.AuroraTableNameService]]
   */
  def apply[T](tableNameResolver: TableNameResolver[T], configFile: File): AuroraTableNameService[T] =
    apply(AuroraTableNameServiceFactory.create(tableNameResolver, configFile))

  /**
   * ファクトリメソッド。
   *
   * @param tableNameResolver [[net.gree.aurora.scala.domain.tablename.TableNameResolver]]
   * @param config
   * @tparam T ヒントの型
   * @return [[net.gree.aurora.scala.application.AuroraTableNameService]]
   */
  def apply[T](tableNameResolver: TableNameResolver[T], config: Configuration): AuroraTableNameService[T] =
    apply(AuroraTableNameServiceFactory.create(tableNameResolver, config.underlying))

}
