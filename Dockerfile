FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/chomp-food-docker.jar chomp-food-docker.jar
ENTRYPOINT ["java", "-jar", "chomp-food-docker.jar"]



