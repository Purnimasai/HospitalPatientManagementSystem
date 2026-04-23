package org.jsp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deletePatient")
public class DeletePatientServlet extends HttpServlet {
    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String name = req.getParameter("name");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital", "root", "root");

            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM patient WHERE name=?");

            ps.setString(1, name);
            ps.executeUpdate();

            resp.sendRedirect("displayAll");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}