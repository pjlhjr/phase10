package phase10.exceptions;

/**
 * This exception is used throughout the Phase10Game logic to indicate invalid
 * operations
 * 
 * @author Evan Forbes
 * 
 */
public class Phase10Exception extends RuntimeException {

	private static final long serialVersionUID = 20121L;

	/**
	 * Default Phase10Exception
	 */
	public Phase10Exception() {
	}

	/**
	 * Phase10Exception with the given message
	 * 
	 * @param message
	 *            the error message
	 */
	public Phase10Exception(String message) {
		super(message);
	}

	/**
	 * Phase10Exception
	 * 
	 * @param arg0
	 *            Throwable argument
	 */
	public Phase10Exception(Throwable arg0) {
		super(arg0);
	}

	/**
	 * Phase10Exception with the given message and throwable
	 * 
	 * @param arg0
	 *            the message
	 * @param arg1
	 *            throwable
	 */
	public Phase10Exception(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Phase10Exception with the given message and throwable
	 * 
	 * @param arg0
	 *            message
	 * @param arg1
	 *            throwable
	 * @param arg2
	 *            boolean
	 * @param arg3
	 *            boolean
	 */
	public Phase10Exception(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
