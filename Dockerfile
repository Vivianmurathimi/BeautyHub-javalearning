# Step 1 — Start with Eclipse Temurin Java 17
FROM eclipse-temurin:17-jdk-jammy

# Step 2 — Who maintains this
LABEL maintainer="vivianmurathimi@gmail.com"

# Step 3 — Create working directory
WORKDIR /app

# Step 4 — Copy the JAR into container
COPY target/beauty-hub-backend-0.0.1-SNAPSHOT.jar app.jar

# Step 5 — Tell Docker which port our app uses
EXPOSE 8080

# Step 6 — Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]