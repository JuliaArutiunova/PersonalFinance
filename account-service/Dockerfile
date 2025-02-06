FROM eclipse-temurin:21.0.5_11-jre-noble

MAINTAINER ITAcademy

ADD ./target/account-service.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/account-service.jar"]

EXPOSE 8080 8089