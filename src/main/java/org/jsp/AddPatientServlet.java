package org.jsp;

import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/addPatient")
public class AddPatientServlet extends HttpServlet {

  
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("login.html");
            return;
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");

        out.println("<html><body><center>");
        out.println("<h2>Add Patient</h2>");

        out.println("<form method='post' action='addPatient'>");
        out.println("Name: <input type='text' name='name'><br><br>");
        out.println("Age: <input type='number' name='age'><br><br>");
        out.println("Disease: <input type='text' name='disease'><br><br>");
        out.println("<input type='submit' value='Add Patient'>");
        out.println("</form>");

        out.println("<br><form action='home'><input type='submit' value='Home'></form>");

        out.println("</center></body></html>");
    }

    //Store in DB
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));
        String disease = req.getParameter("disease");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital", "root", "root");

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO patient(name, age, disease) VALUES (?, ?, ?)");

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, disease);

            ps.executeUpdate();

            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();

            out.println("<h3>Patient Added Successfully </h3>");
            out.println("<a href='home'>Go to Home</a>");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}