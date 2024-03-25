FROM eclipse-temurin:11-alpine
MAINTAINER smartvoucher
RUN apk add --no-cache bash
ARG JAR_FILE
COPY target/web-ecommerce-smartvoucher-1.0-SNAPSHOT.jar web-ecommerce-smartvoucher-1.0.jar
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "web-ecommerce-smartvoucher-1.0.jar"]