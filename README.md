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
- `src/main/webapp/`: Contains JSP files and other web resources.
- `sql/`: Contains SQL scripts for database setup.

## Setup Instructions

### Prerequisites

- JDK 11 or higher
- Apache Tomcat 9 or higher
- MySQL database
- Maven

### Database Setup

1. Create a MySQL user and grant privileges:
    ```sql
    -- 01-create-user.sql
    CREATE USER 'webstudent'@'localhost' IDENTIFIED BY 'webstudent';
    GRANT ALL PRIVILEGES ON * . * TO 'webstudent'@'localhost';
    ```

2. Create the database and tables, and insert initial data:
    ```sql
    -- 02-student-tracker.sql
    CREATE DATABASE IF NOT EXISTS `web_student_tracker` /*!40100 DEFAULT CHARACTER SET latin1 */;
    USE `web_student_tracker`;

    DROP TABLE IF EXISTS `student`;
    CREATE TABLE `student` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `first_name` varchar(45) DEFAULT NULL,
      `last_name` varchar(45) DEFAULT NULL,
      `email` varchar(45) DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

    INSERT INTO `student` VALUES
    (1,'Mary','Public','mary@luv2code.com'),
    (2,'John','Doe','john@luv2code.com'),
    (3,'Ajay','Rao','ajay@luv2code.com'),
    (4,'Bill','Neely','bill@luv2code.com'),
    (5,'Maxwell','Dixon','max@luv2code.com');
    ```

### Configuration

1. Configure the datasource in `context.xml`:

    ```xml
    <Resource name="jdbc/web_student_tracker"
              auth="Container"
              type="javax.sql.DataSource"
              maxTotal="100"
              maxIdle="30"
              maxWaitMillis="10000"
              username="webstudent"
              password="webstudent"
              driverClassName="com.mysql.cj.jdbc.Driver"
              url="jdbc:mysql://localhost:3306/web_student_tracker?allowPublicKeyRetrieval=true&amp;useSSL=False&amp;serverTimezone=UTC"/>
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
- Verify the datasource configuration in `context.xml`.

## License

This project is licensed under the MIT License.