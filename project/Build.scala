import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "play2mongo"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    "org.mongodb" % "mongo-java-driver" % "2.9.0-RC1",
    "org.quartz-scheduler" % "quartz" % "2.1.2",
    "org.slf4j" % "slf4j-api" % "1.6.6",
    "ch.qos.logback" % "logback-classic" % "1.0.7",
    "org.springframework" % "spring-context" % "3.1.0.RELEASE",
    "org.springframework" % "spring-tx" % "3.1.0.RELEASE",
    "org.springframework" % "spring-expression" % "3.0.7.RELEASE",
    "org.springframework.data" % "spring-data-mongodb" % "1.1.1.RELEASE"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
	// Spring repositories
	resolvers += "Spring Snapshot" at "http://repo.springsource.org/snapshot",
   ivyLoggingLevel := UpdateLogging.Full


  )

}
