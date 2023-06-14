package streams.flightradar

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import radar.{AirportUpdateEvent, FlightUpdateEvent}

case class FlightUpdateEventProducer(airports: Seq[AirportUpdateEvent])(implicit
    config: Configuration
) extends Thread
    with FlightGenerator
    with LazyLogging {

<<<<<<< HEAD
  final val TOTAL_MESSAGES: Int = 10
=======
  final val TOTAL_MESSAGES: Int = 5000
>>>>>>> 18e79edb9b607ba4f9e695e616e6c9c8ece82bb3
  val producer = new KafkaProducer[String, FlightUpdateEvent](config.streamProperties)

  override def run(): Unit =
    for (i <- 1 to TOTAL_MESSAGES) {
      val flight: FlightUpdateEvent = generateFlight(airports)
      producer.send(
        new ProducerRecord(config.flightRadar.topic.flightUpdateEvent, flight.id, flight)
      )

      logger.info(f"Published update airport event with ID ${flight.id}")
    }
}
