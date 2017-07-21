#!/bin/bash

#=================================================================
# Pulsar-Tests
#
# 
# @description: Setup script for Pulsar-Tests
# @author: Elisha Lai
# @version: 1.0 29/06/2017
#=================================================================

# Download Pulsar distribution
echo "Downloading Pulsar distribution..."
wget https://github.com/yahoo/pulsar/releases/download/vlatest/pulsar-latest-bin.tar.gz

# Unzip Pulsar distribution
echo "Unzipping HornetQ distribution..."
tar -xvzf pulsar-1.18-bin.tar.gz

