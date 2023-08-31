FROM 192.168.5.26/plugins/lvdong-java:17-jre-alpine

RUN mkdir -p /register

WORKDIR /register

ARG JAR_FILE=target/register.jar

COPY ${JAR_FILE} register.jar

EXPOSE 8848

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD java $JAVA_OPTS -jar register.jar
