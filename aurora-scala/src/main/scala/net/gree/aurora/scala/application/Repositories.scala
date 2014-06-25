package net.gree.aurora.scala.application

import net.gree.aurora.scala.domain.datasource.DataSourceRepository
import net.gree.aurora.scala.domain.shardingconfig.ShardingConfigRepository

trait Repositories {

  val dataSourceRepository: DataSourceRepository

  val shardingConfigRepository: ShardingConfigRepository

}
