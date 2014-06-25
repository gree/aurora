package net.gree.aurora.scala.application

import java.io.File
import net.gree.aurora.application.{AuroraShardingConfigLoadService => JAuroraShardingConfigLoadService}
import net.gree.aurora.application.{AuroraTableNameService => JAuroraTableNameService}
import net.gree.aurora.application.{Repositories => JRepositories}
import net.gree.aurora.domain.tablename.{TableName => JTableName}
import org.sisioh.config.Configuration
import org.sisioh.dddbase.utils.{Try => JTry}
import scala.util.{Failure, Success, Try}

private[application]
class AuroraShardingConfigLoadServiceImpl(val underlying: JAuroraShardingConfigLoadService) extends AuroraShardingConfigLoadService {

  def loadFromConfigFile(configFile: File): Try[Repositories] = {
    val result = underlying.loadFromConfigFile(configFile)
    result match {
      case success: JTry.Success[_] =>
        val _result = success.get().asInstanceOf[JRepositories]
        Success(RepositoriesImpl(_result))
      case failure: JTry.Failure[_] =>
        Failure(failure.getCause)
    }
  }

  def loadFromConfig(config: Configuration): Try[Repositories] = {
    val result = underlying.loadFromConfig(config.underlying)
    result match {
      case success: JTry.Success[_] =>
        val _result = success.get().asInstanceOf[JRepositories]
        Success(RepositoriesImpl(_result))
      case failure: JTry.Failure[_] =>
        Failure(failure.getCause)
    }
  }

}
