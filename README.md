Description:
The Cinema Room REST Service is a comprehensive backend system designed for managing a movie theatre's ticketing operations. Developed using Java and the Spring Boot framework, this project includes functionalities such as seat availability, ticket purchasing, refund processing, and detailed statistics retrieval. The service exposes a set of RESTful endpoints that enable seamless interaction with the cinema's ticketing system.

Key Features:
Seat Booking: Users can view the available seats, choose their preferred ones, and purchase tickets.
Refund Processing: Implemented a mechanism for users to return purchased tickets, updating the seat availability accordingly.
Statistics Endpoint: Introduced a /stats endpoint for theatre managers to retrieve essential statistics, including total income, available seats, and purchased tickets.

Technologies Used:
-Java
-Spring Boot
-RESTful API Design
-Object-Oriented Programming
-Error Handling and HTTP Status Codes

How to Use:
Access seat availability using the /seats endpoint.
Purchase tickets with the /purchase endpoint, providing the desired seat coordinates.
Return tickets using the /return endpoint, specifying the ticket token.
