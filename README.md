B9
===========

Java Robotics library for the Raspberry Pi.

B9 is a Java library to provide robotics devices support and uses the excellent [Pi4J](https://github.com/Pi4J) library.

Stuff that's in there now..

* Controller for Blue Robotics BlueESC (T100/T200) marine thrusters
* GPS parsing and integration via the Pi's onboard serial
* Spacial maths. Decimal degrees, headings - position, target and angle calculations.
* Binary motor control (forward and reverse 'tank tracks')
* Abstract Time (system time, GPS time, simulated time for running test cases)
* Wii Classic Controller (I2C)
* Raspberry Pi camera (via the raspistill executable)
* Compact Binary Symmetric Serialization (for UDP broadcast of telemetry and command packets)

Stuff that's in there but is still in progress..

* Compass Module

This project is part of the [Robotics Mission to Antarctica](http://www.slipperyseal.net/projects/nyx-robot-mission/)

![Nyx2](http://www.slipperyseal.net/images/nyx2-1.jpg "Nyx2")
