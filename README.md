# Práctica 2: Creación API REST 
## Carritos de la Compra

Servicio web desarrollado con **Spring Boot** para la gestión de carritos de la compra.

### Endpoints de la API

| Método | Ruta | Descripción | Respuestas |
| :--- | :--- | :--- | :--- |
| **GET** | '/carritos' | Listar todos los carritos | 200 OK |
| **GET** | '/carritos/{id}' | Buscar un carrito por ID | 200 OK, 404 Not Found (si el carrito no existe) |
| **POST** | '/carritos' | Crear un nuevo carrito | 201 Created |
| **PUT** | '/carritos/{id}' | Actualizar un carrito | 200 OK, 404 Not Found (si no existe carrito con ese id) |
| **DELETE** | '/carritos/{id}' | Eliminar un carrito | 204 No Content |

En la creación y actualización de carritos, desde la pestaña de body de Postman (raw-JSON), se ha incluido lo siguiente para actualizar la información de los productos del carrito

'''
{
"idArticulo": 101,
"descripcion": "pan",
"unidades": 1,
"precioFinal": 1.99
}
'''

La API corre por defecto en el puerto '8081' (el 8080 estaba en uso). ('http://localhost:8081/carritos')
Para este proyecto se ha empleado Java 21, Spring Boot 4.0.1, Maven y Hashmaps para persistencia en memoria.