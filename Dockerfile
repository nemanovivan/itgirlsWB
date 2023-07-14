FROM eclipse-temurin:20-jdk-jammy
COPY /target/WB-0.0.1-SNAPSHOT.jar WB-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","WB-0.0.1-SNAPSHOT.jar"]