package com.form;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bean.Branch;
import com.bean.Course;
import com.bean.Institution;
import com.bean.Student;
import com.bean.Subject;

@Component("JDBCUtil")
public class JDBCUtil {
	
	// JDBC driver name and database URL
		static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
		static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/EMP";
		//static final String DB_URL = "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12348159";

		//  Database credentials
		static final String USER = "root";
		static final String PASS = "rajan";
		//static final String USER = "sql12348159";
		//static final String PASS = "dEEwGHqICT";
		
		
		public ArrayList<Institution> getInstitutionList() {
			Connection conn = null;
			Statement stmt = null;
			ArrayList<Institution> arrayList = new ArrayList<Institution>();
			try{
				Class.forName(JDBC_DRIVER);

				conn = DriverManager.getConnection(DB_URL,USER,PASS);

				stmt = conn.createStatement();
				String sql;
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
		
		public ArrayList<Subject> getSubjectList(String institution, String course, String branch, String year) {
			Connection conn = null;
			Statement stmt = null;
			ArrayList<Subject> arrayList = new ArrayList<Subject>();
			try{
				Class.forName(JDBC_DRIVER);

				conn = DriverManager.getConnection(DB_URL,USER,PASS);

				stmt = conn.createStatement();
				String sql;
				sql = "SELECT s.id, s.name FROM Subject s";
				
				if(!StringUtils.isEmpty(institution) || !StringUtils.isEmpty(course) || !StringUtils.isEmpty(branch) || !StringUtils.isEmpty(year)) {
					sql += " WHERE ";
					if(!StringUtils.isEmpty(institution))
						sql += "s.institutionId='" + institution+"' AND ";
					if(!StringUtils.isEmpty(course))
						sql += "s.courseId='" + course+"' AND ";
					if(!StringUtils.isEmpty(branch))
						sql += "s.branchId='" + branch+"' AND ";
					if(!StringUtils.isEmpty(year))
						sql += "s.year='" + year+"' AND ";
					sql = sql.substring(0, sql.length()-4);
				}
				
				ResultSet rs = stmt.executeQuery(sql);

				while(rs.next()){
					Subject subject = new Subject();
					subject.setId(rs.getString("id"));
					subject.setName(rs.getString("name"));
					arrayList.add(subject);

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
		
		public boolean addSubject(String institution, String course, String branch, String year, String subject) {
			Connection conn = null;
			Statement stmt = null;
			boolean isSuccess = true;
			try{
				Class.forName(JDBC_DRIVER);

				conn = DriverManager.getConnection(DB_URL,USER,PASS);

				stmt = conn.createStatement();
				String sql;
				sql = "INSERT INTO Subject(institutionId,courseId,branchId,year,name) VALUES(";
				
				if(!StringUtils.isEmpty(institution) && !StringUtils.isEmpty(course) && !StringUtils.isEmpty(branch) 
						&& !StringUtils.isEmpty(year) && !StringUtils.isEmpty(subject)) {
					sql += "'" +institution+"',";
					sql += "'" +course+"',";
					sql += "'" +branch+"',";
					sql += "'" +year+"',";
					sql += "'" +subject+"')";
					
					int a = stmt.executeUpdate(sql);
				}
				
				

				stmt.close();
				conn.close();

			}catch(SQLIntegrityConstraintViolationException se){
				isSuccess = false;
				System.out.print("Constraint violation exception");
				se.printStackTrace();
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
			return isSuccess;
		}
		
		public boolean updateSubject(String institution, String course, String branch, String year, String subject, String newSubject) {
			Connection conn = null;
			Statement stmt = null;
			boolean isSuccess = true;
			try{
				Class.forName(JDBC_DRIVER);

				conn = DriverManager.getConnection(DB_URL,USER,PASS);

				stmt = conn.createStatement();
				
				if(!StringUtils.isEmpty(institution) && !StringUtils.isEmpty(course) && !StringUtils.isEmpty(branch) 
						&& !StringUtils.isEmpty(year) && !StringUtils.isEmpty(subject) && !StringUtils.isEmpty(newSubject)) {
					String sql;
					sql = "UPDATE Subject ";
					sql += "SET ";
					sql += "name='" + newSubject+"' ";
					
					sql += "WHERE ";
					sql += "institutionId='" + institution+"' AND ";
					sql += "courseId='" + course+"' AND ";
					sql += "branchId='" + branch+"' AND ";
					sql += "year='" + year+"' AND ";
					sql += "id='" + subject+"'";
					
					int a = stmt.executeUpdate(sql);
				}
				
				

				stmt.close();
				conn.close();

			}catch(SQLIntegrityConstraintViolationException se){
				isSuccess = false;
				System.out.print("Constraint violation exception");
				se.printStackTrace();
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
			return isSuccess;
		}
		
		public boolean deleteSubject(String institution, String course, String branch, String year, String subject) {
			Connection conn = null;
			Statement stmt = null;
			boolean isSuccess = true;
			try{
				Class.forName(JDBC_DRIVER);

				conn = DriverManager.getConnection(DB_URL,USER,PASS);

				stmt = conn.createStatement();
				
				String sql;
				sql = "DELETE FROM Subject ";
				sql += "WHERE ";
				sql += "institutionId='" + institution+"' AND ";
				sql += "courseId='" + course+"' AND ";
				sql += "branchId='" + branch+"' AND ";
				sql += "year='" + year+"' AND ";
				sql += "id='" + subject+"'";
				
				int a = stmt.executeUpdate(sql);
				System.out.print("Data Deleted successfully");
				stmt.close();
				conn.close();
				
			}catch(SQLIntegrityConstraintViolationException se){
				isSuccess = false;
				System.out.print("Constraint violation exception");
				se.printStackTrace();
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
			return isSuccess;
		}
}
