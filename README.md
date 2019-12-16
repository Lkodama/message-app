# message-app
Rest application with Java/Spring Framework

# Run Locally
 `mvn spring-boot:run`

# Run Tests
`mvn test`

## Api Endpoints
POST - /message 
Body: `{ "content": "Lorem ipsum message" }`

PUT - /message 
Body: `{
    "id": 1,
    "content": "Lorem ipsum message",
    "createdDate": 1576528782737
    }`

DELETE - /message/{:id}

GET - /message/{:id}

GET - /message?page=0&size=1



