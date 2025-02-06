## Description
"PersonalFinance" is a learning project RESTful API, developed using Spring Boot. The API provides capabilities to manage users, currency and operation category reference data, and to track accounts and transactions on them.
## Functionality
### Users
* Registration: The administrator can add new users to the system.
* Information Retrieval: The administrator can retrieve a list of users with pagination, as well as detailed information about a specific user by their UUID.
* Editing: The administrator can edit user information.
### Cabinet
* Registration: Users can register themselves in the system.
* Verification: Users must confirm their registration via email.
* Authorization: Users can log in to the system, receiving a JWT token to access protected resources.
* Retrieving Self Information: Authorized users can get information about their profile.
### Classifier
* Currencies: The administrator or manager can add new currencies to the directory, and also get a list of available currencies with pagination.
* Operation Categories: The administrator or manager can add new operation categories to the directory, and also get a list of available categories with pagination.
### Account
* Creation: Authorized users can create new accounts, specifying their type (cash, bank account, deposit), currency, and description.
* Information Retrieval: Users can retrieve a list of their accounts with pagination, as well as detailed information about a specific account by its UUID.
* Editing: Users can edit information about their accounts.
### Operation
* Adding: Users can add operations to their accounts, specifying the date, description, operation category, and value.
* Information Retrieval: Users can retrieve a list of operations for a specific account with pagination.
* Editing: Users can edit information about operations.
* Deleting: Users can delete operations.
## Technologies:
* Java 21
* Maven
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* PostgreSQL
* Docker