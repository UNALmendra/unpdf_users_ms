FROM maven:latest
WORKDIR /Users
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run