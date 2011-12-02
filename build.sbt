import AssemblyKeys._

seq(assemblySettings: _*)

name := "wavy"

version := "0.1"

libraryDependencies ++= Seq(
  "org.twitter4j" % "twitter4j-core" % "2.2.5",
  "org.twitter4j" % "twitter4j-stream" % "2.2.5"
)
