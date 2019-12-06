package business;

public class InvalidBookException extends Exception {

	private static final long serialVersionUID = -7030041229684031543L;
	
	public InvalidBookException() {
		super();
	}
	public InvalidBookException(String msg) {
		super(msg);
	}
	public InvalidBookException(Throwable t) {
		super(t);
	}

}
