## PAYMENT METHOD API SPECS

## CREATE PAYMENT METHOD

Endpoint : /api/payment-methods/create

REQUEST HEADER : 

- X-API-TOKEN-ADMIN : Token

Request Body : 

```json
{
  "name" : "Indomaret"
}
```

Response Body (Success) :

```json
{
  "data": {
    "id": "1",
    "name": "Indomaret"
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
      "name": "Indomaret"
    },
    {
      "id": "2",
      "name": "BRI"
    }
    
  ]
}
```




