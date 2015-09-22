package net.catchpole.B9.devices.gps.parser;

import net.catchpole.B9.math.DecimalCoordinates;
import net.catchpole.B9.spacial.Location;

public class LocationParser implements GpsMessageParser<Location> {
    private DecimalCoordinates decimalCoordinates = new DecimalCoordinates();

    public Location parse(String[] line) {
        if (line[3].length() == 1 && line[5].length() == 1) {
            return new Location(decimalCoordinates.latitudeFromDegreesMinutes(line[2], line[3]),
                    decimalCoordinates.longitudeFromDegreesMinutes(line[4], line[5]), Double.parseDouble(line[9]));
        }
        return null;
    }

    public String getKey() {
        return "$GPGGA";
    }
}
