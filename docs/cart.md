## CART API SPEC

## CREATE CART

Endpoint : POST /api/cart

Request Header :

- X-API-TOKEN : Token

Request Body :

```json
{
  "user_id": "123232",
  "product_id" : "232323"
}
```

Response Body (Success) :

```json
{
  "data": "Product berhasil ditambahkan ke keranjang"
}
```

Response Body (Failed) :

```json
{
  "data": "Unauthorized"
}
```

## GET ALL CART

Endpoint : GET /api/cart

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data": [
    {
      "id": "232323",
      "user_id": "123232",
      "product_id": "232323"
    }
  ]
}
```

Response Body (Failed) :

```json
{
  "errors": "unauthorized"
}
```

## SEARCH CART

Endpoint : GET /api/cart

Request Header :

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data": [
    {
      "id": "12323",
      "user_id": "123232",
      "product_id": "232323"
    }
  ],
  "paging": {
    "total_page": 10,
    "size": 10,
    "current_page": 0
  }
}
```

Response Body (Failed) :

## DELETE CART

Endpoint : DELETE /api/cart/{cartId}

Request Header :

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data": "Keranjang berhasil dihapus"
}
```

Response Body (Failed) :

```json
{
  "data": "unauthorized"
}
```