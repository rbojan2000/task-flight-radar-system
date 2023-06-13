package streams.flightradar

import com.typesafe.config.{Config, ConfigObject}
import pureconfig._
import pureconfig.generic.auto._

import java.util.Properties

case class KafkaConfig(
    producer: ConfigObject,
    topic: ConfigObject
)

case class FlightRadarConfig(topic: TopicConfig, airportInputFile: String)

case class TopicConfig(
    flightUpdateEvent: String,
    airportUpdateEvent: String
)

case class Configuration(kafka: KafkaConfig, flightRadar: FlightRadarConfig) {
  protected val producerConfig: Config = kafka.producer.toConfig
  protected val topicConfig: Config = kafka.topic.toConfig

  def streamProperties: Properties = {
    val props = new Properties()

    producerConfig.entrySet.forEach { entry =>
      props.put(entry.getKey, entry.getValue.unwrapped)
    }

    topicConfig.entrySet.forEach { entry =>
      props.put(
        f"topic.${entry.getKey}",
        entry.getValue.unwrapped
      )
    }
    props
  }

}

object Configuration {
  def apply(): Configuration = ConfigSource.default.loadOrThrow[Configuration]
}
