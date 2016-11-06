package ch.raspberryjavame.blogspot.info.system;

public class SystemInfoException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;
	private Throwable exception;
	private int errorCode;

	public SystemInfoException(String message, Throwable exception, int code) {
		this.message = message;
		this.exception = exception;
		this.errorCode = code;
	}

	public SystemInfoException(String message, Throwable exception) {
		this.message = message;
		this.exception = exception;
		this.errorCode = 0;
	}

	public SystemInfoException(String message) {
		this.message = message;
		this.errorCode = 0;
	}

	public SystemInfoException(Throwable exception) {
		this.exception = exception;
		this.message = "";
		this.errorCode = 0;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
