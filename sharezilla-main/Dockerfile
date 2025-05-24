# Stage 1: Build the application
FROM openjdk:17-jdk AS builder

WORKDIR /app

# Copy pom.xml and Maven wrapper to leverage Docker cache
COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw .
COPY mvnw.cmd .

# Copy the rest of the application source code
COPY src src

# Make mvnw executable
RUN chmod +x ./mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the JAR file from the builder stage
# The JAR filename might need adjustment after the first build
COPY --from=builder /app/target/sharezilla-main-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
