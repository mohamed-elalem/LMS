package business;

public class BookNotFoundException extends Exception {
	

	private static final long serialVersionUID = 8825719057313677178L;

	public BookNotFoundException() {
		super();
	}
	
	public BookNotFoundException(String msg) {
		super(msg);
	}
	
	public BookNotFoundException(Throwable t) {
		super(t);
	}

}
