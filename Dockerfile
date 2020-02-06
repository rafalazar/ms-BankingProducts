FROM openjdk:8
VOLUME /tmp
EXPOSE 8104
ADD target/service-BankingProducts-0.0.1-SNAPSHOT.jar msbanking.jar
ENTRYPOINT ["java","-jar","msbanking.jar"]