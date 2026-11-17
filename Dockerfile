# Etapa 1: Construcción con Maven y Java 21
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar configuración de dependencias primero para cachear capas
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final ligera con JRE 21
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el ejecutable generado
COPY --from=build /app/target/*.jar app.jar

# Puerto por defecto (Render lo sobreescribe dinámicamente con la variable PORT)
ENV PORT=8082
EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
