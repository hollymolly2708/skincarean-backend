## ORDER API SPEC

## CREATE DIRECTLY ORDER

Endpoint : POST api/orders/checkout/direct

Request Header : 

- X-API-TOKEN : Token

Request Body :

```json
{
  "quantity": 20,
  "paymentMethodId": 6,
  "description": "Order dibuat",
  "shippingAddress": "TCP",
  "productId": "0bf3789c-a2d5-4acb-9c8d-01b95c2c0119",
  "productVariantId": 2
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

## CREATE ORDER FROM CART

Endpoint : POST /api/orders/checkout/cart

Request Header : 

- X-API-TOKEN : Token

Request Body :

```json
{

  "paymentMethodId" : 5,
  "description": "Order dibuat",
  "shippingAddress":"TCP"

}
```

Response Body (Success) :

```json
{
  "data" : "Order berhasil dibuat"
}
```

Response Body (Failed) :

```json
{
  "errors" : "Unauthorized"
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
      "orderId": "0ac1ec3d-20db-47b8-b7a9-b30310b647c0",
      "orderStatus": "Selesai",
      "finalPrice": 1051730.00,
      "description": "",
      "shippingCost": 20000.00,
      "shippingAddress": "test",
      "tax": 49130.00,
      "orderItems": [
        {
          "price": 325600.00,
          "quantity": 1,
          "product": {
            "productId": "2ad4d75f-da29-45b0-8b21-343636492113",
            "productName": "LANEIGE Water Sleeping Mask EX 70ml",
            "thumbnailImage": "https://drive.google.com/uc?export=view&id=1A3DhXsx8y038k0hue32piaJJkD9XtEOk",
            "brandName": "LANEIGE Water Sleeping Mask EX 70ml",
            "categoryName": "Night Cream"
          },
          "productVariant": {
            "id": 10,
            "size": "70 ml",
            "price": 325600.00,
            "originalPrice": 370000.00,
            "discount": 12.00,
            "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1EssUv59OtERh4bvIxDsfq6J_r4ROqN05"
          }
        },
        {
          "price": 360000.00,
          "quantity": 2,
          "product": {
            "productId": "65e87eb0-47a0-4097-b8f7-869bbc4bae24",
            "productName": "TRESEMME Revitalize Color Shampoo 828ml",
            "thumbnailImage": "https://drive.google.com/uc?export=view&id=1Nd5ccMHy5rZYn2ZzwTNc-Xs2lDqohYu6",
            "brandName": "TRESEMME Revitalize Color Shampoo 828ml",
            "categoryName": "Shampoo"
          },
          "productVariant": {
            "id": 77,
            "size": "828ml",
            "price": 180000.00,
            "originalPrice": 200000.00,
            "discount": 10.00,
            "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1O8jywmd1IS8Iu3x4JWwhyp8IMn0JB794"
          }
        },
        {
          "price": 216000.00,
          "quantity": 2,
          "product": {
            "productId": "8ca882fa-afd4-42a5-bcad-568b8b211e4b",
            "productName": "COSRX Facewash Good Morning Cleanser 150ml",
            "thumbnailImage": "https://drive.google.com/uc?export=view&id=1bgrMcTJpe9fRNv7vDnJiCoVUncxai05t",
            "brandName": "COSRX Facewash Good Morning Cleanser 150ml",
            "categoryName": "Facial Cleanser"
          },
          "productVariant": {
            "id": 26,
            "size": "150 ml",
            "price": 108000.00,
            "originalPrice": 120000.00,
            "discount": 10.00,
            "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1qJlFow1XOGIk9z17dQYRoyBjG7cijx7S"
          }
        },
        {
          "price": 81000.00,
          "quantity": 1,
          "product": {
            "productId": "72f274ec-5843-422a-b2b2-e46e62d997e0",
            "productName": "MAKARIZO Olive Extract Creambath",
            "thumbnailImage": "https://drive.google.com/uc?export=view&id=1NGami9lY2-aBSTGdVzAuRBnz5cKS2fgx",
            "brandName": "MAKARIZO Olive Extract Creambath",
            "categoryName": "Hair Mask"
          },
          "productVariant": {
            "id": 84,
            "size": "300ml",
            "price": 81000.00,
            "originalPrice": 90000.00,
            "discount": 10.00,
            "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1Wp6pvL4nma1XIPAgECQP4lsdTGDWqOcS"
          }
        }
      ],
      "payment": {
        "paymentCode": "453219603556",
        "paymentStatus": "Lunas",
        "paymentMethodId": 30,
        "paymentMethodName": "BRI",
        "paidDate": "2024-11-03T16:01:24.000+00:00",
        "totalPaid": 1051730.00
      }
    }
  ],
  "errors": null,
  "paging": null,
  "isSuccess": null
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
    "orderId": "4a8155cc-7fc9-4a81-9eee-d008c2b5c6c2",
    "orderStatus": "Selesai",
    "finalPrice": 1070630.00,
    "description": "",
    "shippingCost": 20000.00,
    "shippingAddress": "test",
    "tax": 50030.00,
    "orderItems": [
      {
        "price": 216000.00,
        "quantity": 2,
        "product": {
          "productId": "8ca882fa-afd4-42a5-bcad-568b8b211e4b",
          "productName": "COSRX Facewash Good Morning Cleanser 150ml",
          "thumbnailImage": "https://drive.google.com/uc?export=view&id=1qJlFow1XOGIk9z17dQYRoyBjG7cijx7S",
          "brandName": "COSRX",
          "categoryName": "Facial Cleanser"
        },
        "productVariant": {
          "id": 26,
          "size": "150 ml",
          "price": 108000.00,
          "originalPrice": 120000.00,
          "discount": 10.00,
          "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1qJlFow1XOGIk9z17dQYRoyBjG7cijx7S"
        }
      },
      {
        "price": 325600.00,
        "quantity": 1,
        "product": {
          "productId": "2ad4d75f-da29-45b0-8b21-343636492113",
          "productName": "LANEIGE Water Sleeping Mask EX 70ml",
          "thumbnailImage": "https://drive.google.com/uc?export=view&id=1EssUv59OtERh4bvIxDsfq6J_r4ROqN05",
          "brandName": "LANEIGE",
          "categoryName": "Night Cream"
        },
        "productVariant": {
          "id": 10,
          "size": "70 ml",
          "price": 325600.00,
          "originalPrice": 370000.00,
          "discount": 12.00,
          "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1EssUv59OtERh4bvIxDsfq6J_r4ROqN05"
        }
      },
      {
        "price": 18000.00,
        "quantity": 1,
        "product": {
          "productId": "72f274ec-5843-422a-b2b2-e46e62d997e0",
          "productName": "MAKARIZO Olive Extract Creambath",
          "thumbnailImage": "https://drive.google.com/uc?export=view&id=1sVOvx7UpbHggUh2jtmViDsJ4WGLKPTxJ",
          "brandName": "MAKARIZO",
          "categoryName": "Hair Mask"
        },
        "productVariant": {
          "id": 85,
          "size": "60ml",
          "price": 18000.00,
          "originalPrice": 20000.00,
          "discount": 10.00,
          "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1sVOvx7UpbHggUh2jtmViDsJ4WGLKPTxJ"
        }
      },
      {
        "price": 360000.00,
        "quantity": 2,
        "product": {
          "productId": "65e87eb0-47a0-4097-b8f7-869bbc4bae24",
          "productName": "TRESEMME Revitalize Color Shampoo 828ml",
          "thumbnailImage": "https://drive.google.com/uc?export=view&id=1O8jywmd1IS8Iu3x4JWwhyp8IMn0JB794",
          "brandName": "TRESEMME",
          "categoryName": "Shampoo"
        },
        "productVariant": {
          "id": 77,
          "size": "828ml",
          "price": 180000.00,
          "originalPrice": 200000.00,
          "discount": 10.00,
          "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1O8jywmd1IS8Iu3x4JWwhyp8IMn0JB794"
        }
      },
      {
        "price": 81000.00,
        "quantity": 1,
        "product": {
          "productId": "72f274ec-5843-422a-b2b2-e46e62d997e0",
          "productName": "MAKARIZO Olive Extract Creambath",
          "thumbnailImage": "https://drive.google.com/uc?export=view&id=1Wp6pvL4nma1XIPAgECQP4lsdTGDWqOcS",
          "brandName": "MAKARIZO",
          "categoryName": "Hair Mask"
        },
        "productVariant": {
          "id": 84,
          "size": "300ml",
          "price": 81000.00,
          "originalPrice": 90000.00,
          "discount": 10.00,
          "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1Wp6pvL4nma1XIPAgECQP4lsdTGDWqOcS"
        }
      }
    ],
    "payment": {
      "paymentCode": "213934405705",
      "paymentStatus": "Lunas",
      "paymentMethodId": 31,
      "paymentMethodName": "BRI",
      "paidDate": "2024-11-03T16:04:19.000+00:00",
      "totalPaid": 1070630.00
    }
  },
  "errors": null,
  "paging": null,
  "isSuccess": null
}
```

Response Body (Failed) :

```json
{
  "errors" : "Unauthorized"
}
```

## GET ALL PENDING ORDER 

Endpoint : GET /api/orders/pending-order

Request Header : 

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data": [
    {
      "orderId": "0ac1ec3d-20db-47b8-b7a9-b30310b647c0",
      "orderStatus": "Selesai",
      "finalPrice": 1051730.00,
      "description": "",
      "shippingCost": 20000.00,
      "shippingAddress": "test",
      "tax": 49130.00,
      "orderItems": [
        {
          "price": 325600.00,
          "quantity": 1,
          "product": {
            "productId": "2ad4d75f-da29-45b0-8b21-343636492113",
            "productName": "LANEIGE Water Sleeping Mask EX 70ml",
            "thumbnailImage": "https://drive.google.com/uc?export=view&id=1A3DhXsx8y038k0hue32piaJJkD9XtEOk",
            "brandName": "LANEIGE Water Sleeping Mask EX 70ml",
            "categoryName": "Night Cream"
          },
          "productVariant": {
            "id": 10,
            "size": "70 ml",
            "price": 325600.00,
            "originalPrice": 370000.00,
            "discount": 12.00,
            "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1EssUv59OtERh4bvIxDsfq6J_r4ROqN05"
          }
        },
        {
          "price": 360000.00,
          "quantity": 2,
          "product": {
            "productId": "65e87eb0-47a0-4097-b8f7-869bbc4bae24",
            "productName": "TRESEMME Revitalize Color Shampoo 828ml",
            "thumbnailImage": "https://drive.google.com/uc?export=view&id=1Nd5ccMHy5rZYn2ZzwTNc-Xs2lDqohYu6",
            "brandName": "TRESEMME Revitalize Color Shampoo 828ml",
            "categoryName": "Shampoo"
          },
          "productVariant": {
            "id": 77,
            "size": "828ml",
            "price": 180000.00,
            "originalPrice": 200000.00,
            "discount": 10.00,
            "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1O8jywmd1IS8Iu3x4JWwhyp8IMn0JB794"
          }
        },
        {
          "price": 216000.00,
          "quantity": 2,
          "product": {
            "productId": "8ca882fa-afd4-42a5-bcad-568b8b211e4b",
            "productName": "COSRX Facewash Good Morning Cleanser 150ml",
            "thumbnailImage": "https://drive.google.com/uc?export=view&id=1bgrMcTJpe9fRNv7vDnJiCoVUncxai05t",
            "brandName": "COSRX Facewash Good Morning Cleanser 150ml",
            "categoryName": "Facial Cleanser"
          },
          "productVariant": {
            "id": 26,
            "size": "150 ml",
            "price": 108000.00,
            "originalPrice": 120000.00,
            "discount": 10.00,
            "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1qJlFow1XOGIk9z17dQYRoyBjG7cijx7S"
          }
        },
        {
          "price": 81000.00,
          "quantity": 1,
          "product": {
            "productId": "72f274ec-5843-422a-b2b2-e46e62d997e0",
            "productName": "MAKARIZO Olive Extract Creambath",
            "thumbnailImage": "https://drive.google.com/uc?export=view&id=1NGami9lY2-aBSTGdVzAuRBnz5cKS2fgx",
            "brandName": "MAKARIZO Olive Extract Creambath",
            "categoryName": "Hair Mask"
          },
          "productVariant": {
            "id": 84,
            "size": "300ml",
            "price": 81000.00,
            "originalPrice": 90000.00,
            "discount": 10.00,
            "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1Wp6pvL4nma1XIPAgECQP4lsdTGDWqOcS"
          }
        }
      ],
      "payment": {
        "paymentCode": "453219603556",
        "paymentStatus": "Lunas",
        "paymentMethodId": 30,
        "paymentMethodName": "BRI",
        "paidDate": "2024-11-03T16:01:24.000+00:00",
        "totalPaid": 1051730.00
      }
    }
  ],
  "errors": null,
  "paging": null,
  "isSuccess": null
}
```

