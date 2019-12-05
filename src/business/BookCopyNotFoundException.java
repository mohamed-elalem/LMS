package business;

public class BookCopyNotFoundException extends Exception {
	
	private static final long serialVersionUID = -4761582546943956366L;

	public BookCopyNotFoundException() {
		super();
	}
	
	public BookCopyNotFoundException(String msg) {
		super(msg);
	}
	
	public BookCopyNotFoundException(Throwable t) {
		super(t);
	}

}
