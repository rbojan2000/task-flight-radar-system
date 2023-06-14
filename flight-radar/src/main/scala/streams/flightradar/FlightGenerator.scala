package streams.flightradar

import com.typesafe.scalalogging.LazyLogging
import radar.{AirportUpdateEvent, FlightUpdateEvent}

import java.time.{Instant, LocalDate, LocalDateTime, ZoneId, ZoneOffset, ZonedDateTime}
import java.util.UUID
import scala.util.Random

trait FlightGenerator extends LazyLogging {

  final val AVERAGE_RADIUS_OF_EARTH_KM: Int = 6371
  final val AVERAGE_AIRSPEED_KMH: Int = 900
  final val START_DATETIME: LocalDateTime = LocalDateTime.of(2023, 4, 5, 14, 30, 30)
  final val END_DATETIME: LocalDateTime = LocalDateTime.of(2023, 4, 5, 17, 30, 30)
  val FLIGHT_STATUSES: Seq[String] = Seq("SCHEDULED", "LANDED", "LATE", "CANCELED")

  val random: Random = new Random(42)

  def generateFlight(supportedAirports: Seq[AirportUpdateEvent]): FlightUpdateEvent = {
    val destination: Array[AirportUpdateEvent] = random.shuffle(supportedAirports).take(2).toArray
    val start: AirportUpdateEvent = destination(0)
    val end: AirportUpdateEvent = destination(1)
    val id = UUID.nameUUIDFromBytes(random.nextString(10).getBytes)
    val departureTime = random.between(
      START_DATETIME.toInstant(ZoneOffset.UTC).toEpochMilli,
      END_DATETIME.toInstant(ZoneOffset.UTC).toEpochMilli
    )
    FlightUpdateEvent(
      id = id.toString,
      date = Some(millisToDate(departureTime).toString),
      destination = formatDestination(start, end),
      timezones = formatTimezone(start, end),
      status = Some(randomFlightStatus),
      gate = Some(id.toString.substring(0, 3)),
      airline = Some(id.toString.substring(0, 6)),
      STD = Some(departureTime),
      STA = Some(approximateArrivalTime(departureTime, start, end))
    )
  }

  def millisToDate(timestamp: Long)(implicit zone: ZoneId = ZoneOffset.UTC): LocalDate =
    ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), zone).toLocalDate

  def formatDestination(start: AirportUpdateEvent, end: AirportUpdateEvent): String =
    f"${start.city}/${start.country}(${start.code})->${end.city}/${end.country}(${end.code})"

  def formatTimezone(start: AirportUpdateEvent, end: AirportUpdateEvent): String =
    f"${start.tz.getOrElse(None)}->${end.tz.getOrElse(None)}"

  def randomFlightStatus: String =
    FLIGHT_STATUSES(random.nextInt(FLIGHT_STATUSES.size))

  def approximateArrivalTime(
      departureTimeMs: Long,
      start: AirportUpdateEvent,
      end: AirportUpdateEvent
  ): Long = {
    val distance = calcHaversineDistance(
      start.latitude.get,
      start.longitude.get,
      end.latitude.get,
      end.longitude.get
    )
    logger.info(f"Approximate distance between ${start.city} and ${end.city} is $distance KM.")

    val durationRation: Double = distance.toDouble / AVERAGE_AIRSPEED_KMH
    val hours: Int = durationRation.intValue
    val minutes: Int = ((durationRation - hours) * 60).toInt

    departureTimeMs + (hours * 1000 * 60 * 60) + (minutes * 1000 * 60)
  }

  def calcHaversineDistance(
      sourceLat: Double,
      sourceLong: Double,
      destLat: Double,
      destLong: Double
  ): Long = {
    val lngDistance: Double = Math.toRadians(sourceLong - destLong)
    val latDistance: Double = Math.toRadians(sourceLat - destLat)
    val sinLat = Math.sin(latDistance / 2)
    val sinLng = Math.sin(lngDistance / 2)

    val a: Double = sinLat * sinLat + Math.cos(Math.toRadians(sourceLat)) * Math.cos(
      Math.toRadians(destLat)
    ) * sinLng * sinLng

    val c: Double = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

    (AVERAGE_RADIUS_OF_EARTH_KM * c).round.toInt
  }
}
