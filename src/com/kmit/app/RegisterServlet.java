package com.kmit.app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("view/register.html").forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		String fullname=request.getParameter("fullname");
		String email=request.getParameter("email");
		String pass=request.getParameter("password");
		String confirm_passw=request.getParameter("confpassword");
		if(!pass.contentEquals(confirm_passw)) {
			request.getRequestDispatcher("view/register.html").forward(request, response);
		}
		ServletContext sc=getServletContext();
		String driverName=sc.getInitParameter("driverName");
		String driverUrl=sc.getInitParameter("driverUrl");
		String username=sc.getInitParameter("username");
		String password=sc.getInitParameter("password");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			//connection
			Class.forName(driverName);
			conn = DriverManager.getConnection(driverUrl, username, password);
			
			System.out.println("Connection established");
			String sql = "select * from login where username= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if(rs != null && rs.next()) {
				//data is present
				request.setAttribute("error", "User already exist");
				request.getRequestDispatcher("ErrorServlet").forward(request, response);
			}
			else {
				//insert operation
				String insertSQL = "insert into  login (username,fullname,email, password)  values (?,?,?,?);";
				pstmt = conn.prepareStatement(insertSQL);
				pstmt.setString(1, name);
				pstmt.setString(2, fullname);
				pstmt.setString(3, email);
				pstmt.setString(4, pass);
				
				int i = pstmt.executeUpdate();
				if(i==1) {
					//login servlet
					response.sendRedirect("LoginServlet");
				}
				else {
					request.setAttribute("error", "Access denied! ");
					//request.getRequestDispatcher("ErrorServlet").forward(request, response);
					request.getRequestDispatcher("ErrorServlet").forward(request, response);
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Connection failed");
		}
		finally {
			try {
				pstmt.close();
				rs.close();
				conn.close();
				
			}catch (Exception e) {
				request.setAttribute("error", "Registration failed! "+ e.getMessage());
				request.getRequestDispatcher("ErrorServlet").forward(request, response);
			}}}}
