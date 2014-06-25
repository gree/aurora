package net.gree.aurora.scala.application

import net.gree.aurora.application.{Repositories => JRepositories}
import net.gree.aurora.scala.domain.shardingconfig.ShardingConfigRepository
import net.gree.aurora.scala.domain.datasource.DataSourceRepository

private[application]
case class RepositoriesImpl(underlying: JRepositories) extends Repositories {
  val dataSourceRepository: DataSourceRepository = DataSourceRepository(underlying.getDataSourceRepository)
  val shardingConfigRepository: ShardingConfigRepository = ShardingConfigRepository(underlying.getShardingConfigRepository)
}
