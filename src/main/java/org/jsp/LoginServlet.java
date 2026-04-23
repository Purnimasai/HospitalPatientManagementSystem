package org.jsp;

import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital", "root", "root");

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM staff1 WHERE email=? AND password=?");

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = req.getSession();
                session.setAttribute("name", rs.getString("name"));
                session.setAttribute("email", email);
                
                resp.sendRedirect("home");
            } else {
                resp.getWriter().println("<h3> Invalid Login </h3>");
            }

        } catch (Exception e) {
        	    resp.setContentType("text/html");
        	    PrintWriter out = resp.getWriter();
        	    out.println("<h2>ERROR: " + e.getMessage() + "</h2>");
        	    e.printStackTrace();
        	
        }
    }
}