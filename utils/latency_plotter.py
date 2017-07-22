#!/usr/bin/env python3

# =============================================================================
#  Pulsar-Test
#
#  @author: Elisha Lai
#  @description: Utility script to plot the latencies
#  @version: 1.0 27/06/2017
# =============================================================================

import numpy as np
import matplotlib.pyplot as plt


# Plot histogram of latencies
def plot_histogram(data):
    num_messages, latency = np.histogram(data, bins='auto')
    plt.title("Histogram of HornetQ End-to-End Latency")
    plt.xlabel("Latency (ms)")
    plt.ylabel("Number of Messages")
    plt.plot(latency[:-1], num_messages)
    plt.savefig("latency-histogram.png")
    plt.close()


# Plot cumulative distribution function of latencies
def plot_cdf(data):
    sorted_data = np.sort(data)
    num_data_points = data.size
    probability = np.array(range(num_data_points)) / float(num_data_points)
    plt.title("CDF of HornetQ End-to-End Latency")
    plt.xlabel("Latency (ms)")
    plt.ylabel("Probability")
    plt.plot(sorted_data, probability)
    plt.savefig("latency-cdf.png")
    plt.close()


def main():
    # Load data from the CSV file with the latencies
    latencies = np.loadtxt("latency.csv", delimiter=",", skiprows=1, usecols=3)
    plot_histogram(latencies)
    plot_cdf(latencies)


if __name__ == "__main__":
    main()
