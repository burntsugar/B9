package net.catchpole.B9.devices.serial;

import java.io.IOException;

public class PiSerialPort implements SerialPort {
    private String port;

    public PiSerialPort() {
    }

    public PiSerialPort(String port) {
        this.port = port;
    }

    @Override
    public SerialConnection openConnection(int baud) throws IOException, InterruptedException {
        return new PiSerialConnection(port, baud);
    }
}