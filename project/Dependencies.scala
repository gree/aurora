import sbt._

trait Dependencies {

  val logbackVersion = "1.0.7"
  val slf4jVersion = "1.6.6"

  // --- for Logger
  val slf4jApi = "org.slf4j" % "slf4j-api" % slf4jVersion
  val log4jOverSlf4j = "org.slf4j" % "log4j-over-slf4j" % slf4jVersion
  val julToSlf4j = "org.slf4j" % "jul-to-slf4j" % slf4jVersion
  val grizzledSlf4j = "org.clapper" %% "grizzled-slf4j" % "1.0.2"

  val logbackCore = "ch.qos.logback" % "logback-core" % logbackVersion exclude("org.slf4j", "slf4j-api")
  val logbackClassic = "ch.qos.logback" % "logback-classic" % logbackVersion

  // --- for Test
  val junit = "junit" % "junit" % "4.8.1"
  val hamcrest = "org.hamcrest" % "hamcrest-all" % "1.3"
  val specs2 = "org.specs2" %% "specs2" % "2.3.12"
  val mockito = "org.mockito" % "mockito-core" % "1.9.5"
  val s2util = "org.seasar.util" % "s2util" % "0.0.1"

  // --- for ddd
  val javaDddbase = "org.sisioh" % "java-dddbase" % "0.1.0"
  val scalaDddbaseCore = "org.sisioh" %% "scala-dddbase-core" % "0.1.28-SNAPSHOT"

  // --- other
  val eaioUUID = "com.eaio.uuid" % "uuid" % "3.4"
  val scalaToolbox = "org.sisioh" %% "scala-toolbox" % "0.0.9-SNAPSHOT"
  val sisiohConfig = "org.sisioh" %% "sisioh-config" % "0.0.4"

  val mysqlConnector = "mysql" % "mysql-connector-java" % "5.1.27"

}
