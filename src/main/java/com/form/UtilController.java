package com.form;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bean.Branch;
import com.bean.Course;
import com.bean.Institution;
import com.bean.Student;
import com.bean.Subject;

@Controller
public class UtilController {

	@Autowired
	JDBCDemo JDBCDemo;
	
	@Autowired
	JDBCUtil JDBCUtil;

	@RequestMapping(value = "/getSubjectList",method = RequestMethod.POST)
	public String getSubjectList(ModelMap model, HttpServletResponse response,Student student) {
		try {
			if(StringUtils.isEmpty(student.getInstitution()) || StringUtils.isEmpty(student.getBranch()) ||  StringUtils.isEmpty(student.getSemester()) || StringUtils.isEmpty(student.getCourse())) {
				model.addAttribute("message", "Cannot accept null value");
				response.setStatus(400);
			}else {
				ArrayList<Subject> arrayList = JDBCUtil.getSubjectList(student.getInstitution(),student.getCourse(), student.getBranch(), student.getSemester());
				if(arrayList.size() != 0) {
					ObjectMapper Obj = new ObjectMapper(); 
					 String jsonStr = Obj.writeValueAsString(arrayList);
					 model.addAttribute("message", jsonStr);
					response.setStatus(200);
				}else {
					model.addAttribute("message", "No Record Found");
					response.setStatus(400);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "textResponse";
	}
	
	@RequestMapping(value = "/addSubject",method = RequestMethod.GET)
	public String getAddSubjectForm(ModelMap model, HttpServletResponse response,Student student, String subject) {
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
		return "addSubject";
	}
	
	@RequestMapping(value = "/addNewSubject",method = RequestMethod.POST)
	public String addSubject(ModelMap model, HttpServletResponse response,Student student, String subject) {
		try {
			if(StringUtils.isEmpty(student.getInstitution()) || StringUtils.isEmpty(student.getBranch()) ||  StringUtils.isEmpty(student.getSemester()) || StringUtils.isEmpty(student.getCourse()) || StringUtils.isEmpty(subject)) {
				model.addAttribute("message", "Cannot accept null value");
				response.setStatus(400);
			}else {
				boolean isSuccess = JDBCUtil.addSubject(student.getInstitution(),student.getCourse(), student.getBranch(), student.getSemester(), subject);
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
}
