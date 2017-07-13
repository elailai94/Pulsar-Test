//=============================================================================
// Pulsar-Tests
//
// @description: Module for providing functions to work with BaseClient objects
// @author: Elisha Lai
// @version: 1.0 27/06/2017
//=============================================================================

package com.elishalai;

import com.yahoo.pulsar.client.api.PulsarClient;

public abstract class BaseClient {
  private String serverAddress = "";
  private int serverPort = -1;

  private PulsarClient client = null;

  // Constructor
  protected BaseClient(String serverAddress, int serverPort) {
    this.serverAddress = serverAddress;
    this.serverPort = serverPort;
  }

  // Perform client initialization
  protected void initialize() throws Exception {
    String connectionURL = String.format("pulsar://%s:%d", serverAddress, serverPort);
    client = PulsarClient.create(connectionURL);
  }

  // Return the client
  protected PulsarClient getClient() {
    return client;
  }

  // Calculate the throughput of the client
  protected double calculateThroughput(int numMessages, long duration) {
    double durationInSeconds = (1.0 * duration) / 1000;
    double throughput = (1.0 * numMessages) / durationInSeconds;

    return throughput;
  }

  // Perform client cleanup
  protected void cleanup() throws Exception {
    if (client != null) {
      client.close();
    }
  }
}