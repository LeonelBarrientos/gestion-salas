# gestion-salas

Microservicio Spring Boot (Java 17, Maven) con la gestión de salas de la app `gestiondereservas`.

- Puerto: **8082**
- Base de datos por defecto: **H2 en memoria** (no hay que instalar nada; se reinicia al apagar el servicio).
  Para MySQL: `mvn spring-boot:run -Dspring-boot.run.profiles=mysql` (ajusta usuario/clave en `application-mysql.properties`).
- Al arrancar carga las 8 salas de ejemplo de la app (C204, C205, F101, D302, A105, S210, G104, T305) si la tabla está vacía. Se desactiva con `app.seed=false`.
- Swagger: http://localhost:8082/swagger-ui.html

## Ejecutar

```
mvn spring-boot:run
```

## Endpoints

| Método | Ruta | Qué hace |
|---|---|---|
| GET | `/api/salas?busqueda=c20` | Lista salas (busca por código o nombre, sin importar mayúsculas) |
| GET | `/api/salas/{id}` | Una sala con su equipamiento |
| GET | `/api/salas/codigo-disponible?codigo=C204&excluirId=0` | `true` si el código no está en uso (`excluirId` sirve al editar) |
| POST | `/api/salas` | Crea una sala (201) |
| PUT | `/api/salas/{id}` | Actualiza la sala y reemplaza su equipamiento |
| PATCH | `/api/salas/{id}/estado?activa=false` | Activa o desactiva la sala |

Ejemplo de cuerpo para POST/PUT:

```json
{
  "codigo": "c206",
  "nombre": "Lab. Redes",
  "area": "INFORMATICA",
  "capacidad": 28,
  "activa": true,
  "imagenUri": null,
  "equipamiento": [
    { "nombre": "Proyector", "ocupado": false },
    { "nombre": "Computadores", "ocupado": true }
  ]
}
```

`area` acepta: INFORMATICA, FOTOGRAFIA, DISENO, COMUNICACIONES, SALUD, GASTRONOMIA, TURISMO, CONSTRUCCION.

## Reglas (las mismas de `ValidacionesSala.kt`)

- Código y nombre obligatorios; capacidad entre 1 y 200.
- El código se guarda en mayúsculas y sin espacios, así `c206` y `C206` son la misma sala (409 si ya existe).
- No puede haber dos equipos con el mismo nombre en una sala (400).

Errores: `404` no existe, `409` código repetido, `400` datos inválidos. El cuerpo es `{ "status", "mensaje", "detalles" }`.
