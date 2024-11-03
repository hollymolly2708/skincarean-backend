## PRODUCT API SPEC

## CREATE PRODUCT

Endpoint : POST /api/products

Request Header :

- X-API-TOKEN-ADMIN : Token

Request Body :

```json
{
  "productName": "MAKARIZO Olive Extract Creambath",
  "productDescription": "Makarizo Olive Extract Creambath is a nourishing hair treatment enriched with the natural goodness of olive extract. This creambath deeply moisturizes and revitalizes dry, damaged hair, restoring its natural shine and softness. The olive extract works to repair and strengthen hair fibers, protecting against further damage from environmental factors and styling. Suitable for all hair types, it leaves hair feeling smooth, manageable, and healthy. Ideal for use in salons or as a luxurious home hair treatment.",
  "isPromo": true,
  "thumbnailImage": "https://drive.google.com/uc?export=view&id=1NGami9lY2-aBSTGdVzAuRBnz5cKS2fgx",
  "bpomCode": "-",
  "brandId": 4,
  "categoryItemId": 31,
  "isPopularProduct": true,
  "ingredient": "Aqua, Cetearyl Alcohol, Cetrimonium Chloride, Olea Europaea (Olive) Fruit Extract, Behentrimonium Methosulfate, Cetyl Alcohol, Glycerin, Polyquaternium-7, Fragrance, Propylene Glycol, Panthenol, Methylparaben, Phenoxyethanol, Disodium EDTA, Citric Acid, Ethylhexylglycerin.",
  "productVariants": [
    {
      "size": "300ml",
      "stok": 1000,
      "originalPrice": 90000,
      "discount": 10,
      "thumbnailVariantImage": "https://drive.google.com/uc?export=view&id=1Wp6pvL4nma1XIPAgECQP4lsdTGDWqOcS",
      "productImages": [
        {
          "imageUrl": "https://drive.google.com/uc?export=view&id=1Wp6pvL4nma1XIPAgECQP4lsdTGDWqOcS"
        },
        {
          "imageUrl": "https://drive.google.com/uc?export=view&id=1d5wtNwRRcTQ63yrYzRngDPE9OUjQJfEw"
        },
        {
          "imageUrl": "https://drive.google.com/uc?export=view&id=188KNikYqC9p3-I01Rg-9pyj8phcVzCwb"
        }
      ]
    },
    {
      "size": "60ml",
      "stok": 1000,
      "originalPrice": 20000,
      "discount": 10,
      "thumbnailVariantImage": "https://drive.google.com/uc?export=view&id=1sVOvx7UpbHggUh2jtmViDsJ4WGLKPTxJ",
      "productImages": [
        {
          "imageUrl": "https://drive.google.com/uc?export=view&id=116tFeHV5uusON2yFUn9EM4-X2JOMBjTy"
        },
        {
          "imageUrl": "https://drive.google.com/uc?export=view&id=1THgaXwefsuu2R4VVWoqVQvmvNIOuOvgN"
        },
        {
          "imageUrl": "https://drive.google.com/uc?export=view&id=1SIdnFHeCWISUlYDDew0BOFxvlbOSH5vK"
        }
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

Response Body (Success):

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
      "productId": "0bc69fa2-9396-41d1-aab8-e8adeff2d70c",
      "productName": "SOMETHINC Dark Spot Reducer Ampoule 30ml",
      "isPromo": true,
      "thumbnailImage": "https://drive.google.com/uc?export=view&id=1yxQwr9TTZW6uTsfdIZ_hCfoexTp6TLGO",
      "isPopularProduct": true,
      "brandName": "SOMETHINC",
      "categoryName": "Serum",
      "firstOriginalPrice": 180000.00,
      "firstPrice": 162000.00,
      "firstDiscount": 10.00,
      "minPrice": 162000.00,
      "maxPrice": 162000.00
    },
    {
      "productId": "0bf3789c-a2d5-4acb-9c8d-01b95c2c0119",
      "productName": "SOMEBYMI AHA BHA PHA 30 Days Miracle Cleansing Bar 109g",
      "isPromo": true,
      "thumbnailImage": "https://drive.google.com/uc?export=view&id=1Ex_-Eo8q_U2FJGN9l7_RBfncfxS8Eu4T",
      "isPopularProduct": true,
      "brandName": "SOMEBYMI",
      "categoryName": "Body Wash",
      "firstOriginalPrice": 120000.00,
      "firstPrice": 105600.00,
      "firstDiscount": 12.00,
      "minPrice": 105600.00,
      "maxPrice": 105600.00
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
  "errors": "Unauthorized"
}
```

