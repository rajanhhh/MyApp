package com.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bean.Attendance;
import com.bean.AttendanceForm;
import com.bean.AttendancePercentage;
import com.bean.Branch;
import com.bean.Course;
import com.bean.Institution;
import com.bean.Student;
import com.bean.UpdateAttendance;
import com.google.gson.Gson;


@Controller
public class MyController {

	@Autowired
	JDBCDemo JDBCDemo;
	
	@Autowired
	JDBCUtil JDBCUtil;
	
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String welcomeForm(ModelMap model) {
		try {
			return "welcomePage";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "welcomePage";
	}

	@RequestMapping(value = "/get",method = RequestMethod.GET)
	public String getForm(ModelMap model) {
		try {
			 ObjectMapper Obj = new ObjectMapper(); 
			ArrayList<Institution> institutionList= JDBCUtil.getInstitutionList();
			 String institutionListJSON = Obj.writeValueAsString(institutionList);
			 model.addAttribute("institutionList", institutionListJSON);
			 
			 ArrayList<Course> courseList= JDBCUtil.getCourseList();
			 String courseListJSON = Obj.writeValueAsString(courseList);
			 model.addAttribute("courseList", courseListJSON);
			 
			 ArrayList<Branch> branchList= JDBCUtil.getBranchList();
			 String branchListJSON = Obj.writeValueAsString(branchList);
			 model.addAttribute("branchList", branchListJSON);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "viewDetails";
	}
	@RequestMapping(value = "/add",method = RequestMethod.GET)
	public String showAddForm(ModelMap model) {
		try {
			ObjectMapper Obj = new ObjectMapper(); 
			ArrayList<Institution> institutionList= JDBCUtil.getInstitutionList();
			 String institutionListJSON = Obj.writeValueAsString(institutionList);
			 model.addAttribute("institutionList", institutionListJSON);
			 
			 ArrayList<Course> courseList= JDBCUtil.getCourseList();
			 String courseListJSON = Obj.writeValueAsString(courseList);
			 model.addAttribute("courseList", courseListJSON);
			 
			 ArrayList<Branch> branchList= JDBCUtil.getBranchList();
			 String branchListJSON = Obj.writeValueAsString(branchList);
			 model.addAttribute("branchList", branchListJSON);
			return "addDetails";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "addDetails";
	}
	@RequestMapping(value = "/addData",method = RequestMethod.POST)
	public String submitAddForm(ModelMap model,Student student, HttpServletResponse response) {
		try {
			boolean isSuccess = false;
			if(StringUtils.isEmpty(student.getId()) ||  StringUtils.isEmpty(student.getInstitution())
					||	StringUtils.isEmpty(student.getFirst()) ||  StringUtils.isEmpty(student.getLast()) 
					|| StringUtils.isEmpty(student.getBranch()) ||  StringUtils.isEmpty(student.getSemester()) || StringUtils.isEmpty(student.getCourse())) {
				model.addAttribute("message", "Cannot accept null value");
				
			}else {
				isSuccess = JDBCDemo.addStudentDetails(student);
				if(isSuccess) {
					model.addAttribute("message", "Data inserted successfully");
					response.setStatus(200);
				}else {
					model.addAttribute("message", "Duplicate ID entered");
					response.setStatus(400);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "textResponse";
	}
	
	@RequestMapping(value = "/getfilteredData",method = RequestMethod.POST)
	public String getFilteredData(ModelMap model,Student student, HttpServletResponse response) {
		try {
			ArrayList<Student> arrayList = JDBCDemo.fetchFilteredData(student.getId(),student.getInstitution(),student.getCourse(), student.getBranch(), student.getSemester());
			if(arrayList.size() != 0) {
				ObjectMapper Obj = new ObjectMapper(); 
				 String jsonStr = Obj.writeValueAsString(arrayList);
				 model.addAttribute("message", jsonStr);
				response.setStatus(200);
			}else {
				model.addAttribute("message", "No Record Found");
				response.setStatus(400);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "textResponse";
	}
	
	@RequestMapping(value = "/updateData",method = RequestMethod.GET)
	public String getUpdateDetailsForm(ModelMap model) {
		try {
			 ObjectMapper Obj = new ObjectMapper(); 
			ArrayList<Institution> institutionList= JDBCUtil.getInstitutionList();
			 String institutionListJSON = Obj.writeValueAsString(institutionList);
			 model.addAttribute("institutionList", institutionListJSON);
			 
			 ArrayList<Course> courseList= JDBCUtil.getCourseList();
			 String courseListJSON = Obj.writeValueAsString(courseList);
			 model.addAttribute("courseList", courseListJSON);
			 
			 ArrayList<Branch> branchList= JDBCUtil.getBranchList();
			 String branchListJSON = Obj.writeValueAsString(branchList);
			 model.addAttribute("branchList", branchListJSON);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "modifyData";
	}
	
	@RequestMapping(value = "/updateStudentData",method = RequestMethod.POST)
	public String updateStudentDetails(ModelMap model,Student student, String newFirst, String newLast, String newInstitution, String newCourse, String newBranch, String newSemester, HttpServletResponse response) {
		try {
			if(!StringUtils.isEmpty(student.getId()) && !StringUtils.isEmpty(student.getInstitution())
					&&	!StringUtils.isEmpty(student.getFirst()) && !StringUtils.isEmpty(student.getLast()) 
					&& !StringUtils.isEmpty(student.getBranch()) && !StringUtils.isEmpty(student.getSemester()) && !StringUtils.isEmpty(student.getCourse())
					&& !StringUtils.isEmpty(newFirst) && !StringUtils.isEmpty(newLast)
					&&	!StringUtils.isEmpty(newInstitution) && !StringUtils.isEmpty(newCourse) 
					&& !StringUtils.isEmpty(newBranch) && !StringUtils.isEmpty(newSemester)) {
				ArrayList<Attendance> arrayList= JDBCDemo.fetchAttendanceRecord(student.getId(),student.getInstitution(),student.getCourse(), student.getBranch(), student.getSemester(),null,null,null);
				 if(arrayList.size() == 0) {
					 boolean isSuccess = JDBCDemo.updateStudentDetails(student, newFirst, newLast, newInstitution, newCourse, newBranch, newSemester);
					 if(isSuccess) {
						model.addAttribute("message", "Successfully Updated");
						response.setStatus(200);
					 }else {
						 model.addAttribute("message", "Error Occurred");
						 response.setStatus(400);
					 }
				}else {
					model.addAttribute("message", "Cannot edit as Attendance data already exists for this student.\nContact admin to modify data.");
					response.setStatus(400);
				}
			}else {
				model.addAttribute("message", "Cannot enter null value");
				response.setStatus(400);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "textResponse";
	}
	
	@RequestMapping(value = "/deleteStudentData",method = RequestMethod.POST)
	public String deleteStudentDetails(ModelMap model,Student student, HttpServletResponse response) {
		try {
			if(!StringUtils.isEmpty(student.getId()) && !StringUtils.isEmpty(student.getInstitution())
					&&	!StringUtils.isEmpty(student.getFirst()) && !StringUtils.isEmpty(student.getLast()) 
					&& !StringUtils.isEmpty(student.getBranch()) && !StringUtils.isEmpty(student.getSemester()) && !StringUtils.isEmpty(student.getCourse())) {
				ArrayList<Attendance> arrayList= JDBCDemo.fetchAttendanceRecord(student.getId(),student.getInstitution(),student.getCourse(), student.getBranch(), student.getSemester(),null,null,null);
				 if(arrayList.size() == 0) {
					 boolean isSuccess = JDBCDemo.deleteStudentDetails(student);
					 if(isSuccess) {
						model.addAttribute("message", "Successfully Deleted");
						response.setStatus(200);
					 }else {
						 model.addAttribute("message", "Error Occurred");
						 response.setStatus(400);
					 }
				}else {
					model.addAttribute("message", "Cannot delete as Attendance data already exists for this student.Contact admin to modify data.");
					response.setStatus(400);
				}
			}else {
				model.addAttribute("message", "Cannot enter null value");
				response.setStatus(400);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "textResponse";
	}
	
	@RequestMapping(value = "/addAttendance",method = RequestMethod.GET)
	public String addAttendancePage(ModelMap model) {
		try {
			 ObjectMapper Obj = new ObjectMapper(); 
			 
			 ArrayList<Institution> institutionList= JDBCUtil.getInstitutionList();
			 String institutionListJSON = Obj.writeValueAsString(institutionList);
			 model.addAttribute("institutionList", institutionListJSON);
			 
			 ArrayList<Course> courseList= JDBCUtil.getCourseList();
			 String courseListJSON = Obj.writeValueAsString(courseList);
			 model.addAttribute("courseList", courseListJSON);
			 
			 ArrayList<Branch> branchList= JDBCUtil.getBranchList();
			 String branchListJSON = Obj.writeValueAsString(branchList);
			 model.addAttribute("branchList", branchListJSON);
			return "addAttendance";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "addAttendance";
	}
	
	@RequestMapping(value = "/submitAttendance",method = RequestMethod.POST, produces="text/plain")
	public String submitAttendance(ModelMap model,HttpServletRequest request, HttpServletResponse response,AttendanceForm attendanceForm) {
		try {
			ArrayList<Attendance> arrayList = new ArrayList<Attendance>();
			Gson gson = new Gson();
			boolean isSuccess = true;
			
			if(StringUtils.isEmpty(attendanceForm.getDate())) {
				model.addAttribute("message", "Please enter a valid date");
				response.setStatus(400);
				return "textResponse";
				
			}
			List<Map<String, String>> attendanceList = gson.fromJson(attendanceForm.getAttendanceList(), List.class);
			for (Map<String, String> map : attendanceList) { 
				Attendance attendance = new Attendance();
				attendance.setId(map.get("id"));
				attendance.setInstitution(map.get("institution"));
				attendance.setCourse(map.get("course"));
				attendance.setBranch(map.get("branch"));
				attendance.setSemester(map.get("semester"));
				attendance.setPresence(map.get("presence"));
				attendance.setSubject(attendanceForm.getSubject());
				arrayList.add(attendance);
			}
			isSuccess= JDBCDemo.addAttendanceRecord(attendanceForm.getDate(), arrayList);
			if(isSuccess) {
				model.addAttribute("message", "Data inserted successfully");
				response.setStatus(200);
			}else {
				model.addAttribute("message", "Duplicate data entered");
				response.setStatus(400);
			}
			return "textResponse";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "textResponse";
	}
	
	@RequestMapping(value = "/viewAttendance",method = RequestMethod.GET)
	public String viewAttendance(ModelMap model,HttpServletRequest request) {
		try {
			 ObjectMapper Obj = new ObjectMapper(); 
			 
			 ArrayList<Institution> institutionList= JDBCUtil.getInstitutionList();
			 String institutionListJSON = Obj.writeValueAsString(institutionList);
			 model.addAttribute("institutionList", institutionListJSON);
			 
			 ArrayList<Course> courseList= JDBCUtil.getCourseList();
			 String courseListJSON = Obj.writeValueAsString(courseList);
			 model.addAttribute("courseList", courseListJSON);
			 
			 ArrayList<Branch> branchList= JDBCUtil.getBranchList();
			 String branchListJSON = Obj.writeValueAsString(branchList);
			 model.addAttribute("branchList", branchListJSON);
			return "viewAttendance";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "viewAttendance";
	}
	@RequestMapping(value = "/getAttendanceData",method = RequestMethod.POST)
	public String getAttendanceData(ModelMap model,HttpServletRequest request, HttpServletResponse response,Student student, String subject, String startDate, String endDate) {
		try {
			if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
				model.addAttribute("message", "Please enter a valid date");
				response.setStatus(400);
				return "textResponse";
				
			}
			 ArrayList<Attendance> arrayList= JDBCDemo.fetchAttendanceRecord(student.getId(),student.getInstitution(),student.getCourse(), student.getBranch(), student.getSemester(),subject,startDate, endDate);
			 if(arrayList.size() != 0) {
				 ObjectMapper Obj = new ObjectMapper(); 
				 String jsonStr = Obj.writeValueAsString(arrayList);
				 model.addAttribute("message", jsonStr);
				response.setStatus(200);
			}else {
				model.addAttribute("message", "No Data Found");
				response.setStatus(400);
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "textResponse";
	}
	
	@RequestMapping(value = "/getAttendancePercentage",method = RequestMethod.POST)
	public String getAttendancePercentage(ModelMap model,HttpServletRequest request, HttpServletResponse response,Student student, String subject, String startDate, String endDate) {
		try {
			if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
				model.addAttribute("message", "Please enter a valid date");
				response.setStatus(400);
				return "textResponse";
				
			}
			 ArrayList<AttendancePercentage> arrayList= JDBCDemo.fetchAttendanceRecordPercentage(student.getInstitution(),student.getCourse(), student.getBranch(), student.getSemester(),subject,startDate, endDate);
			 if(arrayList.size() != 0) {
				 for (AttendancePercentage attendancePercentage : arrayList) {
					 if(StringUtils.isEmpty(attendancePercentage.getTotalDays()) || attendancePercentage.getTotalDays() == "0") {
						 attendancePercentage.setAttendedDays("0");
						 attendancePercentage.setTotalDays("0");
						 attendancePercentage.setPercentage("0");
					 }else {
						 attendancePercentage.setPercentage (String.valueOf(Integer.valueOf(attendancePercentage.getAttendedDays())*100/Integer.valueOf(attendancePercentage.getTotalDays())));
					 }
				}
				 ObjectMapper Obj = new ObjectMapper(); 
				 String jsonStr = Obj.writeValueAsString(arrayList);
				 model.addAttribute("message", jsonStr);
				response.setStatus(200);
			}else {
				model.addAttribute("message", "No Data Found");
				response.setStatus(400);
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "textResponse";
	}
	
	@RequestMapping(value = "/updateAttendance",method = RequestMethod.GET)
	public String updateAttendance(ModelMap model,HttpServletRequest request) {
		try {
			 ObjectMapper Obj = new ObjectMapper(); 
			 
			 ArrayList<Institution> institutionList= JDBCUtil.getInstitutionList();
			 String institutionListJSON = Obj.writeValueAsString(institutionList);
			 model.addAttribute("institutionList", institutionListJSON);
			 
			 ArrayList<Course> courseList= JDBCUtil.getCourseList();
			 String courseListJSON = Obj.writeValueAsString(courseList);
			 model.addAttribute("courseList", courseListJSON);
			 
			 ArrayList<Branch> branchList= JDBCUtil.getBranchList();
			 String branchListJSON = Obj.writeValueAsString(branchList);
			 model.addAttribute("branchList", branchListJSON);
			return "updateAttendance";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "updateAttendance";
	}
	
	@RequestMapping(value = "/getAttendanceDataForAll",method = RequestMethod.POST)
	public String getAttendanceDataForAll(ModelMap model,HttpServletRequest request, HttpServletResponse response,Student student, String subject, String date) {
		try {
			if(StringUtils.isEmpty(date)) {
				model.addAttribute("message", "Please enter a valid date");
				response.setStatus(400);
				return "textResponse";

			}
			ArrayList<Attendance> arrayList= JDBCDemo.fetchAttendanceRecord(student.getId(),student.getInstitution(),student.getCourse(), student.getBranch(), student.getSemester(),subject, date,date);
			if(arrayList.size() ==0){
				model.addAttribute("message", "Attendance Data doesn't exist for this date. Please go to 'Add Attendance' section");
				response.setStatus(400);
				return "textResponse";
			}else {
				ArrayList<UpdateAttendance> arrayList1= JDBCDemo.fetchAttendanceRecordForAll(student.getInstitution(),student.getCourse(), student.getBranch(), student.getSemester(),subject, date);
				if(arrayList1.size() != 0) {
					for (UpdateAttendance updateAttendance : arrayList1) { 
						if(StringUtils.isEmpty(updateAttendance.getPresence()))
							updateAttendance.setStatus("new");
						else
							updateAttendance.setStatus("old");
					}
					ObjectMapper Obj = new ObjectMapper(); 
					String jsonStr = Obj.writeValueAsString(arrayList1);
					model.addAttribute("message", jsonStr);
					response.setStatus(200);
				}else {
					model.addAttribute("message", "No Data Found");
					response.setStatus(400);
				}
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "textResponse";
	}
	
	@RequestMapping(value = "/submitUpdatedAttendance",method = RequestMethod.POST, produces="text/plain")
	public String submitUpdatedAttendance(ModelMap model,HttpServletRequest request, HttpServletResponse response,AttendanceForm attendanceForm) {
		try {
			ArrayList<UpdateAttendance> arrayList = new ArrayList<UpdateAttendance>();
			Gson gson = new Gson();
			boolean isSuccess = true;
			
			if(StringUtils.isEmpty(attendanceForm.getDate())) {
				model.addAttribute("message", "Please enter a valid date");
				response.setStatus(400);
				return "textResponse";
				
			}
			List<Map<String, String>> attendanceList = gson.fromJson(attendanceForm.getAttendanceList(), List.class);
			for (Map<String, String> map : attendanceList) { 
				UpdateAttendance updateAttendance = new UpdateAttendance();
				updateAttendance.setId(map.get("id"));
				updateAttendance.setInstitution(map.get("institution"));
				updateAttendance.setCourse(map.get("course"));
				updateAttendance.setBranch(map.get("branch"));
				updateAttendance.setSemester(map.get("semester"));
				updateAttendance.setPresence(map.get("newPresence"));
				updateAttendance.setStatus(map.get("status"));
				arrayList.add(updateAttendance);
			}
			isSuccess= JDBCDemo.updateAttendanceRecordForAll(attendanceForm.getDate(), attendanceForm.getSubject(), arrayList);
			if(isSuccess) {
				model.addAttribute("message", "Data Updated successfully");
				response.setStatus(200);
			}else {
				model.addAttribute("message", "Duplicate data entered");
				response.setStatus(400);
			}
			
			return "textResponse";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "textResponse";
	}
}