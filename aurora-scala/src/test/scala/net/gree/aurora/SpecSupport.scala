package net.gree.aurora

import net.gree.aurora.scala.application.AuroraTableNameServiceImplSpec
import org.seasar.util.io.ResourceUtil
import org.sisioh.config.{ConfigurationResolveOptions, ConfigurationParseOptions, Configuration}

trait SpecSupport {

  def loadConfig(testName: String): Configuration = {
    val resourcePath = ResourceUtil.getResourcePath(classOf[AuroraTableNameServiceImplSpec]) + "_" + testName + ".conf"
    val configuration = Configuration.load(
      resourcePath,
      ConfigurationParseOptions.defaults.setAllowMissing(false),
      ConfigurationResolveOptions.noSystem
    )
    configuration
  }

}
