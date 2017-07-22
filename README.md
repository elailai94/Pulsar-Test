# Pulsar-Tests
### About
This repository contains a program to measure the performance and scalability of [Pulsar](http://pulsar.incubator.apache.org/). It is written entirely in Java.

### Setup
In a new terminal window, download the Pulsar binaries and install the packages by running:
```Bash
./setup.sh
```

### Execution
In the same terminal window, start the Pulsar standalone server by running:
```Bash
bin/pulsar standalone
```
In a new terminal window, start the consumer by running:
```Bash
mvn -DskipTests -Pconsumer package
```
In a new terminal window, start the producer by running:
```Bash
mvn -DskipTests -Pproducer package

### License
* This repository is licensed under the [MIT License](https://github.com/elailai94/Pulsar-Tests/blob/master/LICENSE.md).
