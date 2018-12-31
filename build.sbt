Common.settings
name := """my-second-app"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  cache,
  ws
)
libraryDependencies += guice
libraryDependencies ++= Common.commonDependencies
