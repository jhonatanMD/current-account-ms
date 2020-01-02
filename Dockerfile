FROM openjdk:8
VOLUME /tmp
ADD ./target/current-account-ms-0.0.1-SNAPSHOT.jar current-account-ms.jar
ENTRYPOINT ["java","-jar","/current-account-ms.jar"]