//=============================================================================
// Pulsar-Test
//
// @description: Module for providing functions to work with Logger objects
// @author: Elisha Lai
// @version: 1.0 27/06/2017
//=============================================================================

package com.elishalai;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Logger {
  private BufferedWriter loggerWriter;
  
  // Constructor
  public Logger(String fileName) throws Exception {
    String filePath = String.format("./results/%s", fileName);
    loggerWriter = new BufferedWriter(new FileWriter(new File(fileName)));
  }

  // Log latency log header to the log file
  public void logLatencyLogHeader() throws Exception {
    String header = "message_id,sent_timestamp,received_timestamp,latency";
    logEntry(header);
  }

  // Log latency log entry to the log file
  public void logLatencyLogEntry(int messageID, long sentTimestamp,
    long receivedTimestamp, long latency) throws Exception {
    String entry =
      String.format("%d,%d,%d,%d", messageID, sentTimestamp,
        receivedTimestamp, latency);
    logEntry(entry);
  }

  // Log throughput log header to the log file
  public void logThroughputLogHeader() throws Exception {
    String header = "number_of_messages,duration,throughput";
    logEntry(header);
  }

  // Log throughput log entry to the log file
  public void logThroughputLogEntry(int numMessages, long duration,
    double throughput) throws Exception {
    double durationInSeconds = (1.0 * duration) / 1000;
    String entry =
      String.format("%d,%.2f,%.2f", numMessages, durationInSeconds,
        throughput);
    logEntry(entry);
  }

  // Log entry to the log file
  private void logEntry(String entry) throws Exception {
    loggerWriter.write(entry);
    loggerWriter.newLine();
    loggerWriter.flush();
  }

  // Close the logger
  public void close() throws Exception {
    loggerWriter.close();
  }
}
