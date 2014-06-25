package net.gree.aurora.scala.domain.datasource

import net.gree.aurora.domain.datasource.{DataSource => JDataSource}
import org.sisioh.dddbase.utils.{Option => JOption}

private[domain]
class DataSourceImpl(val underlying: JDataSource) extends DataSource {

  val identity = DataSourceId(underlying.getIdentity.getValue)

  val dataSourceType = underlying.getDataSourceType

  val host = underlying.getHost

  val port = {
    underlying.getPort match {
      case s: JOption.Some[_] =>
        Some(s.get.asInstanceOf[Int])
      case n: JOption.None[_] =>
        None
    }
  }

  override def toString =
    Seq(
      s"identity = $identity",
      s"host = $host",
      s"port = $port"
    ).mkString("DataSource(", ", ", ")")

}
