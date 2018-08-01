/**
 * Project Compile-On-The-Fly-UI
 * UnretrievableInputCodeException.java
 * 
 * Subclass of Exception.java for handling exceptions relating to unretrievable input codes
 * 
 * @author alyssa
 */

package pkgRunInputCodeUI;

@SuppressWarnings("serial")
public class UnretrievableInputCodeException extends Exception {

	public UnretrievableInputCodeException() {
		super();
	}

	public UnretrievableInputCodeException(String message) {
		super(message);
	}

	public UnretrievableInputCodeException(Throwable cause) {
		super(cause);
	}

	public UnretrievableInputCodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnretrievableInputCodeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
