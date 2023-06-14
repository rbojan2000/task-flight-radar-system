package utils;


import radar.FlightUpdateEvent;
import radar.TransformedFlight;

import java.time.Instant;

public class TransformedFlightMapper {

    public static String extractArrivalAirportCode(String inputString) {
        int startIndex = inputString.lastIndexOf("(") + 1;
        int endIndex = inputString.lastIndexOf(")");

        if (startIndex >= 0 && endIndex >= 0 && endIndex > startIndex) {
            return inputString.substring(startIndex, endIndex);
        }

        return null;
    }

    public TransformedFlight transformFlightUpdateEventToTransformedFlight(FlightUpdateEvent flightUpdateEvent) {

        TransformedFlight transformedFlight = new TransformedFlight();

        transformedFlight.setId(flightUpdateEvent.getId());
        transformedFlight.setDate(flightUpdateEvent.getDate());
        transformedFlight.setFrom(extractStartPlaceFromDestination(flightUpdateEvent.getDestination().toString()));
        transformedFlight.setArrivalAirportCode(extractArrivalAirportCode(flightUpdateEvent.getDestination().toString()));
        transformedFlight.setDepartureAirportCode(extractDepartureAirportCode(flightUpdateEvent.getDestination().toString()));
        transformedFlight.setDepartureTime(formatFlightTime(flightUpdateEvent.getSTD()));
        transformedFlight.setArrivalTime(formatFlightTime(flightUpdateEvent.getSTA()));
        transformedFlight.setDuration(calculateDuration(flightUpdateEvent.getSTD(), flightUpdateEvent.getSTA()));
        transformedFlight.setStatus(flightUpdateEvent.getStatus());
        transformedFlight.setGate(flightUpdateEvent.getGate());
        transformedFlight.setAirline(flightUpdateEvent.getAirline());
        transformedFlight.setArrivalTimestamp(flightUpdateEvent.getSTA());
        transformedFlight.setDepartureTimestamp(flightUpdateEvent.getSTD());

        return transformedFlight;
    }

    private String extractStartPlaceFromDestination(String destination) {
        return destination.split("->")[0];
    }

    private String formatFlightTime(long arrivalTimestamp) {
        Instant instant = Instant.ofEpochMilli(arrivalTimestamp);
        return instant.toString();
    }

    private int calculateDuration(long departureTimestamp, long arrivalTimestamp) {
        long durationInMillis = arrivalTimestamp - departureTimestamp;
        return (int) (durationInMillis / (1000 * 60)); // Convert milliseconds to minutes
    }

    public String extractDepartureAirportCode(String inputString) {
        int startIndex = inputString.indexOf("(") + 1;
        int endIndex = inputString.indexOf(")");

        if (startIndex >= 0 && endIndex >= 0 && endIndex > startIndex) {
            return inputString.substring(startIndex, endIndex);
        }

        return null;
    }
}
