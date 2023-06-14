package dao;

import lombok.*;
import radar.AirportUpdateEvent;
import radar.TransformedFlight;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FlightDAO {

    private AirportUpdateEvent airportKpi;
    private TransformedFlight transformedFlight;
}
