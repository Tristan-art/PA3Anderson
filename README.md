# isil-2026-ii
DESARROLLO DE APLICACIONES EMPRESARIALES AVANZADO
"# PA3Anderson" 
# PA3 - Sistema de Pedidos Spring Boot (Módulo de Reportes & Seguridad)

Este proyecto corresponde a la **Proceso de Aprendizaje 3 (PA3)** del curso Desarrollo de Aplicaciones Empresariales Avanzadas. Consiste en la extensión de una aplicación Spring Boot existente para incorporar un módulo de analítica y reportes mediante **Spring JDBC (`JdbcTemplate`)**, control de acceso por roles con **Spring Security**, consumo de servicios externos y un conjunto completo de pruebas automatizadas con **JUnit 5, Mockito y MockMvc**.

---

## Tecnologías y Herramientas

* **Java:** 21 (LTS)
* **Framework:** Spring Boot 4.1.1
* **Módulos Spring:** Spring MVC, Spring Data JPA, Spring JDBC (`JdbcTemplate`), Spring Security, Spring HATEOAS
* **Base de Datos:** MySQL 8.0 / 9.x (Producción/Desarrollo) y H2 Database (Pruebas en memoria)
* **Integración Externa:** REST Client (`InventarioRestClient`) hacia `inventario-service`
* **Pruebas Automatizadas:** JUnit 5, Mockito, Spring Security Test, MockMvc
* **Motor de Plantillas:** JSP (JavaServer Pages) & JSTL
* **Gestor de Dependencias:** Apache Maven 3.9+
  



##  Requisitos Previos

1. **JDK 21** instalado y configurado en la variable de entorno `JAVA_HOME`.
2. **MySQL Server** en ejecución (Puerto por defecto `3306`).
3. **Apache Maven** instalado o uso del wrapper `./mvnw`.
4. IDE recomendado: **IntelliJ IDEA** o Eclipse.

---

Usuarios y Roles del Laboratorio (Spring Security)
La aplicación utiliza la configuración de seguridad en memoria definida en SecurityConfig.java. Para acceder a los diferentes recursos, utiliza las siguientes credenciales:
operador    OperadorDemo2026!

consulta    ConsultaDemo2026!


Resumen de Pruebas Destacadas PA3:
PedidoReporteJdbcRepositoryTest.deberiaBuscarPedidosPorClienteYTotalMinimo() - Test de consulta nativa JDBC.

PedidoReporteJdbcRepositoryTest.deberiaListarClientesFrecuentesCorrectamente() - Test de aglomeración de proyecciones.

PedidoReporteServiceImplTest.deberiaLanzarExcepcionCuandoMinPedidosEsInvalido() - Validación unitaria de negocio con Mockito (assertThrows).

PedidoReporteControllerIntegrationTest.deberiaPermitirAccesoAReportePedidosConRolConsulta() - Verificación de autorización HTTP 200 con MockMvc y CONSULTA.

PedidoReporteControllerIntegrationTest.deberiaBloquearAccesoAUsuarioAnonimoAlReporte() - Bloqueo de seguridad y redirección HTTP a anónimos.


base de datos  MySQL :sistema_pedidos
DB_USERNAME root
DB_PASSWORD root
aplicación: XAMP
crear la basae de datos sistema_pedidos en XAMP



Rutas Nuevas e Integraciones

GET /api/v1/orders/busqueda-avanzada?cliente={nombre}&totalMinimo={monto}
GET /api/v1/orders/clientes-frecuentes?minPedidos={cantidad}
GET/api/v1/pedidos/estadisticas/resumen


 Orden de Arranque y Ejecución

1. **Configurar la Base de Datos:**
   * Asegúrate de tener **MySQL Server** en ejecución (puerto `3306`).
   * Ejecuta el script SQL proporcionado en este repositorio para crear la base de datos `sistema_pedidos` y su tabla con datos iniciales.

2. **Configurar Credenciales (Opcional):**
   * Si tus credenciales de MySQL difieren de las por defecto (`root / root`), actualiza los parámetros `DB_USERNAME` y `DB_PASSWORD` en el archivo `src/main/resources/application.properties`.

3. **Compilar y Ejecutar Pruebas Automatizadas :**
   * Valida que todo el conjunto de pruebas pase en verde ejecutando:
     ```bash
     mvn clean test
     ```

4. **Iniciar la Aplicación Spring Boot:**
   * Ejecuta el siguiente comando Maven en la raíz del proyecto:
     ```bash
     mvn spring-boot:run
     ```

5. **Acceder a la Aplicación:**
   * Abre tu navegador web e ingresa a la siguiente URL:
     ```text
     http://localhost:8081/sistema-pedidos/
     ```
   * Utiliza las credenciales de los roles de laboratorio (`consulta` o `operador`) según corresponda para explorar las vistas y reportes.



   Limitaciones Técnicas:
   El sistema presenta una persistencia efímera en memoria mediante H2 durante las pruebas automatizadas y depende de la disponibilidad del microservicio externo en InventarioRestClient para validar stock en tiempo real. Asimismo, las consultas de reportes con JdbcTemplate requieren una adecuada indexación en MySQL para evitar latencias ante grandes volúmenes de datos, mientras que la gestión de accesos está limitada por un esquema de autenticación en memoria en lugar de una persistencia dinámica de usuarios y roles.
