FROM java:8
VOLUME /tmp
ADD target/RibbonClient-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar"]