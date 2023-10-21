FROM --platform=linux/arm64/v8 openjdk:17-ea-slim-buster

WORKDIR /home/app
RUN mkdir files
COPY build/libs/*.war app.war

ARG ENVIRONMENT
ENV SPRING_PROFILES_ACTIVE=${ENVIRONMENT}

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.war"]