package net.gree.aurora.scala.domain.datasource

import net.gree.aurora.domain.datasource.{DataSourceId => JDataSourceId}

private[domain]
case class DataSourceIdImpl(underlying: JDataSourceId) extends DataSourceId {

  def value = underlying.getValue

  override def toString = Seq(s"value = $value").mkString("DataSourceId(",", ", ")")

}
