import sbt.Keys._
import sbt._
import com.typesafe.sbt.packager.Keys.maintainer
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

import sbtbuildinfo.BuildInfoPlugin
import sbtbuildinfo.BuildInfoPlugin.autoImport._
import sbtbuildinfo.BuildInfoOption.ToJson
import sbtbuildinfo.BuildInfoOption.ToMap
import sbtbuildinfo.BuildInfoOption.BuildTime
import java.util.Calendar


import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport.HeaderLicense
import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport.HeaderLicenseStyle
import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport.headerLicense
import sbt.Keys.organizationName
import sbt.Keys._
import sbt._
import sbt.io.Path.allSubpaths
import scoverage.ScoverageKeys._
import sbtdynver.DynVerPlugin.autoImport.dynverSeparator
import sbtdynver.DynVerPlugin.autoImport.dynverSonatypeSnapshots
import sbtdynver.DynVerPlugin.autoImport.dynverVTagPrefix

object V {

  val laminar = "0.14.5"
  val scalajsDom = "2.1.0"

}



object Deps {

  // val laminar: ModuleID = "com.raquo" %%% "laminar" % V.laminar

  // lazy val scalajsDom = "org.scala-js" %%% "scalajs-dom" % V.scalajsDom


  // val testing: Seq[ModuleID] =
  //  Seq(scalactic % "test", scalatest % "test", scalacheck % "test")

}

object Compile {

  def withInfo(p: Project): Project = {
    p.settings(
      ThisBuild / maintainer := "reid@ossum.biz",
      ThisBuild / organization := "com.ossum",
      ThisBuild / organizationHomepage :=
        Some(new URL("https://ossuminc.com/")),
      ThisBuild / organizationName := "Ossum Inc.",
      ThisBuild / startYear := Some(2023),
      ThisBuild / licenses +=
        (
          "Apache-2.0",
          new URL("https://www.apache.org/licenses/LICENSE-2.0.txt")
        ),
      ThisBuild / versionScheme := Option("early-semver"),
      ThisBuild / dynverVTagPrefix := false,
      // NEVER  SET  THIS: version := "0.1"
      // IT IS HANDLED BY: sbt-dynver
      ThisBuild / dynverSeparator := "-",
      headerLicense := Some(HeaderLicense.ALv2(
        startYear.value.get.toString,
        "Ossum, Inc.",
        HeaderLicenseStyle.SpdxSyntax
      ))
    )
  }

  def withScalaCompile(p: Project): Project = {
    p.configure(withInfo).settings(
      scalaVersion := "2.13.10",
      scalacOptions := Seq("-deprecation", "-feature", "-Werror", "-explain")
    )
  }

  def withCoverage(percent: Int = 50)(p: Project): Project = {
    p.settings(
      coverageFailOnMinimum := true,
      coverageMinimumStmtTotal := percent,
      coverageMinimumBranchTotal := percent,
      coverageMinimumStmtPerPackage := percent,
      coverageMinimumBranchPerPackage := percent,
      coverageMinimumStmtPerFile := percent,
      coverageMinimumBranchPerFile := percent,
      coverageExcludedPackages := "<empty>"
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
      .configure(withCoverage(coveragePercent))
      .configure(mavenPublish)
      .configure(Utils.buildInfo("com.ossum.lamboot"))
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

object Utils {
  def buildInfo(pkg: String)(project: Project): Project = {
    project
      .enablePlugins(BuildInfoPlugin)
      .settings(
        buildInfoObject := "RiddlBuildInfo",
        buildInfoPackage := pkg,
        buildInfoOptions := Seq(ToMap, ToJson, BuildTime),
        buildInfoUsePackageAsPath := true,
        buildInfoKeys ++= Seq[BuildInfoKey](
          name,
          version,
          description,
          organization,
          organizationName,
          BuildInfoKey.map(organizationHomepage) { case (k, v) =>
            k -> v.get.toString
          },
          BuildInfoKey.map(startYear) { case (k, v) =>
            k -> v.map(_.toString).getOrElse("2023")
          },
          BuildInfoKey.map(startYear) { case (k, v) =>
            "copyright" -> s"© ${v.map(_.toString).getOrElse("2023")}-${
              Calendar
                .getInstance().get(Calendar.YEAR)
            } Ossum Inc."
          },
          scalaVersion,
          sbtVersion,
          BuildInfoKey.map(scalaVersion) { case (k, v) =>
            "scalaCompatVersion" -> v.substring(0, v.lastIndexOf('.'))
          },
          BuildInfoKey.map(licenses) { case (k, v) =>
            k -> v.map(_._1).mkString(", ")
          }
        )
      )
  }
}
