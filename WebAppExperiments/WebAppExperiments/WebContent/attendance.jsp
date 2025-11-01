<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Student Attendance Portal</title>
<style>
    body { font-family: Arial, sans-serif; padding: 20px; }
    form { max-width: 400px; margin: 0 auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }
    label, input, select { display: block; margin-bottom: 10px; }
    input[type="text"], input[type="date"], select { width: 100%; padding: 10px; box-sizing: border-box; }
    input[type="submit"] { background-color: #007bff; color: white; padding: 10px 15px; border: none; cursor: pointer; }
    .message { margin-top: 20px; padding: 10px; border-radius: 5px; }
    .success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
    .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
</style>
</head>
<body>
    <form action="AttendanceServlet" method="POST">
        <h2>Mark Student Attendance</h2>
        
        <label for="studentId">Student ID:</label>
        <input type="text" id="studentId" name="studentId" required><br>
        
        <label for="attendDate">Date:</label>
        <input type="date" id="attendDate" name="attendDate" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" required><br>
        
        <label for="status">Status:</label>
        <select id="status" name="status" required>
            <option value="Present">Present</option>
            <option value="Absent">Absent</option>
            <option value="Late">Late</option>
        </select><br>
        
        <input type="submit" value="Save Attendance">
    </form>
    
    <% 
        String message = (String) request.getAttribute("message");
        String messageType = (String) request.getAttribute("messageType");
        if (message != null) {
    %>
        <div class="message <%= messageType %>">
            <p><%= message %></p>
        </div>
    <% 
        }
    %>
</body>
</html>