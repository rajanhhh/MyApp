package com.form;

//STEP 1. Import required packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bean.Attendance;
import com.bean.AttendancePercentage;
import com.bean.Student;
import com.bean.UpdateAttendance;

@Component("JDBCHandler")
public class JDBCHandler {
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

	public ArrayList<Student> fetch() {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<Student> arrayList = new ArrayList<Student>();
		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = conn.createStatement();
			String sql;
			//sql = "SELECT id, first, last, age FROM Employees";
			sql = "SELECT s.id,s.first,s.last,i.name institution,b.name branch,s.semester,c.name course FROM Student s INNER JOIN Institution i ON s.institutionId = i.id INNER JOIN Branch b ON s.branchId = b.id INNER JOIN Course c ON s.courseId = c.id";
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){
				Student student = new Student();
				student.setId(rs.getString("id"));
				student.setFirst(rs.getString("first"));
				student.setLast(rs.getString("last"));
				student.setInstitution(rs.getString("institution"));
				student.setCourse(rs.getString("course"));
				student.setBranch(rs.getString("branch"));
				student.setSemester(rs.getString("semester"));
				arrayList.add(student);

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
	
	public static ArrayList<Student> fetchFilteredData(String id, String institution, String course, String branch, String semester) {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<Student> arrayList = new ArrayList<Student>();
		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = conn.createStatement();
			String sql;
			sql = "SELECT s.id,s.first,s.last,s.institutionId institutionId,s.branchId branchId, s.courseId courseId, i.name institution,b.name branch,s.semester,c.name course FROM Student s INNER JOIN Institution i ON s.institutionId = i.id INNER JOIN Branch b ON s.branchId = b.id INNER JOIN Course c ON s.courseId = c.id";
			
			if(!StringUtils.isEmpty(id) || !StringUtils.isEmpty(institution) || !StringUtils.isEmpty(course) || !StringUtils.isEmpty(branch) || !StringUtils.isEmpty(semester)) {
				sql += " WHERE ";
				if(!StringUtils.isEmpty(id))
					sql += "s.id='" + id+"' AND ";
				if(!StringUtils.isEmpty(institution))
					sql += "s.institutionId='" + institution+"' AND ";
				if(!StringUtils.isEmpty(course))
					sql += "s.courseId='" + course+"' AND ";
				if(!StringUtils.isEmpty(branch))
					sql += "s.branchId='" + branch+"' AND ";
				if(!StringUtils.isEmpty(semester))
					sql += "s.semester='" + semester+"' AND ";
				sql = sql.substring(0, sql.length()-4);
			}
			
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){
				Student student = new Student();
				student.setId(rs.getString("id"));
				student.setFirst(rs.getString("first"));
				student.setLast(rs.getString("last"));
				student.setInstitution(rs.getString("institutionId") + "~"+ rs.getString("institution"));
				student.setCourse(rs.getString("courseId") + '~' + rs.getString("course"));
				student.setBranch(rs.getString("branchId") + '~' + rs.getString("branch"));
				student.setSemester(rs.getString("semester"));
				arrayList.add(student);

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
	
	public boolean addStudentDetails(Student student) {
		Connection conn = null;
		Statement stmt = null;
		boolean isSuccess = true;
		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = conn.createStatement();
			
			String sql;
			sql = "INSERT INTO Student (id, first, last, courseId, semester, branchId, institutionId) VALUES (";
			if(null != student) {
				sql += student.getId()+",";
				sql += "'" +student.getFirst()+"',";
				sql += "'" +student.getLast()+"',";
				sql += "'" +student.getCourse()+"',";
				sql += "'" +student.getSemester()+"',";
				sql += "'" +student.getBranch()+"',";
				sql += student.getInstitution() + ")";
				int a = stmt.executeUpdate(sql);
			}
			System.out.print("Data inserted successfully");
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
	
	public boolean updateStudentDetails(Student student,String newFirst, String newLast, String newInstitution, String newCourse, String newBranch, String newSemester) {
		Connection conn = null;
		Statement stmt = null;
		boolean isSuccess = true;
		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = conn.createStatement();
			
			String sql;
			sql = "UPDATE Student ";
			sql += "SET ";
			sql += "first='" + newFirst+"' , ";
			sql += "last='" + newLast+"' , ";
			sql += "institutionId='" + newInstitution+"' , ";
			sql += "courseId='" + newCourse+"' , ";
			sql += "branchId='" + newBranch+"' , ";
			sql += "semester='" + newSemester+"' ";
			
			sql += "WHERE ";
			sql += "id='" + student.getId()+"' AND ";
			sql += "first='" + student.getFirst()+"' AND ";
			sql += "last='" + student.getLast()+"' AND ";
			sql += "institutionId='" + student.getInstitution()+"' AND ";
			sql += "courseId='" + student.getCourse()+"' AND ";
			sql += "branchId='" + student.getBranch()+"' AND ";
			sql += "semester='" + student.getSemester()+"'";
			
			int a = stmt.executeUpdate(sql);
			System.out.print("Data Updated successfully");
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
	
	public boolean deleteStudentDetails(Student student) {
		Connection conn = null;
		Statement stmt = null;
		boolean isSuccess = true;
		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = conn.createStatement();
			
			String sql;
			sql = "DELETE FROM Student ";
			sql += "WHERE ";
			sql += "id='" + student.getId()+"' AND ";
			sql += "first='" + student.getFirst()+"' AND ";
			sql += "last='" + student.getLast()+"' AND ";
			sql += "institutionId='" + student.getInstitution()+"' AND ";
			sql += "courseId='" + student.getCourse()+"' AND ";
			sql += "branchId='" + student.getBranch()+"' AND ";
			sql += "semester='" + student.getSemester()+"'";
			
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
	
	public boolean addAttendanceRecord(String date,ArrayList<Attendance> arrayList) {
		Connection conn = null;
		Statement stmt = null;
		boolean isSuccess = true;
		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = conn.createStatement();
			for (int i = 0; i < arrayList.size(); i++) {
				String sql;
				Attendance attendance = arrayList.get(i);
				sql = "INSERT INTO Attendance (id, date, institutionId, courseId, branchId, semester, presence, subjectId) VALUES (";
				if(null != attendance) {
					sql += attendance.getId()+",";
					sql += "'" +date+"',";
					sql += "'" +attendance.getInstitution()+"',";
					sql += "'" +attendance.getCourse()+"',";
					sql += "'" +attendance.getBranch()+"',";
					sql += "'" +attendance.getSemester()+"',";
					sql += "'" +attendance.getPresence()+"',";
					sql += "'" +attendance.getSubject()+ "')";
				}
				int a = stmt.executeUpdate(sql);
				System.out.print("Data inserted successfully");
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
	
	public ArrayList<Attendance> fetchAttendanceRecord(String id,String institution, String course, String branch, String semester,String subject, String startDate, String endDate) {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<Attendance> arrayList = new ArrayList<Attendance>();
		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = conn.createStatement();
			String sql = "";
			sql = "SELECT a.date, a.id,a.presence,s.first, s.last, s.institutionId institutionId,s.branchId branchId, s.courseId courseId, i.name institution, c.name course, b.name branch, a.semester, sub.name subject FROM Attendance a INNER JOIN Student s ON a.id = s.id INNER JOIN Institution i ON s.institutionId = i.id INNER JOIN Branch b ON s.branchId = b.id INNER JOIN Course c ON s.courseId = c.id INNER JOIN Subject sub ON a.subjectId = sub.id";
			
			if(!StringUtils.isEmpty(id) || !StringUtils.isEmpty(institution) || !StringUtils.isEmpty(course) || !StringUtils.isEmpty(branch) || !StringUtils.isEmpty(semester) || !StringUtils.isEmpty(subject) || !StringUtils.isEmpty(startDate) || !StringUtils.isEmpty(endDate)) {
				sql += " WHERE ";
				if(!StringUtils.isEmpty(id))
					sql += "s.id='" + id+"' AND ";
				if(!StringUtils.isEmpty(institution))
					sql += "s.institutionId='" + institution+"' AND ";
				if(!StringUtils.isEmpty(course))
					sql += "s.courseId='" + course+"' AND ";
				if(!StringUtils.isEmpty(branch))
					sql += "s.branchId='" + branch+"' AND ";
				if(!StringUtils.isEmpty(semester))
					sql += "s.semester='" + semester+"' AND ";
				if(!StringUtils.isEmpty(subject))
					sql += "sub.id='" + subject+"' AND ";
				if(!StringUtils.isEmpty(startDate))
					sql += "a.date>='" + startDate+"' AND ";
				if(!StringUtils.isEmpty(endDate))
					sql += "a.date<='" + endDate+"' AND ";
				sql = sql.substring(0, sql.length()-4);
			}
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				Attendance attendance = new Attendance();
				attendance.setDate(rs.getString("date"));
				attendance.setId(rs.getString("id"));
				attendance.setFirst(rs.getString("first"));
				attendance.setLast(rs.getString("last"));
				attendance.setPresence(rs.getString("presence"));
				attendance.setInstitution(rs.getString("institutionId") + "~"+ rs.getString("institution"));
				attendance.setCourse(rs.getString("courseId") + '~' + rs.getString("course"));
				attendance.setBranch(rs.getString("branchId") + '~' + rs.getString("branch"));
				attendance.setSemester(rs.getString("semester"));
				attendance.setSubject(rs.getString("subject"));
				arrayList.add(attendance);
			}
			//
			stmt.close();
			conn.close();
			
		}catch(SQLIntegrityConstraintViolationException se){
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
		return arrayList;
	}
	
	public ArrayList<AttendancePercentage> fetchAttendanceRecordPercentage(String id,String institution, String course, String branch, String semester, String subject,String startDate, String endDate) {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<AttendancePercentage> arrayList = new ArrayList<AttendancePercentage>();
		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = conn.createStatement();
			String sql = "";
			sql = "SELECT a.id ,s.first, s.last,s.institutionId institutionId,s.branchId branchId, s.courseId courseId, i.name institution, c.name course, b.name branch, a.semester,sub.name subject,SUM(a.id LIKE '%') totalDays, SUM(presence=\"Y\") attendedDays FROM Attendance a INNER JOIN Student s ON a.id = s.id INNER JOIN Institution i ON s.institutionId = i.id INNER JOIN Branch b ON s.branchId = b.id INNER JOIN Course c ON s.courseId = c.id INNER JOIN Subject sub ON a.subjectId = sub.id";
			
			if(!StringUtils.isEmpty(institution) || !StringUtils.isEmpty(course) || !StringUtils.isEmpty(branch) || !StringUtils.isEmpty(semester) || !StringUtils.isEmpty(startDate) || !StringUtils.isEmpty(endDate) || !StringUtils.isEmpty(subject)) {
				sql += " WHERE ";
				if(!StringUtils.isEmpty(id))
					sql += "s.id='" + id+"' AND ";
				if(!StringUtils.isEmpty(institution))
					sql += "s.institutionId='" + institution+"' AND ";
				if(!StringUtils.isEmpty(course))
					sql += "s.courseId='" + course+"' AND ";
				if(!StringUtils.isEmpty(branch))
					sql += "s.branchId='" + branch+"' AND ";
				if(!StringUtils.isEmpty(semester))
					sql += "s.semester='" + semester+"' AND ";
				if(!StringUtils.isEmpty(subject))
					sql += "sub.id='" + subject+"' AND ";
				if(!StringUtils.isEmpty(startDate))
					sql += "a.date>='" + startDate+"' AND ";
				if(!StringUtils.isEmpty(endDate))
					sql += "a.date<='" + endDate+"' AND ";
				sql = sql.substring(0, sql.length()-4);
				sql += " GROUP BY a.id, sub.id";
			}
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				AttendancePercentage attendancePercentage = new AttendancePercentage();
				attendancePercentage.setId(rs.getString("id"));
				attendancePercentage.setFirst(rs.getString("first"));
				attendancePercentage.setLast(rs.getString("last"));
				attendancePercentage.setInstitution(rs.getString("institutionId") + "~"+ rs.getString("institution"));
				attendancePercentage.setCourse(rs.getString("courseId") + '~' + rs.getString("course"));
				attendancePercentage.setBranch(rs.getString("branchId") + '~' + rs.getString("branch"));
				attendancePercentage.setSemester(rs.getString("semester"));
				attendancePercentage.setSubject(rs.getString("subject"));
				attendancePercentage.setTotalDays(rs.getString("totalDays"));
				attendancePercentage.setAttendedDays(rs.getString("attendedDays"));
				arrayList.add(attendancePercentage);
			}
			//
			stmt.close();
			conn.close();
			
		}catch(SQLIntegrityConstraintViolationException se){
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
		return arrayList;
	}
	
	public ArrayList<UpdateAttendance> fetchAttendanceRecordForAll(String institution, String course, String branch, String semester,String subject, String date) {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<UpdateAttendance> arrayList = new ArrayList<UpdateAttendance>();
		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = conn.createStatement();
			String sql = "";
			sql = "SELECT s1.id,s1.first,s1.last,s1.institutionId,i.name institution,s1.courseId,c.name course,s1.branchId,b.name branch,s1.semester,pack.date,pack.presence,pack.subject FROM Student s1 LEFT JOIN (SELECT s2.id,a.date,a.presence, sub.name subject FROM Student s2 LEFT JOIN Attendance a ON a.id = s2.id LEFT JOIN Subject sub ON a.subjectId = sub.id";
			
			sql += " WHERE ";
			sql += "s2.institutionId='" + institution+"' AND ";
			sql += "s2.courseId='" + course+"' AND ";
			sql += "s2.branchId='" + branch+"' AND ";
			sql += "s2.semester='" + semester+"' AND ";
			sql += "a.date='" + date +"' AND ";
			sql += "sub.id='" + subject +"') ";
			sql += " pack ON s1.id = pack.id LEFT JOIN Institution i ON s1.institutionId = i.id LEFT JOIN Branch b ON s1.branchId = b.id LEFT JOIN Course c ON s1.courseId = c.id WHERE ";
			sql += "s1.institutionId='" + institution+"' AND ";
			sql += "s1.courseId='" + course+"' AND ";
			sql += "s1.branchId='" + branch+"' AND ";
			sql += "s1.semester='" + semester+"'";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				UpdateAttendance updateAttendance = new UpdateAttendance();
				updateAttendance.setId(rs.getString("id"));
				updateAttendance.setFirst(rs.getString("first"));
				updateAttendance.setLast(rs.getString("last"));
				updateAttendance.setPresence(rs.getString("presence"));
				updateAttendance.setInstitution(rs.getString("institutionId") + "~"+ rs.getString("institution"));
				updateAttendance.setCourse(rs.getString("courseId") + '~' + rs.getString("course"));
				updateAttendance.setBranch(rs.getString("branchId") + '~' + rs.getString("branch"));
				updateAttendance.setSemester(rs.getString("semester"));
				arrayList.add(updateAttendance);
			}
			//
			stmt.close();
			conn.close();
			
		}catch(SQLIntegrityConstraintViolationException se){
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
		return arrayList;
	}
	
	public boolean updateAttendanceRecordForAll(String date,String subject,ArrayList<UpdateAttendance> arrayList) {
		Connection conn = null;
		Statement stmt = null;
		boolean isSuccess = true;
		try{
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = conn.createStatement();
			for (int i = 0; i < arrayList.size(); i++) {
				String sql;
				UpdateAttendance updateAttendance = arrayList.get(i);
				if(updateAttendance.getStatus().equalsIgnoreCase("new")) {
					sql = "INSERT INTO Attendance (id, date, institutionId, courseId, branchId, semester, presence, subjectId) VALUES (";
					sql += updateAttendance.getId()+",";
					sql += "'" +date+"',";
					sql += "'" +updateAttendance.getInstitution()+"',";
					sql += "'" +updateAttendance.getCourse()+"',";
					sql += "'" +updateAttendance.getBranch()+"',";
					sql += "'" +updateAttendance.getSemester()+"',";
					sql += "'" +updateAttendance.getPresence()+"',";
					sql += "'" +subject+ "')";
					
					int a = stmt.executeUpdate(sql);
					System.out.print("Data inserted successfully");
				}else {
					sql = "UPDATE Attendance ";
					sql += "SET presence='" + updateAttendance.getPresence()+"' ";
					
					sql += "WHERE ";
					sql += "id='" + updateAttendance.getId()+"' AND ";
					sql += "institutionId='" + updateAttendance.getInstitution()+"' AND ";
					sql += "courseId='" + updateAttendance.getCourse()+"' AND ";
					sql += "branchId='" + updateAttendance.getBranch()+"' AND ";
					sql += "semester='" + updateAttendance.getSemester()+"' AND ";
					sql += "date='" + date+"' AND ";
					sql += "subjectId='" + subject+"'";
					
					int a = stmt.executeUpdate(sql);
					System.out.print("Data Updated successfully");
				}
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
}