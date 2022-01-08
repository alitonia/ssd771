curl -i \
-X PATCH \
-H 'Content-Type: application/json' \
-d '{"cardCode":"121319_group3_2020", "owner":"Group 3", "cvvCode":"865", "dateExpired":"1125"}' \
https://ecopark-system-api.herokuapp.com/api/card/reset-balance