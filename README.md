# EquiposFutbol API

API REST para la aplicación móvil **EquiposFutbol**, desarrollada con **arquitectura hexagonal**,
persistencia en **PostgreSQL** y despliegue en **Render**.

- **Estudiante:** Alexis Cera
- **Correo:** acerap@unicartagena.edu.co
- **Ejercicio asignado:** 8 — EquiposFutbol
- **Corte:** 2
- **URL en producción:** https://equiposfutbol-api.onrender.com

---

## Stack

Java 21 · Spring Boot 4.1.2 · Maven · PostgreSQL 18 · Docker · Render

---

## Arquitectura

El código se separa en cuatro capas. La regla es que las dependencias apuntan **hacia adentro**:
el dominio no conoce a nadie, y la infraestructura depende de él.

```
com.unicartagena.equiposfutbol
├── domain/              Núcleo. Sin Spring, sin base de datos.
│   ├── models/          EquipoFutbol, Usuario
│   ├── valueobjects/    EquipoId, NombreEquipo, Estadisticas, Username, RawPassword...
│   ├── exceptions/      Excepciones de negocio
│   └── ports/out/       Puertos de SALIDA (lo que el dominio necesita del exterior)
│
├── application/         Casos de uso
│   ├── port/in/         Puertos de ENTRADA
│   └── service/         EquipoService, UserService, comandos, queries y mappers
│
├── adapter/             Implementaciones de los puertos de salida
│   ├── persistence/jpa/ Entidades JPA, repositorios Spring Data y adaptadores
│   ├── security/        BCrypt y generador de claves temporales
│   └── notification/    Envío de correo SMTP
│
├── entrypoint/rest/v1/  Adaptador de ENTRADA: controllers, DTOs y manejo de errores
└── config/              Ensamblado de beans
```

**Por qué importa:** `UserService` y `EquipoService` no tienen una sola anotación de Spring.
Se construyen a mano en `ApplicationConfiguration`. Eso mantiene la lógica de negocio
independiente del framework: cambiar PostgreSQL por otra base solo implica escribir otro
adaptador, sin tocar el dominio.

---

## Reglas de negocio

- `partidosGanados + empates` nunca puede superar `partidosJugados`. Lo valida el value object
  `Estadisticas`, así que es imposible guardar un equipo incoherente.
- El nombre del equipo se guarda también normalizado (minúsculas, sin espacios extra) con índice
  único, para impedir duplicados tipo "Junior FC" y "junior fc".
- `partidosPerdidos` y `promedioGoles` no se almacenan: se calculan.

---

## Endpoints

### Autenticación — `/api/v1/auth`

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/login` | Inicia sesión. 200 si es correcto, 401 si no. |
| POST | `/recuperar-clave` | Envía una clave temporal al correo registrado. |

### Usuarios — `/api/v1/users`

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/` | Registrar usuario |
| GET | `/{userId}` | Consultar usuario |
| GET | `/` | Listar usuarios |
| PUT | `/{userId}` | Actualizar nombre y correo |
| DELETE | `/{userId}` | Eliminar usuario |
| GET | `/reportes/por-nombre?fragmento=X` | **Reporte 1:** usuarios cuyo nombre contiene X |
| GET | `/reportes/por-fecha?desde=X&hasta=Y` | **Reporte 2:** usuarios registrados entre dos fechas |

