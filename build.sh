#!/bin/bash
# Render build script
echo "Starting Maven build..."
mvn clean package -DskipTests -B
echo "Build completed successfully!"
ls -la target/
