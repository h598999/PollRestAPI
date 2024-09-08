curl -X POST "http://localhost:8080/api/v1/votes/1" \
-H "Content-Type: application/json" \
-d '{
  "voteOption": {
    "id": 1,
    "caption": "Python"
  }
}' | jq

