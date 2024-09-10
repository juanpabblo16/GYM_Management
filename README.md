# Gym Management Project with Microservices

This project is a gym management system designed using a microservices architecture. Each microservice is responsible for a specific part of the gym's functionality, allowing for high scalability and maintainability of the system.

## Project Specifications

### Microservices

1. **Member Microservice**
   - **Responsibility**: Management of gym members.
   - **Database**: Members database.
   - **Endpoints**:
     - `POST /members`: Create a new member.
     - `GET /members`: Retrieve all members.

2. **Class Microservice**
   - **Responsibility**: Management of scheduled gym classes.
   - **Database**: Classes database.
   - **Endpoints**:
     - `POST /class`: Schedule a new class.
     - `GET /class`: Retrieve all classes.

3. **Trainer Microservice**
   - **Responsibility**: Management of trainers.
   - **Database**: Trainers database.
   - **Endpoints**:
     - `POST /trainer`: Create a new trainer.
     - `GET /trainer`: Retrieve all trainers.

4. **Team Microservice**
   - **Responsibility**: Management of gym equipment.
   - **Database**: Equipment database.
   - **Endpoints**:
     - `POST /team`: Create a new team.
     - `GET /team`: Retrieve all teams.

## Keycloak Configuration

1. **Create a Realm:** Set up a new Realm in Keycloak for your application.
2. **Create Clients:**
    - Go to the "Clients" section and create a new client named `Team-services`.
    - Configure the following parameters:
      - `Client ID`: `Team-services`
      - `Client Protocol`: `openid-connect`
      - `Access Type`: `confidential`
    - Ensure the following authentication flows are enabled:
      - `Standard Flow Enabled`: `true`
      - `Direct Access Grants Enabled`: `true`
    - Add the `Redirect URIs`:
      - `http://localhost:8080/*`
      - `http://localhost:8084/*`
3. **Create Roles:**
    - Create roles for your application: `ROLE_MEMBER`, `ROLE_TRAINER`, `ROLE_ADMIN`.
4. **Create Users:**
    - Create users in Keycloak and assign the corresponding roles.

Authentication using postman
![](/imgs/Captura%20de%20pantalla%202024-09-10%20020826.png)
![](/imgs/teams.png)
## API Documentation with Swagger

Swagger is integrated to provide an interactive user interface for API documentation. To access the Swagger documentation:

- Ensure the application is running.
- Navigate to: `http://localhost:8080/swagger-ui.html` or `http://localhost:8084/swagger-ui.html`

Team-microservices example
![](/imgs/swagerteam.png)

## Implementation Steps

1. **Defining Requirements**
   - Identification of bounded contexts and responsibility segregation.
   - Definition of endpoints and interaction between microservices.

2. **Architecture Design**
   - Creation of an architecture diagram illustrating the relationship between microservices and their databases.
   ![Component Diagram](Gym_Managment-Page-1.jpg)

Link:[https://drive.google.com/file/d/1oIcTH7_EvVnRKV65AhUcJzeUh7r7hcnS/view?usp=sharing]
3. **Microservice Implementation**
   - Configuration of projects with Spring Boot.
   - Creation of entities, repositories, services, and controllers for each microservice.
   - Implementation of communication between microservices.

4. **Database Configuration**
   - Definition of database structures for each microservice.

5. **Documentation and Deployment**
   - Preparation of project documentation.
   - Local deployment of the microservices.

## How to Run the Project

1. Clone the Repository
   ```bash
   git clone https://github.com/juanpabblo16/GYM_Management.git

2. Open each microservice in a separate project in IntelliJ.
3. Run the projects.
4. Test the endpoints with Postman.

## Technologies Used

- **Spring Boot**: Main framework for microservice development.
- **Spring Data JPA**: For persistence management and interaction with databases.
- **H2 Database**: In-memory database used for development and testing
- **Postman**: Tool for testing API endpoints.
- **Draw io**: Tool for creating UML diagrams of the system architecture.
- **Maven**: Project management and build automation tool.

## Authors

- Jesus Garces
- Juan Pablo Acevedo
- Juan Sebastian Diaz
