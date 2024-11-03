## PAYMENT METHOD API SPECS

## CREATE PAYMENT METHOD

Endpoint : /api/payment-methods/create

REQUEST HEADER : 

- X-API-TOKEN-ADMIN : Token

Request Body : 

```json
{
  "name": "Shopee",
  "image": "https://drive.google.com/uc?export=view&id=1ncM-_BLSof9iSH6B_WQqtb2MePjtl4q8",
  "description": "Pembayaran menggunakan Shopee."
}

```

Response Body (Success) :

```json
{
  "data": {
    "id": "1",
    "name": "Indomaret",
    "image": "https://drive.google.com/uc?export=view&id=1ncM-_BLSof9iSH6B_WQqtb2MePjtl4q8",
    "description": "Pembayaran menggunakan Shopee."
  }
  
}
```

Response Body (Failed) :

```json
{
  "errors": "Unauthorized"
}
```

## DELETE PAYMENT METHOD 

Endpoint : DELETE /api/payment-methods/{paymentMethodId}

Request Header : 

- X-API-TOKEN-ADMIN : Token

Response Body (Success): 

```json
{
  "data" : "Payment Method has succesfully deleted"
}
```

Response Body (Failed) :

```json
{
  "errors" : "Unauthorized"
}
```

## PATCH PAYMENT METHOD 

Endpoint : PATCH /api/payment-methods/{paymentMethodId}

Request Header :

- X-API-TOKEN-ADMIN : Token

Request Body :

```json
{
  "name" : "Indomaret new"
}
```

Response Body (Success) :

```json
{
  "data": {
    "name": "Indomaret new"
  }

}
```

Response Body (Failed) :

```json
{
  "errors" : "Unauthorized"
}
```

## GET ALL PAYMENT METHODS

Request Header : 

- X-API-TOKEN-ADMIN : Token

```json
{
  "data": [
    {
      "id": "1",
      "name": "Indomaret",
      "image": "https://drive.google.com/uc?export=view&id=1ncM-_BLSof9iSH6B_WQqtb2MePjtl4q8",
      "description": "Pembayaran menggunakan Indomaret."
    },
    {
      "id": "2",
      "name": "BRI",
      "image": "https://drive.google.com/uc?export=view&id=1ncM-_BLSof9iSH6B_WQqtb2MePjtl4q8",
      "description": "Pembayaran menggunakan BRI."
    }
  ]
}
```




