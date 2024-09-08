curl -X POST http://localhost:8080/api/v1/users \
-H "Content-Type: application/json" \
-d '{
  "username": "john_doe3",
  "email": "john3@example.com"
}' | jq


