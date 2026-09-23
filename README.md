#  Papelería E-Commerce

## Descripción del contexto o dominio del proyecto

Papelería E-Commerce es una aplicación desarrollada bajo una arquitectura de microservicios que permite gestionar las operaciones de una tienda de artículos de papelería. El sistema contempla la administración de productos, inventario, ventas, clientes, precios, facturación, envíos, finanzas, notificaciones y seguridad, utilizando un API Gateway como punto de acceso centralizado a todos los servicios.

El objetivo del proyecto es aplicar los principios de arquitectura de microservicios, separación de responsabilidades y desarrollo de APIs REST utilizando Spring Boot, facilitando el mantenimiento, escalabilidad e independencia de cada módulo.

---

# Integrantes

* Kevin Mansilla
* Pablo Velásquez
* Nicolás López

---

# Microservicios implementados

| Microservicio     |  Puerto  |
| ----------------- | :------: |
| API Gateway       | **8080** |
| catalogo-ms       | **8081** |
| crm-ms            | **8082** |
| envios-ms         | **8083** |
| facturacion-ms    | **8084** |
| finanzas-ms       | **8085** |
| inventario-ms     | **8086** |
| notificaciones-ms | **8087** |
| precios-ms        | **8088** |
| seguridad-ms      | **8089** |
| ventas-ms         | **8090** |

---

# Rutas principales del API Gateway

| Ruta                     | Microservicio     |
| ------------------------ | ----------------- |
| `/api/catalogo/**`       | catalogo-ms       |
| `/api/crm/**`            | crm-ms            |
| `/api/envios/**`         | envios-ms         |
| `/api/facturas/**`       | facturacion-ms    |
| `/api/finanzas/**`       | finanzas-ms       |
| `/api/inventarios/**`    | inventario-ms     |
| `/api/notificaciones/**` | notificaciones-ms |
| `/api/precios/**`        | precios-ms        |
| `/api/usuarios/**`       | seguridad-ms      |
| `/api/ventas/**`         | ventas-ms         |

---

# Documentación Swagger

Cada microservicio dispone de documentación mediante Swagger/OpenAPI.

| Microservicio     | URL                                         |
| ----------------- | ------------------------------------------- |
| catalogo-ms       | http://localhost:8081/swagger-ui/index.html |
| crm-ms            | http://localhost:8082/swagger-ui/index.html |
| envios-ms         | http://localhost:8083/swagger-ui/index.html |
| facturacion-ms    | http://localhost:8084/swagger-ui/index.html |
| finanzas-ms       | http://localhost:8085/swagger-ui/index.html |
| inventario-ms     | http://localhost:8086/swagger-ui/index.html |
| notificaciones-ms | http://localhost:8087/swagger-ui/index.html |
| precios-ms        | http://localhost:8088/swagger-ui/index.html |
| seguridad-ms      | http://localhost:8089/swagger-ui/index.html |
| ventas-ms         | http://localhost:8090/swagger-ui/index.html |

---

# Instrucciones básicas de ejecución local

## Requisitos

* Java JDK 21
* Maven 3.9 o superior
* MySQL Server
* Git
* IntelliJ IDEA o Visual Studio Code

## Clonar el proyecto

```bash
git clone https://github.com/kevinw607/EP2-FullStack.git
cd papeleria-ecommerce
```

## Configurar la base de datos

Crear las bases de datos correspondientes para cada microservicio y configurar las credenciales en los archivos `application.properties`.

## Compilar cada microservicio

Desde la carpeta de cada microservicio ejecutar:

```bash
mvn clean install
```

## Ejecutar los microservicios

Levantar los servicios en el siguiente orden:

1. seguridad-ms
2. catalogo-ms
3. crm-ms
4. envios-ms
5. facturacion-ms
6. finanzas-ms
7. inventario-ms
8. notificaciones-ms
9. precios-ms
10. ventas-ms
11. api-gateway

Una vez iniciados todos los servicios, el sistema estará disponible a través del Gateway en:

**http://localhost:8080**

---

# Ejecución remota

Actualmente el proyecto está preparado para ejecutarse en un entorno local. Para un despliegue remoto será necesario configurar un servidor con Java, Maven y MySQL, además de actualizar las direcciones de los microservicios en el API Gateway según el entorno de producción.
