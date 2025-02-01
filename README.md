# Student Tracker App

This is a web application for tracking students using Java, JSP, and JSTL. The application allows you to add, update, delete, and list students.

## Technologies Used

- Java
- JSP
- JSTL
- Jakarta Servlet
- Maven
- MySQL

## Project Structure

- `src/main/java/com/djain/web/jdbc/`: Contains Java classes for database operations and servlets.
- `WebContent/`: Contains JSP files and other web resources.
- `sql/`: Contains SQL scripts for database setup.

## Setup Instructions

### Prerequisites

- JDK 11 or higher
- Apache Tomcat 9 or higher
- MySQL database
- Maven

### Database Setup

1. Create a MySQL database named `web_student_tracker`.
2. Execute the SQL scripts in the `sql` directory to create the necessary tables and insert initial data.

    ```sql
    -- create-database.sql
    CREATE DATABASE web_student_tracker;

    -- create-tables.sql
    USE web_student_tracker;
    CREATE TABLE student (
        id INT NOT NULL AUTO_INCREMENT,
        first_name VARCHAR(45) NOT NULL,
        last_name VARCHAR(45) NOT NULL,
        email VARCHAR(45) NOT NULL,
        PRIMARY KEY (id)
    );

    -- insert-data.sql
    USE web_student_tracker;
    INSERT INTO student (first_name, last_name, email) VALUES ('John', 'Doe', 'john.doe@example.com');
    INSERT INTO student (first_name, last_name, email) VALUES ('Jane', 'Smith', 'jane.smith@example.com');
    ```

### Configuration

1. Configure the datasource in `context.xml` or `web.xml`:

    ```xml
    <Resource name="jdbc/web_student_tracker"
              auth="Container"
              type="javax.sql.DataSource"
              maxTotal="100"
              maxIdle="30"
              maxWaitMillis="10000"
              username="your-username"
              password="your-password"
              driverClassName="com.mysql.cj.jdbc.Driver"
              url="jdbc:mysql://localhost:3306/web_student_tracker"/>
    ```

2. Update the `pom.xml` file with the necessary dependencies:

    ```xml
    <dependencies>
        <dependency>
            <groupId>jakarta.servlet.jsp.jstl</groupId>
            <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
            <version>3.0.1</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.web</groupId>
            <artifactId>jakarta.servlet.jsp.jstl</artifactId>
            <version>3.0.1</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.26</version>
        </dependency>
    </dependencies>
    ```

### Running the Application

1. Build the project using Maven:

    ```sh
    mvn clean install
    ```

2. Deploy the generated WAR file to your Apache Tomcat server.
3. Access the application at `http://localhost:8080/web-student-tracker`.

## Usage

- **Add Student**: Click the "Add Student" button and fill in the form.
- **Update Student**: Click the "Update" link next to a student.
- **Delete Student**: Click the "Delete" link next to a student.
- **List Students**: The students are listed when you first open the web application.

## Troubleshooting

- Ensure the database is running and accessible.
- Check the Tomcat logs for any errors.
- Verify the datasource configuration in `context.xml` or `web.xml`.

## License

This project is licensed under the MIT License.