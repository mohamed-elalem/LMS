package business;

public interface BookController {

	void create(String isbn, String title, String authors, int maxCheckoutLength, int numCopies) 
			throws InvalidBookException;

}
