package net.gree.aurora.scala.domain.datasource

import net.gree.aurora.domain.datasource.{DataSourceIdRandomResolver => JDataSourceIdRandomResolver}
import scala.collection.JavaConverters._

/**
 * ランダムに[[net.gree.aurora.scala.domain.datasource.DataSourceId]]を解決するため
 * の[[net.gree.aurora.domain.datasource.DataSourceIdResolver]]。
 *
 * @param underlying [[net.gree.aurora.domain.datasource.DataSourceIdRandomResolver]]
 */
class DataSourceIdRandomResolver(val underlying: JDataSourceIdRandomResolver)
  extends DataSourceIdResolver[Int] {

  def toJava(value: Option[Int]): Integer = value match {
    case Some(v) => v
    case None => null.asInstanceOf[Integer]
  }

  def apply(value: Option[Int]): DataSourceId = {
    val result = underlying.apply(toJava(value))
    DataSourceId(result)
  }

  override def equals(obj: Any): Boolean = obj match {
    case that: DataSourceIdRandomResolver =>
      underlying.equals(that)
    case _ => false
  }

  override def hashCode: Int = 31 * underlying.##

}

/**
 * コンパニオンオブジェクト。　
 */
object DataSourceIdRandomResolver {

  /**
   * JavaオブジェクトからScalaオブジェクトを生成する。
   *
   * @param underlying [[net.gree.aurora.domain.datasource.DataSourceIdRandomResolver]]
   * @return [[net.gree.aurora.scala.domain.datasource.DataSourceIdRandomResolver]]
   */
  private[scala] def apply(underlying: JDataSourceIdRandomResolver): DataSourceIdRandomResolver =
    new DataSourceIdRandomResolver(underlying)

  /**
   * ファクトリメソッド。
   *
   * @param dataSourceIds [[net.gree.aurora.scala.domain.datasource.DataSourceId]]の集合
   * @return [[net.gree.aurora.scala.domain.datasource.DataSourceIdRandomResolver]]
   */
  def apply(dataSourceIds: Seq[DataSourceId]): DataSourceIdRandomResolver =
    apply(new JDataSourceIdRandomResolver(dataSourceIds.map(DataSourceId.toJava).asJava))

}
