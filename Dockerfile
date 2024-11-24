# Usar uma imagem base com JDK para compilar e rodar o Spring Boot
FROM openjdk:17-jdk-slim AS build

# Diretório de trabalho no container
WORKDIR /app

# Copiar o arquivo JAR da aplicação para dentro do contêiner
COPY target/*.jar app.jar

# Expôr a porta configurada no application.properties (5020)
EXPOSE 5020

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
