package com.kmit.app;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/TodoDeleteServlet")
public class TodoDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int todo_id=Integer.parseInt(request.getParameter("todo_id"));
		
		ServletContext sc = getServletContext();
	    String driverName=sc.getInitParameter("driverName"); 
	    String driverUrl=sc.getInitParameter("driverUrl"); 
	    String username=sc.getInitParameter("username"); 
	    String password=sc.getInitParameter("password");
	    
	    Connection conn=null;
		PreparedStatement pstmt =null;
		
		try
		{
			Class.forName(driverName);
			conn = DriverManager.getConnection(driverUrl, username, password);
			
			String sql = "delete from todos where todo_id= ?";
			pstmt =conn.prepareStatement(sql);
			pstmt.setInt(1, todo_id);
			int delete = pstmt.executeUpdate();
			if(delete==1)
			{
				response.sendRedirect("TodoServlet");
			}	
			else{
				request.setAttribute("error","Not able to delete ");
				request.getRequestDispatcher("ErrorServlet").forward(request, response);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			request.setAttribute("error","Not able to delete : "+e.getMessage() );
			request.getRequestDispatcher("ErrorServlet").forward(request, response);
		}
		finally
		{
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("error","Not able to delete : "+e.getMessage() );
				request.getRequestDispatcher("ErrorServlet").forward(request, response);
			}
		}

	}

}