## GET DETAIL PRODUCT

Endpoint : GET /api/products/{productId}

Response Body (Success):

```json
{
  "data": {
    "productId": "0297fec5-942b-4a8b-a80d-d7ffbfdea750",
    "productName": "TRESEMME Scalp Care Shampoo",
    "productDescription": "TRESemmé Scalp Care, a shampoo that provides 2 benefits at once, namely preventing dandruff and hair loss. Dandruff itself is triggered by the growth of fungi and bacteria which causes scalp cells to peel quickly, making the scalp sensitive and itchy. For this reason, the active ingredient ZPTO is an effective solution for treating dandruff so that it will no longer make you worry about white spots on dark colored clothes or when your hair is braided. Not only does it reduce dandruff, TRESemmé Scalp Care also contains Tea Tree Oil which nourishes your hair roots so your hair can grow strong. Now, your hairstyle doesn't always have to be short because of hair loss, but you can create your own style!",
    "isPromo": true,
    "thumbnailImage": "https://drive.google.com/uc?export=view&id=1EOlyFfkria5SYymiMHKPDrC78AYLRRgO",
    "bpomCode": "-",
    "totalStok": 1000,
    "brandName": "TRESEMME",
    "isPopularProduct": true,
    "ingredient": "Aqua, Sodium Laureth Sulfate, Cocamidopropyl Betaine, Glycerin, Acrylates Copolymer, Carbomer, Citric acid, Cocamide MEA, Dimethiconol, Disodium EDTA, Glycol Distearate, Guar Hydroxypropyltrimonium Chloride, Mica, Parfum, PEG-45M, Phenoxyethanol, PPG-12, Silica, Sodium benzoate, Sodium chloride, Sodium hydroxide, TEA-Dodecylbenzenesulfonate, TEA-Sulfate, Benzyl alcohol, Benzyl Salicylate, Citronellol, Geraniol, Hexyl Cinnamal, Limonene, Linalool, CI 77891",
    "categoryName": "Shampoo",
    "minPrice": 36000.00,
    "maxPrice": 72000.00,
    "productVariants": [
      {
        "id": 78,
        "size": "170ml",
        "stok": 1000,
        "price": 36000.00,
        "originalPrice": 40000.00,
        "discount": 10.00,
        "thumbnailVariantImage": "https://drive.google.com/uc?export=view&id=19uw6L3msvIFkd1xAYsxWMX_rw54u5muR",
        "productVariantImages": [
          {
            "id": 194,
            "imageUrl": "https://drive.google.com/uc?export=view&id=1PHsDHGhl3g-Q8HgRF4pNa-KcFcNEC-8e"
          },
          {
            "id": 195,
            "imageUrl": "https://drive.google.com/uc?export=view&id=1zYRxs_UJ63Ni_rneX1q1VVBOfnT73OF1"
          },
          {
            "id": 196,
            "imageUrl": "https://drive.google.com/uc?export=view&id=19uw6L3msvIFkd1xAYsxWMX_rw54u5muR"
          }
        ]
      },
      {
        "id": 79,
        "size": "340ml",
        "stok": 1000,
        "price": 72000.00,
        "originalPrice": 80000.00,
        "discount": 10.00,
        "thumbnailVariantImage": "https://drive.google.com/uc?export=view&id=1_1c63cdVS5Sg8BUTv1PFYzz77FT1Yk8J",
        "productVariantImages": [
          {
            "id": 197,
            "imageUrl": "https://drive.google.com/uc?export=view&id=1_1c63cdVS5Sg8BUTv1PFYzz77FT1Yk8J"
          }
        ]
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
  "errors": "Unauthorized"
}
```

