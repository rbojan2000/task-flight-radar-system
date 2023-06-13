ThisBuild / scalaVersion := "2.13.10"
ThisBuild / organization := "smartcat"
ThisBuild / version := "1.0.0"

lazy val root = (project in file("."))
  .settings(
    name := "flight-radar",
    resolvers ++= Seq(
      "confluent" at "https://packages.confluent.io/maven/"
    ),
    libraryDependencies ++= Dependencies.all,
    resolvers ++= Seq("confluent".at("https://packages.confluent.io/maven/")),
    Compile / sourceGenerators += (Compile / avroScalaGenerateSpecific).taskValue,
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", xs @ _*) =>
        xs map {
          _.toLowerCase
        } match {
          case "services" :: xs =>
            MergeStrategy.filterDistinctLines
          case _ => MergeStrategy.discard
        }

      case _ => MergeStrategy.first
    },
    assembly / assemblyJarName := s"${name.value}-${version.value}.jar",
    assembly / test := {}
  )

scalafmtOnCompile := true
