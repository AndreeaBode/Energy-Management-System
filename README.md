# Energy-Management-System

Distributed Systems
In this project, I developed a client-server web application structured as microservices. The backend is implemented in Java and Spring Boot, while the frontend is developed in Angular.

Within the project, there are two types of users: an admin and a regular user. The admin has the ability to perform CRUD operations on users and devices. Additionally, the admin can communicate with multiple users through a chat implemented with WebSocket.

A user can view the devices they own devices and visualize a graph based on daily consumption per hour. To create the graph, data is read every 10 minutes from a CSV file, and the sum of the read data is calculated. If the sum exceeds the predefined maximum hourly consumption for each device, users with those devices will receive a notification. This notification is implemented through WebSocket.

The data from the file is read by a Producer and sent through a RabbitMQ queue to the Consumer. The Consumer is responsible for checking if the device for which it received data exists, and it communicates through a queue with the Device microservice to obtain information about the devices.

For security, JWT Token is used, stored in localStorage, and sent in headers with each request.


Commands
• docker-compose up
• docker build -t frontend:latest .
• docker run --name frontend -ti -p 4200:80 frontend:latest
• docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management
• docker exec -it rabbitmq /bin/bash

Build and Run Considerations
- Frontend
Installation and Setup:
Make sure you have Node.js and npm (Node Package Manager) installed. Install Angular CLI using the following command:
• npm install -g @angular/cli
  
Configuration and Project Setup:
 Navigate to the frontend directory and execute the following commands to create an Angular project and install dependencies:
• ng new energy-management-app
• cd energy-management-app
• ng generate component component_name
To run the application, use the command:
• ng serve
