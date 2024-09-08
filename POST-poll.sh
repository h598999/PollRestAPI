curl -X POST http://localhost:8080/api/v1/Polls/0 \
-H "Content-Type: application/json" \
-d '{
  "question": "What is your favorite programming language?",
  "voteOptions": [
    {
      "caption": "Java",
      "presentationOrder": 1
    },
    {
      "caption": "Python",
      "presentationOrder": 2
    },
    {
      "caption": "C++",
      "presentationOrder": 3
    }
  ]
}' | jq

