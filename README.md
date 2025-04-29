# 💸 Tenpo Challenge Fullstack 2025

Este proyecto es una solución completa para el challenge técnico Fullstack propuesto por Tenpo.  
Incluye una API REST en **Spring Boot** y un frontend en **React + TailwindCSS**, conectados a una base de datos **PostgreSQL** mediante Docker.

---

## 📦 Tecnologías utilizadas

- Backend: Java 17, Spring Boot 3, Maven
- Frontend: React + Vite + TypeScript + TailwindCSS
- Base de datos: PostgreSQL 15
- Infraestructura: Docker + Docker Compose
- Documentación: Swagger/OpenAPI (`/swagger-ui`)
- Validaciones con DTO y anotaciones
- Control global de errores (`@RestControllerAdvice`)
- Límite de 100 transacciones por usuario
- Rate limit de 3 requests por minuto por cliente

---

## 🚀 ¿Cómo ejecutar el proyecto localmente?

### Requisitos

- Node.js 18+ (ideal: Node 20)
- Docker + Docker Compose
- Java 17 o superior
- Maven 3.8+

### Clonar el proyecto

```bash
git clone https://github.com/shnitzell/Tenpo-fullstack-challenge
cd challenge-fullstack-2025
```

### Levantar todo con Docker Compose (backend, frontend, base de datos)

```bash
docker-compose up --build
```

- El backend quedará en: `http://localhost:8080`
- El frontend en: `http://localhost:5173` _(puede variar)_
- Swagger UI: `http://localhost:8080/swagger-ui`

### Levantar solo backend y base de datos (modo desarrollo)

```bash
docker-compose -f docker-compose-dev.yml up --build
```

Y correr frontend con Vite:

```bash
cd frontend
npm install
npm run dev
```

---

## 🧪 Interacción con la API

### 🔗 Base URL

```
http://localhost:8080
```

### 📄 Documentación Swagger

Accede a:

```
http://localhost:8080/swagger-ui
```

Para ver y probar todos los endpoints.

---

## 📚 Endpoints disponibles

### Crear transacción

```http
POST /transaction
```

**Request Body (JSON):**

```json
{
  "amount": 15000,
  "commerce": "Supermercado LunaPLata",
  "tenpistaName": "Manuel Turizo",
  "transactionDate": "2025-04-28T14:30:00"
}
```

**Restricciones:**

- `amount ≥ 0`
- `transactionDate` no puede ser en el futuro
- Máximo 100 transacciones por tenpista

---

### Listar transacciones

```http
GET /transaction
```

---

### Buscar transacción por ID

```http
GET /transaction/{id}
```

---

### Actualizar transacción

```http
PUT /transaction/{id}
```

---

### Eliminar transacción

```http
DELETE /transaction/{id}
```

---

## 🛡️ Validaciones y control de errores

- Todos los errores se devuelven con un formato estructurado:

```json
{
  "timestamp": "2025-04-28T21:09:46.473Z",
  "status": 400,
  "error": "Validation Error",
  "message": {
    "amount": "Amount must be zero or positive"
  }
}
```

---

## 🚦 Rate Limiting

Cada cliente (IP) está limitado a **3 requests por minuto**.  
Si se supera el límite, se devuelve:

```http
429 Too Many Requests
```

---

## 🎯 Notas finales

- Frontend permite listar y crear transacciones desde un formulario simple.
- Se usa React Query + Axios para comunicación.
- TailwindCSS para estilos limpios y modernos.
- CORS está habilitado para permitir integración local con Vite (`localhost:5173`, `5174`, etc.).

---

## 👨‍💻 Autor

Desarrollado por Luis Eduardo Romero Castro
Challenge Fullstack 2025 - Tenpo 🚀
