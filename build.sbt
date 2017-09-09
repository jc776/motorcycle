scalaVersion in ThisBuild := "2.12.3"
version in ThisBuild := "0.0.0-SNAPSHOT"

val circeVersion = "0.8.0"

val shared = crossProject.crossType(CrossType.Pure)
  .settings(
    libraryDependencies ++= Seq(
      "io.circe" %%% "circe-core" % circeVersion,
      "io.circe" %%% "circe-generic" % circeVersion,
      "io.circe" %%% "circe-parser" % circeVersion,
      "biz.enef" %%% "slogging" % "0.5.3",
      "com.lihaoyi" %%% "pprint" % "0.5.0"
    ),
	  addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M10" cross CrossVersion.full),
    watchSources := { Seq(WatchSource(baseDirectory.value / "src")) } // * 1
  )
  .jsSettings(name := "motorcycle-sharedJS")
  .jvmSettings(name := "motorcycle-sharedJVM")

val sharedJS = shared.js 
val sharedJVM = shared.jvm 

val client = project 
  .settings(
    name := "motorcycle-client",
    libraryDependencies ++= Seq(
      "me.shadaj" %%% "slinky-core" % "0.1.1",
      "me.shadaj" %%% "slinky-web" % "0.1.1"
    ),
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },
	  // addCompilerPlugin(paradise) or does shared handle it?
    watchSources := { Seq(WatchSource(baseDirectory.value / "src")) } // * 1
  )
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(sharedJS)

val server = project
  .settings(
    name := "motorcycle-server",
    libraryDependencies ++= Seq(
      "io.undertow" % "undertow-core" % "1.4.12.Final",
      "com.lihaoyi" %% "ammonite-ops" % "1.0.2"
      //"org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0"
    ),
    watchSources := { Seq(WatchSource(baseDirectory.value / "src")) } // * 1
  )
  .dependsOn(sharedJVM)

scalacOptions in ThisBuild ++= Seq(
  "-target:jvm-1.8",
  "-encoding", "UTF-8",
  "-unchecked",
  "-feature",
  "-deprecation",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Ywarn-unused",
  "-Ymacro-expand:normal" // Scala-IDE implicits?
)

val start = taskKey[Unit]("Start the server.")
start in Compile := Def.sequential(
  (reStart in Compile in server).toTask("") 
).value
val dev = taskKey[Unit]("On-change build tasks. (should I merge this with 'start'?)")
dev in Compile := Def.sequential(
  (compile in Compile in server),
  (fastOptJS in Compile in client),
  (copyResources in Compile in server) 
).value 

// * 1. sbt keeps locking node_modules/ and bin/.
watchSources := { Seq() } 

// * 2. "This file is derived, are you sure you want to edit it?"
EclipseKeys.eclipseOutput in ThisBuild := Some("target-ide")
  

