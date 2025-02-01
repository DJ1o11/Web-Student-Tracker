package com.djain.web.jdbc;

import javax.sql.DataSource;

import java.sql.*;
import java.util.*;

public class StudentDbUtil {

    private final DataSource dataSource;

    public StudentDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<Student> getStudents() throws Exception {
        List<Student> students = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            String sql = "select * from student order by last_name";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // retrieve data from rs row
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");

                Student tempStudent = new Student(id, firstName, lastName, email);
                students.add(tempStudent);
            }
            return students;

        } finally { // close jdbc objects
            close(conn, stmt, rs);
        }
    }

    private void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close(); // does not really close it, just puts it back in connection pool

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void addStudent(Student theStudent) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // get db connection
            conn = dataSource.getConnection();

            // create sql for insert
            String sql = "insert into student "
                    + "(first_name, last_name, email) "
                    + "values (?, ?, ?)";

            stmt = conn.prepareStatement(sql);

            // set param values for student
            stmt.setString(1, theStudent.getFirstName()); // stmts are 1-indexed
            stmt.setString(2, theStudent.getLastName());
            stmt.setString(3, theStudent.getEmail());

            // execute sql insert
            stmt.execute();
        } finally {
            // cleanup jdbc objects
            close(conn, stmt, null);
        }
    }

    public Student getStudent(String theStudentId) throws Exception {
        Student theStudent;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int studentId;

        try {
            // convert studentId to int
            studentId = Integer.parseInt(theStudentId);

            // get conn to db
            conn = dataSource.getConnection();

            // create sql to get selected student
            String sql = "select * from student where id=?";

            // create prepared stmt
            stmt = conn.prepareStatement(sql);

            // set params
            stmt.setInt(1, studentId);

            // execute stmt
            rs = stmt.executeQuery();

            //retrieve data from rs row
            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");

                theStudent = new Student(studentId, firstName, lastName, email);
            } else {
                throw new Exception("Could not find student with id " + theStudentId);
            }

            return theStudent;
        } finally {
            // clean up jdbc objects
            close(conn, stmt, rs);
        }
    }

    public void updateStudent(Student theStudent) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // get db conn
            conn = dataSource.getConnection();

            // create sql update stmt
            String sql = "update student "
                    + "set first_name = ?, last_name = ?, email = ? "
                    + "where id = ?";

            // prepare stmt
            stmt = conn.prepareStatement(sql);

            // set params
            stmt.setString(1, theStudent.getFirstName());
            stmt.setString(2, theStudent.getLastName());
            stmt.setString(3, theStudent.getEmail());
            stmt.setInt(4, theStudent.getId());

            // execute sql stmt
            stmt.execute();

        } finally {
            close(conn, stmt, null);
        }
    }

    public void deleteStudent(String theStudentId) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // convert studentId to int
            int studentId = Integer.parseInt(theStudentId);

            // get conn to db
            conn = dataSource.getConnection();

            // create sql to delete student
            String sql = "delete from student where id=?";

            // prepare stmt
            stmt = conn.prepareStatement(sql);

            // set params
            stmt.setInt(1, studentId);

            // execute sql stmt
            stmt.execute();
        }
        finally {
            close(conn, stmt, null);
        }
    }
}