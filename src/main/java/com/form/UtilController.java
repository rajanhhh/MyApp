package com.form;

import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bean.Student;

@Controller
public class UtilController {

	@Autowired
	JDBCDemo JDBCDemo;

	@RequestMapping(value = "/getInstitutionList",method = RequestMethod.GET)
	public String getForm(ModelMap model) {
		try {
			 ArrayList<Student> arrayList= JDBCDemo.fetch();
			 ObjectMapper Obj = new ObjectMapper(); 
			 String jsonStr = Obj.writeValueAsString(arrayList);
			 model.addAttribute("formData", jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "viewDetails";
	}
}
