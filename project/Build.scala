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
      libraryDependencies ++= Seq(
        "org.scalaquery" % "scalaquery_2.9.0-1" % "0.9.5",
        "net.databinder" %% "unfiltered-filter" % "0.5.3",
        "net.databinder" %% "unfiltered-jetty" % "0.5.3",
        "org.mortbay.jetty" % "jetty" % "6.1.22" % "container",
        "org.hsqldb" % "hsqldb" % "[2,)"
      )
    )
  ) dependsOn(unfilteredScalate)

}

