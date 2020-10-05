package com.kmit.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ErrorServlet")
public class ErrorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("<html>"
				+ "<head>"
				+ "<title>Todo - Error</title>"
				+ "<link href='css/bootstrap.min.css' rel='stylesheet'>"
				+ "<link href='css/login.css' rel='stylesheet'>"
				+ "<script src='js/jquery.min.js'></script>"
				+ "<script src='js/bootstrap.min.js'></script>"
				+ "</head>"
				+ "<body>"
				+ "<header>"
				+ "<nav class='navbar navbar-default'>"
				+ "<a href='/' class='navbar-brand'>Brand</a>"
				+ "<a href='/' class='navbar-brand'>Brand</a>"
				+ "</nav>"
				+ "</header>"
				+ "<div class='container'>"
				+ "<h1 align='center'>Error</h1>");
		
		String error = (String) request.getAttribute("error");
		out.println("<p>"+ error +"</p>");
		out.println("<p>Issue has been created and notified to developer/administrator</p>");
		out.println("<a class='btn btn-success' href='LoginServlet'>Click Here</a>");
		
		out.println("</div>"
				+ "</body>"
				+ "</html>");

	
	}
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	doGet(request, response);
	}

}
