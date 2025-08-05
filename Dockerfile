# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copy the POM file
COPY pom.xml .

# Download all dependencies
# This is done in a separate step to leverage Docker cache
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Install curl for healthcheck, create data directory, and set up files
RUN apk --no-cache add curl && \
    mkdir -p /app/data && chmod 777 /app/data

# Copy the products.json file directly to the data directory
COPY data/products.json /app/data/products.json

# Also copy as a template for volume initialization (fallback)
COPY data/products.json /app/products.json.template

# Set permissions and verify the files exist and have content
RUN chmod 644 /app/data/products.json && \
    chmod 644 /app/products.json.template && \
    ls -la /app/data/ && ls -la /app/ | grep template && cat /app/data/products.json | head -5

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8085

# Set the entry point
ENTRYPOINT ["java", "-jar", "app.jar"]