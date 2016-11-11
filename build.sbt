import NativePackagerKeys._

name := "crawler-front"

val app = crossProject.settings(
  unmanagedSourceDirectories in Compile +=
    baseDirectory.value  / "shared" / "main" / "scala",
  libraryDependencies ++= Seq(
    "com.lihaoyi" %%% "scalatags" % "0.4.6",
    "com.lihaoyi" %%% "upickle" % "0.2.7",
    "io.circe" %%% "circe-core" % "0.2.0",
    "io.circe" %%% "circe-parse" % "0.2.0",
    "io.circe" %%% "circe-generic" % "0.2.0",
    "com.lihaoyi" %%% "scalarx" % "0.2.8",
    "com.github.japgolly.scalacss" %%% "core" % "0.5.1",
    "com.github.japgolly.scalacss" %%% "ext-scalatags" % "0.5.1"
  ),
  scalaVersion := "2.11.8"
).jsSettings(
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.8.0"
  )
).jvmSettings(
  libraryDependencies ++= Seq(
    "com.typesafe.akka" % "akka-http-experimental_2.11" % "2.4.11",
    "de.heikoseeberger" % "akka-http-circe_2.11" % "1.10.1"
  )
)

lazy val appJS = app.js
lazy val appJVM = app.jvm.settings(
  (resources in Compile) += (fastOptJS in (appJS, Compile)).value.data
)