import sbt._

import Keys._
import AndroidKeys._

object General {
  val settings = Defaults.defaultSettings ++ Seq (
    name := "$name$",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "$scala_version$",
    platformName in Android := "android-$api_level$"
  )

  val proguardSettings = Seq (
    useProguard in Android := $useProguard$
  )

  lazy val fullAndroidSettings =
    General.settings ++
    AndroidProject.androidSettings ++
    TypedResources.settings ++
    proguardSettings ++
    AndroidManifestGenerator.settings ++
    AndroidMarketPublish.settings ++ Seq (
      keyalias in Android := "change-me",
      libraryDependencies += "org.scalatest" %% "scalatest" % "1.7.RC1" % "test"
    )
}

object AndroidBuild extends Build {
  lazy val main = Project (
    "$name$",
    file("."),
    settings = General.fullAndroidSettings
  )

  lazy val tests = Project (
    "tests",
    file("tests"),
    settings = General.settings ++
               AndroidTest.settings ++
               General.proguardSettings ++ Seq (
      name := "$name$Tests"
    )
  ) dependsOn main
}
