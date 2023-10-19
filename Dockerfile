FROM --platform=linux/arm64/v8 tomcat:9.0.82-jdk17-corretto

COPY ./build/libs/blog-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080