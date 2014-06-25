package net.gree.aurora.scala.application

import java.io.File
import net.gree.aurora.application.{AuroraShardingConfigLoadService => JAuroraShardingConfigLoadService, AuroraShardingConfigLoadServiceFactory}
import org.sisioh.config.Configuration
import scala.util.Try

trait AuroraShardingConfigLoadService {

  def loadFromConfigFile(configFile: File): Try[Repositories]

  def loadFromConfig(config: Configuration): Try[Repositories]

}

object AuroraShardingConfigLoadService {

  private[scala] def apply(underlying: JAuroraShardingConfigLoadService): AuroraShardingConfigLoadService =
    new AuroraShardingConfigLoadServiceImpl(underlying)

  def apply(): AuroraShardingConfigLoadService =
    apply(AuroraShardingConfigLoadServiceFactory.create())

}
