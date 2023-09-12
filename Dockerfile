ARG JAVA_BUILD_IMAGE
ARG JAVA_RUNTIME_IMAGE

FROM ${JAVA_BUILD_IMAGE} as build

COPY . .
ARG PACKAGE_REGISTRY_URL
ARG PACKAGE_REGISTRY_TOKEN_NAME
ARG PACKAGE_REGISTRY_TOKEN
RUN ./gradlew --no-build-cache --no-daemon -PPACKAGE_REGISTRY_URL=${PACKAGE_REGISTRY_URL} -PPACKAGE_REGISTRY_TOKEN_NAME=${PACKAGE_REGISTRY_TOKEN_NAME} -PPACKAGE_REGISTRY_TOKEN=${PACKAGE_REGISTRY_TOKEN} installDist

FROM ${JAVA_BUILD_IMAGE} as extractor
COPY --from=build /target/*.jar /app/app.jar
RUN java -Djarmode=layertools -jar /app/app.jar extract --destination /extract/

FROM ${JAVA_BUILD_IMAGE}
WORKDIR app
COPY --from=extractor /extract/dependencies/ ./
COPY --from=extractor /extract/spring-boot-loader/ ./
COPY --from=extractor /extract/snapshot-dependencies/ ./
COPY --from=extractor /extract/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]