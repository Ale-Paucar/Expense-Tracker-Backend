# Expense Tracker API

## Descripción

API REST desarrollada con **Spring Boot** para gestionar **usuarios, categorías y transacciones personales** (ingresos y gastos). Permite filtrar transacciones, generar reportes en formato **.CSV**, este es un proyecto práctico de backend.


---

## Tecnologías

* Java 21
* Spring Boot
* Spring Data
* PostgreSQL

---

## Endpoints principales

### Usuarios

* `GET /users` → Obtener todos los usuarios
* `GET /users/{id}` → Obtener usuario por ID
* `POST /users` → Crear nuevo usuario
* `PUT /users/{id}` → Actualizar usuario
* `DELETE /users/{id}` → Eliminar usuario

---

### Categorías

* `GET /categories` → Listar categorías
* `GET /categories/{id}` → Obtener categoría por ID
* `POST /categories` → Crear categoría
* `PUT /categories/{id}` → Actualizar categoría

---

### Transacciones

* `GET /transaction/{id}` → Obtener transacción por ID
* `GET /transaction` → Listar transacciones con filtros

    * Filtros disponibles:

        * `month`
        * `year`
        * `categoryId`
        * `userId` (obligatorio)
        * `transactionType`
* `POST /transaction` → Crear transacción
* `PUT /transaction/{id}` → Actualizar transacción
* `DELETE /transaction/{id}` → Eliminar transacción

---

### Reportes

* `GET /transaction/report.csv` → Exportar transacciones a CSV

Filtros incorporados:

* `month`
* `year`
* `categoryId`
* `userId` (obligatorio)
* `transactionType`

El archivo CSV se descarga automáticamente con un nombre basado en el usuario.

---

## Nota

* El proyecto usa **DTOs** para separar requests y responses.
* Las variables y métodos están en inglés porque es el idioma más utilizado en el desarrollo de software.
* Cualquier comentario constructivo es bienvenido.



---

