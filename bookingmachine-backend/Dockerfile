FROM gradle:jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle build -x test

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

ENV DB_FILE_PATH=/app/db/booking.db SQLEAN_LIB_PATH=/app/sqlean/sqlean
EXPOSE $PORT
CMD ["java", "-jar", "app.jar"]