name := "leapfin"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "com.github.scopt" %% "scopt" % "3.7.0",
  "io.monix" %% "monix" % "3.0.0-RC1",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)