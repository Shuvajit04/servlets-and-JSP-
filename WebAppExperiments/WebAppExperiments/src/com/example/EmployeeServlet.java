package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Use doGet() to handle both the search form and the "View All" link
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Check for the search parameter
        String empIdStr = request.getParameter("empId");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><title>Employee Records</title>");
        out.println("<style>table { width: 50%; border-collapse: collapse; margin-top: 20px; }" +
                    "th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }" +
                    "th { background-color: #f2f2f2; }</style>");
        out.println("</head><body>");
        out.println("<h1>Employee Records</h1>");
        out.println("<p><a href='employee.html'>Back to Search/Home</a></p>");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Load JDBC Driver
            Class.forName(DBConfig.JDBC_DRIVER);
            
            // 2. Open a connection
            conn = DriverManager.getConnection(DBConfig.DB_URL, DBConfig.USER, DBConfig.PASS);
            
            String sql;
            if (empIdStr != null && !empIdStr.trim().isEmpty()) {
                // Search by ID
                int empId = Integer.parseInt(empIdStr);
                sql = "SELECT EmpID, Name, Salary FROM Employee WHERE EmpID = " + empId;
                out.println("<h2>Search Result for Employee ID: " + empId + "</h2>");
            } else {
                // View All
                sql = "SELECT EmpID, Name, Salary FROM Employee";
                out.println("<h2>All Employees</h2>");
            }
            
            // 3. Execute query
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            // 4. Display results in an HTML table
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Name</th><th>Salary</th></tr>");

            boolean found = false;
            while (rs.next()) {
                found = true;
                int id = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                
                out.println("<tr><td>" + id + "</td><td>" + name + "</td><td>" + String.format("%.2f", salary) + "</td></tr>");
            }
            
            out.println("</table>");

            if (!found) {
                out.println("<p style='color: red;'>No employee record found.</p>");
            }

        } catch (ClassNotFoundException e) {
            out.println("<h2 style='color: red;'>JDBC Driver not found.</h2><p>" + e.getMessage() + "</p>");
        } catch (SQLException e) {
            out.println("<h2 style='color: red;'>Database Error.</h2><p>" + e.getMessage() + "</p>");
        } catch (NumberFormatException e) {
            out.println("<h2 style='color: red;'>Invalid Employee ID format.</h2>");
        } finally {
            // 5. Close resources
            try { if (rs != null) rs.close(); } catch (SQLException ignored) { }
            try { if (stmt != null) stmt.close(); } catch (SQLException ignored) { }
            try { if (conn != null) conn.close(); } catch (SQLException ignored) { }
        }

        out.println("</body></html>");
    }
}