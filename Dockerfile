FROM openjdk:20
EXPOSE 8080
ADD target/bus-management-tool.jar bus-management-tool.jar
ENTRYPOINT["java", "-jar", "/bus-management-tool.jar"]
