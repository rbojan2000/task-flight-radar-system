package streams.flightradar

import com.typesafe.scalalogging.LazyLogging
import radar.AirportUpdateEvent

import scala.io.Source

trait AirportParser extends LazyLogging {
  final val DELIMITER: String = ","

  def parse(fileName: String): Seq[AirportUpdateEvent] = {
    logger.info(s"Reading airports from $fileName.")
    Source
      .fromResource(fileName)
      .getLines
      .map { line =>
        val tokens: Array[String] = line.split(DELIMITER)
        logger.info(s"Parsing airport with code ${tokens(0)}.")

        AirportUpdateEvent(
          code = tokens(0),
          airport = tokens(1),
          city = tokens(2),
          country = tokens(3),
          latitude = Some(tokens(4).toDouble),
          longitude = Some(tokens(5).toDouble),
          tz = Some(tokens(6))
        )
      }
      .toSeq
  }
}
