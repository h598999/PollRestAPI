curl -X PUT "http://localhost:8080/api/v1/votes/0" \
-H "Content-Type: application/json" \
-d '{
  "voteOption": {
    "id": 0,
    "caption": "Java"
  }
}' | jq
