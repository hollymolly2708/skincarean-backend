## PRODUCT API SPEC

## CREATE PRODUCT

Endpoint : POST /api/products

Request Header :

- X-API-TOKEN : Token

Request Body :

```json
{
  "name": "Skintific Moisturizer 5x Ceramide",
  "description": "Moisturizer yang menggabungkan 3 kandungan aktif Ceramide, Hyaluronic Acid, dan Centella Asiatica, untuk merawat permasalahan skin barrier seperti jerawat, kemerahan, kulit bertekstur, dan juga kulit kering. Diperkaya dengan teknologi 5X Ceramide, yang merupakan gabungan 5 jenis Ceramide untuk melembabkan secara mendalam dan memperkuat lapisan epidermis kulit. 5X Ceramide ini membantu untuk menjaga dan melindungi skin barrier dengan menjaga kelembaban dan melindungi kulit dari faktor eksternal.",
  "added_by_admin": "Fiqri",
  "thumbnail_image": "skintific.jpeg",
  "is_promo": true,
  "bpom_code": "NA11230100349",
  "variants": [
    {
      "size": "30g",
      "price": "107.940",
      "discount": 30,
      "original_price": "154.000",
      "quantity": 30,
      "product_image": [
        "skintific1.jpeg",
        "skintific2.jpeg",
        "skintific3.jpeg"
      ]
    },
    {
      "size": "15g",
      "price": "60.720",
      "discount": 30,
      "original_price": "110.000",
      "quantity": 30,
      "product_image": [
        "skintific15.jpeg",
        "skintific15.jpeg",
        "skintific15.jpeg"
      ]
    }
  ]
}

```

Response Body (Success) :

```json
{
  "data": "Product berhasil dibuat"
}
```

Response Body (Failed) :


```json
{
  "errors": "Unauthorized"
}
```

## GET ALL PRODUCTS

Endpoint : GET /api/products

Request Header :

- X-API-TOKEN : Token

Response Body (Success):

```json
{
  "data": [
    {
      "id": "random-string",
      "name": "Skintific Moisturizer 5x Ceramide",
      "description": "Moisturizer yang menggabungkan 3 kandungan aktif Ceramide, Hyaluronic Acid, dan Centella Asiatica, untuk merawat permasalahan skin barrier seperti jerawat, kemerahan, kulit bertekstur, dan juga kulit kering. Diperkaya dengan teknologi 5X Ceramide, yang merupakan gabungan 5 jenis Ceramide untuk melembabkan secara mendalam dan memperkuat lapisan epidermis kulit. 5X Ceramide ini membantu untuk menjaga dan melindungi skin barrier dengan menjaga kelembaban dan melindungi kulit dari faktor eksternal.",
      "added_by_admin": "Fiqri",
      "thumbnail_image": "skintific.jpeg",
      "is_promo": true,
      "bpom_code": "NA11230100349",
     
      "price_range": {
        "min": {
          "price": "60.720",
          // Harga setelah diskon terendah
          "discount": 30,
          "original_price": "110.000"
          // Harga asli terendah
        },
        "max": {
          "price": "107.940",
          "discount": 40,
          // Harga setelah diskon tertinggi
          "original_price": "154.000"
          // Harga asli tertinggi
        }
      }
    }
  ]
}

```

Response Body (Failed) :

```json
{
  "errors": "Unauthorized"
}
```

## GET DETAIL PRODUCT

Endpoint : GET /api/products/{productId}

Request Header :

- X-API-TOKEN : Token

Response Body (Success):

```json
{
  "data": [
    {
      "id": "random-string",
      "name": "Skintific Moisturizer 5x Ceramide",
      "description": "Moisturizer yang menggabungkan 3 kandungan aktif Ceramide, Hyaluronic Acid, dan Centella Asiatica, untuk merawat permasalahan skin barrier seperti jerawat, kemerahan, kulit bertekstur, dan juga kulit kering. Diperkaya dengan teknologi 5X Ceramide, yang merupakan gabungan 5 jenis Ceramide untuk melembabkan secara mendalam dan memperkuat lapisan epidermis kulit. 5X Ceramide ini membantu untuk menjaga dan melindungi skin barrier dengan menjaga kelembaban dan melindungi kulit dari faktor eksternal.",
      "added_by_admin": "Fiqri",
      "thumbnail_image": "skintific.jpeg",
      "is_promo": true,
      "bpom_code": "NA11230100349",
      "variants": [
        {
          "size": "30g",
          "price": "107.940",
          "discount": 30,
          "original_price": "154.000",
          "quantity": 30,
          "product_image": [
            "skintific1.jpeg",
            "skintific2.jpeg",
            "skintific3.jpeg"
          ]
        },
        {
          "size": "15g",
          "price": "60.720",
          "discount": 40,
          "original_price": "110.000",
          "quantity": 30,
          "product_image": [
            "skintific15.jpeg",
            "skintific15.jpeg",
            "skintific15.jpeg"
          ]
        }
      ]
    }
  ]
}

```

Response Body (Failed) :

```json
{
  "errors": "Unauthorized"
}
```

## SEARCH PRODUCT

Endpoint : GET /api/products

Query Param :

- name : String, product name, using like query
- page : Integer, start from 0, default 0
- size : Integer, default 10

Request Header :

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data": [
    {
      "id": "random-string",
      "name": "Skintific Moisturizer 5x Ceramide",
      "description": "Moisturizer yang menggabungkan 3 kandungan aktif Ceramide, Hyaluronic Acid, dan Centella Asiatica, untuk merawat permasalahan skin barrier seperti jerawat, kemerahan, kulit bertekstur, dan juga kulit kering. Diperkaya dengan teknologi 5X Ceramide, yang merupakan gabungan 5 jenis Ceramide untuk melembabkan secara mendalam dan memperkuat lapisan epidermis kulit. 5X Ceramide ini membantu untuk menjaga dan melindungi skin barrier dengan menjaga kelembaban dan melindungi kulit dari faktor eksternal.",
      "added_by_admin": "Fiqri",
      "thumbnail_image": "skintific.jpeg",
      "is_promo": true,
      "bpom_code": "NA11230100349",
   
      "price_range": {
        "min": {
          "price": "60.720",
          "discount": 30,
          // Harga setelah diskon terendah
          "original_price": "110.000"
          // Harga asli terendah
        },
        "max": {
          "price": "107.940",
          "discount": 40,
          // Harga setelah diskon tertinggi
          "original_price": "154.000"
          // Harga asli tertinggi
        }
      }
    }
  ],
  "paging": {
    "total_page": 10,
    "size": 10,
    "current_page": 0
  },
  "errors": null
}

```

Response Body (Failed) :

```json
{
  "errors" : "unauthorized"
}
```

## UPDATE PRODUCT

Endpoint PATCH /api/products/{productId}

Request Header :

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data": [
    {
      "id": "random-string",
      "name": "Skintific Moisturizer 5x Ceramide",
      "description": "Moisturizer yang menggabungkan 3 kandungan aktif Ceramide, Hyaluronic Acid, dan Centella Asiatica, untuk merawat permasalahan skin barrier seperti jerawat, kemerahan, kulit bertekstur, dan juga kulit kering. Diperkaya dengan teknologi 5X Ceramide, yang merupakan gabungan 5 jenis Ceramide untuk melembabkan secara mendalam dan memperkuat lapisan epidermis kulit. 5X Ceramide ini membantu untuk menjaga dan melindungi skin barrier dengan menjaga kelembaban dan melindungi kulit dari faktor eksternal.",
      "added_by_admin": "Fiqri",
      "thumbnail_image": "skintific.jpeg",
      "is_promo": true,
      "bpom_code": "NA11230100349",
      "variants": [
        {
          "size": "30g",
          "price": "107.940",
          "discount": 30,
          "original_price": "154.000",
          "quantity": 30,
          "product_image": [
            "skintific1.jpeg",
            "skintific2.jpeg",
            "skintific3.jpeg"
          ]
        },
        {
          "size": "15g",
          "price": "60.720",
          "discount": 30,
          "original_price": "110.000",
          "quantity": 30,
          "product_image": [
            "skintific15.jpeg",
            "skintific15.jpeg",
            "skintific15.jpeg"
          ]
        }
      ]
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

## DELETE PRODUCT

Endpoint : DELETE /api/products/{productId}

Request Header :

- X-API-TOKEN : Token

Response Body (Success) :

```json
{
  "data" : "product berhasil dihapus"
}
```

Response Body (Failed) :

```json
{
  "errors": "Unauthorized"
}
```