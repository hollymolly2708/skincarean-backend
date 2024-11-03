## CART API SPEC

## ADD CART

Endpoint : POST /api/carts

Request Header :

- X-API-TOKEN : Token

Request Body :

```json
{
  "productId" : "0bc69fa2-9396-41d1-aab8-e8adeff2d70c",
  "quantity" : 2,
  "productVariantId" : 10
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

Endpoint : GET /api/cartItem

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data": {
    "id": 2,
    "quantity": 7,
    "totalPrice": 1000600.00,
    "cartItems": [
      {
        "id": 35,
        "total": 216000.00,
        "isActive": true,
        "quantity": 2,
        "product": {
          "productId": "8ca882fa-afd4-42a5-bcad-568b8b211e4b",
          "productName": "COSRX Facewash Good Morning Cleanser 150ml",
          "thumbnailImage": "https://drive.google.com/uc?export=view&id=1bgrMcTJpe9fRNv7vDnJiCoVUncxai05t",
          "brandName": "COSRX",
          "categoryName": "Facial Cleanser"
        },
        "productVariant": {
          "size": "150 ml",
          "price": 108000.00,
          "originalPrice": 120000.00,
          "discount": 10.00,
          "thumbnailVariantImage": "https://drive.google.com/uc?export=view&id=1qJlFow1XOGIk9z17dQYRoyBjG7cijx7S",
          "id": 26
        }
      },
      {
        "id": 36,
        "total": 360000.00,
        "isActive": true,
        "quantity": 2,
        "product": {
          "productId": "65e87eb0-47a0-4097-b8f7-869bbc4bae24",
          "productName": "TRESEMME Revitalize Color Shampoo 828ml",
          "thumbnailImage": "https://drive.google.com/uc?export=view&id=1Nd5ccMHy5rZYn2ZzwTNc-Xs2lDqohYu6",
          "brandName": "TRESEMME",
          "categoryName": "Shampoo"
        },
        "productVariant": {
          "size": "828ml",
          "price": 180000.00,
          "originalPrice": 200000.00,
          "discount": 10.00,
          "thumbnailVariantImage": "https://drive.google.com/uc?export=view&id=1O8jywmd1IS8Iu3x4JWwhyp8IMn0JB794",
          "id": 77
        }
      },
      {
        "id": 37,
        "total": 325600.00,
        "isActive": true,
        "quantity": 1,
        "product": {
          "productId": "2ad4d75f-da29-45b0-8b21-343636492113",
          "productName": "LANEIGE Water Sleeping Mask EX 70ml",
          "thumbnailImage": "https://drive.google.com/uc?export=view&id=1A3DhXsx8y038k0hue32piaJJkD9XtEOk",
          "brandName": "LANEIGE",
          "categoryName": "Night Cream"
        },
        "productVariant": {
          "size": "70 ml",
          "price": 325600.00,
          "originalPrice": 370000.00,
          "discount": 12.00,
          "thumbnailVariantImage": "https://drive.google.com/uc?export=view&id=1EssUv59OtERh4bvIxDsfq6J_r4ROqN05",
          "id": 10
        }
      }
    ]
  },
  "errors": null,
  "paging": null,
  "isSuccess": null
}
```

Response Body (Failed) :

```json
{
  "errors": "unauthorized"
}
```

## SEARCH CART

Endpoint : GET /api/cartItem

Request Header :

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data": {
    "id": 2,
    "quantity": 7,
    "totalPrice": 1000600.00,
    "cartItems": [
      {
        "id": 35,
        "total": 216000.00,
        "isActive": true,
        "quantity": 2,
        "product": {
          "productId": "8ca882fa-afd4-42a5-bcad-568b8b211e4b",
          "productName": "COSRX Facewash Good Morning Cleanser 150ml",
          "thumbnailImage": "https://drive.google.com/uc?export=view&id=1bgrMcTJpe9fRNv7vDnJiCoVUncxai05t",
          "brandName": "COSRX",
          "categoryName": "Facial Cleanser"
        },
        "productVariant": {
          "size": "150 ml",
          "price": 108000.00,
          "originalPrice": 120000.00,
          "discount": 10.00,
          "thumbnailVariantImage": "https://drive.google.com/uc?export=view&id=1qJlFow1XOGIk9z17dQYRoyBjG7cijx7S",
          "id": 26
        }
      },
      {
        "id": 36,
        "total": 360000.00,
        "isActive": true,
        "quantity": 2,
        "product": {
          "productId": "65e87eb0-47a0-4097-b8f7-869bbc4bae24",
          "productName": "TRESEMME Revitalize Color Shampoo 828ml",
          "thumbnailImage": "https://drive.google.com/uc?export=view&id=1Nd5ccMHy5rZYn2ZzwTNc-Xs2lDqohYu6",
          "brandName": "TRESEMME",
          "categoryName": "Shampoo"
        },
        "productVariant": {
          "size": "828ml",
          "price": 180000.00,
          "originalPrice": 200000.00,
          "discount": 10.00,
          "thumbnailVariantImage": "https://drive.google.com/uc?export=view&id=1O8jywmd1IS8Iu3x4JWwhyp8IMn0JB794",
          "id": 77
        }
      }
    ]
  },
  "errors": null,
  "paging": null,
  "isSuccess": null
}
```

Response Body (Failed) :

## DELETE ALL ITEM FROM CART

Endpoint : DELETE /api/carts

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

## DELETE CART ITEM

Endpoint : DELETE /api/carts/{cartItemId}

Request Header : 

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data" : "Product berhasil dihapus dari keranjang"
}
```
Response Body (Failed) :