## GET ALL COMPLETE ORDER

Endpoint : GET /api/orders/complete-order

Request Header :

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data": [
    {
      "orderId": "0ac1ec3d-20db-47b8-b7a9-b30310b647c0",
      "orderStatus": "Selesai",
      "finalPrice": 1051730.00,
      "description": "",
      "shippingCost": 20000.00,
      "shippingAddress": "test",
      "tax": 49130.00,
      "orderItems": [
        {
          "price": 325600.00,
          "quantity": 1,
          "product": {
            "productId": "2ad4d75f-da29-45b0-8b21-343636492113",
            "productName": "LANEIGE Water Sleeping Mask EX 70ml",
            "thumbnailImage": "https://drive.google.com/uc?export=view&id=1A3DhXsx8y038k0hue32piaJJkD9XtEOk",
            "brandName": "LANEIGE Water Sleeping Mask EX 70ml",
            "categoryName": "Night Cream"
          },
          "productVariant": {
            "id": 10,
            "size": "70 ml",
            "price": 325600.00,
            "originalPrice": 370000.00,
            "discount": 12.00,
            "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1EssUv59OtERh4bvIxDsfq6J_r4ROqN05"
          }
        },
        {
          "price": 360000.00,
          "quantity": 2,
          "product": {
            "productId": "65e87eb0-47a0-4097-b8f7-869bbc4bae24",
            "productName": "TRESEMME Revitalize Color Shampoo 828ml",
            "thumbnailImage": "https://drive.google.com/uc?export=view&id=1Nd5ccMHy5rZYn2ZzwTNc-Xs2lDqohYu6",
            "brandName": "TRESEMME Revitalize Color Shampoo 828ml",
            "categoryName": "Shampoo"
          },
          "productVariant": {
            "id": 77,
            "size": "828ml",
            "price": 180000.00,
            "originalPrice": 200000.00,
            "discount": 10.00,
            "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1O8jywmd1IS8Iu3x4JWwhyp8IMn0JB794"
          }
        },
        {
          "price": 216000.00,
          "quantity": 2,
          "product": {
            "productId": "8ca882fa-afd4-42a5-bcad-568b8b211e4b",
            "productName": "COSRX Facewash Good Morning Cleanser 150ml",
            "thumbnailImage": "https://drive.google.com/uc?export=view&id=1bgrMcTJpe9fRNv7vDnJiCoVUncxai05t",
            "brandName": "COSRX Facewash Good Morning Cleanser 150ml",
            "categoryName": "Facial Cleanser"
          },
          "productVariant": {
            "id": 26,
            "size": "150 ml",
            "price": 108000.00,
            "originalPrice": 120000.00,
            "discount": 10.00,
            "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1qJlFow1XOGIk9z17dQYRoyBjG7cijx7S"
          }
        },
        {
          "price": 81000.00,
          "quantity": 1,
          "product": {
            "productId": "72f274ec-5843-422a-b2b2-e46e62d997e0",
            "productName": "MAKARIZO Olive Extract Creambath",
            "thumbnailImage": "https://drive.google.com/uc?export=view&id=1NGami9lY2-aBSTGdVzAuRBnz5cKS2fgx",
            "brandName": "MAKARIZO Olive Extract Creambath",
            "categoryName": "Hair Mask"
          },
          "productVariant": {
            "id": 84,
            "size": "300ml",
            "price": 81000.00,
            "originalPrice": 90000.00,
            "discount": 10.00,
            "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1Wp6pvL4nma1XIPAgECQP4lsdTGDWqOcS"
          }
        }
      ],
      "payment": {
        "paymentCode": "453219603556",
        "paymentStatus": "Lunas",
        "paymentMethodId": 30,
        "paymentMethodName": "BRI",
        "paidDate": "2024-11-03T16:01:24.000+00:00",
        "totalPaid": 1051730.00
      }
    }
  ],
  "errors": null,
  "paging": null,
  "isSuccess": null
}
```

## GET ALL CANCEL ORDER

Endpoint : GET /api/orders/cancel-order

Request Header :

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data": [
    {
      "orderId": "0ac1ec3d-20db-47b8-b7a9-b30310b647c0",
      "orderStatus": "Selesai",
      "finalPrice": 1051730.00,
      "description": "",
      "shippingCost": 20000.00,
      "shippingAddress": "test",
      "tax": 49130.00,
      "orderItems": [
        {
          "price": 325600.00,
          "quantity": 1,
          "product": {
            "productId": "2ad4d75f-da29-45b0-8b21-343636492113",
            "productName": "LANEIGE Water Sleeping Mask EX 70ml",
            "thumbnailImage": "https://drive.google.com/uc?export=view&id=1A3DhXsx8y038k0hue32piaJJkD9XtEOk",
            "brandName": "LANEIGE Water Sleeping Mask EX 70ml",
            "categoryName": "Night Cream"
          },
          "productVariant": {
            "id": 10,
            "size": "70 ml",
            "price": 325600.00,
            "originalPrice": 370000.00,
            "discount": 12.00,
            "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1EssUv59OtERh4bvIxDsfq6J_r4ROqN05"
          }
        },
        {
          "price": 360000.00,
          "quantity": 2,
          "product": {
            "productId": "65e87eb0-47a0-4097-b8f7-869bbc4bae24",
            "productName": "TRESEMME Revitalize Color Shampoo 828ml",
            "thumbnailImage": "https://drive.google.com/uc?export=view&id=1Nd5ccMHy5rZYn2ZzwTNc-Xs2lDqohYu6",
            "brandName": "TRESEMME Revitalize Color Shampoo 828ml",
            "categoryName": "Shampoo"
          },
          "productVariant": {
            "id": 77,
            "size": "828ml",
            "price": 180000.00,
            "originalPrice": 200000.00,
            "discount": 10.00,
            "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1O8jywmd1IS8Iu3x4JWwhyp8IMn0JB794"
          }
        },
        {
          "price": 216000.00,
          "quantity": 2,
          "product": {
            "productId": "8ca882fa-afd4-42a5-bcad-568b8b211e4b",
            "productName": "COSRX Facewash Good Morning Cleanser 150ml",
            "thumbnailImage": "https://drive.google.com/uc?export=view&id=1bgrMcTJpe9fRNv7vDnJiCoVUncxai05t",
            "brandName": "COSRX Facewash Good Morning Cleanser 150ml",
            "categoryName": "Facial Cleanser"
          },
          "productVariant": {
            "id": 26,
            "size": "150 ml",
            "price": 108000.00,
            "originalPrice": 120000.00,
            "discount": 10.00,
            "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1qJlFow1XOGIk9z17dQYRoyBjG7cijx7S"
          }
        },
        {
          "price": 81000.00,
          "quantity": 1,
          "product": {
            "productId": "72f274ec-5843-422a-b2b2-e46e62d997e0",
            "productName": "MAKARIZO Olive Extract Creambath",
            "thumbnailImage": "https://drive.google.com/uc?export=view&id=1NGami9lY2-aBSTGdVzAuRBnz5cKS2fgx",
            "brandName": "MAKARIZO Olive Extract Creambath",
            "categoryName": "Hair Mask"
          },
          "productVariant": {
            "id": 84,
            "size": "300ml",
            "price": 81000.00,
            "originalPrice": 90000.00,
            "discount": 10.00,
            "thumbnailImageVariant": "https://drive.google.com/uc?export=view&id=1Wp6pvL4nma1XIPAgECQP4lsdTGDWqOcS"
          }
        }
      ],
      "payment": {
        "paymentCode": "453219603556",
        "paymentStatus": "Lunas",
        "paymentMethodId": 30,
        "paymentMethodName": "BRI",
        "paidDate": "2024-11-03T16:01:24.000+00:00",
        "totalPaid": 1051730.00
      }
    }
  ],
  "errors": null,
  "paging": null,
  "isSuccess": null
}
```

## CANCEL ORDER

Endpoint : GET /api/orders/{orderId}/cancel-order

Request Header :

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data": "order berhasil dibatalkan"
}
```


Response Body (Failed) :

```json
{
  "errors": "Unauthorized"
}
```


## DELETE ORDER

Endpoint : GET /api/orders/{orderId}

Request Header :

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data": "orderItem berhasil dihapus"
}
```


Response Body (Failed) :

```json
{
  "errors": "unauthorized"
}
```