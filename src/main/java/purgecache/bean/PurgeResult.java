package purgecache.bean;

public class PurgeResult {
	
	
	private int status;
	
	private String message;
	
	public PurgeResult(int status, String message) {
		this.status = status;
		this.message = message;
//		System.out.println(String.format("status %s , message %s", status, message));
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}	
