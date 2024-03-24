#!/bin/bash

./terminate.sh
# Set the path to the Java executable
JAVA_HOME=/usr/bin/java
export PATH=$JAVA_HOME/bin:$PATH

# Set the path to the JAR files
java -jar server/web-Ecommerce-smartvoucher/target/web-ecommerce-smartvoucher-1.0-SNAPSHOT.jar &

# Wait for all processes to finish
wait