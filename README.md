## SKINCAREAN

Skincarean is an online shopping project which has been built from scratch by me used Java spring boot Framework. This web application provides an API
that would be used by front end such as Android, web, and many more.

The backend used Spring boot, and the project use MVC architecture, Spring Data JPA for Efficient database using MySQL.
Optimize with lombok to reduce boilerplate code, this project is packaged as a WAR file for easy deployment on any servlet container, like Apache Tomcat.

## FEATURES

* Spring Boot MVC Architecture : This project follows a well organized architecture with service, repository, and controller layers
  to ensure clean code, easy maintenance, and scalability.


* Technology or maven library that i used :

   * Java Spring boot Version 3.3.3 : Core framework for the application backend, handling business logic and database interaction.
   * Sping data JPA: Simpilified data access and database queries with custom JPA finder methods.
   * MySQL: Relational database used for data persistence.
   * Lombok: Simplifies code with annotations to eliminate boilerplate code like getter, setter, constructors.
   * Google Api Client & Google Auth : These are would be used for the front end that want to login by gmail.
   * Validation: This one will be used for validation that frontend send request to backend, is the request that sent by frontend has valid or not.


* Authentication and Authorization :

   * To handle authentication and authorization, i use Bearer TOKEN Authentication. So whenever user login using simple login or with google. Backend would be send the Bearer TOKEN for the frontend, and the token will be used by frontend in the request header. So, to access the feature like cart and order, user only needed to send request to backend accompanied by Bearer TOKEN which is in the request header and some request body if there are.
     So the backend will validate, is the user valid or not. Whenever user is'nt valid, the backend will be sent 401 response code that means "Unauthorized". And this is not only for login but whenever user wanna to access the feature of this application that need authorization.


* Deployment: Packaged as a WAR file, suitable for deployment on any servlet container like Apache Tomcat.


* Modern Development Practices: 

  * Spring boot DevTools for faster development and hot reload.
  * Custom JPA Queries for efficient data retrieval and management.


## REQUIREMENTS

* Java 17
* MySQL -Maven -Apache Tomcat (for War deployment)


## INSTALLATION 

1. Clone the repository

   ```bash
    git clone https://github.com/hollymolly2708/skincarean-backend
   ```
   
2. Navigate to project 
  
   ```bash
   cd skincarean
   ```
   
3. Setup the MySQL database :

   ```bash
   CREATE DATABASE skincarean;
   ```
4. Configure the database connection settings in application.properties

   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/skincarean
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
5. Run the application: Access the app at http://localhost:8080


## PATH TO API SPECS

- **User API SPEC Path**: `[User][docs/users.md]`

- **Admin API SPEC Path**: `[Admin][docs/admin.md]`

- **Product API SPEC Path**: `[Product][docs/product.md]` 
- **Payment Method API SPEC Path**: `[Payment][docs/payment-method.md]`  

- **Brand API SPEC Path**: `[Brand][docs/brand.md]`  

- **Category API SPEC Path**: `[Category][docs/category.md]`

- **Cart API SPEC Path**: `[Cart][docs/cart.md]`

- **Order API SPEC Path**: `[Order][docs/order.md]` 

- **Payment API SPEC Path**: `[Payment][docs/payment.md]` 




     
