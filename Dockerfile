FROM amazoncorretto:20

ARG JAR_FILE=target/edificad.jar

COPY target/edificad.jar /app/edificad.jar

WORKDIR /app

ENTRYPOINT ["java", "-jar","/app/edificad.jar"]

# Construindo imagem
# docker build -t nome-da-imagem-a-ser-criada .
# ex: docker build -t edificad-docker .
# Criar um arquivo .env e adicionar as variáveis de ambiente necessárias. [conexões de banco, profile]
# docker run --env-file=D:\env\spring-app.env -p 5000:5000 edificad-docker