package org.jsp;

import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/removePatient")
public class RemovePatientServlet extends HttpServlet {

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
        out.println("<h2>Remove Patient</h2>");

        out.println("<form method='post' action='removePatient'>");
        out.println("Enter Name: <input type='text' name='name'><br><br>");
        out.println("<input type='submit' value='Delete Patient'>");
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
                "DELETE FROM patient WHERE name = ?");

            ps.setString(1, name);

            int i = ps.executeUpdate();

            if (i > 0) {
                out.println("<h3 style='color:green;'>Patient Deleted Successfully </h3>");
            } else {
                out.println("<h3 style='color:red;'>No Patient Found </h3>");
            }

            out.println("<br><a href='removePatient'>Delete Another</a>");
            out.println("<br><a href='home'>Go to Home</a>");

            con.close();

        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }
}