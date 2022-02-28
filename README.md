# Cashapp Online bank
Online bank provides customers with options to manage their bank account with basic functionalities.
We need to provide the below capabilities:
* Deposit, withdraw and maintain a balance for multiple customers
* Return a customer’s balance 
* Prevent customers from withdrawing more money than they have in their account

## Technologies Used

The project utilises the following technologies:
- Java 11
- Spring Boot
- Gradle
- H2 in memory as the data store (for simplicity)
- API specifications is provided as swagger file under 'openAPI' folder

## Design considerations
The following design considerations were made:
- A customer can have multiple bank accounts. Excluded many:many scenarios for customer to bank account.
- An account can have multiple transactions. Excluded fund transfer from one account to another.
```
┌─────────┐   1:n  ┌────────┐
│ Bank    ├───────►│Customer│
└─────────┘        └───┬────┘
                       │1:n
                       │
                   ┌───▼────┐
                   │ Account│
                   └───┬────┘
                       │
                       │1:n
                 ┌─────▼───────┐
                 │ Transaction │
                 └─────────────┘
```
- Use of single endpoint for posting transactions. This is to avoid new endpoint per transactionType 
and to align to restful standards.

## To do
Given the time constraints, the following are to be considered in future release:
- Add security to endpoints
- Replace h2 in memory with proper db instance
- Containerize for improved deployment
- CRUD operations
- Multi threaded Concurrency
- Extend usecase on many to many relationship for customer-bankaccount

## Build & Run
The project utilises Gradle Wrapper.
To build the project, browse to the project root directory and simply execute:
```
./gradlew build
```
To run the application, execute:
```
./gradlew bootRun
```
This will initialise SpringBoot on port 8080 where the API can be accessed via:
```
http://localhost:8080
```

The project is preloaded with sample customer data with associated accounts and transactions:

### Account
| id | customer_id | balance |
| --- | --- | --- |
| 1001   | 1001 | 100 |
| 1002   | 1002 | 200 |

### Bank
| id | name | balance |
| --- | --- | --- |
| 1001   | Cash app online bank | 300 |

## Endpoints
Post a transaction to customer's bank account with transaction type - withdraw/deposit. 
Sample request part of the integration test
```shell
POST http://localhost:8080/v1/accounts/{id}/transaction
```
Get customer's bank account details
```shell

GET http://localhost:8080/v1/customer/{id}/accounts
```

Get Bank balance details
```shell
http://localhost:8080/v1/bank
```