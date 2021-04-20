FROM openjdk:11.0.4-jre-slim

COPY target/addressbook.jar /usr/src/addressbook.jar

WORKDIR usr/src
ENTRYPOINT ["java","-Dspring.profiles.active=prd","-jar", "addressbook.jar"]