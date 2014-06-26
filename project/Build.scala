import sbt.Keys._
import sbt._
import xerial.sbt.Sonatype.SonatypeKeys._
import xerial.sbt.Sonatype._

object AuroraBuild extends Build with Dependencies {

  def projectId(state: State) = extracted(state).currentProject.id

  def extracted(state: State) = Project extract state

  lazy val commonSettings = sonatypeSettings ++ Seq(
    organization := "net.gree.aurora",
    profileName := "net.gree.aurora",
    version := "0.0.4-SNAPSHOT",
    scalaVersion := "2.10.4",
    crossScalaVersions := Seq("2.11.1", "2.10.4"),
    javacOptions ++= Seq("-source", "1.7", "-encoding", "UTF-8"),
    scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation", "-encoding", "UTF-8"),
    resolvers ++= Seq(
      "Sonatype Snapshot Repository" at "https://oss.sonatype.org/content/repositories/snapshots/",
      "Sonatype Release Repository" at "https://oss.sonatype.org/content/repositories/releases/",
      "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
      "Seasar Repository" at "http://maven.seasar.org/maven2/"
    ),
    libraryDependencies ++= Seq(
      junit % "test",
      hamcrest % "test",
      specs2 % "test",
      mockito % "test",
      logbackClassic % "test",
      s2util % "test",
      slf4jApi,
      grizzledSlf4j,
      scalaDddbaseCore,
      javaDddbase
    ),
    shellPrompt := {
      "sbt (%s)> " format projectId(_)
    },
    publishMavenStyle := true,
    pomIncludeRepository := { _ => false},
    publishTo <<= version {
      (v: String) =>
        val nexus = "https://oss.sonatype.org/"
        if (v.trim.endsWith("SNAPSHOT"))
          Some("snapshots" at nexus + "content/repositories/snapshots")
        else
          Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    credentials ++= {
      val sonatype = ("Sonatype Nexus Repository Manager", "oss.sonatype.org")
      def loadMavenCredentials(file: java.io.File): Seq[Credentials] = {
        xml.XML.loadFile(file) \ "servers" \ "server" map (s => {
          val host = (s \ "id").text
          val realm = if (host == sonatype._2) sonatype._1 else "Unknown"
          Credentials(realm, host, (s \ "username").text, (s \ "password").text)
        })
      }
      val ivyCredentials = Path.userHome / ".ivy2" / ".credentials"
      val mavenCredentials = Path.userHome / ".m2" / "settings.xml"
      (ivyCredentials.asFile, mavenCredentials.asFile) match {
        case (ivy, _) if ivy.canRead => Credentials(ivy) :: Nil
        case (_, mvn) if mvn.canRead => loadMavenCredentials(mvn)
        case _ => Nil
      }
    },
    pomExtra := {
      <url>https://github.com/gree/aurora</url>
        <licenses>
          <license>
            <name>MIT</name>
            <url>http://opensource.org/licenses/MIT</url>
          </license>
        </licenses>
        <scm>
          <connection>scm:git:github.com/gree/aurora</connection>
          <developerConnection>scm:git:git@github.com:gree/aurora.git</developerConnection>
          <url>https:///github.com/gree/aurora</url>
        </scm>
        <developers>
          <developer>
            <id>j5ik2o</id>
            <name>Junichi Kato</name>
            <url>http://j5ik2o.me</url>
          </developer>
        </developers>
    }
  )

  val core = Project(
    id = "aurora-core",
    base = file("aurora-core"),
    settings = commonSettings ++ Seq(
      crossPaths := false,
      libraryDependencies ++= Seq(
        sisiohConfig
      )
    )
  )


  val coreExample = Project(
    id = "aurora-core-example",
    base = file("aurora-core-example"),
    settings = commonSettings ++ Seq(
      crossPaths := false,
      libraryDependencies ++= Seq(
        mysqlConnector
      )
    )
  ) dependsOn (core)

  val scala = Project(
    id = "aurora-scala",
    base = file("aurora-scala"),
    settings = commonSettings ++ Seq(
    )
  ) dependsOn (core)

  val scalaExample = Project(
    id = "aurora-scala-example",
    base = file("aurora-scala-example"),
    settings = commonSettings ++ Seq(
      crossPaths := false,
      libraryDependencies ++= Seq(
        mysqlConnector
      )
    )
  ) dependsOn (scala)

  val root = Project(
    id = "aurora",
    base = file("."),
    settings = commonSettings ++ Seq(
      publishArtifact := false,
      publish := {},
      publishLocal := {}
    )
  ) aggregate(core, scala)

}
