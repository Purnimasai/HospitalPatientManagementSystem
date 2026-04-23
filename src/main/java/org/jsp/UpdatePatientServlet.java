package org.jsp;

import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/updatePatient")
public class UpdatePatientServlet extends HttpServlet {

    // OPEN UPDATE FORM WITH OLD VALUES
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String name = req.getParameter("name");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String oldName = "";
        int age = 0;
        String disease = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital", "root", "root");

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM patient WHERE name=?");

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                oldName = rs.getString("name");
                age = rs.getInt("age");
                disease = rs.getString("disease");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // UPDATE FORM (AUTO FILLED)
        out.println("<html><body><center>");
        out.println("<h2>Update Patient</h2>");

        out.println("<form method='get' action='updatePatient'>");

        out.println("<input type='hidden' name='oldName' value='" + oldName + "'>");

        out.println("Name: <input type='text' name='name' value='" + oldName + "'><br><br>");
        out.println("Age: <input type='number' name='age' value='" + age + "'><br><br>");
        out.println("Disease: <input type='text' name='disease' value='" + disease + "'><br><br>");

        out.println("<input type='submit' value='Update'>");

        out.println("</form>");
        out.println("</center></body></html>");
    }

    //   UPDATE DATABASE
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String oldName = req.getParameter("oldName");
        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));
        String disease = req.getParameter("disease");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital", "root", "root");

            PreparedStatement ps = con.prepareStatement(
                "UPDATE patient SET name=?, age=?, disease=? WHERE name=?");

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, disease);
            ps.setString(4, oldName);

            ps.executeUpdate();

            con.close();

            // GO BACK TO DISPLAY PAGE
            resp.sendRedirect("displayAll");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}