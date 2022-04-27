ARG JAVA_IMAGE=openjdk:17

FROM maven:3.8.5-openjdk-17-slim as build
COPY . .
RUN mvn -B -Dmaven.repo.local=.m2/repository package

FROM ${JAVA_IMAGE} as extractor
COPY --from=build /target/*.jar /app/app.jar
RUN java -Djarmode=layertools -jar /app/app.jar extract --destination /extract/

FROM ${JAVA_IMAGE}
WORKDIR app
COPY --from=extractor /extract/dependencies/ ./
COPY --from=extractor /extract/spring-boot-loader/ ./
COPY --from=extractor /extract/snapshot-dependencies/ ./
COPY --from=extractor /extract/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]