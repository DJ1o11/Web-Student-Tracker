package com.djain.web.jdbc;

import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import jakarta.annotation.*;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {

    // Define datasource/connection pool for Resource Injection
    @Resource(name = "jdbc/web_student_tracker")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter out = resp.getWriter();
        resp.setContentType("text/plain");

        // get a connection to the db
        Connection conn;
        Statement stmt;
        ResultSet rs;

        try {
            conn = dataSource.getConnection();

            // create sql statements
            String sql = "select * from student";
            stmt = conn.createStatement();

            // execute sql query
            rs = stmt.executeQuery(sql);

            // process the result set
            while (rs.next()) {
                String email = rs.getString("email");
                out.println(email);
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}