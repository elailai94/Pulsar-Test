#!/bin/bash

#=================================================================
# Pulsar-Test
#
# 
# @description: Setup script for Pulsar-Test
# @author: Elisha Lai
# @version: 1.0 29/06/2017
#=================================================================

# Clean project directory
echo "Cleaning project directory..."
rm -rf bin
rm -rf conf
rm -rf data
rm -rf lib
rm -rf *.csv

# Download Pulsar distribution
echo "Downloading Pulsar distribution..."
wget https://github.com/apache/incubator-pulsar/releases/download/v1.18/pulsar-1.18-bin.tar.gz

# Unzip Pulsar distribution
echo "Unzipping Pulsar distribution..."
tar -xvzf pulsar-1.18-bin.tar.gz

# Move Pulsar binaries, configurations and libraries to the project directory
echo "Moving Pulsar binaries, configurations and libraries to the project directory..."
mv pulsar-1.18/bin bin
mv pulsar-1.18/conf conf
mv pulsar-1.18/lib lib

# Remove unnecessary files
echo "Removing unnecessary files..."
rm -rf pulsar-1.18-bin.tar.gz
rm -rf pulsar-1.18

# Install packages based on platform
OS="`uname`"
case $OS in
  'Linux')
    # Resynchronizing package index files from their sources
    echo "Resynchronizing package index files from their sources..."
    sudo apt-get update
    # Install  NTP package
    echo "Installing NTP package..."
    sudo apt-get install ntp
    # Install Maven package
    echo "Installing Maven package..."
    sudo apt-get install maven
    ;;
  'Darwin')
    # Check if Homebrew is already installed
    which -s brew
    if [[ $? != 0 ]] ; then
      # Installing Homebrew
      echo "Installing Homebrew..."
      ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
    fi
    # Install NTP package
    echo "Installing NTP package..."
    brew install ntp
    # Install Maven package
    echo "Installing Maven package..."
    brew install maven
    ;;
  *)
    echo "Please consult documentation regarding installing Maven package for your platform."
    ;;
esac

# Validate project directory
echo "Validating project directory..."
mvn validate

# Cleaning project directory
echo "Cleaning project directory..."
mvn clean

echo "Setup complete."
