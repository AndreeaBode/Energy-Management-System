# Energy-Management-System

Distributed Systems
In this project, I developed a client-server web application structured as microservices. The backend is implemented in Java and Spring Boot, while the frontend is developed in Angular.

Within the project, there are two types of users: an admin and a regular user. The admin has the ability to perform CRUD operations on users and devices. Additionally, the admin can communicate with multiple users through a chat implemented with WebSocket.

A user can view the devices they own devices and visualize a graph based on daily consumption per hour. To create the graph, data is read every 10 minutes from a CSV file, and the sum of the read data is calculated. If the sum exceeds the predefined maximum hourly consumption for each device, users with those devices will receive a notification. This notification is implemented through WebSocket.

The data from the file is read by a Producer and sent through a RabbitMQ queue to the Consumer. The Consumer is responsible for checking if the device for which it received data exists, and it communicates through a queue with the Device microservice to obtain information about the devices.

For security, JWT Token is used, stored in localStorage, and sent in headers with each request.
