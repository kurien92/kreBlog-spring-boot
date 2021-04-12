FROM openjdk:15.0.2-slim
WORKDIR /home/app
RUN mkdir files
COPY build/libs/*.war app.war

ARG ENVIRONMENT
ENV SPRING_PROFILES_ACTIVE=${ENVIRONMENT}

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.war"]