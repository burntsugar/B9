package net.catchpole.B9.devices.gps;

import junit.framework.TestCase;
import net.catchpole.B9.devices.gps.listener.DateTimeListener;
import net.catchpole.B9.devices.gps.listener.LocationListener;
import net.catchpole.B9.devices.gps.listener.VectorListener;
import net.catchpole.B9.spacial.Location;
import net.catchpole.B9.spacial.Vector;
import org.junit.Test;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

public class GpsParserTest {
    @Test
    public void testGspReader() throws Exception {
        DataInputStream dataInputStream = new DataInputStream(this.getClass().getResourceAsStream("/telemetry/walkies.gps"));

        final List<Location> locationResults = new ArrayList<Location>();
        final List<Vector> vectorResults = new ArrayList<Vector>();
        final List<String> dateTimeResults = new ArrayList<String>();

        GpsParser gpsParser = new GpsParser();
        gpsParser.addListener(new LocationListener() {
            public void listen(Location location) {
                locationResults.add(location);
            }
        });

        gpsParser.addListener(new VectorListener() {
            public void listen(Vector vector) {
                vectorResults.add(vector);
            }
        });

        gpsParser.addListener(new DateTimeListener() {
            public void listen(String date) {
                dateTimeResults.add(date);
            }
        });

        String line;
        while ((line = dataInputStream.readLine()) != null) {
            gpsParser.parse(line);
        }

        TestCase.assertEquals(773, locationResults.size());
        // floating point values need to be tested with the Almost class. for now, checking the toString
        TestCase.assertEquals("Location -37.872653 145.023383 -57.4 m", locationResults.get(0).toString());

        TestCase.assertEquals(943, vectorResults.size());
        TestCase.assertEquals("Heading 279.71", vectorResults.get(900).getHeading().toString());
        TestCase.assertEquals(5.67d, vectorResults.get(900).getVelocity());

        TestCase.assertEquals(943, dateTimeResults.size());
        TestCase.assertEquals("2015-03-04T09:36:35.000Z", dateTimeResults.get(900));
    }
}
