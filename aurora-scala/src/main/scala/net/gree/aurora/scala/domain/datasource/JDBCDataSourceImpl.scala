package net.gree.aurora.scala.domain.datasource

import net.gree.aurora.domain.datasource.{JDBCDataSource => JJDBCDataSource, DataSourceType}
import org.sisioh.dddbase.utils.{Option => JOption}

private[domain]
class JDBCDataSourceImpl(val underlying: JJDBCDataSource)
  extends JDBCDataSource {

  val identity = DataSourceId(underlying.getIdentity.getValue)

  val dataSourceType: DataSourceType = underlying.getDataSourceType

  val driverClassName = underlying.getDriverClassName

  val host = underlying.getHost

  val port = {
    underlying.getPort match {
      case s: JOption.Some[_] =>
        Some(s.get.asInstanceOf[Int])
      case n: JOption.None[_] =>
        None
    }
  }

  val url = underlying.getUrl

  val userName = underlying.getUserName

  val password = underlying.getPassword

}
