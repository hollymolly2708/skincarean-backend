## ORDER API SPEC

## CREATE ORDER

Endpoint : POST api/orders

Request Header : 

- X-API-TOKEN : Token

Request Body :

```json
{
  "quantity": 20,
  "description": "Order dibuat",
  "productId": "9949fed3-e979-46dc-b143-194686ca2277",
  "shippingAddress": "TCP",
  "shippingCost": 20000,
  "paymentMethodId": 2,
  "tax": 100000
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
      "orderId": "1",
      "quantity": 20,
      "description": "Order dibuat",
      "productId": "9949fed3-e979-46dc-b143-194686ca2277",
      "shippingAddress": "TCP",
      "paymentStatus": "Belum dibayar",
      "shippingCost": 20000,
      "paymentMethodId": 2,
      "tax": 100000
    },
    {
      "orderId": "1",
      "quantity": 20,
      "description": "Order dibuat",
      "paymentMethodId": 2,
      "productId": "9949fed3-e979-46dc-b143-194686ca2277",
      "shippingAddress": "TCP",
      "paymentStatus": "Belum dibayar",
      "shippingCost": 20000,
      "tax": 100000
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
  "data": {
    "orderId": "1",
    "quantity": 20,
    "description": "Order dibuat",
    "productId": "9949fed3-e979-46dc-b143-194686ca2277",
    "shippingAddress": "TCP",
    "paymentStatus": "Belum dibayar",
    "paymentMethodId": 2,
    "shippingCost": 20000,
    "tax": 100000,
    "created_at": "27/08/2000 22:10:05",
    "expired_at": "27/08/2000 22:10:05", //24 jam setelah order dibuat
    "detail_product": {
      "productName": "Skintific Serum 125",
      "productDescription": "Serum Skintific 125g untuk meremajakan kulit",
      "isPromo": true,
      "thumbnailImage": "http",
      "bpomCode": "NA93029039",
      "size": "25",
      "stok": 20,
      "originalPrice": 100000.00,
      "price": 75000.00,
      "discount": 25.00,
      "brands": "Skintific",
      "categoryItem": "Serum",
      "productImage": [
        {
          "id": 4,
          "imageUrl": "skintific1.jpeg"
        },
        {
          "id": 5,
          "imageUrl": "gambar2.jpeg"
        },
        {
          "id": 6,
          "imageUrl": "gambar3.jpeg"
        }
      ]
    }
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Unauthorized"
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