package exceptions;

public class StudentAlreadyRegisteredException extends RuntimeException {
	private String message;
	
	public StudentAlreadyRegisteredException(String message) {
		this.message = message;
	}
	 
	public String getMessage() {
		return this.message;
	}
}