# withdrawalnotice
Automating the withdrawal notice 

I used an in-memory database(H2) to store data.

To run This Project You will need to clone it/ download it and have it running in your IDE(I used IntelliJ).
Testing the endpoints you can use Postman.

Endpoints you can run:
GET-all investors 
http://localhost:8080/investors
Status code=200 ok

POST-create investor
http://localhost:8080/investors
Status code=201 created

GET-investor by id
http://localhost:8080/investors/1
Status code=200 ok

POST-create product for an existing investor
http://localhost:8080/investors/1/products
Status code=201 created

POST -create a withdrawal
http://localhost:8080/investors/1/1/withdrawal
Status code=201 created
