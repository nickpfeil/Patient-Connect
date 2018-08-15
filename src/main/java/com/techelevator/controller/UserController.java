package com.techelevator.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techelevator.model.Doctor;
import com.techelevator.model.DoctorDAO;
import com.techelevator.model.Patient;
import com.techelevator.model.PatientDAO;
import com.techelevator.model.User;
import com.techelevator.model.UserDAO;

@Controller
public class UserController {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private DoctorDAO doctorDAO;
	
	@Autowired
	private PatientDAO patientDAO;

	@Autowired
	public UserController(UserDAO userDAO, DoctorDAO doctorDAO, PatientDAO patientDAO) {
		this.userDAO = userDAO;
		this.doctorDAO = doctorDAO;
		this.patientDAO = patientDAO;
	}

	@RequestMapping(path="/newUser", method=RequestMethod.GET)
	public String displayNewUserForm(ModelMap modelHolder) {
		if( ! modelHolder.containsAttribute("user")) {
			modelHolder.addAttribute("user", new User());
		}
		return "newUser";
	}
	
	@RequestMapping(path="/newUser", method=RequestMethod.POST)
	public String createUser(@Valid @ModelAttribute User user, @RequestParam String profileType, BindingResult result, RedirectAttributes flash) {
		if(result.hasErrors()) {
			flash.addFlashAttribute("user", user);
			flash.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "user", result);
			return "redirect:/newUser";
		}
		
		if(profileType.equals("Doctor"))
		{
			userDAO.saveUser(user.getUserName(), user.getPassword());
			return "redirect:/doctorRegistration";
		}
		else if(profileType.equals("Patient"))
		{
			userDAO.saveUser(user.getUserName(), user.getPassword());
			return "redirect:/patientRegistration";
		}
		else return "redirect:/newUser";
	}
	
	@RequestMapping(path="/doctorRegistration", method=RequestMethod.GET)
	public String registerDoctor() {
		return "doctorRegistration";
	}
	
	@RequestMapping(path="/doctorRegistration", method=RequestMethod.POST)
	public String registerDoctor(
				@RequestParam String firstName,				
				@RequestParam String lastName,
				@RequestParam String practice,
				ModelMap modelHolder,
				RedirectAttributes flashScope  //pass a flash scope variable into save method
			) {
			Doctor newDoctor = new Doctor();
			newDoctor.setFirstName(firstName);
			newDoctor.setLastName(lastName);
			newDoctor.setPractice(practice);
			
			doctorDAO.save(newDoctor);
			modelHolder.put("doctor", newDoctor);
			
			flashScope.addFlashAttribute("message", "New doctor profile created!");
			
			return "redirect:/doctor";
	}
	
	@RequestMapping(path="/patientRegistration", method=RequestMethod.GET)
	public String registerPatient() {
		return "patientRegistration";
	}
	
	@RequestMapping(path="/patientRegistration", method=RequestMethod.POST)
	public String patientDoctor(
				@RequestParam String firstName,				
				@RequestParam String lastName,
				@RequestParam String address,
				@RequestParam String city,				
				@RequestParam String state,
				@RequestParam String zip,
				@RequestParam String email,				
				@RequestParam String phone,
				@RequestParam String insurance,
				ModelMap modelHolder,
				RedirectAttributes flashScope  //pass a flash scope variable into save method
			) {
			Patient newPatient = new Patient();
			newPatient.setFirstName(firstName);
			newPatient.setLastName(lastName);
			newPatient.setAddress(address);
			newPatient.setCity(city);
			newPatient.setState(state);
			newPatient.setZip(zip);
			newPatient.setEmail(email);
			newPatient.setPhone(phone);
			newPatient.setInsurance(insurance);
			
			patientDAO.save(newPatient);
			modelHolder.put("patient", newPatient);
			
			flashScope.addFlashAttribute("message", "New patient profile created!");
			
			return "redirect:/patient";
	}
	
	@RequestMapping(path="/patient", method=RequestMethod.GET)
	public String patientProfile() {
		return "patient";
	}
	
	@RequestMapping(path="/doctor", method=RequestMethod.GET)
	public String doctorProfile() {
		return "doctor";
	}
	
	@RequestMapping(path="/appointment", method=RequestMethod.GET)
	public String viewCalendars() {
		return "appointment";
	}
	
	
}
