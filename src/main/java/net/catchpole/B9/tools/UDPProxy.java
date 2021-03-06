package net.catchpole.B9.tools;

import net.catchpole.B9.network.UDPSession;

/**
 * Allows UDP communications between two inbound firewalled applications.
 * Pointing them both to a server running UDPProxy which has a public IP.
 * Packets received will be routed as replies to packets received on the alternate port.
 */
public class UDPProxy {
    public static void main(String[] args) throws Exception {
        final UDPSession udpSession1 = new UDPSession(Integer.parseInt(args[0]));
        final UDPSession udpSession2 = new UDPSession(Integer.parseInt(args[1]));

        udpSession1.setPacketTarget(udpSession2);
        udpSession2.setPacketTarget(udpSession1);

        new Thread() {
            @Override
            public void run() {
                udpSession1.listen();
            }
        }.start();

        udpSession2.listen();
    }
}
