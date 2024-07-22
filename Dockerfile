# Run this command in the project root to build the Docker image
# docker rm -f todoapp-container && docker run  --platform linux/amd64 -d -p 8080:8080 --name todoapp-container todoapp:latest

FROM --platform=linux/amd64 node:20 AS client-builder
WORKDIR /builder
COPY client/package.json client/package-lock.json .
RUN npm install
COPY client/ .
RUN npm run build

FROM --platform=linux/amd64 bellsoft/liberica-runtime-container:jdk-21-crac-cds-slim-glibc AS server-builder
WORKDIR /builder
COPY server/gradle/ gradle
COPY --chmod=+x server/gradlew.bat .
COPY --chmod=+x server/gradlew .
RUN ./gradlew --version --no-daemon
COPY server/ .
RUN ./gradlew clean build --no-daemon
RUN java -Djarmode=tools -jar build/libs/todoapp.jar extract --layers --destination extracted

FROM --platform=linux/amd64 bellsoft/liberica-runtime-container:jdk-21-crac-cds-slim-glibc
WORKDIR /application
COPY --from=client-builder /builder/dist/pages/ templates
COPY --from=client-builder /builder/dist/favicon.ico static/
COPY --from=client-builder /builder/dist/profile-picture.png static/
COPY --from=client-builder /builder/dist/assets/ static/assets
COPY --from=server-builder /builder/extracted/application/ .
COPY --from=server-builder /builder/extracted/dependencies/ .
COPY --from=server-builder /builder/extracted/snapshot-dependencies/ .
COPY --from=server-builder /builder/extracted/spring-boot-loader/ .
ENTRYPOINT ["java", "-jar", "todoapp.jar"]