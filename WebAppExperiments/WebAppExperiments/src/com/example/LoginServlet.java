package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// The annotation maps the servlet to the URL /LoginServlet
@WebServlet("/LoginServlet") 
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Use doPost() to handle the form's POST method
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Retrieve the parameters from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Hardcoded credentials for validation
        String validUser = "admin";
        String validPass = "pass123";

        // Set content type for the response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // 2. Validate the credentials
        out.println("<html><head><title>Login Status</title>");
        out.println("<style>body { font-family: Arial, sans-serif; text-align: center; padding-top: 50px; }</style>");
        out.println("</head><body>");

        if (username.equals(validUser) && password.equals(validPass)) {
            // Successful Login
            out.println("<h1>Welcome, " + username + "!</h1>");
            out.println("<p>You have successfully logged in.</p>");
        } else {
            // Invalid Credentials
            out.println("<h1 style='color: red;'>Login Failed!</h1>");
            out.println("<p>Invalid username or password. Please try again.</p>");
            out.println("<p><a href='login.html'>Go back to Login</a></p>");
        }

        out.println("</body></html>");
    }
}