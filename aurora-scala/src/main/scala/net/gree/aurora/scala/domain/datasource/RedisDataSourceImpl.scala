package net.gree.aurora.scala.domain.datasource

import net.gree.aurora.domain.datasource.{RedisDataSource => JRedisDataSource}
import org.sisioh.dddbase.utils.{Option => JOption}

private[domain]
class RedisDataSourceImpl(val underlying: JRedisDataSource) extends RedisDataSource {

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

  val databaseNumber = Option[Integer](underlying.getDatabaseNumber).map(_.asInstanceOf[Int])

  val password = Option(underlying.getPassword)

}
