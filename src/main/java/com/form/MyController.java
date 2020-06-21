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
			ArrayList<Student> arrayList = JDBCDemo.fetchFilteredData(student.getInstitution(),student.getCourse(), student.getBranch(), student.getSemester());
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
	public String submitAddAttendanceForm(ModelMap model,HttpServletRequest request, HttpServletResponse response,AttendanceForm attendanceForm) {
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
	public String viewAttendance(ModelMap model,HttpServletRequest request, HttpServletResponse response,String date) {
		try {
			ArrayList<Student> arrayList= JDBCDemo.fetch();
			 ObjectMapper Obj = new ObjectMapper(); 
			 String jsonStr = Obj.writeValueAsString(arrayList);
			 model.addAttribute("formData", jsonStr);
			 
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
	public String getAttendanceData(ModelMap model,HttpServletRequest request, HttpServletResponse response,Student student, String startDate, String endDate) {
		try {
			if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
				model.addAttribute("message", "Please enter a valid date");
				response.setStatus(400);
				return "textResponse";
				
			}
			 ArrayList<Attendance> arrayList= JDBCDemo.fetchAttendanceRecord(student.getInstitution(),student.getCourse(), student.getBranch(), student.getSemester(),startDate, endDate);
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
	public String getAttendancePercentage(ModelMap model,HttpServletRequest request, HttpServletResponse response,Student student, String startDate, String endDate) {
		try {
			if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
				model.addAttribute("message", "Please enter a valid date");
				response.setStatus(400);
				return "textResponse";
				
			}
			 ArrayList<AttendancePercentage> arrayList= JDBCDemo.fetchAttendanceRecordPercentage(student.getInstitution(),student.getCourse(), student.getBranch(), student.getSemester(),startDate, endDate);
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
}