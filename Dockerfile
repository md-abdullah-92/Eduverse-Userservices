# Use official OpenJDK image
FROM openjdk:17-jdk-slim

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy pom.xml first for better caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Expose port
EXPOSE 10000

# Set environment variables with defaults
ENV PORT=10000
ENV SPRING_PROFILES_ACTIVE=production

# Run the application
CMD ["sh", "-c", "echo 'Starting application...' && echo \"PORT=${PORT}\" && echo \"DATABASE_URL=${DATABASE_URL}\" && echo \"DB_USERNAME=${DB_USERNAME}\" && echo \"DB_PASSWORD=${DB_PASSWORD}\" && echo \"SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}\" && java -jar target/user-services-1.0.0.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE} --server.port=${PORT}"]
