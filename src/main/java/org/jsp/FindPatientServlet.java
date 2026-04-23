package org.jsp;

import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/findPatient")
public class FindPatientServlet extends HttpServlet {


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("login.html");
            return;
        }

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><body><center>");
        out.println("<h2>Find Patient</h2>");

        out.println("<form method='post' action='findPatient'>");
        out.println("Enter Name: <input type='text' name='name'><br><br>");
        out.println("<input type='submit' value='Search'>");
        out.println("</form>");

        out.println("<br><form action='home'><input type='submit' value='Home'></form>");
        out.println("</center></body></html>");
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String name = req.getParameter("name");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital", "root", "root");

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM patient WHERE name = ?");

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            out.println("<html><body><center>");
            out.println("<h2>Patient Details</h2>");

            boolean found = false;

            out.println("<table border='1' cellpadding='10'>");
            out.println("<tr><th>Name</th><th>Age</th><th>Disease</th></tr>");

            while (rs.next()) {
                found = true;

                out.println("<tr>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getInt("age") + "</td>");
                out.println("<td>" + rs.getString("disease") + "</td>");
                out.println("</tr>");
            }

            if (!found) {
                out.println("<tr><td colspan='3'>No Patient Found ❌</td></tr>");
            }

            out.println("</table>");

            out.println("<br><a href='findPatient'>Search Again</a>");
            out.println("<br><a href='home'>Go to Home</a>");

            out.println("</center></body></html>");

            con.close();

        } catch (Exception e) {
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }
}