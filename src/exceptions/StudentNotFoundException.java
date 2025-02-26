package exceptions;

public class StudentNotFoundException extends RuntimeException {
	private String message;

	public StudentNotFoundException(String message) {
		this.message = message;
	}
	public String getMessage() {
		return this.message;
	}
}
