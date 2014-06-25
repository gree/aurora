package net.gree.aurora.scala.domain.shardingconfig

import net.gree.aurora.domain.shardingconfig.{ShardingConfig => JShardingConfig}

class ShardingConfigImpl(val underlying: JShardingConfig) extends ShardingConfig {

  val identity: ShardingConfigId = ShardingConfigId(underlying.getIdentity.getValue)

}
