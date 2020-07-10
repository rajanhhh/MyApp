package com.form;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bean.User;

@Controller
public class AccessController {

	@Autowired
	JDBCAccess JDBCAccess;
	
	@Autowired
	MyController MyController;
	
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public String getLoginPage(HttpSession session,HttpServletResponse response,ModelMap model) {
		try {
			if(String.valueOf(session.getAttribute("session")) == "valid") {
				response.sendRedirect("/addAttendance");
				return "loginPage";
                //return MyController.addAttendancePage(model);
			}
			return "loginPage";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "loginPage";
	}
	
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public String submitAddForm(HttpSession session,ModelMap model,User user, HttpServletResponse response) {
		try {
			if(StringUtils.isEmpty(user.getUsername()) ||  StringUtils.isEmpty(user.getPassword())) {
				model.addAttribute("errorMessage", "Cannot accept null value");
				
			}else {
				ArrayList<User> arrayList = JDBCAccess.validateUser(user.getUsername(), user.getPassword());
				if(arrayList.size() == 0) {
					model.addAttribute("errorMessage", "Invalid username or password");
					response.setStatus(400);
				}else {
					session.setAttribute("session", "valid");
					response.sendRedirect("/addAttendance");
					return "loginPage";
                   //return MyController.addAttendancePage(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "loginPage";
	}
	
	@RequestMapping(value = "/logout",method = RequestMethod.POST)
	public String logout(HttpSession session,ModelMap model) {
		try {
			session.removeAttribute("session");
			return "loginPage";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "loginPage";
	}
}
