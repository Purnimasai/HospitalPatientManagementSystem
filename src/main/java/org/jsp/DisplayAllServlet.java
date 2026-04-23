package org.jsp;

import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/displayAll")
public class DisplayAllServlet extends HttpServlet {

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
        out.println("<h2>All Patients</h2>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital", "root", "root");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patient");

            out.println("<table border='1' cellpadding='10'>");
            out.println("<tr><th>Name</th><th>Age</th><th>Disease</th><th>Update</th><th>Delete</th></tr>");

            while (rs.next()) {

                String name = rs.getString("name");
                int age = rs.getInt("age");
                String disease = rs.getString("disease");

                out.println("<tr>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + age + "</td>");
                out.println("<td>" + disease + "</td>");


                out.println("<td>");
                out.println("<form action='updatePatient' method='get'>");
                out.println("<input type='hidden' name='name' value='" + name + "'>");
                out.println("<input type='submit' value='Update'>");
                out.println("</form>");
                out.println("</td>");


                out.println("<td>");
                out.println("<form action='deletePatient' method='post'>");
                out.println("<input type='hidden' name='name' value='" + name + "'>");
                out.println("<input type='submit' value='Delete' style='background:red;color:white;'>");
                out.println("</form>");
                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            con.close();

        } 
        	catch (Exception e) {
        	    resp.setContentType("text/html");
        	    PrintWriter out1 = resp.getWriter();
        	    out1.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
        	    e.printStackTrace();
        	}
        

        out.println("<br><br>");
        out.println("<form action='home'>");
        out.println("<input type='submit' value='Home'>");
        out.println("</form>");

        out.println("</center></body></html>");
    }
}