#!/bin/bash
# Render deployment script for Java application

echo "Setting up Java environment..."

# Ensure Java 17 is available
java -version

echo "Building with Maven..."
if [ -f "./mvnw" ]; then
    ./mvnw clean package -DskipTests
else
    mvn clean package -DskipTests
fi

echo "Build complete! Starting application..."
java -Xmx512m -jar target/user-services-1.0.0.jar --spring.profiles.active=production
