package net.catchpole.B9.devices.thrusters;

import net.catchpole.B9.devices.clock.Clock;
import net.catchpole.B9.devices.esc.BlueESCData;
import net.catchpole.B9.devices.gps.SimulationGps;
import net.catchpole.B9.math.HeadingCalculator;
import net.catchpole.B9.math.Normalise;
import net.catchpole.B9.spacial.Heading;

import java.util.Random;

public class SimulationThrusters implements Thrusters {
    private HeadingCalculator headingCalculator = new HeadingCalculator();

    private final Random random = new Random();
    private final Clock clock;
    private final SimulationGps simulationGps;
    private final double metersPerSecond;
    private final int errorDegrees;
    private final double steerBias;

    private double left;
    private double right;
    private long updateTime;

    public SimulationThrusters(Clock clock, SimulationGps simulationGps, double metersPerSecond, int errorDegrees, double steerBias) {
        this.clock = clock;
        this.simulationGps = simulationGps;
        this.metersPerSecond = metersPerSecond;
        this.errorDegrees = errorDegrees;
        this.steerBias = steerBias;
        this.updateTime = clock.getCurrentTime();
    }

    public void update(double left, double right) {
        this.left = left;
        this.right = right;
        adviseNewHeading();
    }

    private void adviseNewHeading() {
        double steer = left - right;
        double steerDegrees = steer * steerBias;
        Heading heading = new Heading( Normalise.degrees(simulationGps.getHeading().getDegrees() + steerDegrees));
        double error = errorDegrees == 0 ? 0 : random.nextInt(errorDegrees) - (errorDegrees/2);
        simulationGps.setHeading(new Heading(Normalise.degrees(heading.getDegrees() + error)));

        long time = clock.getCurrentTime();
        long millis = time - updateTime;
        updateTime = time;
        if (left != 0.0 && right != 0.0 && millis != 0L) {
            double metersTravelled = ((double)millis/1000.0D) * this.metersPerSecond * ((left + right) / 2.0d);
            simulationGps.setLocation(
                    headingCalculator.getLocation(simulationGps.getLocation(), simulationGps.getHeading(), metersTravelled)
            );
        }
    }

    public BlueESCData getLeftData() {
        return getData(left);
    }

    public BlueESCData getRightData() {
        return getData(right);
    }

    private BlueESCData getData(double throttle) {
        return new BlueESCData((int)(100.0d * throttle), 12.1 - throttle, 22 + (throttle * 4), throttle * 10);
    }
}