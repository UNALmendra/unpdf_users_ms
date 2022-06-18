FROM maven:latest
WORKDIR /unpdfusersms
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run