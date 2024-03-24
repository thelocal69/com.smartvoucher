#!/bin/bash

for PORT in 8082; do
  PID=$(lsof -t -i:$PORT)
  if [ -n "$PID" ]; then
    echo "Terminating process $PID running on port $PORT..."
    kill $PID
  fi
done


