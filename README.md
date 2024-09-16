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
   ![Component Diagram](imgs/Gym_Managment-Page-1.drawio.png)

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
  
Aquí tienes una sección de README que describe cómo se implementó RabbitMQ en el proyecto:

* * *

🐇 RabbitMQ Implementation
--------------------------

En este proyecto, hemos implementado RabbitMQ como middleware de mensajería para manejar diferentes patrones de comunicación entre microservicios. A continuación, se describe cómo se implementaron las principales funcionalidades de RabbitMQ, incluyendo el sistema de notificaciones para nuevas inscripciones, el patrón publish/subscribe para cambios en los horarios de clases y el manejo de la Dead Letter Queue (DLQ) para los pagos fallidos.

### Tabla de Contenidos

1.  [Instalación de RabbitMQ](#instalaci%C3%B3n-de-rabbitmq)
2.  [Configuración General](#configuraci%C3%B3n-general)
3.  [Sistema de Notificaciones para Nuevas Inscripciones](#sistema-de-notificaciones-para-nuevas-inscripciones)
4.  [Patrón Publish/Subscribe para Horarios de Clases](#patr%C3%B3n-publishsubscribe-para-horarios-de-clases)
5.  [Dead Letter Queue (DLQ) para Pagos Fallidos](#dead-letter-queue-dlq-para-pagos-fallidos)

* * *

### Instalación de RabbitMQ

Para comenzar, asegúrate de que RabbitMQ esté instalado y funcionando localmente o en un servidor. Si aún no lo tienes instalado, puedes hacerlo ejecutando el siguiente comando:

```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

Una vez iniciado el contenedor, puedes acceder al panel de administración de RabbitMQ en `http://localhost:15672` con las credenciales por defecto:

*   Usuario: `guest`
*   Contraseña: `guest`

* * *

### Configuración General

Cada uno de nuestros microservicios incluye una configuración específica para conectar y utilizar RabbitMQ. A continuación se muestra la configuración básica que se repite en la mayoría de los microservicios.

#### Dependencia de Maven

Primero, asegúrate de incluir la siguiente dependencia de RabbitMQ en el `pom.xml` de cada microservicio:

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

#### Configuración en `application.properties`

Cada microservicio debe incluir la configuración para RabbitMQ en el archivo `application.properties`:

```properties
# RabbitMQ configuration
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

#### Configuración de RabbitMQ en el código

En cada microservicio hemos creado una clase de configuración `RabbitMQConfig.java` para definir los intercambios, colas y bindings.

* * *

### Sistema de Notificaciones para Nuevas Inscripciones

El sistema de notificaciones para nuevas inscripciones se implementa utilizando un patrón de cola simple en el microservicio de **Members**. Cada vez que un nuevo miembro se registra, se publica un mensaje en una cola de RabbitMQ, que posteriormente es consumido por un servicio que envía una notificación.

#### Publicador de mensajes

Cuando un nuevo miembro se inscribe, el siguiente código publica una notificación:

```java

@Autowired
private RabbitTemplate rabbitTemplate;

public void notifyNewMember
(Member member) {
    rabbitTemplate.convertAndSend("member-notifications-exchange", "", "Nuevo miembro registrado: " + member.getName());
}
```

#### Consumidor de mensajes

El servicio que escucha las nuevas inscripciones y procesa la notificación:

```java

@RabbitListener(queues = "member-notifications-queue")
public void receiveNewMemberNotification
(String message) {
    System.out.println("Notificación recibida: " + message);
}
```

* * *

### Patrón Publish/Subscribe para Horarios de Clases

Para implementar un patrón **publish/subscribe** en el microservicio de **Classes**, utilizamos un `FanoutExchange`. Este tipo de intercambio distribuye mensajes a todas las colas enlazadas a dicho exchange, lo que nos permite notificar a múltiples consumidores cada vez que hay un cambio en los horarios de clases.

#### Configuración del intercambio `FanoutExchange`

```java

@Bean
public FanoutExchange scheduleChangeExchange
() {
    return new FanoutExchange("schedule-change-exchange");
}
```

#### Publicación de cambios de horario

Cada vez que se programa una nueva clase o se modifica un horario, se publica un mensaje de notificación:

```java

public void notifyScheduleChange
(String classDetails) {
    rabbitTemplate.convertAndSend("schedule-change-exchange", "", "Cambio de horario: " + classDetails);
}
```

#### Consumidor de mensajes de horarios

Un consumidor suscrito a los cambios de horario:

```java

@RabbitListener(queues = "schedule-change-queue")
public void handleScheduleChange
(String message) {
    System.out.println("Notificación de cambio de horario: " + message);
}
```

* * *

### Dead Letter Queue (DLQ) para Pagos Fallidos

Para manejar pagos fallidos, hemos configurado una **Dead Letter Queue (DLQ)** en el microservicio de **Payments**. La DLQ recibe mensajes que no pudieron procesarse correctamente después de un número específico de intentos.

#### Configuración de la cola principal y DLQ

```java

@Bean
public Queue paymentQueue
() {
    return QueueBuilder.durable("pagos-queue")
            .withArgument("x-dead-letter-exchange", "dlx-exchange")
            .withArgument("x-dead-letter-routing-key", "dlx-routing-key")
            .build();
}

@Bean
public Queue deadLetterQueue
() {
    return new Queue("pagos-dlq");
}
```

#### Consumidor de la cola de pagos fallidos

```java

@RabbitListener(queues = "pagos-dlq")
public void processFailedPayments
(String failedMessage) {
    System.out.println("Procesando pago fallido: " + failedMessage);
    // Lógica adicional para manejar errores de pago
}
```

### Resumen

En este proyecto, RabbitMQ es utilizado para implementar una arquitectura de mensajería que soporta:

*   **Notificaciones** para nuevas inscripciones de miembros.
*   **Publish/Subscribe** para notificaciones de cambios en los horarios de clases.
*   **Dead Letter Queues (DLQ)** para manejar errores en el procesamiento de pagos fallidos.

Cada microservicio está configurado con su propia cola y lógica para publicar o suscribirse a eventos relevantes, asegurando un sistema distribuido robusto y escalable.

* * *

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
