package iteration1.models;

public class Review {
	private String reviewer;
	private int uploadId;
	private String feedback;
	private String status;
	
	public Review(String reviewer, int uploadId, String feedback, String status) {
		this.reviewer = reviewer;
		this.uploadId = uploadId;
		this.feedback = feedback;
		this.status = status;
	}
	
	public String getReviewer() {
		return reviewer;
	}
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	public int getUploadId() {
		return uploadId;
	}
	public void setUploadId(int uploadId) {
		this.uploadId = uploadId;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
