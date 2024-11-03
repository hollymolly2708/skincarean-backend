## PAYMENT

## WANNA PAY 

Endpoint : POST /api/payments/confirm

Request Param :

```json
{
  "paymentCode" : "938293928"
}
```

Response Body (Success) :

```json
{
  "data": "Pembayaran berhasil dibayarkan"
 
}
```

Response Body (Failed):

```json
{
  "data": "Kode pembayaran tidak valid"
}
```


