FROM --platform=linux/arm64/v8 openjdk:17-ea-slim-buster

WORKDIR /home/app
RUN mkdir files
COPY build/libs/*.war app.war

ENV JAVA_XMS=256m
ENV JAVA_XMX=512m

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.war"]

ENTRYPOINT java -Xms${JAVA_XMS} -Xmx${JAVA_XMX} -jar -XX:+UseSerialGC -Djava.net.preferIPv4Stack=true -Dspring.profiles.active=prod app.jar