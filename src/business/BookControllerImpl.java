package business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class BookControllerImpl implements BookController{
	
	DataAccess da = new DataAccessFacade();

	@Override
	public void create(String isbn, String title, String authorsStr, int maxCheckoutLength, int numCopies) 
		throws InvalidBookException {
		
		if (isbn.trim().isEmpty())
			throw new InvalidBookException("ISBN is not empty.");
		
		if (title.trim().isEmpty())
			throw new InvalidBookException("Title is not empty.");
		
		List<String> nameAuthors = authorsStr.isEmpty()? null : Arrays.asList(authorsStr.split("\n"));
		if (nameAuthors == null) 
			throw new InvalidBookException("A book has at least one author.");
		
		if (!(maxCheckoutLength == 7 || maxCheckoutLength == 21))
			throw new InvalidBookException("Max checkout length is 7 or 21 days.");
		
		if (numCopies <= 0)
			throw new InvalidBookException("Book has at least one copy.");
		
		List<Author> authors = new ArrayList<Author>();
		for (String name : nameAuthors) {
			String firstName = name.split(" ")[0];
			String lastName = name.replace(firstName, "").trim();
			Author author = new Author(firstName, lastName, null, null, null);
			authors.add(author);
		}
		
		Book book = new Book(isbn, title, maxCheckoutLength, authors);
		for (int i = 0; i < numCopies - 1; i++) {
			book.addCopy();
		}
		da.saveNewBook(book);
		//System.out.println(book);
	}

}
