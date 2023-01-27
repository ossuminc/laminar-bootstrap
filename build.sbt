// enable the Scala.js plugin that’s in 'project/plugins.sbt'
enablePlugins(ScalaJSPlugin)

// this specifies the name of the `main` method
ThisBuild / maintainer := "Ossum Inc."
ThisBuild / organization := "com.ossum"
Global / onChangedBuildSource := ReloadOnSourceChanges
Global / excludeLintKeys ++= Set(Global / organization, Global / maintainer)


lazy val lamboot = project
  .in(file("."))
  .configure(Compile.scala(0))
  .settings(
    name := "lamboot",
    // this states that this project has a Scala.js application with a `main` method
    scalaJSUseMainModuleInitializer := true,
    // this defines the Dom for Node execution
    jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv(),
    testFrameworks += new TestFramework("utest.runner.Framework"),
    libraryDependencies += "com.raquo" %%% "laminar" % V.laminar,
    libraryDependencies ++= Deps.basicTestingDependencies
    // mainClass := Some("com.ossum.lamboot.Main")
  )

