package streams.flightradar

import com.typesafe.scalalogging.LazyLogging
import radar.AirportUpdateEvent

object Application extends AirportParser with LazyLogging {
  def main(args: Array[String]): Unit = {
    val airports: Seq[AirportUpdateEvent] = parse(config.flightRadar.airportInputFile)
    logger.info("Starting producers.")

    AirportUpdateEventProducer(airports).start()
    FlightUpdateEventProducer(airports).start()
  }
}
