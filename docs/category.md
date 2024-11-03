## CATEGORY API SPECS

## CREATE CATEGORY

Endpoint : POST /api/categories

Request Header : 

- X-API-TOKEN-ADMIN : Token

Request Body : 

```json
{
  "name": "HAIRCARE",
  "categoryImage": "https://drive.google.com/uc?export=view&id=1Xe8x4Orvr8yka1rN7eq4IZHLQwWhJopk",
  "categoryItemRequests": [
    {
      "name": "Shampoo",
      "description": "Shampoo is a hair care product used to cleanse the hair and scalp. It removes dirt, oil, and product buildup, leaving hair clean and refreshed."
    },
    {
      "name": "Conditioner",
      "description": "Conditioner is used after shampooing to hydrate and detangle hair. It helps to soften the hair and improve manageability, often containing nourishing ingredients."
    },
    {
      "name": "Hair Mask",
      "description": "A hair mask is a deep conditioning treatment designed to nourish and repair damaged hair. It provides intensive moisture and nutrients for healthier-looking hair."
    },
    {
      "name": "Leave-In Conditioner",
      "description": "Leave-in conditioner is a lightweight product applied after washing hair to provide extra moisture and protection. It helps to detangle and smooth hair without rinsing."
    },
    {
      "name": "Hair Oil",
      "description": "Hair oil is a product used to add shine, hydration, and nourishment to hair. It can help tame frizz and improve overall hair health."
    },
    {
      "name": "Heat Protectant Spray",
      "description": "Heat protectant spray is used before styling with heat tools to shield hair from damage. It creates a protective barrier to minimize heat exposure."
    },
    {
      "name": "Scalp Treatment",
      "description": "Scalp treatment products target scalp issues such as dryness, dandruff, or irritation. They can help maintain a healthy scalp environment for optimal hair growth."
    },
    {
      "name": "Hair Spray",
      "description": "Hair spray is a styling product used to hold hairstyles in place. It provides flexible or strong hold depending on the formulation."
    },
    {
      "name": "Dry Shampoo",
      "description": "Dry shampoo is a powder or spray product used to absorb oil and refresh hair between washes. It helps to extend the time between shampooing."
    },
    {
      "name": "Hair Serum",
      "description": "Hair serum is a lightweight product that provides shine and smoothness to hair. It often contains silicones and other nourishing ingredients for a polished finish."
    }
  ]
}

```

Response Body (Success) :

```json
{
  "data" : "Category berhasil dibuat"
}
```

Response Body (Failed) :

```json
{
  "errors": "Unauthorized"
}
```

## GET ALL CATEGORIES

Endpoint : GET /api/categories

Response Body (Success) :

```json
{
    "data": [
        {
            "id": 1,
            "categoryImage": "https://drive.google.com/uc?export=view&id=1MfPksh3mxjcfsOI8I26J57rkcKth8iIw",
            "name": "BODYCARE",
            "createdAt": "2024-10-31T03:09:33.000+00:00",
            "lastUpdatedAt": "2024-10-31T03:09:33.000+00:00"
        },
        {
            "id": 2,
            "categoryImage": "https://drive.google.com/uc?export=view&id=1PhP2ZHhOpH1exLqZN_g-Cj0enDey0OY7",
            "name": "SKINCARE",
            "createdAt": "2024-10-31T03:12:32.000+00:00",
            "lastUpdatedAt": "2024-10-31T03:12:32.000+00:00"
        },
        {
            "id": 3,
            "categoryImage": "https://drive.google.com/uc?export=view&id=1Xe8x4Orvr8yka1rN7eq4IZHLQwWhJopk",
            "name": "HAIRCARE",
            "createdAt": "2024-10-31T03:13:05.000+00:00",
            "lastUpdatedAt": "2024-10-31T03:13:05.000+00:00"
        }
    ],
    "errors": null,
    "paging": null,
    "isSuccess": null
}
```

## GET DETAIL CATEGORY 

Endpoint : GET /api/categories/{categoryId}

Response Body (Success) :

```json
{
    "data": {
        "id": 3,
        "name": "HAIRCARE",
        "categoryImage": "https://drive.google.com/uc?export=view&id=1Xe8x4Orvr8yka1rN7eq4IZHLQwWhJopk",
        "createdAt": "2024-10-31T03:13:05.000+00:00",
        "lastUpdatedAt": "2024-10-31T03:13:05.000+00:00",
        "categoryItems": [
            {
                "id": 29,
                "name": "Shampoo",
                "description": "Shampoo is a hair care product used to cleanse the hair and scalp. It removes dirt, oil, and product buildup, leaving hair clean and refreshed."
            },
            {
                "id": 30,
                "name": "Conditioner",
                "description": "Conditioner is used after shampooing to hydrate and detangle hair. It helps to soften the hair and improve manageability, often containing nourishing ingredients."
            },
            {
                "id": 31,
                "name": "Hair Mask",
                "description": "A hair mask is a deep conditioning treatment designed to nourish and repair damaged hair. It provides intensive moisture and nutrients for healthier-looking hair."
            },
            {
                "id": 32,
                "name": "Leave-In Conditioner",
                "description": "Leave-in conditioner is a lightweight product applied after washing hair to provide extra moisture and protection. It helps to detangle and smooth hair without rinsing."
            },
            {
                "id": 33,
                "name": "Hair Oil",
                "description": "Hair oil is a product used to add shine, hydration, and nourishment to hair. It can help tame frizz and improve overall hair health."
            },
            {
                "id": 34,
                "name": "Heat Protectant Spray",
                "description": "Heat protectant spray is used before styling with heat tools to shield hair from damage. It creates a protective barrier to minimize heat exposure."
            },
            {
                "id": 35,
                "name": "Scalp Treatment",
                "description": "Scalp treatment products target scalp issues such as dryness, dandruff, or irritation. They can help maintain a healthy scalp environment for optimal hair growth."
            },
            {
                "id": 36,
                "name": "Hair Spray",
                "description": "Hair spray is a styling product used to hold hairstyles in place. It provides flexible or strong hold depending on the formulation."
            },
            {
                "id": 37,
                "name": "Dry Shampoo",
                "description": "Dry shampoo is a powder or spray product used to absorb oil and refresh hair between washes. It helps to extend the time between shampooing."
            },
            {
                "id": 38,
                "name": "Hair Serum",
                "description": "Hair serum is a lightweight product that provides shine and smoothness to hair. It often contains silicones and other nourishing ingredients for a polished finish."
            }
        ]
    },
    "errors": null,
    "paging": null,
    "isSuccess": null
}
```

