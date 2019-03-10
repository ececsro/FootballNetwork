# our base image
FROM ubuntu:latest

# copy files required for the app to run
COPY target/ContactService-0.4.1-SNAPSHOT.jar /usr/src/app/

# tell the port number the container should expose
EXPOSE 8443

# run the application
CMD ["java", "-jar", "/usr/src/app/ContactService-0.4.1-SNAPSHOT.jar"]