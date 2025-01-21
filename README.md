Proyecto: Catálogo de Libros

Este es un proyecto desarrollado en Java Spring Boot con PostgreSQL para gestionar un catálogo de libros. Incluye funcionalidades como la búsqueda, registro, y listados de libros y autores, y se integra con la API Gutendex para obtener información adicional sobre libros.

Características del Proyecto

Funcionalidades Principales

Buscar libro por título:

Si el libro no existe, se muestra un mensaje "Libro no encontrado".

Si el libro es encontrado, se registra automáticamente en la base de datos.

Listar libros registrados.

Listar autores registrados.

Listar autores vivos en un determinado año.

Listar libros por idioma:

Los idiomas deben ser ingresados como códigos (e.g., es para español, en para inglés).

En la consola se muestra tanto el código como el idioma.

Evitar duplicados:

Si se intenta registrar un libro ya existente, aparece un mensaje informativo.

Integración con Gutendex

Al iniciar la aplicación, los datos de libros se obtienen desde la API Gutendex y se guardan en la base de datos.

Tecnologías Utilizadas

Java 17

Spring Boot (con JPA, HikariCP, y otros módulos relevantes).

PostgreSQL para la persistencia de datos.

Gutendex API para datos adicionales.

RestTemplate para realizar las peticiones HTTP.

Requisitos Previos

Java 17 instalado.

PostgreSQL configurado con:

Base de datos: literatura_db

Usuario: postgres

Contraseña: Juanda04.

IntelliJ IDEA (o cualquier otro IDE compatible).

Configuración del archivo application.properties:

spring.datasource.url=jdbc:postgresql://localhost/literatura_db
spring.datasource.username=postgres
spring.datasource.password=Juanda04.
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.pool-name=MyHikariPool
spring.datasource.hikari.auto-commit=false

Estructura del Proyecto

Modelo: Clases Libro y Autor para representar la información de libros y autores.

Repositorio: Interfaces LibroRepository y AutorRepository para manejar las operaciones con la base de datos.

Servicios:

LibroService: Maneja la lógica de negocio relacionada con los libros.

AutorService: Maneja la lógica de negocio relacionada con los autores.

Clase Principal: Inicializa la aplicación y muestra el menú interactivo.

Ejecución del Proyecto

Clonar el repositorio:

git clone <URL_DEL_REPOSITORIO>
cd <NOMBRE_DEL_PROYECTO>

Compilar y ejecutar:

./mvnw spring-boot:run

Interactúa con la aplicación desde la consola para usar las funcionalidades.

Ejemplo de Uso

Menú Interactivo:

Seleccione una opción:
1 - Buscar libro por título.
2 - Listar libros registrados.
3 - Listar autores registrados.
4 - Listar autores vivos en un determinado año.
5 - Listar libros por idioma.
0 - Salir.

Mensajes Informativos:

Si el libro no existe:

Libro no encontrado.

Si el libro ya está registrado:

Este libro ya está registrado.

Al buscar por idioma:
