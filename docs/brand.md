## BRAND API SPEC

## CREATE BRAND 

Endpoint : POST /api/brands

Request Header : 

X-API-TOKEN-ADMIN : TOKEN

Request Body :

```json
{
    "name": "GARNIER",
    "description": "Garnier is a globally recognized skincare and hair care brand that offers a wide range of products designed to nourish, protect, and beautify. Originating from France, Garnier focuses on creating innovative formulations that cater to various skin and hair types, incorporating natural ingredients like fruit extracts and botanical oils. Known for its commitment to sustainability and effective solutions, Garnier's products address concerns such as hydration, anti-aging, and skin brightening, helping people achieve healthy, radiant skin and hair.",
    "brandLogo": "https://drive.google.com/uc?export=view&id=1hwgorMeRFXqs5EmeZIXwawfe_UZWOjRR",
    "brandPoster": "https://drive.google.com/uc?export=view&id=1noKVgLs-xEbXlwznBkXo6FRnlpEh_1DL",
    "websiteMediaUrl": "https://www.garnierusa.com/",
    "instagramUrl": "https://www.instagram.com/garnierusa/",
    "facebookUrl": "https://www.facebook.com/GarnierUSA/",
    "contactEmailUrl": "",
    "address": "France",
    "isTopBrand": true
}

```

Response Body (Success) :

```json
{
  "data" : "Brand berhasil ditambahkan"
}
```

Response Body (Failed) :

```json
{
  "data" : "Unauthorized"
}
```

## GET ALL BRANDS

Endpoint : GET /api/brands

Response Body (Success) :

```json
{
  "data": [
    {
      "id": 1,
      "name": "TRESEMME",
      "description": "For 70 years, TRESemmé has helped women express themselves confidently as they make their mark on the world. From our origins in salons, TRESemme has been driven by a simple truth: every woman deserves to look and feel fabulous, like they've just stepped out of the salon. TRESemmé is dedicated to creating hair care and styling products that are salon quality without the salon price. Salon inspired and tested products that help you create your own style and experience that salon feeling every day. TRESemmé promotes the values of independence, self-reliance, and most importantly, the confidence you need to step up and meet the next challenge. We understand the power of hair to boost your confidence, which is why we believe in empowering modern women everywhere to lead the way.",
      "websiteMediaUrl": "https://www.tresemme.com/us/en/",
      "instagramUrl": "https://www.instagram.com/tresemme/",
      "facebookUrl": "https://web.facebook.com/TRESemme?_rdc=1&_rdr",
      "contactEmailUrl": "",
      "brandPoster": "https://drive.google.com/uc?export=view&id=1iq1UeBoF-m8PkvP4QjwRflCcpnAEBmFV",
      "brandLogo": "https://drive.google.com/uc?export=view&id=1ucATRXlUHKXpyr7WVjRgX0Xxsszm0dnl",
      "address": "United States",
      "createdAt": "2024-10-31T03:09:21.000+00:00",
      "isTopBrand": null,
      "lastUpdatedAt": "2024-10-31T03:09:21.000+00:00"
    },
    {
      "id": 2,
      "name": "INNISFREE",
      "description": "Here at innisfree, we create affordable skincare with a conscious touch. Our secret sauce? Blending nature and science into powerful formulas that feature effective, sustainably sourced ingredients. We’re committed to an earth-friendly skincare industry and give back through mindful packaging, recycling programs for empties, reforestation efforts, and more. If you’re looking for products that fit your values and your wallet, this is your happy place.",
      "websiteMediaUrl": "https://us.innisfree.com/",
      "instagramUrl": "https://www.instagram.com/innisfreeusa/",
      "facebookUrl": "hhttps://web.facebook.com/innisfreeusa/?_rdc=1&_rdr",
      "contactEmailUrl": "",
      "brandPoster": "https://drive.google.com/uc?export=view&id=1aWPFaPPQRA0PTOIxUR__pXMTy19rN7Ce",
      "brandLogo": "https://drive.google.com/uc?export=view&id=1mwr66AmLic1HwfZwShQoD1M95dNmkQ5a",
      "address": "South Korea",
      "createdAt": "2024-10-31T03:17:55.000+00:00",
      "isTopBrand": null,
      "lastUpdatedAt": "2024-10-31T03:17:55.000+00:00"
    }
  ],
  "errors": null,
  "paging": null,
  "isSuccess": null
}
```

## GET DETAIL BRAND

Endpoint : GET /api/brands/{brandId}

Response Body (Success) : 
```json
{
    "data": {
        "id": 2,
        "name": "INNISFREE",
        "description": "Here at innisfree, we create affordable skincare with a conscious touch. Our secret sauce? Blending nature and science into powerful formulas that feature effective, sustainably sourced ingredients. We’re committed to an earth-friendly skincare industry and give back through mindful packaging, recycling programs for empties, reforestation efforts, and more. If you’re looking for products that fit your values and your wallet, this is your happy place.",
        "websiteMediaUrl": "https://us.innisfree.com/",
        "instagramUrl": "https://www.instagram.com/innisfreeusa/",
        "facebookUrl": "hhttps://web.facebook.com/innisfreeusa/?_rdc=1&_rdr",
        "contactEmailUrl": "",
        "brandPoster": "https://drive.google.com/uc?export=view&id=1aWPFaPPQRA0PTOIxUR__pXMTy19rN7Ce",
        "brandLogo": "https://drive.google.com/uc?export=view&id=1mwr66AmLic1HwfZwShQoD1M95dNmkQ5a",
        "address": "South Korea",
        "createdAt": "2024-10-31T03:17:55.000+00:00",
        "isTopBrand": null,
        "lastUpdatedAt": "2024-10-31T03:17:55.000+00:00"
    },
    "errors": null,
    "paging": null,
    "isSuccess": null
}
```

## UPDATE BRAND 

Endpoint : PATCH /api/brands{brandId}

Request Header :

- X-API-TOKEN-ADMIN : Token

Request Body : 
```json
{
    "name":"Somethinc",
    "description":"blablabla",
    "brandLogo":"http:logo",
    "brandPoster":"http:poster",
    "websiteMediaUrl":"http:website",
    "instagramUrl":"http:instagram",
    "facebookUrl":"http:facebook",
    "contactEmailUrl":"http:contactEmail",
    "address":"alamat baru"
}
```

Response Body (Success) :

```json
{
    "name":"Somethinc",
    "description":"blablabla",
    "brandLogo":"http:logo",
    "brandPoster":"http:poster",
    "websiteMediaUrl":"http:website",
    "instagramUrl":"http:instagram",
    "facebookUrl":"http:facebook",
    "contactEmailUrl":"http:contactEmail",
    "address":"alamat baru"
}
```

Response Body (Failed) : 

```json
{
  "errors": "Unathorized"
}
```

## DELETE BRAND

Endpoint : DELETE /api/brands/{brandId}

Request Header : 

- X-API-TOKEN-ADMIN : Token

Response Body (Success) :

```json
{
  "data" : "Berhasil menghapus brand"
}
```

Response Body (Failed) :

```json
{
  "errors" : "Unauthorized"
}
```

## GET ALL BRAND BY TOP BRAND

Endpoint : GET /api/brands/top-brands

Response Body (Success) :

```json
{
  "data": [
    {
      "id": 1,
      "name": "TRESEMME",
      "description": "For 70 years, TRESemmé has helped women express themselves confidently as they make their mark on the world. From our origins in salons, TRESemme has been driven by a simple truth: every woman deserves to look and feel fabulous, like they've just stepped out of the salon. TRESemmé is dedicated to creating hair care and styling products that are salon quality without the salon price. Salon inspired and tested products that help you create your own style and experience that salon feeling every day. TRESemmé promotes the values of independence, self-reliance, and most importantly, the confidence you need to step up and meet the next challenge. We understand the power of hair to boost your confidence, which is why we believe in empowering modern women everywhere to lead the way.",
      "websiteMediaUrl": "https://www.tresemme.com/us/en/",
      "instagramUrl": "https://www.instagram.com/tresemme/",
      "facebookUrl": "https://web.facebook.com/TRESemme?_rdc=1&_rdr",
      "contactEmailUrl": "",
      "brandPoster": "https://drive.google.com/uc?export=view&id=1iq1UeBoF-m8PkvP4QjwRflCcpnAEBmFV",
      "brandLogo": "https://drive.google.com/uc?export=view&id=1ucATRXlUHKXpyr7WVjRgX0Xxsszm0dnl",
      "address": "United States",
      "createdAt": "2024-10-31T03:09:21.000+00:00",
      "isTopBrand": null,
      "lastUpdatedAt": "2024-10-31T03:09:21.000+00:00"
    },
    {
      "id": 2,
      "name": "INNISFREE",
      "description": "Here at innisfree, we create affordable skincare with a conscious touch. Our secret sauce? Blending nature and science into powerful formulas that feature effective, sustainably sourced ingredients. We’re committed to an earth-friendly skincare industry and give back through mindful packaging, recycling programs for empties, reforestation efforts, and more. If you’re looking for products that fit your values and your wallet, this is your happy place.",
      "websiteMediaUrl": "https://us.innisfree.com/",
      "instagramUrl": "https://www.instagram.com/innisfreeusa/",
      "facebookUrl": "hhttps://web.facebook.com/innisfreeusa/?_rdc=1&_rdr",
      "contactEmailUrl": "",
      "brandPoster": "https://drive.google.com/uc?export=view&id=1aWPFaPPQRA0PTOIxUR__pXMTy19rN7Ce",
      "brandLogo": "https://drive.google.com/uc?export=view&id=1mwr66AmLic1HwfZwShQoD1M95dNmkQ5a",
      "address": "South Korea",
      "createdAt": "2024-10-31T03:17:55.000+00:00",
      "isTopBrand": null,
      "lastUpdatedAt": "2024-10-31T03:17:55.000+00:00"
    }
  ],
  "errors": null,
  "paging": null,
  "isSuccess": null
}
```

## GET ALL PRODUCTS BY BRAND

Endpoint : /api/brands/{brandId}/products

Response Body (Success) : 

```json
{
  "data": [
    {
      "productId": "0297fec5-942b-4a8b-a80d-d7ffbfdea750",
      "productName": "TRESEMME Scalp Care Shampoo",
      "isPromo": true,
      "thumbnailImage": "https://drive.google.com/uc?export=view&id=1EOlyFfkria5SYymiMHKPDrC78AYLRRgO",
      "isPopularProduct": true,
      "brandName": "TRESEMME",
      "categoryName": "Shampoo",
      "firstOriginalPrice": 40000.00,
      "firstPrice": 36000.00,
      "firstDiscount": 10.00,
      "minPrice": 36000.00,
      "maxPrice": 72000.00
    },
    {
      "productId": "29701145-6056-4199-91ea-bfae3203251a",
      "productName": "TRESEMME Effortless Waves Shampoo 170Ml",
      "isPromo": true,
      "thumbnailImage": "https://drive.google.com/uc?export=view&id=1X8fWdX85WoT3fApE0cZ8REeDF3MqOrRb",
      "isPopularProduct": true,
      "brandName": "TRESEMME",
      "categoryName": "Shampoo",
      "firstOriginalPrice": 40000.00,
      "firstPrice": 40000.00,
      "firstDiscount": 0.00,
      "minPrice": 40000.00,
      "maxPrice": 40000.00
    },
    {
      "productId": "57ab9d25-fa77-4c1b-b9ec-ced60efe960b",
      "productName": "TRESEMME Botanique Nourish And Replenish 700ml",
      "isPromo": false,
      "thumbnailImage": "https://drive.google.com/uc?export=view&id=1JJMpZ-bdTwkig1i6uINPZahm1lISzE8k",
      "isPopularProduct": true,
      "brandName": "TRESEMME",
      "categoryName": "Shampoo",
      "firstOriginalPrice": 120000.00,
      "firstPrice": 108000.00,
      "firstDiscount": 10.00,
      "minPrice": 108000.00,
      "maxPrice": 108000.00
    },
  
  ],
  "errors": null,
  "paging": null,
  "isSuccess": null
}

```

