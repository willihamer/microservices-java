FROM openjdk:12
VOLUME /tmp
EXPOSE 9300
ADD ./target/oauth-service-0.0.1-SNAPSHOT.jar oauth-service.jar
ENTRYPOINT ["java","-jar", "/oauth-service.jar"]