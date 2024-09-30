# Parking Management Solution
## Overview
The Parking Management Solution is a full-stack Java Spring Boot application designed to manage vehicle parking. It supports vehicle entry and exit, parking spot allocation, and fee calculation, and is designed with multi-tenancy in mind to accommodate multiple customers. This solution can be deployed on Kubernetes, making it highly scalable and suitable for large parking facilities.

## Features
- Vehicle Management: Capture vehicle details such as number, type, entry and exit time.
- Parking Spot Management: Assign, monitor, and release parking spots.
- Fee Calculation: Calculate parking fees based on assigned parking spot rates.
- Multi-Tenancy: Support for multiple tenants using tenant-level data separation.
- REST API Access: Interaction with the system is provided through REST APIs.

## Technologies Used
- Java 17: Programming language used to implement the backend.
- Spring Boot: Framework used for building the application quickly, providing features such as REST API development, dependency injection, and security.
- PostgreSQL: Relational database used for persistent data storage.
- Kubernetes (K8s): Container orchestration for deployment, scaling, and management of the application.
- H2 Database: In-memory database used for development and testing purposes.
- Docker and Containerd: To create, deploy, and run application containers.
- Rancher Desktop: Simplified Kubernetes setup for local development and testing.

## System Architecture
The application follows a three-tier architecture consisting of:

* Presentation Layer: REST APIs for managing vehicles, parking spots, and fees.
* Business Layer: Implements core business logic, including tenant filtering and parking fee calculation.
* Data Access Layer: Uses Spring Data JPA for database interaction.

### Requirements
* Maven: Build and dependency management.
* Spring Boot 3.0+: Framework for the application.
* PostgreSQL 13+: Database for storing persistent information.
* Docker: To containerize the application.
* Kubernetes (K8s): To deploy and manage the application in a containerized environment.
* Rancher Desktop: Recommended for local Kubernetes cluster setup.
### Setup and Installation
Prerequisites
- Install Java 17.
- Install Maven for building the project.
- Install Docker and Containerd.
- Install Rancher Desktop to work with Kubernetes.
- Set up a PostgreSQL instance or use an in-memory H2 database for local development.

### Build and Run
Clone the Repository:

```git clone https://github.com/M23CSA508/parking-manager.git
cd parking-manager```
Build the Project: Use Maven to compile the code and create an executable JAR.

```mvn clean install```
Run the Application: Run the application using the Spring Boot Maven plugin.

```mvn spring-boot:run```
Database Configuration:

H2 Database: For development, configure the application.yml file to use H2.
PostgreSQL: Set up connection details in application.yml for a PostgreSQL instance.

### Deploy to Kubernetes
Build Docker Image: Build the application Docker image.

```docker build -t parking-management-solution .```
Deploy to Kubernetes: Use Rancher Desktop to manage your Kubernetes cluster locally.

```kubectl apply -f kubernetes/deployment.yaml```
Accessing the Application
The REST APIs can be accessed using a tool like Postman or cURL.

### Database Changes
Database schema changes are tracked using Liquibase.
Change sets are located under src/main/resources/db/changelog/.
Run Liquibase commands to apply database changes, or they will be automatically applied on startup.

### API Endpoints
The main API endpoints provided by the application are as follows:

Vehicle Management:

POST /api/vehicles - Create a new vehicle entry.
GET /api/vehicles/{vehicleId} - Retrieve vehicle information.
Parking Spot Management:

POST /api/parking-spots - Create a new parking spot.
GET /api/parking-spots - List all available parking spots.
Fee Calculation:

GET /api/fees/calculate - Calculate the fee for a given vehicle.
Example Requests
sh
Copy code
curl -X POST http://localhost:8080/api/vehicles \
-H "Content-Type: application/json" \
-H "Tenant-Id: tenant1" \
-d '{"vehicleNumber": "ABC123", "vehicleType": "Car"}'
Multi-Tenancy Support
The solution is designed with multi-tenancy using tenant-level database separation:

Each database is seperated for each tenant to ensure data isolation.
The application logic and queries enforce tenant-level data source selection.
Tenant information is passed through API request header to manage separation.

### Development Environment
Local Development: Use H2 Database for testing purposes.
Database Migration: Liquibase is used to manage the database schema changes and initial seed data.
Local Deployment: Rancher Desktop simplifies setting up and managing a local Kubernetes environment.
### License
This project is licensed under the MIT License. See the [LICENSE](https://opensource.org/license/mit) file for more details.

### Contact
For any questions, please reach out to:

Email: [Suvodip Som] (mailto:m23csa533@iitj.ac.in), [Swapnil Adak](mailto:m23csa534@iitj.ac.in) and [Anindya Bandopadhyay](mailto:m23csa508@iitj.ac.in)
GitHub: [GitHub Repository](https://github.com/M23CSA508/parking-manager.git)