### Equipos — `/api/v1/equipos`

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/` | Crear equipo |
| GET | `/{equipoId}` | Consultar equipo |
| GET | `/` | Listar equipos |
| PUT | `/{equipoId}` | Actualizar equipo |
| DELETE | `/{equipoId}` | Eliminar equipo |
| GET | `/reportes/por-pais?pais=X&categoria=Y` | **Reporte 3:** equipos por país, categoría opcional |
| GET | `/reportes/por-rendimiento?minimoGoles=N&minimoCampeonatos=M` | **Reporte 4:** equipos por desempeño |

### Códigos de respuesta

| Código | Cuándo |
|---|---|
| 200 | Consulta o actualización correcta |
| 201 | Recurso creado |
| 204 | Eliminado, o recuperación de clave procesada |
| 400 | Datos inválidos o regla de negocio violada |
| 401 | Credenciales incorrectas |
| 404 | El recurso no existe |
| 409 | Ya existe un recurso con ese nombre o correo |

### Ejemplo — crear equipo

```json
POST /api/v1/equipos
{
  "nombre": "Junior FC",
  "eslogan": "El tiburon del Caribe",
  "tecnico": "Juan Perez",
  "pais": "Colombia",
  "ciudad": "Barranquilla",
  "categoria": "PRIMERA_A",
  "goles": 42,
  "partidosJugados": 20,
  "partidosGanados": 12,
  "empates": 5,
  "campeonatos": 10,
  "expulsiones": 3
}
```

Categorías válidas: `PRIMERA_A`, `PRIMERA_B`, `JUVENIL`, `INFANTIL`, `AFICIONADO`.

---

## Ejecutar en local

### Requisitos

- JDK 21
- Maven 3.9 o superior
- PostgreSQL 14 o superior

### Pasos

```bash
git clone https://github.com/AlexisJuliette/equiposfutbol-api.git
cd equiposfutbol-api
```

Crea la base de datos:

```sql
CREATE DATABASE equiposfutbol;
```

Ejecuta:

```bash
mvn spring-boot:run
```

La API queda en `http://localhost:8080`. Hibernate crea las tablas solo.

### Variables de entorno

| Variable | Por defecto | Para qué sirve |
|---|---|---|
| `JDBC_DATABASE_URL` | `jdbc:postgresql://localhost:5432/equiposfutbol` | Cadena de conexión |
| `JDBC_DATABASE_USERNAME` | `postgres` | Usuario de la base |
| `JDBC_DATABASE_PASSWORD` | `postgres` | Contraseña de la base |
| `PORT` | `8080` | Puerto del servidor |
| `SMTP_HOST` | vacío | Servidor de correo. Si está vacío, no se envían correos. |
| `SMTP_PORT` | `587` | Puerto SMTP |
| `SMTP_USERNAME` | vacío | Cuenta emisora |
| `SMTP_PASSWORD` | vacío | Contraseña de aplicación del correo |
| `MAIL_FROM` | `no-reply@equiposfutbol.app` | Remitente |

---

## Despliegue en Render

El `Dockerfile` usa construcción en dos etapas: la primera compila con Maven, la segunda deja
solo el JRE y el `.jar`. La imagen final no contiene el código fuente ni herramientas de
compilación.

Configuración del servicio:

- **Language:** Docker
- **Branch:** `main`
- **Region:** Oregon (la misma de la base de datos)
- **Instance Type:** Free

Render entrega la conexión de Postgres como `postgresql://usuario:clave@host/base`, formato que
**JDBC no entiende**. Hay que traducirla a `jdbc:postgresql://host:5432/base` y pasar usuario y
contraseña como variables aparte.

Con Auto-Deploy activado, cada `git push` sobre `main` redespliega el servicio.

> El plan gratuito suspende el servicio tras 15 minutos sin tráfico; la siguiente petición lo
> despierta en aproximadamente un minuto. La aplicación móvil contempla ese retraso.

---

## Seguridad

- Las contraseñas se guardan cifradas con **BCrypt**, nunca en texto plano, y no aparecen en
  ninguna respuesta de la API.
- `RawPassword` sobreescribe `toString()` para que una contraseña no termine en un log por error.
- El login responde lo mismo si el usuario no existe o si la clave está mal, para no revelar qué
  usuarios están registrados.
- `/recuperar-clave` responde 204 exista o no el correo, por la misma razón.
- Las credenciales de base de datos y correo viven solo como variables de entorno en Render;
  nunca están en el repositorio.
- El manejador global de errores devuelve mensajes genéricos, sin trazas ni nombres de clases
  internas.

### Limitación conocida

Los endpoints **no exigen autenticación**: cualquiera con la URL puede consultarlos y
modificarlos. El login valida credenciales pero no protege las rutas. La solución sería emitir un
token JWT al iniciar sesión y exigirlo en cada petición mediante un filtro de seguridad. No se
implementó porque excede el alcance de la actividad.