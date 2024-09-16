## ADMIN API SPEC

## CREATE ADMIN 

Endpoint : POST /api/admins/auth/register

Request Body :

```json
{
  "username": "admin",
  "password": "rahasia123",
  "confirm_password": "rahasia123",
  "fullname": "ADMIN",
  "address": "TCP Blok E5 no 6",
  "email": "admin@gmail.com",
  "isAdmin": true,
  "phone": "0823923232"
}
```

Response Body (Success) :

```json
{
  "data": "Admin berhasil dibuat"
}
```

Response Body (Failed) :

```json
{
  "errors": "username must not be blank"
}
```

## LOGIN ADMIN

Endpoint : POST /api/admins/auth/login


Request Body :

```json
{
  "username": "admin",
  "password": "rahasia123"
}
```

Response Body (Success) :

```json
{
  "data": {
    "token": "Token",
    "expired_at": 2323232
  }
}
```

Response Body (Failed) :

```json
{
  "errors": "username atau password salah"
}
```

## LOGOUT ADMIN

Endpoint : DELETE /api/admins/auth/logout

Request Header :

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data" : "Berhasil logout"
}
```

Response Body (Failed) :

```json
{
  "errors" : "Unauthorized"
}
```

## GET DETAIL ADMIN

Endpoint : GET /api/admins/current-admin

Request Header :

- X-API-TOKEN : Token


Response Body (Success) :

```json
{
  "username": "admin",
  "fullname": "admin New",
  "email": "admin@gmail.com",
  "created_at": "27/08/2000 22:10:05",
  "updated_at": "27/08/2000 22:10:05",
  "phone": "082323232",
  "is_admin" : true,
  "address": "TCP BLOK E5 No 6 new"
}
```

Response Body (Failed) :

```json
{
  "errors" : "Unauthorized"
}
```

## UPDATE ADMIN

Endpoint : PATCH /api/admins/current-admin

Request Header :

- X-API-TOKEN : Token

Request Body :

Request Body :

```json
{
  "fullname": "admin New",
  "email": "admin@gmail.com new",
  "phone": "082323232",
  "address": "TCP BLOK E5 NO 6 new"
}
```

Response Body (Success) :

```json
{
  "username": "admin",
  "fullname": "admin New",
  "email": "admin@gmail.com",
  "phone": "082323232",
  "created_at": "27/08/2000 22:10:05",
  "updated_at": "29/08/2000 22:10:05",
  "isAdmin" : true,
  "address": "TCP BLOK E5 NO 6 new"
}
```

Response Body (Failed) :

```json
{
  "errors" : "Unauthorized"
}
```

## DELETE ADMIN

Endpoint : DELETE /api/admins/current-admin

Response Body (Success) :

```json
{
  "data": "berhasil hapus admin"
}
```

Response Body (Failed) :

```json
{
  "errors": "Unauthorized"
}
```
