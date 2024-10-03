# neoris-prueba-backend

Realizado por: Christian Fabara Riofrío

# Requerimientos:

- Sistema operativo: windows/linux  
- Java: Version: Cualquier version de openjdk 11
- Apache Maven: Version: apache-maven-3.9.6-bin
- Contenerizacion: docker desktop (opcional)

# Datos importantes:

- Script de base de datos: Se encuentra en el directorio del proyecto, se llama BaseDatos.sql

- Archivo de docker: Se encuentra en el directorio del proyecto, se llama DockerFile

# Configuraciones

### Arrancar el proyecto

- 1.1 compilar con este comando:

        mvn clean install package

- - 1.2 Ejecutar con el siguiente comando:

        mvn spring-boot run

### Pasos para instalación con docker:

Ubicarse en el path del proyecto: prueba-backend

- 2.1 Compilar y generar empaquetado:

        mvn clean install package

- 2.2 Generar imagen de Docker-DEV:

        docker build -f .\DockerFile -t neoris-backend:0.0.1-SNAPSHOT .

- 2.3 Deployar en Docker-DEV:

      docker run -d -p 8080 neoris-backend:0.0.1-SNAPSHOT  --name neoris-backend:0.0.1-SNAPSHOT

- 2.4 Deployar con docker compose:

        docker-compose up
