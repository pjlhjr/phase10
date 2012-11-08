package phase10.exceptions;

public class Phase10Exception extends RuntimeException {

	private static final long serialVersionUID = 20121L;

	public Phase10Exception() {
	}

	public Phase10Exception(String message) {
		super(message);
	}

	public Phase10Exception(Throwable arg0) {
		super(arg0);
	}

	public Phase10Exception(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public Phase10Exception(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
