package phase10.exceptions;

public class Phase10LanguageException extends RuntimeException {

	private static final long serialVersionUID = 20121L;

	public Phase10LanguageException() {
	}

	public Phase10LanguageException(String message) {
		super(message);
	}

	public Phase10LanguageException(Throwable arg0) {
		super(arg0);
	}

	public Phase10LanguageException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public Phase10LanguageException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
