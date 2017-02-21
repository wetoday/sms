FROM openjdk:8u121-jdk

MAINTAINER sshyun33@gmail.com

WORKDIR /app

ADD . ./

RUN ./gradlew \
    && echo "org.gradle.daemon=false" >> ~/.gradle/gradle.properties \
    && ./gradlew build clean

ENTRYPOINT ["/bin/bash", "./gradlew"]

CMD ["--version"]
