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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("view/login.html").forward(request, response);}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		String pass=request.getParameter("password");
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
			String sql = "select * from login where username= ? and password= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setNString(2, pass);
			rs = pstmt.executeQuery();
			if(rs != null && rs.next()) {
				//data is present
				HttpSession session=request.getSession();
				session.setAttribute("user_id", rs.getInt("user_id"));
				session.setAttribute("name", rs.getString("fullname"));
				
				response.sendRedirect("TodoServlet");
				}
			else {
				//request.setAttribute("error", "User already exist");
				request.getRequestDispatcher("LoginServlet").forward(request, response);
			
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Unable to Login!");
			request.getRequestDispatcher("ErrorServlet").forward(request, response);
		}
		finally {
			try {
				pstmt.close();
				rs.close();
				conn.close();
				
			}catch (Exception e) {
				request.setAttribute("error", "Registration failed! "+ e.getMessage());
				request.getRequestDispatcher("ErrorServlet").forward(request, response);
			}
		}
	
	
	
	}

}
