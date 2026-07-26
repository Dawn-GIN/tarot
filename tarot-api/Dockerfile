FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY tarot-api-app/target/tarot-api.jar app.jar

ENV JAVA_OPTS="-Xms256m -Xmx512m"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
