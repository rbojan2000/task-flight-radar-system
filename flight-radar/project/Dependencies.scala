import sbt._

object Dependencies {
  val pureConfigVersion = "0.15.0"
  val kafkaClientsVersion = "3.3.1"
  val avroSerializerVersion = "5.0.0"
  val scalaLoggingVersion = "3.9.5"

  val pureConfig = "com.github.pureconfig" %% "pureconfig" % pureConfigVersion
  val kafkaClients = "org.apache.kafka" % "kafka-clients" % kafkaClientsVersion
  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion
  val avroSerializer = "io.confluent" % "kafka-avro-serializer" % avroSerializerVersion

  val all: Seq[ModuleID] = Seq(
    pureConfig,
    kafkaClients,
    avroSerializer,
    scalaLogging
  )
}
