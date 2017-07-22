//=============================================================================
// Pulsar-Test
//
// @description: Module for providing functions to work with ConsumerClient
//   objects
// @author: Elisha Lai
// @version: 1.0 27/06/2017
//=============================================================================

package com.elishalai;

import java.nio.ByteBuffer;

import com.yahoo.pulsar.client.api.Consumer;
import com.yahoo.pulsar.client.api.Message;

public class ConsumerClient extends BaseClient {
  private static final String QUEUE_NAME =
    "persistent://sample/standalone/ns1/testQueue";
  private static final String SUBSCRIPTION_NAME = "testSubscription";

  private static int numMessages = -1;
  private static Logger latencyLogWriter = null;
  private static Logger throughputLogWriter = null;
  private Consumer consumer = null;

  public static void main(String[] arguments) throws Exception {
    try {
      String serverAddress = arguments[0];
      int serverPort = Integer.parseInt(arguments[1]);
      numMessages = Integer.parseInt(arguments[2]);

      latencyLogWriter = new Logger("latency.csv");
      latencyLogWriter.logLatencyLogHeader();

      throughputLogWriter = new Logger("consumer-throughput.csv");
      throughputLogWriter.logThroughputLogHeader();

      new ConsumerClient(serverAddress, serverPort).run();
      System.out.println("Consumer executed successfully.");
    } catch (Exception exception) {
      System.out.println("Consumer wasn't able to execute successfully. An error has occurred.");
      exception.printStackTrace();
    } finally {
      if (latencyLogWriter != null) {
        latencyLogWriter.close();
      }

      if (throughputLogWriter != null) {
        throughputLogWriter.close();
      }
    }
  }

  // Constructor
  private ConsumerClient(String serverAddress, int serverPort) {
    super(serverAddress, serverPort);
  }

  // Receive messages from the server
  private void run() throws Exception {
    try {
      // Initialize the base client
      initialize();

      // Create a consumer to consume messages from the queue
      consumer = getClient().subscribe(QUEUE_NAME, SUBSCRIPTION_NAME);

      // Consume messages from the queue
      int messagesCount = 0;
      long duration = 0;
      while (messagesCount < numMessages) {
        long startTime = System.currentTimeMillis();
        Message message = consumer.receive();
        long endTime = System.currentTimeMillis();

        if (message != null) {
          consumer.acknowledge(message);
          messagesCount += 1;
          duration += (endTime - startTime);

          int messageID = messagesCount;
          long sentTimestamp = getSentTimestamp(message);
          long receivedTimestamp = System.currentTimeMillis();
          long latency = receivedTimestamp - sentTimestamp;
          latencyLogWriter.logLatencyLogEntry(messageID, sentTimestamp,
            receivedTimestamp, latency);
        }
      }

      // Calculate the throughput of the consumer
      double throughput = calculateThroughput(numMessages, duration);
      throughputLogWriter.logThroughputLogEntry(numMessages, duration, throughput);
    } catch (Exception exception) {
      throw exception;
    } finally {
      if (consumer != null) {
        consumer.close();
      }

      // Clean up the base client
      cleanup();
    }
  }

  // Get the sent timestamp from the message
  long getSentTimestamp(Message message) throws Exception {
    byte[] messageBody = message.getData();
    ByteBuffer buffer = ByteBuffer.wrap(messageBody);

    return buffer.getLong();
  }
}
