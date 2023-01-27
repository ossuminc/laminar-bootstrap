import sbt.Keys._
import sbt._
import sbt.Keys.organizationName
import com.typesafe.sbt.packager.Keys.maintainer
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.nio.Keys.{ReloadOnSourceChanges, onChangedBuildSource}
import sbtdynver.DynVerPlugin.autoImport.dynverSeparator
import sbtdynver.DynVerPlugin.autoImport.dynverSonatypeSnapshots
import sbtdynver.DynVerPlugin.autoImport.dynverVTagPrefix

object V {

  val scalac = "3.2.0"
  val laminar = "0.14.5"
  val scalajsDom = "2.1.0"
  val scalatest = "3.2.14"

}

object Deps {

  val basicTestingDependencies: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % V. scalatest % Test
  )


  // lazy val scalajsDom = "org.scala-js" %%% "scalajs-dom" % V.scalajsDom


  // val testing: Seq[ModuleID] =
  //  Seq(scalactic % "test", scalatest % "test", scalacheck % "test")

}

object Compile {

  def withInfo(p: Project): Project = {
    p.settings(
      ThisBuild / maintainer := "reid@ossum.biz",
      ThisBuild / organization := "com.ossum",
      // ThisBuild / organizationHomepage :=
      //   Some(new URL("https://ossuminc.com/")),
      // ThisBuild / organizationName := "Ossum Inc.",
      ThisBuild / startYear := Some(2023),
      /* ThisBuild / licenses +=
        (
          "Apache-2.0",
          new URL("https://www.apache.org/licenses/LICENSE-2.0.txt")
        ), */
      ThisBuild / versionScheme := Option("early-semver"),
      ThisBuild / dynverVTagPrefix := false,
      // NEVER  SET  THIS: version := "0.1"
      // IT IS HANDLED BY: sbt-dynver
      ThisBuild / dynverSeparator := "-"
    )
  }

  def withScalaCompile(p: Project): Project = {
    p.configure(withInfo).settings(
      scalaVersion := V.scalac,
      scalacOptions := Seq("-deprecation", "-feature", "-Werror", "-explain")
    )
  }

  def mavenPublish(p: Project): Project = {
    p.configure(withScalaCompile).settings(
      ThisBuild / dynverSonatypeSnapshots := true,
      ThisBuild / dynverSeparator := "-",
      maintainer := "reid@ossum.biz",
      organization := "com.ossum",
      organizationName := "Ossum Inc.",
      organizationHomepage := Some(url("https://ossuminc.com")),
      scmInfo := Some(ScmInfo(
        url("https://github.com/ossuminc/laminar-bootstrap"),
        "scm:git:git://github.com/ossuminc/laminar-bootstrap.git"
      )),
      developers := List(Developer(
        id = "reid-spencer",
        name = "Reid Spencer",
        email = "reid@reidspencer.com",
        url = url("https://github.com/reid-spencer")
      )),
      description :=
        """Some utility functionst o make integration Laminar and Bootstrap easier.""".stripMargin,
      licenses := List(
        "Apache License, Version 2.0" ->
          new URL("https://www.apache.org/licenses/LICENSE-2.0")
      ),
      homepage := Some(url("https://github.io/ossuminc/laminar-bootstrap")),

      // Remove all additional repository other than Maven Central from POM
      pomIncludeRepository := { _ => false },
      publishTo := {
        val nexus = "https://oss.sonatype.org/"
        if (isSnapshot.value) {
          Some("snapshots" at nexus + "content/repositories/snapshots")
        } else {
          Some("releases" at nexus + "service/local/staging/deploy/maven2")
        }
      },
      publishMavenStyle := true
    )
  }

  def scala(coveragePercent: Int = 1)(project: Project): Project = {
    project
      .configure(withScalaCompile)
      .configure(mavenPublish)
      .settings (
    )
  }
  def scalajs(project: Project): Project = {
    project
      .enablePlugins(ScalaJSPlugin)
      .configure(scala(0))
      .settings(
        scalaJSUseMainModuleInitializer := true,
        libraryDependencies ++= Seq(
          "org.scala-js" %% "scalajs-dom" % "0.9.1",
          "com.lihaoyi" %% "utest" % "0.4.5" % "test"
        ),
        testFrameworks += new TestFramework("utest.runner.Framework")
      )
  }
}
