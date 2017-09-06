scalaVersion in ThisBuild := "2.12.3"
version in ThisBuild := "0.0.0-SNAPSHOT"

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

val circeVersion = "0.8.0"

val shared = crossProject.crossType(CrossType.Pure)
  .settings(
    libraryDependencies ++= Seq(
      "io.circe" %%% "circe-core" % circeVersion,
      "io.circe" %%% "circe-generic" % circeVersion,
      "io.circe" %%% "circe-parser" % circeVersion,
      "biz.enef" %%% "slogging" % "0.5.3"
    )
  )
  .jsSettings(name := "motorcycle-sharedJS")
  .jvmSettings(name := "motorcycle-sharedJVM")

val sharedJS = shared.js 
val sharedJVM = shared.jvm 

val client = project 
  .settings(
    name := "motorcycle-client",
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },
    libraryDependencies ++= Seq(
      "me.shadaj" %%% "slinky-core" % "0.1.1",
      "me.shadaj" %%% "slinky-web" % "0.1.1"
    )
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
    )
  )
  .dependsOn(sharedJVM)