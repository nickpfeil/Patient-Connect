package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCReviewDAO implements ReviewDAO{
		
		private JdbcTemplate jdbcTemplate;

		@Autowired
		public void DBCReviewDAO(DataSource dataSource) {
			this.jdbcTemplate = new JdbcTemplate(dataSource);
		}

//		METHOD TO DISPLAY ALL THE REVIEWS SUBMITTED
		@Override
		public List<Review> displayAllReviews(){
			List<Review> reviews = new ArrayList<>();
			String sqlDisplayAllReviews = "SELECT * " + 
										  "FROM reviews r ";
			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlDisplayAllReviews);
			
			while(results.next()) {
				reviews.add(mapRowToReview(results));
			}
			return reviews;
		}
		
//				GET DOCTOR NAME FOR SPECIFIC REVIEWS
		@Override
		public Doctor getDoctorNameByReview(Long doctorId) {
			Doctor reviewDoctor = new Doctor();
			String sqlDoctorNameByReview = "SELECT d.last_name, d.first_name " + 
					  					   "FROM doctor d " + 
					  					   "JOIN reviews r " + 
					  					   "ON d.doctor_id = r.doctor_id " +
					  					   "WHERE r.doctor_id = ?;";
			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlDoctorNameByReview, doctorId);
			while(results.next()) {
				
			}
			return reviewDoctor;
		}

		@Override
		public List<Review> searchReviewsByDoctorId(Long doctorId) {
			List<Review> reviewList = new ArrayList<>();
			String sqlSearchReviewByDoctorId = "SELECT * FROM reviews WHERE doctor_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchReviewByDoctorId, doctorId);
		
		while(results.next()){
			reviewList.add(mapRowToReview(results));
		}	
		return reviewList;
	}

//		METHOD TO SAVE A NEW REVIEW
		
		@Override
		public Long saveReview(Review review) {
			String sqlNewReview = "INSERT INTO reviews(doctor_id, user_id, review, rating) "
											 + "VALUES(?, ?, ?, ?) RETURNING review_id;";
			return jdbcTemplate.queryForObject(sqlNewReview, Long.class, review.getDoctorId(), review.getUserId(), 
												review.getReview(), review.getRating());
		}

//		METHOD TO GET ALL DOCTORS FROM THE DB TO DISPLAY IN THE SURVEY
		
		@Override
		public List<Doctor> getDoctorNames() {
			List<Doctor> allDoctors = new ArrayList<>();
			String sqlGetAllDoctors = "SELECT * FROM doctor;";
			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllDoctors);
			while(results.next()) {
				Doctor doctor = new Doctor();
				
				doctor.setDoctorId(results.getLong("doctor_id"));
				doctor.setLastName(results.getString("last_name"));
				doctor.setFirstName(results.getString("first_name"));
				
				allDoctors.add(doctor);
			}
			return allDoctors;
		}
		
//		GET DOCTOR INFO FROM DR ID
		
		@Override
		public List<Doctor> getDoctorInfoFromId(Long doctorId) {
			List<Doctor> doctorList = new ArrayList<>();
			String sqlDoctorNameByReview = "SELECT * " + 
										   "FROM doctor d " + 
										   "WHERE d.doctor_id = ?;";
			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlDoctorNameByReview, doctorId);
			while(results.next()) {
				Doctor doctor = new Doctor();
				doctor.setDoctorId(results.getLong("doctor_id"));
				doctor.setLastName(results.getString("last_name"));
				doctor.setFirstName(results.getString("first_name"));
				doctor.setPractice(results.getString("practice"));
				doctorList.add(doctor);
			}
			return doctorList;
		}
		
		private Review mapRowToReview(SqlRowSet results) {
		// TODO Auto-generated method stub
			Review review = new Review();
			review.setReviewId(results.getLong("review_id"));
			review.setDoctorId(results.getInt("doctor_id"));
			review.setUserId(results.getInt("user_id"));
			review.setReview(results.getString("review"));
			review.setRating(results.getInt("rating"));

			return review;
	}		
}


