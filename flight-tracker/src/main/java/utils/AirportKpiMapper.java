package utils;

import radar.AirportKpi;
import radar.AirportUpdateEvent;
import radar.Flight;

public class AirportKpiMapper {

    private static void updateAirportInfo(Flight enrichedFlight, AirportKpi airportKpi) {
        AirportUpdateEvent airportUpdateEvent = enrichedFlight.getAirportUpdateEvent();
        airportKpi.setCode(airportUpdateEvent.getCode());
        airportKpi.setAirport(airportUpdateEvent.getAirport());
        airportKpi.setTz(airportUpdateEvent.getTz());
        airportKpi.setLongitude(airportUpdateEvent.getLongitude());
        airportKpi.setLatitude(airportUpdateEvent.getLatitude());
        airportKpi.setCountry(airportUpdateEvent.getCountry());
        airportKpi.setCity(airportUpdateEvent.getCity());
    }

    private static void updateDeparturesLast5Minutes(Flight enrichedFlight, AirportKpi airportKpi) {
        if (!isScheduledOrLanded(enrichedFlight)) {
            Long departuresLast5Minutes = airportKpi.getDeparturesLast5Minutes();
            airportKpi.setDeparturesLast5Minutes(
                    departuresLast5Minutes != null ? departuresLast5Minutes + 1 : 1L
            );
        }
    }

    private static void updateCanceledFlightsLast5Minutes(Flight enrichedFlight, AirportKpi airportKpi) {
        if (isCanceled(enrichedFlight)) {
            Long canceledFlightsLast5Minutes = airportKpi.getCanceledFlightsLast5Minutes();
            airportKpi.setCanceledFlightsLast5Minutes(
                    canceledFlightsLast5Minutes != null ? canceledFlightsLast5Minutes + 1 : 1L
            );
        } else {
            airportKpi.setCanceledFlightsLast5Minutes(0L);
        }
    }

    private static void updateMinFlightDuration(Flight enrichedFlight, AirportKpi airportKpi) {
        if (isLanded(enrichedFlight)) {
            Long minFlightDuration = airportKpi.getMinFlightDuration();
            long enrichedFlightDuration = enrichedFlight.getTransformedFlight().getDuration();
            if (minFlightDuration == null || minFlightDuration == 0 || enrichedFlightDuration < minFlightDuration) {
                airportKpi.setMinFlightDuration(enrichedFlightDuration);
            }
        }
    }

    private static void updateLastDepartureTimestamp(Flight enrichedFlight, AirportKpi airportKpi) {
        Long lastDepartureTimestamp = airportKpi.getLastDepartureTimestamp();
        long enrichedFlightDepartureTimestamp = enrichedFlight.getTransformedFlight().getDepartureTimestamp();
        if (lastDepartureTimestamp == null || enrichedFlightDepartureTimestamp > lastDepartureTimestamp) {
            airportKpi.setLastDepartureTimestamp(enrichedFlightDepartureTimestamp);
        }
    }

    private static boolean isScheduledOrLanded(Flight enrichedFlight) {
        String status = enrichedFlight.getTransformedFlight().getStatus().toString();
        return status.equals("SCHEDULED") || status.equals("LANDED");
    }

    private static boolean isCanceled(Flight enrichedFlight) {
        String status = enrichedFlight.getTransformedFlight().getStatus().toString();
        return status.equals("CANCELED");
    }

    private static boolean isLanded(Flight enrichedFlight) {
        String status = enrichedFlight.getTransformedFlight().getStatus().toString();
        return status.equals("LANDED");
    }

    public static AirportKpi updateAirportKpi(Flight enrichedFlight, AirportKpi airportKpi) {
        updateAirportInfo(enrichedFlight, airportKpi);
        updateDeparturesLast5Minutes(enrichedFlight, airportKpi);
        updateCanceledFlightsLast5Minutes(enrichedFlight, airportKpi);
        updateMinFlightDuration(enrichedFlight, airportKpi);
        updateLastDepartureTimestamp(enrichedFlight, airportKpi);

        return airportKpi;
    }
}
