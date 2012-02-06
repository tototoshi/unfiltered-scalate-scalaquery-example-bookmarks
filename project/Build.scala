import sbt._
import Keys._

object BookmarkProject extends Build {

  lazy val unfilteredScalate =
    uri("git://github.com/unfiltered/unfiltered-scalate#0.5.3")

  lazy val root = Project (
    id = "bookmark",
    base = file ("."),
    settings = Defaults.defaultSettings ++ Seq (
      scalaVersion := "2.9.1",
      resolvers ++= Seq(
        "seratch.github.com releases"  at "http://seratch.github.com/mvn-repo/releases"
      ),
      libraryDependencies ++= Seq(
        "com.github.seratch" %% "scalikejdbc" % "0.2.0" withSources (),
        "commons-dbcp" % "commons-dbcp" % "1.4",
        "net.databinder" %% "unfiltered-filter" % "0.5.3",
        "net.databinder" %% "unfiltered-jetty" % "0.5.3",
        "org.mortbay.jetty" % "jetty" % "6.1.22" % "container",
        "org.hsqldb" % "hsqldb" % "[2,)"
      )
    )
  ) dependsOn(unfilteredScalate)

}

