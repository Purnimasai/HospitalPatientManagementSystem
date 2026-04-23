package org.jsp;

import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        HttpSession session = req.getSession(false);

        if (session == null) {
            resp.sendRedirect("login.html");
            return;
        }

        String name = (String) session.getAttribute("name");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<body>");

        out.println("<center>");
        out.println("<h2>Welcome " + name + "</h2>");

        out.println("<table border='1' cellpadding='20'>");


        out.println("<tr>");
        out.println("<td>");
        out.println("<form action='addPatient'method='get'>");
        out.println("<input type='submit' value='Add Patient'>");
        out.println("</form>");
        out.println("</td>");

        out.println("<td>");
        out.println("<form action='removePatient' method='get'>");
        out.println("<input type='submit' value='Remove Patient'>");
        out.println("</form>");
        out.println("</td>");
        out.println("</tr>");


        out.println("<tr>");
        out.println("<td>");
        out.println("<form action='findPatient' method='get'>");
        out.println("<input type='submit' value='Find Patient'>");
        out.println("</form>");
        out.println("</td>");

        out.println("<td>");
        out.println("<form action='displayAll' method='get'>");
        out.println("<input type='submit' value='Display All'>");
        out.println("</form>");
        out.println("</td>");
        out.println("</tr>");


        out.println("<tr>");
        out.println("<td>");
        out.println("<form action='profile' method='get'>");
        out.println("<input type='submit' value='Profile'>");
        out.println("</form>");
        out.println("</td>");

        out.println("<td>");
        out.println("<form action='/HospitalPatientManagementSystem/logout' method='get'>");
        out.println("<input type='submit' value='Logout'>");
        out.println("</form>");
        out.println("</td>");

        out.println("</table>");

        out.println("</center>");
        out.println("</body>");
        out.println("</html>");
    }
}