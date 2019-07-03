FROM openjdk
ADD target/minimum-over-capacity-1.0-SNAPSHOT.jar /data/app.jar
WORKDIR /data

#
# We can have some env vars here
#
CMD exec java ${JAVA_OPTS} -jar app.jar --spring.config.name=application