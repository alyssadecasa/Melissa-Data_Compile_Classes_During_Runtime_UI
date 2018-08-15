/**
 * Project Compile-On-The-Fly-UI
 * PlaceholderStringNotFoundException.java
 * 
 * Exception class for throwing exceptions when the placeholder String
 * in the source file is not found
 * 
 * @author alyssa
 */

package pkgRunInputCodeUI;

@SuppressWarnings("serial")
public class PlaceholderStringNotFoundException extends Exception {

	public PlaceholderStringNotFoundException() {
		super();
	}

	public PlaceholderStringNotFoundException(String message) {
		super(message);
	}

	public PlaceholderStringNotFoundException(Throwable cause) {
		super(cause);
	}

	public PlaceholderStringNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlaceholderStringNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