## POPULAR PRODUCT

Endpoint : GET /api/products/popular-product

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
            "productId": "0bc69fa2-9396-41d1-aab8-e8adeff2d70c",
            "productName": "SOMETHINC Dark Spot Reducer Ampoule 30ml",
            "isPromo": true,
            "thumbnailImage": "https://drive.google.com/uc?export=view&id=1yxQwr9TTZW6uTsfdIZ_hCfoexTp6TLGO",
            "isPopularProduct": true,
            "brandName": "SOMETHINC",
            "categoryName": "Serum",
            "firstOriginalPrice": 180000.00,
            "firstPrice": 162000.00,
            "firstDiscount": 10.00,
            "minPrice": 162000.00,
            "maxPrice": 162000.00
        },
        {
            "productId": "0bf3789c-a2d5-4acb-9c8d-01b95c2c0119",
            "productName": "SOMEBYMI AHA BHA PHA 30 Days Miracle Cleansing Bar 109g",
            "isPromo": true,
            "thumbnailImage": "https://drive.google.com/uc?export=view&id=1Ex_-Eo8q_U2FJGN9l7_RBfncfxS8Eu4T",
            "isPopularProduct": true,
            "brandName": "SOMEBYMI",
            "categoryName": "Body Wash",
            "firstOriginalPrice": 120000.00,
            "firstPrice": 105600.00,
            "firstDiscount": 12.00,
            "minPrice": 105600.00,
            "maxPrice": 105600.00
        }
    ],
  "errors": null,
  "paging": null,
  "isSuccess": null
}
```

## DETAIL PRODUCT BY VARIANT 

Endpoint : GET /api/products/{productId}/variants/{variantId}

Response Body (Success) :

```json
{
    "data": {
        "productId": "72f274ec-5843-422a-b2b2-e46e62d997e0",
        "productName": "MAKARIZO Olive Extract Creambath",
        "productDescription": "Makarizo Olive Extract Creambath is a nourishing hair treatment enriched with the natural goodness of olive extract. This creambath deeply moisturizes and revitalizes dry, damaged hair, restoring its natural shine and softness. The olive extract works to repair and strengthen hair fibers, protecting against further damage from environmental factors and styling. Suitable for all hair types, it leaves hair feeling smooth, manageable, and healthy. Ideal for use in salons or as a luxurious home hair treatment.",
        "isPromo": true,
        "thumbnailImage": "https://drive.google.com/uc?export=view&id=1NGami9lY2-aBSTGdVzAuRBnz5cKS2fgx",
        "bpomCode": "-",
        "totalStok": 1000,
        "brandName": "MAKARIZO",
        "isPopularProduct": true,
        "ingredient": "Aqua, Cetearyl Alcohol, Cetrimonium Chloride, Olea Europaea (Olive) Fruit Extract, Behentrimonium Methosulfate, Cetyl Alcohol, Glycerin, Polyquaternium-7, Fragrance, Propylene Glycol, Panthenol, Methylparaben, Phenoxyethanol, Disodium EDTA, Citric Acid, Ethylhexylglycerin.",
        "categoryName": "Hair Mask",
        "productVariant": {
            "id": 84,
            "size": "300ml",
            "stok": 1000,
            "price": 81000.00,
            "originalPrice": 90000.00,
            "discount": 10.00,
            "thumbnailVariantImage": "https://drive.google.com/uc?export=view&id=1Wp6pvL4nma1XIPAgECQP4lsdTGDWqOcS",
            "productVariantImages": [
                {
                    "id": 208,
                    "imageUrl": "https://drive.google.com/uc?export=view&id=1Wp6pvL4nma1XIPAgECQP4lsdTGDWqOcS"
                },
                {
                    "id": 209,
                    "imageUrl": "https://drive.google.com/uc?export=view&id=1d5wtNwRRcTQ63yrYzRngDPE9OUjQJfEw"
                },
                {
                    "id": 210,
                    "imageUrl": "https://drive.google.com/uc?export=view&id=188KNikYqC9p3-I01Rg-9pyj8phcVzCwb"
                }
            ]
        }
    },
    "errors": null,
    "paging": null,
    "isSuccess": null
}
```


## SEARCH PRODUCT

Endpoint : GET /api/products

Query Param :

- name : String, product name, using like query
- page : Integer, start from 0, default 0
- size : Integer, default 10

Response Body (Success) :

```json
{
  "data": [
    {
      "productId": "0bc69fa2-9396-41d1-aab8-e8adeff2d70c",
      "productName": "SOMETHINC Dark Spot Reducer Ampoule 30ml",
      "isPromo": true,
      "thumbnailImage": "https://drive.google.com/uc?export=view&id=1yxQwr9TTZW6uTsfdIZ_hCfoexTp6TLGO",
      "isPopularProduct": true,
      "brandName": "SOMETHINC",
      "categoryName": "Serum",
      "firstOriginalPrice": 180000.00,
      "firstPrice": 162000.00,
      "firstDiscount": 10.00,
      "minPrice": 162000.00,
      "maxPrice": 162000.00
    },
    {
      "productId": "2081c27e-fd46-436e-ad07-2f73c3933a86",
      "productName": "SOMETHINC 5% Niacinamide + Moisture Sabi Beet Serum 30ml",
      "isPromo": true,
      "thumbnailImage": "https://drive.google.com/uc?export=view&id=1-M5EQJoYkysF1QyTSu9LlQI6KdCcDaDG",
      "isPopularProduct": true,
      "brandName": "SOMETHINC",
      "categoryName": "Serum",
      "firstOriginalPrice": 150000.00,
      "firstPrice": 135000.00,
      "firstDiscount": 10.00,
      "minPrice": 135000.00,
      "maxPrice": 135000.00
    },
    {
      "productId": "3e3df89b-c126-4e43-b488-340c5960d4ea",
      "productName": "SOMETHINC 2% BHA Salicylic Acid Acne Treatment 30ml",
      "isPromo": true,
      "thumbnailImage": "https://drive.google.com/uc?export=view&id=1bCugIOeicD75Lt13D38uCRvnNCwvkobb",
      "isPopularProduct": true,
      "brandName": "SOMETHINC",
      "categoryName": "Serum",
      "firstOriginalPrice": 90000.00,
      "firstPrice": 81000.00,
      "firstDiscount": 10.00,
      "minPrice": 81000.00,
      "maxPrice": 81000.00
    },
    {
      "productId": "dbd268e7-7b2b-4062-bcc8-fda9af91e772",
      "productName": "SOMETHINC Lemonade Waterless Vitamin C + Ferulic + NAG 30ml",
      "isPromo": true,
      "thumbnailImage": "https://drive.google.com/uc?export=view&id=1A_zZ-b912hQdBNqJaC538tpVD_nIe-et",
      "isPopularProduct": true,
      "brandName": "SOMETHINC",
      "categoryName": "Serum",
      "firstOriginalPrice": 150000.00,
      "firstPrice": 135000.00,
      "firstDiscount": 10.00,
      "minPrice": 135000.00,
      "maxPrice": 135000.00
    }
  ],
  "errors": null,
  "paging": {
    "currentPage": 0,
    "totalPage": 1,
    "size": 10
  },
  "isSuccess": null
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


Request Body :

```json
{
  "productVariants": [
    {
      "id": 64,
      "size": "500ml",
      "stok": 1000,
      "discount": 10,
      "productImages": [
        {
          "imageUrl": "https://drive.google.com/uc?export=view&id=1pYZ1CW31kZKSJGKdb6z8JwOSqT46bm8I",
          "id": 161,
          "productVariantId": 64
        }
      ]
    }
  ]
}
```

Response Body (Success) :

```json
{
  "data": {
    "productId": "0297fec5-942b-4a8b-a80d-d7ffbfdea750",
    "productName": "TRESEMME Scalp Care Shampoo",
    "productDescription": "TRESemmé Scalp Care, a shampoo that provides 2 benefits at once, namely preventing dandruff and hair loss. Dandruff itself is triggered by the growth of fungi and bacteria which causes scalp cells to peel quickly, making the scalp sensitive and itchy. For this reason, the active ingredient ZPTO is an effective solution for treating dandruff so that it will no longer make you worry about white spots on dark colored clothes or when your hair is braided. Not only does it reduce dandruff, TRESemmé Scalp Care also contains Tea Tree Oil which nourishes your hair roots so your hair can grow strong. Now, your hairstyle doesn't always have to be short because of hair loss, but you can create your own style!",
    "isPromo": true,
    "thumbnailImage": "https://drive.google.com/uc?export=view&id=1EOlyFfkria5SYymiMHKPDrC78AYLRRgO",
    "bpomCode": "-",
    "totalStok": 1000,
    "brandName": "TRESEMME",
    "isPopularProduct": true,
    "ingredient": "Aqua, Sodium Laureth Sulfate, Cocamidopropyl Betaine, Glycerin, Acrylates Copolymer, Carbomer, Citric acid, Cocamide MEA, Dimethiconol, Disodium EDTA, Glycol Distearate, Guar Hydroxypropyltrimonium Chloride, Mica, Parfum, PEG-45M, Phenoxyethanol, PPG-12, Silica, Sodium benzoate, Sodium chloride, Sodium hydroxide, TEA-Dodecylbenzenesulfonate, TEA-Sulfate, Benzyl alcohol, Benzyl Salicylate, Citronellol, Geraniol, Hexyl Cinnamal, Limonene, Linalool, CI 77891",
    "categoryName": "Shampoo",
    "minPrice": 36000.00,
    "maxPrice": 72000.00,
    "productVariants": [
      {
        "id": 78,
        "size": "170ml",
        "stok": 1000,
        "price": 36000.00,
        "originalPrice": 40000.00,
        "discount": 10.00,
        "thumbnailVariantImage": "https://drive.google.com/uc?export=view&id=19uw6L3msvIFkd1xAYsxWMX_rw54u5muR",
        "productVariantImages": [
          {
            "id": 194,
            "imageUrl": "https://drive.google.com/uc?export=view&id=1PHsDHGhl3g-Q8HgRF4pNa-KcFcNEC-8e"
          },
          {
            "id": 195,
            "imageUrl": "https://drive.google.com/uc?export=view&id=1zYRxs_UJ63Ni_rneX1q1VVBOfnT73OF1"
          },
          {
            "id": 196,
            "imageUrl": "https://drive.google.com/uc?export=view&id=19uw6L3msvIFkd1xAYsxWMX_rw54u5muR"
          }
        ]
      },
      {
        "id": 79,
        "size": "340ml",
        "stok": 1000,
        "price": 72000.00,
        "originalPrice": 80000.00,
        "discount": 10.00,
        "thumbnailVariantImage": "https://drive.google.com/uc?export=view&id=1_1c63cdVS5Sg8BUTv1PFYzz77FT1Yk8J",
        "productVariantImages": [
          {
            "id": 197,
            "imageUrl": "https://drive.google.com/uc?export=view&id=1_1c63cdVS5Sg8BUTv1PFYzz77FT1Yk8J"
          }
        ]
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