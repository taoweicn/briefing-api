FROM gradle:5.4.1-jdk12 AS build

RUN mkdir -p /workspace
WORKDIR /workspace
COPY . .

RUN gradle build -x test


FROM openjdk:12 AS deploy

RUN mkdir -p /opt/app
COPY --from=build /workspace/build/libs/briefing-0.0.1-SNAPSHOT.jar /opt/app/app.jar

ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
