package com.techelevator.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.techelevator.model.Review;
import com.techelevator.model.ReviewDAO;
import com.techelevator.model.User;
import com.techelevator.model.UserDAO;

@Controller
public class ReviewController {
	
	@Autowired
	private ReviewDAO reviewDAO;
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
	public ReviewController(ReviewDAO reviewDAO, UserDAO userDAO) {
		
		this.reviewDAO = reviewDAO;
		this.userDAO = userDAO;
	}
	
	@RequestMapping(path= {"/review"}, method=RequestMethod.GET)
	public String parksDropdown(ModelMap modelHolder) {
		modelHolder.put("doctorsDropdown", reviewDAO.getDoctorNames());
		return "review";
	}
	
	@RequestMapping(path= {"/review"}, method=RequestMethod.POST)
	public String postNewReview(@RequestParam int doctorId, @RequestParam int rating, @RequestParam String review, HttpSession session) {
		Review newReview = new Review();
		User sessionUser = (User)session.getAttribute("currentUser");
		
		newReview.setDoctorId(doctorId);
		newReview.setUserId((int)userDAO.getUserIdByUsername(sessionUser));
		newReview.setRating(rating);
		newReview.setReview(review);
		
		reviewDAO.saveReview(newReview);
		return "redirect:/result?doctorId="+doctorId;
	}
	
	@RequestMapping(path="/result", method=RequestMethod.POST)
	public String viewSpecificReviews(@RequestParam(required = false) Long doctorId, ModelMap docHolder, ModelMap docNameHolder, ModelMap modelHolder) {
		docHolder.put("doctorsDropdown", reviewDAO.getDoctorNames());
		docNameHolder.put("docName", reviewDAO.getDoctorInfoFromId(doctorId));
		modelHolder.put("docReviews", reviewDAO.searchReviewsByDoctorId(doctorId));
		
		return "redirect:/result?doctorId="+doctorId;
	}
	
	@RequestMapping(path="/result", method=RequestMethod.GET)
	public String viewResult(@RequestParam(required = false) Long doctorId, ModelMap docHolder, ModelMap docNameHolder, ModelMap modelHolder) {
		docHolder.put("doctorsDropdown", reviewDAO.getDoctorNames());
		docNameHolder.put("docName", reviewDAO.getDoctorInfoFromId(doctorId));
		modelHolder.put("docReviews", reviewDAO.searchReviewsByDoctorId(doctorId));
		return "result";
	}

}
