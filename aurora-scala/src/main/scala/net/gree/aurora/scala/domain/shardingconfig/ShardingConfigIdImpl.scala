package net.gree.aurora.scala.domain.shardingconfig

import net.gree.aurora.domain.shardingconfig.{ShardingConfigId => JShardingConfigId}

private[domain]
final class ShardingConfigIdImpl(val underlying: JShardingConfigId) extends ShardingConfigId {

  def value: String = underlying.getValue

}
