package com.form;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.bean.Branch;
import com.bean.Course;
import com.bean.Institution;
import com.bean.Student;

@Component("JDBCUtil")
public class JDBCUtil {
	
	// JDBC driver name and database URL
		static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
		//static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/EMP";
		static final String DB_URL = "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12348159";

		//  Database credentials
		//static final String USER = "root";
		//static final String PASS = "rajan";
		static final String USER = "sql12348159";
		static final String PASS = "dEEwGHqICT";
		
		public void main(String[] args) {
			Connection conn = null;
			Statement stmt = null;
			try{
				//STEP 2: Register JDBC driver
				Class.forName(JDBC_DRIVER);
				//STEP 3: Open a connection
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				

				//STEP 4: Execute a query
				System.out.println("Creating statement...");
				stmt = conn.createStatement();
				String sql;
				sql = "SELECT id, first, last, age FROM Employees";
				ResultSet rs = stmt.executeQuery(sql);

				//STEP 5: Extract data from result set
				while(rs.next()){
					//Retrieve by column name
					int id  = rs.getInt("id");
					int age = rs.getInt("age");
					String first = rs.getString("first");
					String last = rs.getString("last");

					//Display values
					System.out.print("ID: " + id);
					System.out.print(", Age: " + age);
					System.out.print(", First: " + first);
					System.out.println(", Last: " + last);
				}
				//STEP 6: Clean-up environment
				rs.close();
				stmt.close();
				conn.close();
			}catch(SQLException se){
				//Handle errors for JDBC
				se.printStackTrace();
			}catch(Exception e){
				//Handle errors for Class.forName
				e.printStackTrace();
			}finally{
				//finally block used to close resources
				try{
					if(stmt!=null)
						stmt.close();
				}catch(SQLException se2){
				}// nothing we can do
				try{
					if(conn!=null)
						conn.close();
				}catch(SQLException se){
					se.printStackTrace();
				}//end finally try
			}//end try
			System.out.println("Goodbye!");
		}//end main
		
		public ArrayList<Institution> getInstitutionList() {
			Connection conn = null;
			Statement stmt = null;
			ArrayList<Institution> arrayList = new ArrayList<Institution>();
			try{
				Class.forName(JDBC_DRIVER);

				conn = DriverManager.getConnection(DB_URL,USER,PASS);

				stmt = conn.createStatement();
				String sql;
				//sql = "SELECT id, first, last, age FROM Employees";
				sql = "SELECT * FROM Institution;";
				ResultSet rs = stmt.executeQuery(sql);

				while(rs.next()){
					Institution institution = new Institution();
					institution.setId(rs.getString("id"));
					institution.setName(rs.getString("name"));
					institution.setCity(rs.getString("city"));
					arrayList.add(institution);

				}
				stmt.close();
				conn.close();

			}catch(SQLException se){
				se.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					if(stmt!=null)
						stmt.close();
				}catch(SQLException se2){
				}
				try{
					if(conn!=null)
						conn.close();
				}catch(SQLException se){
					se.printStackTrace();
				}
			}
			return arrayList;
		}
		
		public ArrayList<Course> getCourseList() {
			Connection conn = null;
			Statement stmt = null;
			ArrayList<Course> arrayList = new ArrayList<Course>();
			try{
				Class.forName(JDBC_DRIVER);

				conn = DriverManager.getConnection(DB_URL,USER,PASS);

				stmt = conn.createStatement();
				String sql;
				//sql = "SELECT id, first, last, age FROM Employees";
				sql = "SELECT * FROM Course;";
				ResultSet rs = stmt.executeQuery(sql);

				while(rs.next()){
					Course course = new Course();
					course.setId(rs.getString("id"));
					course.setName(rs.getString("name"));
					arrayList.add(course);

				}
				stmt.close();
				conn.close();

			}catch(SQLException se){
				se.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					if(stmt!=null)
						stmt.close();
				}catch(SQLException se2){
				}
				try{
					if(conn!=null)
						conn.close();
				}catch(SQLException se){
					se.printStackTrace();
				}
			}
			return arrayList;
		}
		
		public ArrayList<Branch> getBranchList() {
			Connection conn = null;
			Statement stmt = null;
			ArrayList<Branch> arrayList = new ArrayList<Branch>();
			try{
				Class.forName(JDBC_DRIVER);

				conn = DriverManager.getConnection(DB_URL,USER,PASS);

				stmt = conn.createStatement();
				String sql;
				//sql = "SELECT id, first, last, age FROM Employees";
				sql = "SELECT * FROM Branch;";
				ResultSet rs = stmt.executeQuery(sql);

				while(rs.next()){
					Branch branch = new Branch();
					branch.setId(rs.getString("id"));
					branch.setName(rs.getString("name"));
					arrayList.add(branch);

				}
				stmt.close();
				conn.close();

			}catch(SQLException se){
				se.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					if(stmt!=null)
						stmt.close();
				}catch(SQLException se2){
				}
				try{
					if(conn!=null)
						conn.close();
				}catch(SQLException se){
					se.printStackTrace();
				}
			}
			return arrayList;
		}
}
