# Employee Leave Management System

A comprehensive microservices-based Employee Leave Management System using Spring Boot, Spring Cloud Gateway, Eureka, Quartz Scheduler, Kafka, and Thymeleaf for the UI.

## Features
- **User Interface (Thymeleaf):** Interactive web-based UI for managing leave requests.
- **Spring Cloud Gateway:** API Gateway for routing requests to appropriate microservices.
- **Eureka Server:** Service discovery for microservices.
- **Leave Request Service:** Manages leave requests and integrates with Kafka for message queuing.
- **Employee Management Service:** Handles employee data and integrates with the leave request service.
- **Kafka:** Used for asynchronous messaging between services.
- **Quartz Scheduler:** Schedules jobs for sending notifications before leave end dates.
- **Email Service:** Sends email notifications based on events in the system.

## Microservices Architecture
- **gateway-service:** Routes requests to appropriate microservices.
- **eureka-server:** Service discovery server for registering all microservices.
- **leave-request-service:** Handles leave requests, interacts with employee service, and integrates with Kafka.
- **employee-service:** Manages employee data and integrates with the leave request service.
- **email-service:** Listens to Kafka topics and sends email notifications.
- **scheduler-service:** Uses Quartz Scheduler to schedule and manage jobs.

## Technologies Used
- **Spring Boot:** For building microservices.
- **Spring Cloud Gateway:** For API gateway.
- **Eureka Server:** For service discovery.
- **Quartz Scheduler:** For job scheduling.
- **Kafka:** For message queuing.
- **Thymeleaf:** For building dynamic web pages.
- **MySQL:** For database management.

## Getting Started
1. Clone the repository:
    ```bash
    git clone https://github.com/your-username/employee-leave-management.git
    ```
2. Build and run the services:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
3. Access the UI at `http://localhost:8080`.

## Contributing
Feel free to fork this repository and contribute by submitting a pull request. For major changes, please open an issue first to discuss what you would like to change.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
