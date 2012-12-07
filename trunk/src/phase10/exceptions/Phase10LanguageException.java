package phase10.exceptions;

/**
 * This exception is used ing the Phase10 Game language logic to indicate
 * invalid operations
 * 
 * @author Evan Forbes
 * 
 */
public class Phase10LanguageException extends RuntimeException {

	private static final long serialVersionUID = 20121L;

	/**
	 * Default Phase10LanguageException
	 */
	public Phase10LanguageException() {
	}

	/**
	 * Phase10LanguageException with the given message
	 * 
	 * @param message
	 *            the message
	 */
	public Phase10LanguageException(String message) {
		super(message);
	}

	/**
	 * Phase10LanguageException with throwable
	 * 
	 * @param arg0
	 *            throwable
	 */
	public Phase10LanguageException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * Phase10LanguageException with a message and throwable
	 * 
	 * @param arg0
	 *            the message
	 * @param arg1
	 *            throwable
	 */
	public Phase10LanguageException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Phase10LanguageException with message, throwable
	 * 
	 * @param arg0
	 *            message
	 * @param arg1
	 *            throwable
	 * @param arg2
	 *            boolean 1
	 * @param arg3
	 *            boolean 2
	 */
	public Phase10LanguageException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
