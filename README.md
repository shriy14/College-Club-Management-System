# College Club Management System

This is a web-based College Club Management System developed using Spring Boot, Maven, SQL, and Java. The system facilitates the management of college clubs, events, and memberships. It provides functionalities for member registration, club head management, and faculty oversight.

## Features

- **User Authentication**: Separate login and registration pages for members, club heads, and faculty.
- **Home Page**: Default home page displaying all ongoing events of all clubs.
- **Member Dashboard**: Allows members to view and apply for clubs, view their club memberships, and register for events.
- **Club Head Dashboard**: Provides club heads with features to manage club members, create and manage events, and handle club membership applications.
- **Faculty Dashboard**: Enables faculty members to view all clubs under their supervision.

## Folder Structure

- **Models**: Contains Java classes representing the entities such as Member, Club, Event, etc.
- **Views**: Contains HTML templates for different pages like login, registration, dashboards, etc.
- **Controllers**: Contains Java classes responsible for handling HTTP requests and routing them to appropriate methods.
- **Services**: Contains Java classes implementing business logic and interacting with repositories.
- **Design Patterns**: Implements various design patterns such as MVC, Facade, and Single Responsibility to ensure modular and maintainable codebase.

## Getting Started

1. Clone the repository to your local machine.
2. Import the project into your preferred IDE (Eclipse, IntelliJ, etc.).
3. Set up the required dependencies and configurations like the sql database connection(Java, Spring Boot, Maven).
4. Run the application locally using the embedded server provided by Spring Boot.
5. Access the application through your browser using the specified port (in this case, `localhost:8080`).

## Acknowledgements

- This project was developed as part of the Object Oriented Analysis and Design course at PES University.
