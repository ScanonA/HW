package iteration1.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import iteration1.models.Review;
import iteration1.models.User;

public class ReviewRepository {
	
	public static void addReviewer(Connection conn, String email, int uploadId) throws SQLException {
		
		String query = "INSERT INTO REVIEWS (reviewer, upload_id) VALUES(?, ?)";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, email);
		stmt.setInt(2, uploadId);
		
		stmt.executeUpdate();
		stmt.close();
	}
	
	
		// FOR VIEWING REVIEWS THAT ARE RELATED TO A SPECIFIC UPLOAD (I think this is getting used when an admin sees the statuses reviewers have been assigned to a paper)
	public static ArrayList<Review> getReviewsByUploadId(Connection conn, int uploadId) throws SQLException {
		ArrayList<Review> reviews = new ArrayList<>();
		
		String query = "SELECT * FROM REVIEWS WHERE upload_id = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, uploadId);
		ResultSet result = stmt.executeQuery();
		
		while(result.next()) {
			reviews.add(new Review(result.getString(1), result.getInt(2), result.getString(3), result.getString(4)));
		}
		
		stmt.close();
		result.close();
		
		return reviews;
	}
	
	public static ArrayList<Review> getReviewsAll(Connection conn) throws SQLException {
		ArrayList<Review> reviews = new ArrayList<>();
		
		String query = "SELECT * FROM REVIEWS";
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet result = stmt.executeQuery();
		
		while(result.next()) {
			reviews.add(new Review(result.getString(1), result.getInt(2), result.getString(3), result.getString(4)));
		}
		
		stmt.close();
		result.close();
		
		return reviews;
	}
	
	// FOR VIEWING THE REVIEWS THAT A REVIEWER NEEDS TO REVIEW
	public static ArrayList<Review> getPendingReviewsByReviewer(Connection conn, String email) throws SQLException {
		ArrayList<Review> reviews = new ArrayList<>();
		
		String query = "SELECT * FROM REVIEWS WHERE reviewer = ? AND status IS NULL";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, email);
		ResultSet result = stmt.executeQuery();
		
		while(result.next()) {
			reviews.add(new Review(result.getString(1), result.getInt(2), result.getString(3), result.getString(4)));
		}
		
		stmt.close();
		result.close();
		return reviews;
	}
	
	// FOR PROVIDING FEEDBACK (in the form of a textbox) OR SETTING A STATUS FOR A REVIEW
	public static void updateFeedbackByIdAndReviewer(Connection conn, int uploadId, String email, String feedback) throws SQLException {
		String query = "UPDATE REVIEWS SET feedback = ? WHERE reviewer = ? AND upload_id = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, feedback);
		stmt.setString(2, email);
		stmt.setInt(3, uploadId);
		
		stmt.executeUpdate();
		stmt.close();
	}
	
	public static void updateStatusByIdAndReviewer(Connection conn, int uploadId, String email, String status) throws SQLException {
		String query = "UPDATE REVIEWS SET status = ? WHERE reviewer = ? AND upload_id = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, status);
		stmt.setString(2, email);
		stmt.setInt(3, uploadId);
		
		stmt.executeUpdate();
		stmt.close();
	}
	
	public static String getStatusByIdAndReviewer(Connection conn, int uploadId, String email) throws SQLException {
		String query = "SELECT * FROM REVIEWS WHERE reviewer = ? AND upload_id = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, email);
		stmt.setInt(2, uploadId);
		
		ResultSet result = stmt.executeQuery();
		String status = result.getString(4);
		stmt.close();
		return status;
	}
	
	// FOR VIEWING ALL THE REVIEWS A REVIEWER HAS REVIEWED OR NEEDS TO REVIEW (I'm not actually sure this is necessary...)
	public static ArrayList<Review> getReviewsByReviewer(Connection conn, String email) throws SQLException {
		ArrayList<Review> reviews = new ArrayList<>();
		
		String query = "SELECT * FROM REVIEWS WHERE reviewer = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, email);
		ResultSet result = stmt.executeQuery();
		
		while(result.next()) {
			reviews.add(new Review(result.getString(1), result.getInt(2), result.getString(3), result.getString(4)));
		}
		
		stmt.close();
		result.close();
		return reviews;
	}
	
	
	
	/*
	 * Get Number Of Rows In Repository
	 */
	public static Integer getNumberOfRows(Connection conn) throws SQLException {
		
		String query = "SELECT count(*) AS rowcount FROM REVIEWS";
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet result = stmt.executeQuery();
		result.next();
		int count = result.getInt("rowcount");
		
		stmt.close();
		result.close();
		
		return count;
	}
}
