# Journal Entry Application

## Introduction
The Journal Entry Application is designed to manage personal or professional journal entries efficiently. It provides functionalities to create, read, update, and delete journal entries while ensuring a clean and maintainable codebase.

## Features
- **Create Entries:** Add new journal entries with ease.
- **Read Entries:** View existing journal entries in a user-friendly format.
- **Update Entries:** Edit and update existing journal entries.
- **Delete Entries:** Remove entries that are no longer needed.
- **Simplified String Checks:** Uses `isEmpty()` for more readable and efficient string validations.

## Technologies Used
- **Jakarta EE:** For enterprise-level development with jakarta imports.
- **Spring Data Mongo:** For MongoDB data access and manipulation.
- **Spring MVC:** For web development and creating RESTful services.
- **Lombok:** To reduce boilerplate code.
- **Java SDK:** Version 21.
- **Windows 11 (amd64):** Development environment.

## Installation
1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   ```
2. **Navigate to the project directory:**
   ```bash
   cd journal-entry-application
   ```
3. **Build the project:**
   ```bash
   ./gradlew build
   ```
4. **Run the application:**
   ```bash
   ./gradlew bootRun
   ```

## Usage
- **Accessing the Application:**
  - Open your web browser and navigate to `http://localhost:8080`.
  
- **API Endpoints:**
  - **Create Entry:** `POST /entries`
  - **Get All Entries:** `GET /entries`
  - **Get Entry by ID:** `GET /entries/{id}`
  - **Update Entry:** `PUT /entries/{id}`
  - **Delete Entry:** `DELETE /entries/{id}`

## Code Structure
- **Controller:** `JournalEntryController` handles HTTP requests and responses.
- **Service:** `JournalEntryService` contains business logic for journal entry operations.
- **Repository:** Interfaces with MongoDB for data persistence.
- **Model:** Contains the data model representing a journal entry.

## Contribution
1. **Fork the repository.**
2. **Create a new branch:**
   ```bash
   git checkout -b feature-branch
   ```
3. **Make your changes and commit them:**
   ```bash
   git commit -m "Your detailed description of the changes."
   ```
4. **Push to the branch:**
   ```bash
   git push origin feature-branch
   ```
5. **Create a Pull Request on GitHub.**

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact
If you have any questions or feedback, feel free to contact the maintainer at `madhavchandok.iimt@gmail.com`.
