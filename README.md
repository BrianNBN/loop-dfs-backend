# Loop DFS Backend Application Notes

## Project Overview
Welcome to the Loop DFS backend application, a Fintech micro-service designed to manage credit cards and accounts through RESTful APIs. This micro-service is built using Spring Boot and Java.

## Technologies Used
- **Framework:** Spring Boot 3.2.2
- **Language:** Java (version 17)

## Project Structure
The project follows a standard Spring Boot structure with packages for models, controllers, data, services, and repositories.

## Dependencies Used
- **Spring Web:** Used to build RESTful web services.
- **Spring Data JPA:** Used to access and persist data in SQL stores.
- **Spring Boot Starter Test:** Used to enable testing.
- **H2 Database:** Used as an in-memory database.
- **Lombok:** Used to reduce boilerplate code.
- **javafaker** Used to generate fake data.

## Models
### Card
- **`cardId`:** The ID of the card (not editable).
- **`cardAlias`:** Personalized name of the card (editable).
- **`accountId`:** Account to which the card belongs (not editable).
- **`typeOfCard`:** Indicates if a card is virtual or physical (not editable).

### Account
- **`accountId`:** ID of the account.
- **`iban`:** IBAN of the account.
- **`bicSwift`:** BIC swift of the account.
- **`clientId`:** Client to whom the account belongs.

## Implementation Details
### Card Entity
```java
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;
    private String cardAlias;
    private Long accountId;
    private String typeOfCard;
    
}
```
### Account Entity
```java
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private String iban;
    private String bicSwift;
    private Long clientId;

}
```
### Repository Interfaces
```java
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByAccountId(Long accountId);
}

public interface AccountRepository extends JpaRepository<Account, Long> {
}
```
### Service Classes
```java
@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    public List<Card> getCardsByAccount(Long accountId) {
        return cardRepository.findByAccountId(accountId);
    }

    // CRUD operations
}

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    // CRUD operations
}
```
### Controller Classes
```java
@RestController
@RequestMapping("/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    // CRUD operations

    @GetMapping("/{accountId}")
    public List<Card> getCardsByAccount(@PathVariable Long accountId) {
        return cardService.getCardsByAccount(accountId);
    }
}

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    // CRUD operations
}
```
## Data Storage
- In-memory storage is used for simplicity and ease of testing.
- Fake data is generated for at least two different account IDs and two different client IDs.

## Testing
- CRUD operations have been tested using Postman.
### Step 1: Create an Account

- **Request Type:** POST
- **URL:** http://localhost:8080/accounts
- **Headers:** Content-Type: application/json
- **Body:**
  ```json
  {
    "iban": "DE89370400440532013000",
    "bicSwift": "COBADEFF",
    "clientId": 1
  }
    ```
- **Send the Request**

### Step 2: Create another Account

- **Request Type:** POST
- **URL:** http://localhost:8080/accounts
- **Headers:** Content-Type: application/json
- **Body:**
  ```json
  {
  "iban": "FR1420041010050500013M02606",
  "bicSwift": "BNPAFRPP",
  "clientId": 2
  }
    ```
- **Send the Request**

### Step 3: Retrieve All Accounts

- **Request Type:** GET
- **URL:** http://localhost:8080/accounts
- **Headers:** Content-Type: application/json
- **Send the Request**

### Step 4: Create a Card

- **Request Type:** POST
- **URL:** http://localhost:8080/cards
- **Headers:** Content-Type: application/json
- **Body:**
  ```json
  {
    "cardAlias": "My Card",
    "accountId": 1,
    "typeOfCard": "virtual"
  }
    ```
  
- **Send the Request**

### Step 5: Create another Card

- **Request Type:** POST
- **URL:** http://localhost:8080/cards
- **Headers:** Content-Type: application/json
- **Body:**
  ```json
  {
    "cardAlias": "My Card",
    "accountId": 2,
    "typeOfCard": "physical"
  }
    ```
  
- **Send the Request**

### Step 6: Retrieve All Cards

- **Request Type:** GET
- **URL:** http://localhost:8080/cards
- **Headers:** Content-Type: application/json
- **Send the Request**
- **Note:** The response should contain both cards.

### Step 7: Retrieve Cards by Account ID

- **Request Type:** GET
- **URL:** http://localhost:8080/cards/1
- **Headers:** Content-Type: application/json
- **Send the Request**
- **Note:** The response should contain only the first card.

### Step 8: Update a Card

- **Request Type:** PUT
- **URL:** http://localhost:8080/cards/1
- **Headers:** Content-Type: application/json
- **Body:**
  ```json
  {
    "cardAlias": "UpdatedCardAlias"
  }
    ```
  
- **Send the Request**

### Step 9: Delete a Card

- **Request Type:** DELETE
- **URL:** http://localhost:8080/cards/1
- **Headers:** Content-Type: application/json
- **Send the Request**
- **Note:** The response should be empty.

### Step 10: Delete an Account

- **Request Type:** DELETE
- **URL:** http://localhost:8080/accounts/1
- **Headers:** Content-Type: application/json
- **Send the Request**
- **Note:** The response should be empty.

### Step 11: Retrieve All Accounts

- **Request Type:** GET
- **URL:** http://localhost:8080/accounts
- **Headers:** Content-Type: application/json
- **Send the Request**
- **Note:** The response should contain only the second account.

### Step 12: Retrieve All Cards

- **Request Type:** GET
- **URL:** http://localhost:8080/cards
- **Headers:** Content-Type: application/json
- **Send the Request**
- **Note:** The response should contain only the second card.

## Security
### Basic Security Measures

#### Package Structure:
- The project follows a standard Spring Boot structure, including packages for models, controllers, services, and repositories. This separation helps in maintaining a clear and organized codebase, contributing to better security.

#### Entity Annotations:
- In the Card and Account entities, `@Entity` annotations are used, indicating that these classes are JPA entities. This suggests that the application follows Object-Relational Mapping (ORM) principles, which can enhance data security.

#### Repository Interfaces:
- `CardRepository` and `AccountRepository` extend `JpaRepository`, providing basic CRUD operations for entities. These interfaces are gateways to interact with the underlying database securely.

#### Service Classes:
- The `CardService` and `AccountService` classes act as intermediate layers between controllers and repositories. They encapsulate business logic, promoting a more modular and secure code structure.

#### Controller Classes:
- `CardController` and `AccountController` classes are annotated with `@RestController` and use `@RequestMapping` to define API endpoints. This adheres to RESTful principles, allowing for predictable and secure endpoint paths.

### Authentication and Authorization Considerations

#### Entity Fields:
- The Card and Account entities include fields like `accountId` and `clientId`, suggesting potential relationships between entities. This structure implies that certain operations may be restricted based on the relationships, contributing to authorization considerations.

#### Service Classes:
- The service layer (e.g., `CardService` and `AccountService`) is often where business logic, including authorization checks, can be implemented. While the provided snippets don't explicitly show such checks, this is a common location for them.

#### Controller Classes:
- In the `CardController` class, there is an endpoint "/cards/{accountId}" for retrieving cards by account. This implies that card retrieval is restricted based on the account ID, showcasing an authorization consideration.

#### Security Notes:
- The `notes.md` file is mentioned to contain explanations of important considerations, including security. While the specific details are not provided in the snippets, it suggests that security aspects, including authentication and authorization, are documented.
