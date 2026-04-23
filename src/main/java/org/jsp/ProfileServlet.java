package org.jsp;

import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        HttpSession session = req.getSession(false);

        if (session == null) {
            resp.sendRedirect("login.html");
            return;
        }

        String name = (String) session.getAttribute("name");
        String email = (String) session.getAttribute("email");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<body>");

        out.println("<center>");
        out.println("<h2>Profile Page</h2>");

        out.println("<table border='1' cellpadding='10'>");

        out.println("<tr><td><b>Name</b></td><td>" + name + "</td></tr>");
        out.println("<tr><td><b>Email</b></td><td>" + email + "</td></tr>");

        out.println("</table><br>");


        out.println("<form action='home'>");
        out.println("<input type='submit' value='Home'>");
        out.println("</form><br>");
        out.println("<form action='/HospitalPatientManagementSystem/logout' method='get'>");
        out.println("<input type='submit' value='Logout' >");
        out.println("</form>");

        out.println("</center>");
        out.println("</body>");
        out.println("</html>");
    }
}