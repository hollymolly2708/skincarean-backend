# USER API SPEC

## CREATE

Endpoint :  POST /api/users/auth/register

REQUEST BODY :

```json
{
  "username": "fiqriturhamz",
  "password": "rahasia123",
  "confirm_password": "rahasia123",
  "fullName": "Muhammad Fiqri Turham",
  "address": "TCP Blok E5 no 6",
  "email": "tmuhammadfiqri@gmail.com",
  "phone": "0823923232"
}
```

Response Body (Success) :

```json
{
  "data": "User berhasil dibuat"
}
```

Response Body (Failed) :

```json
{
  "errors": "username must not be blank"
}
```

## LOGIN

Endpoint : POST /api/users/auth/login

Request Body :

```json
{
  "username": "fiqriturhamz",
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

## LOGIN VIA GOOGLE

Endpoint : POST /api/users/auth/login/google/verify

Request body : 

```json
{
  "token" : "asdjkioasjkdiojasodoasdko"
}
```

Response Body (Success) :

```json
{
  "data": {
    "token": "52fec7fc-eb03-44b7-8a05-78096fe4ba16",
    "tokenExpiredAt": 1730700513192,
    "tokenCreatedAt": 1730657313192
  },
  "errors": null,
  "paging": null,
  "isSuccess": true
}
```

Response Body (Failed) : 

```json
{
  "errors" : "Unauthorized"
}
```

## LOGOUT

Endpoint : DELETE /api/users/auth/logout

Request Header :

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data": "logout berhasil"
}
```

Response Body (Failed) :

```json
{
  "errors": "Internal server error"
}
```

## GET DETAIL USER

Endpoint : /api/users/current-user

Request Header :

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "username": "fiqriturhamz",
  "fullName": "Muhammad Fiqri Turham",
  "email": "tmuhammadfiqri@gmail.com",
  "created_at": "27/08/2000 22:10:05",
  "updated_at": "27/08/2000 22:10:05",
  "phone": "082323232",
  "address": "TCP BLOK E5 No 6"
}
```

Response Body (Failed) :

```json
{
  "errors": "Unauthorized"
}
```

## DELETE USER

Endpoint : /api/users/current-user

Request Header :

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data": "User berhasil dihapus"
}

```

Response Body (Failed) :

```json
{
  "errors": "unauthorized"
}
```

## UPDATE USER

Endpoint : PATCH /api/users/current-user

Request Header :

- X-API-TOKEN : Token

Request Body :

```json
{
  "fullName": "Muhammad Fiqri Turham new",
  "email": "tmuhammadfiqri@gmail.com",
  "phone": "082323232",
  "address": "TCP BLOK E5 NO 6 new"
}
```

Response Body (Success) :

```json
{
  "username": "fiqriturhamz",
  "fullName": "Muhammad Fiqri Turham new",
  "email": "tmuhammadfiqri@gmail.com",
  "phone": "082323232",
  "created_at": "27/08/2000 22:10:05",
  "updated_at": "29/08/2000 22:10:05",
  "address": "TCP BLOK E5 NO 6 new"
}
```

Response Body (Failed) :

```json
{
  "errors": "Unauthorized"
}
```

## UPDATE PASSWORD

Endpoint : PUT /api/users/current-user

Request Header :

- X-API-TOKEN : Token

Request Body :

```json
{
  "old_password": "rahasia123",
  "new_password": "rahasiabaru123",
  "confirm_new_password": "rahasiabaru123"
}
```

Response Body (Success) :

```json
{
  "data": "Password berhasil diubah"
}
```

Response Body (Failed) :

```json
{
  "errors": "Unauthorized"
}
```


