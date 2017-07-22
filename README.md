# Pulsar Test
### About
This repository contains a program to measure the performance and scalability of [Pulsar](http://pulsar.incubator.apache.org/). The program is written entirely in Java. Pulsar is an open-source distributed pub-sub messaging system originally created at [Yahoo](https://developer.yahoo.com/open-source/) and now part of the [Apache Software Foundation](https://apache.org/).

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
```

### Configurations
The server address, server port, number of messages and message size can be configured by modifying lines 52-59 and 94-99 in the **pom.xml** file respectively.

### License
* This repository is licensed under the [MIT License](https://github.com/elailai94/Pulsar-Tests/blob/master/LICENSE.md).
