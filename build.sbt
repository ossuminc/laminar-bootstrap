ThisBuild / scalaVersion := "3.2.1"
Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val lamboot = project.in(file("lamboot"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    libraryDependencies ++= Seq("com.raquo" %%% "laminar" % V.laminar)
  )

lazy val examples = project.in(file("examples"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    scalaJSUseMainModuleInitializer := true
  )
  .dependsOn(lamboot)