## UPDATE CATEGORY 

Endpoint : /api/categories/{categoryId}

Request Header : 

- X-API-TOKEN-ADMIN : Token

Request Body : 

```json
{
    "categoryItemRequests": [
        {
            "name":"Sunscreen",
            "description":"Sunscreen is a protective skincare product that shields your skin from the harmful effects of the sun's ultraviolet (UV) rays. It helps prevent sunburn, premature aging, and reduces the risk of skin cancer. Sunscreens come in various forms, such as lotions, creams, gels, and sprays, and are available with different SPF (Sun Protection Factor) levels to suit varying skin types and sun exposure needs. For best results, apply sunscreen generously to all exposed skin and reapply every two hours, or more frequently if swimming or sweating."
        }
    ]
    
}
```
Response Body (Success) :

```json
{
  "data": {
    "id": 3,
    "name": "HAIRCARE",
    "categoryImage": "https://drive.google.com/uc?export=view&id=1Xe8x4Orvr8yka1rN7eq4IZHLQwWhJopk",
    "createdAt": "2024-10-31T03:13:05.000+00:00",
    "lastUpdatedAt": "2024-10-31T03:13:05.000+00:00",
    "categoryItems": [
      {
        "id": 29,
        "name": "Shampoo",
        "description": "Shampoo is a hair care product used to cleanse the hair and scalp. It removes dirt, oil, and product buildup, leaving hair clean and refreshed."
      },
      {
        "id": 30,
        "name": "Conditioner",
        "description": "Conditioner is used after shampooing to hydrate and detangle hair. It helps to soften the hair and improve manageability, often containing nourishing ingredients."
      },
      {
        "id": 31,
        "name": "Hair Mask",
        "description": "A hair mask is a deep conditioning treatment designed to nourish and repair damaged hair. It provides intensive moisture and nutrients for healthier-looking hair."
      },
      {
        "id": 32,
        "name": "Leave-In Conditioner",
        "description": "Leave-in conditioner is a lightweight product applied after washing hair to provide extra moisture and protection. It helps to detangle and smooth hair without rinsing."
      },
      {
        "id": 33,
        "name": "Hair Oil",
        "description": "Hair oil is a product used to add shine, hydration, and nourishment to hair. It can help tame frizz and improve overall hair health."
      },
      {
        "id": 34,
        "name": "Heat Protectant Spray",
        "description": "Heat protectant spray is used before styling with heat tools to shield hair from damage. It creates a protective barrier to minimize heat exposure."
      },
      {
        "id": 35,
        "name": "Scalp Treatment",
        "description": "Scalp treatment products target scalp issues such as dryness, dandruff, or irritation. They can help maintain a healthy scalp environment for optimal hair growth."
      },
      {
        "id": 36,
        "name": "Hair Spray",
        "description": "Hair spray is a styling product used to hold hairstyles in place. It provides flexible or strong hold depending on the formulation."
      },
      {
        "id": 37,
        "name": "Dry Shampoo",
        "description": "Dry shampoo is a powder or spray product used to absorb oil and refresh hair between washes. It helps to extend the time between shampooing."
      },
      {
        "id": 38,
        "name": "Hair Serum",
        "description": "Hair serum is a lightweight product that provides shine and smoothness to hair. It often contains silicones and other nourishing ingredients for a polished finish."
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

## DELETE CATEGORY

Endpoint : /api/categories/{categoryId}

Request Header : 

- X-API-TOKEN-ADMIN : Token

Response Body :

```json
{
  "data" : "Category berhasil dihapus"
}
```

## ADD CATEGORY ITEM

Endpoint : /api/categories/{categoryId}

Request Header :

- X-API-TOKEN-ADMIN : Token

Request Body : 

```json
{
    "name": "Sunscreen",
    "description": "Sunscreen is a protective skincare product that shields your skin from the harmful effects of the sun's ultraviolet (UV) rays. It helps prevent sunburn, premature aging, and reduces the risk of skin cancer. Sunscreens come in various forms, such as lotions, creams, gels, and sprays, and are available with different SPF (Sun Protection Factor) levels to suit varying skin types and sun exposure needs. For best results, apply sunscreen generously to all exposed skin and reapply every two hours, or more frequently if swimming or sweating."
}
```

Response Body :

```json
{
  "data" : "Category Item berhasil ditambah"
}
```