# Product Catalog API

## Descripción del Proyecto

Este proyecto es una API RESTful para un catálogo de productos desarrollada con Spring Boot y siguiendo la Arquitectura Hexagonal. Permite consultar un catálogo de productos con operaciones de lectura, implementando patrones de resiliencia y soporte para alta concurrencia.

## Funcionalidades

- **Gestión de Productos**: Operaciones de lectura para productos
  - Listar todos los productos
  - Buscar productos por ID
  - Buscar productos por tipo
  - Soporte para paginación
- **Documentación API**: Interfaz interactiva con Swagger/OpenAPI
- **Manejo de Errores**: Sistema de manejo de excepciones
- **Alta Concurrencia**: Soporte para múltiples solicitudes simultáneas
- **Resiliencia**: Implementación de patrones para garantizar la disponibilidad del servicio

## Tecnologías Utilizadas

- **Java 21**: Aprovechando las características más recientes del lenguaje, incluyendo Virtual Threads
- **Spring Boot**: Framework para el desarrollo de aplicaciones Java
- **Spring Web**: Para la creación de endpoints RESTful
- **Springdoc OpenAPI (Swagger)**: Para la documentación interactiva de la API
- **Jackson**: Para el procesamiento de JSON
- **Resilience4j**: Implementación de patrones de resiliencia (Circuit Breaker, Retry, Rate Limiter)
- **Maven**: Gestión de dependencias y construcción del proyecto
- **JUnit y Mockito**: Para pruebas unitarias e integración

## Arquitectura

El proyecto sigue la **Arquitectura Hexagonal** (también conocida como Puertos y Adaptadores), que proporciona una clara separación de responsabilidades:

### Capa de Dominio
- **Entidades**: Modelos de negocio (Product, Review, Seller, etc.)
- **Puertos de Entrada**: Interfaces de casos de uso que permiten a actores externos interactuar con el dominio
- **Puertos de Salida**: Interfaces de repositorio que permiten al dominio interactuar con sistemas externos
- **Implementaciones de Casos de Uso**: Orquestan la lógica de negocio

### Capa de Infraestructura
- **Adaptadores de Entrada**: Controladores que adaptan las solicitudes HTTP a llamadas de casos de uso
- **Adaptadores de Salida**: Implementaciones de repositorio que adaptan el dominio a sistemas de persistencia
- **Configuración**: Clases de configuración para varios aspectos de la aplicación

### Persistencia
- **Almacenamiento basado en archivos**: Los productos se almacenan en un archivo JSON
- **Estructuras de datos concurrentes**: Para caché en memoria
- **Bloqueos de lectura-escritura**: Para prevenir condiciones de carrera en el acceso a archivos

## Endpoints de la API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET    | /api/products | Obtener todos los productos |
| GET    | /api/products/page | Obtener todos los productos con paginación |
| GET    | /api/products/{id} | Obtener un producto por ID |
| GET    | /api/products/type/{type} | Obtener productos por tipo |
| GET    | /api/products/type/{type}/page | Obtener productos por tipo con paginación |

## Modelo de Producto

El modelo de producto incluye:

- **id**: Identificador único
- **title**: Título del producto
- **description**: Descripción del producto
- **price**: Precio del producto
- **images**: Lista de URLs de imágenes
- **paymentMethods**: Lista de métodos de pago
- **seller**: Información del vendedor
- **stock**: Stock disponible
- **rating**: Calificación promedio
- **reviews**: Lista de reseñas de clientes

## Documentación de la API con Swagger

La documentación interactiva de la API está disponible a través de Swagger UI. Una vez que la aplicación está en ejecución, puedes acceder a ella en:

```
http://localhost:8080/swagger-ui/index.html
```

Esta interfaz proporciona:
- Vista de todos los endpoints disponibles
- Modelos de solicitud y respuesta
- Capacidad para probar la API directamente desde el navegador

## Patrones de Concurrencia y Resiliencia

La aplicación está diseñada para manejar alta concurrencia y ser resiliente a fallos:

- **Virtual Threads**: Utilizados para manejar solicitudes HTTP de manera eficiente
- **Estructuras de Datos Concurrentes**: ConcurrentHashMap para caché en memoria
- **Bloqueos de Lectura-Escritura**: Utilizados para acceso a archivos para prevenir condiciones de carrera
- **Circuit Breaker**: Previene fallos en cascada
- **Retry**: Reintenta automáticamente operaciones fallidas
- **Rate Limiter**: Limita el número de solicitudes para prevenir sobrecarga

## Cómo Ejecutar el Proyecto

### Requisitos Previos

#### Opción 1: Ejecución Local
- Java 21 o superior
- Maven

#### Opción 2: Usando Docker
- Docker
- Docker Compose

### Pasos para Ejecutar

#### Opción 1: Ejecución Local

1. Clonar el repositorio
2. Navegar al directorio del proyecto
3. Ejecutar la aplicación:

```bash
mvn spring-boot:run
```

#### Opción 2: Usando Docker

1. Clonar el repositorio
2. Navegar al directorio del proyecto
3. Construir y ejecutar la aplicación con Docker Compose:

```bash
docker-compose up -d
```

Para detener la aplicación:

```bash
docker-compose down
```

Para ver los logs de la aplicación:

```bash
docker-compose logs -f
```

La aplicación se iniciará en el puerto 8085.

### Verificación de la Instalación

Para verificar que la aplicación está funcionando correctamente, puedes:

1. Acceder a la documentación Swagger: http://localhost:8085/swagger-ui/index.html
2. Acceder al endpoint de Health de actuator: http://localhost:8085/actuator/health


## Manejo de Errores

La aplicación incluye un sistema de manejo de errores:

- **Excepciones Personalizadas**: Excepciones específicas del dominio
- **Manejador Global de Excepciones**: Manejo centralizado de excepciones