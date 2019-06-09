FROM hub.c.163.com/library/java:8-alpine

MAINTAINER XXX XXX@imooc.com

ADD server/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]