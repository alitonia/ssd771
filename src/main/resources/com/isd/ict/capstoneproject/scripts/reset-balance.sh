curl -i \
-X PATCH \
-H 'Content-Type: application/json' \
-d '{"cardCode":"ict_group3_2021", "owner":"Group 3", "cvvCode":"244", "dateExpired":"1125"}' \
https://ecopark-system-api.herokuapp.com/api/card/reset-balance