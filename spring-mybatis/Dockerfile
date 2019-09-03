FROM openjdk:8-jre
MAINTAINER chanshiyu chanshiyucx@gmail.com

COPY target/spring-mybatis-0.0.1-SNAPSHOT.jar /spring-mybatis.jar

ENTRYPOINT ["java","-jar","/spring-mybatis.jar", "-Xms6.5g", "-Xmx6.5g","-xx:NewSize=5.5g","-xx:MaxNewSize=5.5g","-XX:MaxDirectMemorySize=1g"]
