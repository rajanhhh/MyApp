package com.form;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bean.Student;
import com.bean.Subject;
import com.bean.User;

@Component("JDBCAccess")
public class JDBCAccess {
	// JDBC driver name and database URL
		static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
		//static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/EMP";
		static final String DB_URL = "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12348159";

		//  Database credentials
		//static final String USER = "root";
		//static final String PASS = "rajan";
		static final String USER = "sql12348159";
		static final String PASS = "dEEwGHqICT";

		public static void main(String[] args) {
			Connection conn = null;
			Statement stmt = null;
			try{
				//STEP 2: Register JDBC driver
				Class.forName(JDBC_DRIVER);
				//STEP 3: Open a connection
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				
				Student student = new Student();
				student.setId("123");
				student.setFirst("S");
				student.setLast("Nayak");
				student.setInstitution("2");
				student.setCourse("3");
				student.setSemester("IV");
				student.setBranch("2");
				
				ArrayList<Student> arrayList = new ArrayList<Student>();

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

		public ArrayList<User> validateUser(String username, String password) {
			Connection conn = null;
			Statement stmt = null;
			ArrayList<User> arrayList = new ArrayList<User>();
			try{
				Class.forName(JDBC_DRIVER);

				conn = DriverManager.getConnection(DB_URL,USER,PASS);

				stmt = conn.createStatement();
				String sql;
				//sql = "SELECT id, first, last, age FROM Employees";
				sql = "SELECT * FROM User";

				if(!StringUtils.isEmpty(username) || !StringUtils.isEmpty(password)) {
					sql += " WHERE ";
					if(!StringUtils.isEmpty(username))
						sql += "username='" + username+"' AND ";
					if(!StringUtils.isEmpty(password))
						sql += "password='" + password+"'";
				}
				ResultSet rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					User user = new User();
					user.setId(rs.getString("id"));
					user.setFirst(rs.getString("first"));
					user.setLast(rs.getString("last"));
					//user.setInstitution(rs.getString("institutionId") + "~"+ rs.getString("institution"));
					//user.setAccess(rs.getString("accessId") + "~"+ rs.getString("access"));
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					arrayList.add(user);
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
