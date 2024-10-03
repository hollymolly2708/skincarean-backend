## PAYMENT-PROCESS API SPEC

## CREATE PAYMENT-PROCESS

Endpoint : POST /api/payment-process

Request Header : 

- X-API-TOKEN : Token

Request Body :

```json
{
  "orderId" : "order_id"
}
```

Response Body (Success) :

```json
{
  "data": {
    "order_id" : "random-string",
    "payment_code": "random-long",
    "created_at": "27/08/2000 22:10:05",
    "expired_at": "27/08/2000 24:10:05"
    // 24 jam expired setelah dibuat
  }
 
}
```

Response Body (Failed):

```json
{
  "data": null,
  "errors": "Unauthorized"
}
```

## DELETE PAYMENT-PROCESS 

Request Body : 

```json
{
  "orderId": "order_id"
}
```

Response Body (Success) :

```json
{
  "data" : "Berhasil hapus pembayaran",
  "errors" : null
}
```


