package com.djain.web.jdbc;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import javax.sql.DataSource;
import java.io.*;
import java.util.*;

@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    private StudentDbUtil studentDbUtil;

    @Resource(name="jdbc/web_student_tracker")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();

        //create our student db util, and pass in the conn pool i.e. datasource
        try {
            studentDbUtil = new StudentDbUtil(dataSource);
        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {

            // read the command parameter
            String theCommand = req.getParameter("command");

            // if the command is missing, then default to listing students
            if(theCommand == null) {
                theCommand = "LIST";
            }

            // route to the appropriate method
            switch(theCommand) {
                case "LIST":
                    listStudents(req, resp); // list the students in MVC fashion
                    break;
                case "ADD":
                    addStudent(req, resp);
                    break;
                case "LOAD":
                    loadStudent(req, resp);
                    break;
                case "UPDATE":
                    updateStudent(req, resp);
                case "DELETE":
                    deleteStudent(req, resp);
                default:
                    listStudents(req, resp);
            }

        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    private void deleteStudent (HttpServletRequest req, HttpServletResponse resp) throws Exception {

        // read student id from form data
        String theStudentId = req.getParameter("studentId");

        // delete student from db
        studentDbUtil.deleteStudent(theStudentId);

        // send them back to list-students page
        listStudents(req, resp);
    }

    private void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        // read student info from form data
        int id = Integer.parseInt(req.getParameter("studentId"));
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");

        // create new student obj
        Student theStudent = new Student(id, firstName, lastName, email);

        // perform update on db
        studentDbUtil.updateStudent(theStudent);

        // send them back to list-students page
        listStudents(req, resp);
    }

    private void loadStudent(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // read student id from form data
        String theStudentId = req.getParameter("studentId");

        // get student from db -> dbUtil
        Student theStudent = studentDbUtil.getStudent(theStudentId);

        // place student in req attribute
        req.setAttribute("THE_STUDENT", theStudent);

        // send to jsp page -> update-student-form.jsp
        RequestDispatcher dispatcher = req.getRequestDispatcher("/update-student-form.jsp");
        dispatcher.forward(req, resp);
    }

    private void addStudent(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        // read student info from form data
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");

        // create new student object
        Student theStudent = new Student(firstName, lastName, email);

        // add student to db
        studentDbUtil.addStudent(theStudent);

        // send back to main page -> student list
        listStudents(req, resp);
    }

    private void listStudents(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // get students from db util
        List<Student> students = studentDbUtil.getStudents();

        // add students to the request
        req.setAttribute("STUDENT_LIST", students);

        // send to jsp page (view)
        RequestDispatcher dispatcher = req.getRequestDispatcher("/list-students.jsp");
        dispatcher.forward(req, resp);
    }
}