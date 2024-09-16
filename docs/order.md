## ORDER API SPEC

## CREATE ORDER

Endpoint : POST api/orders

Request Header : 

- X-API-TOKEN : Token

Request Body :

```json
{
  "user_id": "213123",
  "total_amount": "30.000",
  "created_at": "28/08/2024 16:08:99",
  "status_order": "pending",
  "updated_at": "28/08/2024 16:08:99",
  "items": [
    {
      "product_id": "323232",
      "variant_id": "232323",
      "quantity": 2,
      "price": 50.00
    }
  ]
}
```

Response Body :

```json
{
  "data": "Order berhasil dibuat"
}
```

Response Body (Failed) :

```json
{
  "errors": "Unauthorized"
}
```

## GET ALL ORDER

Endpoint : GET /api/orders

Request Header : 

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data": [
    {
      "id": "string", // ID pesanan
      "user_id": "string", // ID pengguna
      "status": "string", // Status pesanan
      "total_amount": 100.00, // Total harga
      "created_at": "timestamp", // Waktu pembuatan
      "updated_at": "timestamp" // Waktu pembaruan
    }
  ]
}
```

Response Body (Failed) :

```json
{
  "errors" : "Unauthorized"
}
```

## GET DETAIL ORDER

Endpoint : GET /api/orders/{orderId}

Request Header :

- X-API-TOKEN : Token

```json
{
  "data": [
    {
      "id": "string", // ID pesanan
      "user_id": "string", // ID pengguna
      "status": "string", // Status pesanan
      "total_amount": 100.00, // Total harga
      "created_at": "timestamp", // Waktu pembuatan
      "updated_at": "timestamp" // Waktu pembaruan,
      "items": [
        {
          "product_id": "323232",
          "variant_id": "232323",
          "quantity": 2,
          "price": 50.00
        }
      ]
    }
  ]
}
```

Response Body (Failed) :

```json
{
  "errors" : "Unauthorized"
}
```

## SEARCH ORDER 

Endpoint : GET/api/orders

Request Header :

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data": [
    {
      "id": "string",
      // ID pesanan
      "user_id": "string",
      // ID pengguna
      "status": "string",
      // Status pesanan
      "total_amount": 100.00,
      // Total harga
      "created_at": "timestamp",
      // Waktu pembuatan
      "updated_at": "timestamp",
      // Waktu pembaruan,
      "items": [
        {
          "product_id": "323232",
          "variant_id": "232323",
          "quantity": 2,
          "price": 50.00
        }
      ],
      "paging": {
        "total_page": 10,
        "size": 10,
        "current_page": 0
      }
    }
  ]
}
```

## DELETE ORDER

Endpoint : GET /api/orders/{orderId}

Request Header :

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data": "order berhasil dihapus"
}
```

Response Body (Failed) :

```json
{
  "errors": "unauthorized"
}
```