```json
{
  "errors" : "Unauthorized"
}
```


## MINUS QUANTITY 

Endpoint : DELETE /api/carts/{cartItemId}/minus-quantity

Request Header : 

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data" : "Berhasil mengurangi quantity"
}
```

Response Body (Failed) :

```json
{
  "errors" : "Unauthorized"
}
```

## SET ACTIVE CART ITEM

Endpoint : GET /api/carts/{cartItemId}

Request Header :

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data" : "Set"
}
```
Response Body (Failed) :

```json
{
  "errors" : "Unauthorized"
}
```

## GET ALL ACTIVE CART ITEMS

Endpoint : GET /api/carts/active-carts

Request Header :

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data": {
    "id": 2,
    "quantity": 7,
    "totalPrice": 1000600.00,
    "cartItems": [
      {
        "id": 35,
        "total": 216000.00,
        "isActive": true,
        "quantity": 2,
        "product": {
          "productId": "8ca882fa-afd4-42a5-bcad-568b8b211e4b",
          "productName": "COSRX Facewash Good Morning Cleanser 150ml",
          "thumbnailImage": "https://drive.google.com/uc?export=view&id=1bgrMcTJpe9fRNv7vDnJiCoVUncxai05t",
          "brandName": "COSRX",
          "categoryName": "Facial Cleanser"
        },
        "productVariant": {
          "size": "150 ml",
          "price": 108000.00,
          "originalPrice": 120000.00,
          "discount": 10.00,
          "thumbnailVariantImage": "https://drive.google.com/uc?export=view&id=1qJlFow1XOGIk9z17dQYRoyBjG7cijx7S",
          "id": 26
        }
      },
      {
        "id": 36,
        "total": 360000.00,
        "isActive": true,
        "quantity": 2,
        "product": {
          "productId": "65e87eb0-47a0-4097-b8f7-869bbc4bae24",
          "productName": "TRESEMME Revitalize Color Shampoo 828ml",
          "thumbnailImage": "https://drive.google.com/uc?export=view&id=1Nd5ccMHy5rZYn2ZzwTNc-Xs2lDqohYu6",
          "brandName": "TRESEMME",
          "categoryName": "Shampoo"
        },
        "productVariant": {
          "size": "828ml",
          "price": 180000.00,
          "originalPrice": 200000.00,
          "discount": 10.00,
          "thumbnailVariantImage": "https://drive.google.com/uc?export=view&id=1O8jywmd1IS8Iu3x4JWwhyp8IMn0JB794",
          "id": 77
        }
      },
      {
        "id": 37,
        "total": 325600.00,
        "isActive": true,
        "quantity": 1,
        "product": {
          "productId": "2ad4d75f-da29-45b0-8b21-343636492113",
          "productName": "LANEIGE Water Sleeping Mask EX 70ml",
          "thumbnailImage": "https://drive.google.com/uc?export=view&id=1A3DhXsx8y038k0hue32piaJJkD9XtEOk",
          "brandName": "LANEIGE",
          "categoryName": "Night Cream"
        },
        "productVariant": {
          "size": "70 ml",
          "price": 325600.00,
          "originalPrice": 370000.00,
          "discount": 12.00,
          "thumbnailVariantImage": "https://drive.google.com/uc?export=view&id=1EssUv59OtERh4bvIxDsfq6J_r4ROqN05",
          "id": 10
        }
      }
    ]
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