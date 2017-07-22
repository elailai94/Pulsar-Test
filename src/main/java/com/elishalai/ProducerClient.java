//=============================================================================
// Pulsar-Test
//
// @description: Module for providing functions to work with ProducerClient
//   objects
// @author: Elisha Lai
// @version: 1.0 27/06/2017
//=============================================================================

package com.elishalai;

import java.util.Random;
import java.nio.ByteBuffer;

import com.yahoo.pulsar.client.api.Producer;

public class ProducerClient extends BaseClient {
  private static final int LONG_SIZE = 4;
  private static final String QUEUE_NAME =
    "persistent://sample/standalone/ns1/testQueue";

  private static int numMessages = -1;
  private static int messageSize = -1;
  private static Logger throughputLogWriter = null;
  private Producer producer = null;

  public static void main(String[] arguments) throws Exception {
    try {
      String serverAddress = arguments[0];
      int serverPort = Integer.parseInt(arguments[1]);
      numMessages = Integer.parseInt(arguments[2]);
      messageSize = Integer.parseInt(arguments[3]);

      throughputLogWriter = new Logger("producer-throughput.csv");
      throughputLogWriter.logThroughputLogHeader();

      new ProducerClient(serverAddress, serverPort).run();
      System.out.println("Producer executed successfully.");
    } catch (Exception exception) {
      System.out.println("Producer wasn't able to execute successfully. An error has occurred.");
      exception.printStackTrace();
    } finally {
      throughputLogWriter.close();
    }
  }

  // Constructor
  private ProducerClient(String serverAddress, int serverPort) {
    super(serverAddress, serverPort);
  }

  // Send messages to the server
  private void run() throws Exception {
    try {
      // Initialize the base client
      initialize();

      // Create a producer to produce messages to the queue
      producer = getClient().createProducer(QUEUE_NAME);

      // Produce messages to the queue
      long duration = 0;
      for (int i = 0; i < numMessages; i++) {
        byte[] message = generateMessageBody(messageSize);

        long startTime = System.currentTimeMillis();
        producer.send(message);
        long endTime = System.currentTimeMillis();
        duration += endTime - startTime;       
      }

      // Calculate the throughput of the producer
      double throughput = calculateThroughput(numMessages, duration);
      throughputLogWriter.logThroughputLogEntry(numMessages, duration, throughput);
    } catch (Exception exception) {
      throw exception;
    } finally {
      if (producer != null) {
        producer.close();
      }

      // Clean up the base client
      cleanup();
    }
  }

  // Generate message body
  private byte[] generateMessageBody(int size) {
    byte[] bytes = new byte[size + LONG_SIZE];
    Random random = new Random();

    // Generate random bytes to fill up byte array
    random.nextBytes(bytes);

    // Put current timestamp in the first four bytes of the byte array
    ByteBuffer buffer = ByteBuffer.wrap(bytes);
    buffer.putLong(System.currentTimeMillis());

    return buffer.array();
  }
}
