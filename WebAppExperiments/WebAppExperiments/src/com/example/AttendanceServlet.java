package com.example;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Read the attendance data
        String studentIdStr = request.getParameter("studentId");
        String attendDate = request.getParameter("attendDate");
        String status = request.getParameter("status");

        String message;
        String messageType;

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            int studentId = Integer.parseInt(studentIdStr);
            
            // Load JDBC Driver
            Class.forName(DBConfig.JDBC_DRIVER);
            
            // 2. Open a connection
            conn = DriverManager.getConnection(DBConfig.DB_URL, DBConfig.USER, DBConfig.PASS);
            
            // 3. Prepare and execute SQL INSERT
            String sql = "INSERT INTO Attendance (StudentID, AttendDate, Status) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentId);
            pstmt.setDate(2, Date.valueOf(attendDate)); // Converts String date to SQL Date
            pstmt.setString(3, status);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                message = "Attendance saved successfully for Student ID: " + studentId;
                messageType = "success";
            } else {
                message = "Failed to save attendance.";
                messageType = "error";
            }
            
        } catch (NumberFormatException e) {
            message = "Error: Invalid Student ID format.";
            messageType = "error";
        } catch (ClassNotFoundException e) {
            message = "Error: JDBC Driver not found. " + e.getMessage();
            messageType = "error";
        } catch (SQLException e) {
            message = "Error: Database operation failed. " + e.getMessage();
            messageType = "error";
        } finally {
            // 4. Close resources
            try { if (pstmt != null) pstmt.close(); } catch (SQLException ignored) { }
            try { if (conn != null) conn.close(); } catch (SQLException ignored) { }
        }

        request.setAttribute("message", message);
        request.setAttribute("messageType", messageType);
        
        request.getRequestDispatcher("/attendance.jsp").forward(request, response);
    }
}