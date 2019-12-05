package business;

public class MemberNotFoundException extends Exception {
	
	private static final long serialVersionUID = 829023475306240177L;

	public MemberNotFoundException() {
		super();
	}
	
	public MemberNotFoundException(String msg) {
		super(msg);
	}
	
	public MemberNotFoundException(Throwable t) {
		super(t);
	}

}
