FROM openjdk:17-oracle
COPY target/winter-hold-0.0.1-SNAPSHOT.jar winterhold.jar
EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -jar winterhold.jar