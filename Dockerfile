FROM amazoncorretto:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
RUN mkdir -p /app/src/main/resources
ENTRYPOINT ["java","-jar","/app.jar"]