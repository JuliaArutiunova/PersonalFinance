FROM eclipse-temurin:21.0.5_11-jre-noble

MAINTAINER ITAcademy

ADD ./target/classifier-service.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/classifier-service.jar"]

EXPOSE 8080 8089