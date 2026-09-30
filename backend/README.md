# ShopKart Backend

Spring Boot 3.2.5 + Java 17 + Spring Security JWT + Spring Data JPA + MySQL.

## Features

- JWT registration/login
- Product catalog and search
- 8 categories
- 120 seeded products
- Cart persistence for authenticated users
- Checkout and order history
- Admin order status APIs
- Newsletter subscription
- CORS for local frontend development

## Run

```bash
mvn clean spring-boot:run
```

API: `http://localhost:8080`

MySQL configuration: `src/main/resources/application.properties`.

The database is `shopkart`; Spring creates the schema automatically with `ddl-auto=update`.

## Demo admin

`admin@shopkart.com` / `Admin@123`

## Main API

| Method | Endpoint | Auth |
|---|---|---|
| POST | `/api/auth/register` | Public |
| POST | `/api/auth/login` | Public |
| GET | `/api/products` | Public |
| GET | `/api/products?category=electronics` | Public |
| GET | `/api/products?search=headphones` | Public |
| GET | `/api/products/{id}` | Public |
| GET | `/api/categories` | Public |
| GET | `/api/cart` | User |
| POST | `/api/cart/items` | User |
| PUT | `/api/cart/items/{productId}?quantity=2` | User |
| DELETE | `/api/cart/items/{productId}` | User |
| DELETE | `/api/cart` | User |
| POST | `/api/orders` | User |
| GET | `/api/orders` | User |
| GET | `/api/orders/{id}` | User |
| GET | `/api/orders/admin/all` | Admin |
| PATCH | `/api/orders/admin/{id}/status?status=SHIPPED` | Admin |
| POST | `/api/newsletter/subscribe` | Public |

Protected requests use:

```text
Authorization: Bearer <JWT token>
```
