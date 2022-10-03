FROM openjdk:18

COPY application/target/application-0.0.1-SNAPSHOT.jar transactions-history.jar

VOLUME /var/lib/docker/volumes

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "transactions-history.jar"]