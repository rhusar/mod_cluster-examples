<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.net.InetAddress" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
    <div class="container">
        <h1>Server Information</h1>
        <p>Current Date and Time: <span class="highlight"><%= new Date() %></span></p>
        <p>Server Hostname: <span class="highlight">
            <%
                try {
                    out.println(InetAddress.getLocalHost().getHostName());
                } catch (Exception e) {
                    out.println("Could not determine hostname.");
                }
            %>
        </span></p>
    </div>
</body>
</html>
