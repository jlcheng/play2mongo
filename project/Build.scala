import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "playmongo"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "org.mongodb" % "mongo-java-driver" % "2.9.0-RC1",
      "org.quartz-scheduler" % "quartz" % "2.1.2",
      "org.slf4j" % "slf4j-api" % "1.6.6",
      "ch.qos.logback" % "logback-classic" % "1.0.7",
      "org.springframework" % "spring-context" % "3.1.2.RELEASE",
      "org.springframework.data" % "spring-data-mongodb" % "1.0.3.RELEASE"

    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
    )

}
