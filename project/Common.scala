import play.sbt.PlayImport.specs2
import play.sbt.PlayImport._
import sbt.Keys._
import sbt._


object Common {
  val commonDependencies = Seq(
    "joda-time" % "joda-time" % "2.9.1",
    "mysql" % "mysql-connector-java" % "5.1.39",
    "cglib" % "cglib" % "3.2.3",
    "com.mchange" % "c3p0" % "0.9.5.2",
    //slick
    "com.typesafe.play" %% "play-slick" % "3.0.0",
    "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0",
      //spec2
    specs2 % Test
  )
  def settings = Seq(
    organization := "loyalcraft.org",
    version := "1.0-SNAPSHOT"
//    doc in Compile <<= target.map(_ / "none"),
//    scalacOptions in ThisBuild ++= Seq(
//      "-target:jvm-1.8",
//      "-encoding", "UTF-8",
//      "-deprecation", // warning and location for usages of deprecated APIs
//      "-feature", // warning and location for usages of features that should be imported explicitly
//      "-unchecked", // additional warnings where generated code depends on assumptions
//      "-language:reflectiveCalls",
//      "-Xlint", // recommended additional warnings
//      "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
//      "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
//      "-Ywarn-inaccessible",
//      "-Ywarn-dead-code"
//    )
  )
}
