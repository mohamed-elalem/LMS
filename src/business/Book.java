package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

/**
 *
 */
final public class Book implements Serializable {
	
	private static final long serialVersionUID = 6110690276685962829L;
	private BookCopy[] copies;
	private List<Author> authors;
	private String isbn;
	private String title;
	private int maxCheckoutLength;
	public Book(String isbn, String title, int maxCheckoutLength, List<Author> authors) {
		this.isbn = isbn;
		this.title = title;
		this.maxCheckoutLength = maxCheckoutLength;
		this.authors = Collections.unmodifiableList(authors);
		copies = new BookCopy[]{new BookCopy(this, 1, true)};	
	}
	
	public void updateCopies(BookCopy copy) {
		for(int i = 0; i < copies.length; ++i) {
			BookCopy c = copies[i];
			if(c.equals(copy)) {
				copies[i] = copy;
				
			}
		}
	}

	public List<Integer> getCopyNums() {
		List<Integer> retVal = new ArrayList<>();
		for(BookCopy c : copies) {
			retVal.add(c.getCopyNum());
		}
		return retVal;
		
	}
	
	public void addCopy() {
		BookCopy[] newArr = new BookCopy[copies.length + 1];
		System.arraycopy(copies, 0, newArr, 0, copies.length);
		newArr[copies.length] = new BookCopy(this, copies.length +1, true);
		copies = newArr;
	}
	
	
	@Override
	public boolean equals(Object ob) {
		if(ob == null) return false;
		if(ob.getClass() != getClass()) return false;
		Book b = (Book)ob;
		return b.isbn.equals(isbn);
	}
	
	
	public boolean isAvailable() {
		if(copies == null) {
			return false;
		}
		return Arrays.stream(copies)
				     .map(l -> l.isAvailable())
				     .reduce(false, (x,y) -> x || y);
	}

	
	public int getNumCopies() {
		return copies.length;
	}
	
	@Override
	public String toString() {
		return "Book [copies=" + Arrays.toString(copies) + ", authors=" + authors + ", isbn=" + isbn + ", title="
				+ title + ", maxCheckoutLength=" + maxCheckoutLength + "]";
	}

	public String getTitle() {
		return title;
	}
	public BookCopy[] getCopies() {
		return copies;
	}
	
	public List<Author> getAuthors() {
		return authors;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public BookCopy getNextAvailableCopy() {	
		Optional<BookCopy> optional 
			= Arrays.stream(copies)
			        .filter(x -> x.isAvailable()).findFirst();
		return optional.isPresent() ? optional.get() : null;
	}
	
	public BookCopy getCopy(int copyNum) {
		for(BookCopy c : copies) {
			if(copyNum == c.getCopyNum()) {
				return c;
			}
		}
		return null;
	}
	public int getMaxCheckoutLength() {
		return maxCheckoutLength;
	}
	
	public int getAvailableCopiesCount() {
		int availableCopy = 0;
		for (BookCopy bookCopy : copies) {
			if (bookCopy.isAvailable()) {
				availableCopy++;
			}
		}
		
		return availableCopy;
	}

	
	public static Book getBookByISBN(String isbn) {
		DataAccess da = new DataAccessFacade();
		
		HashMap<String, Book> books = da.readBooksMap();
		Book foundBook = null;

		for (String bookId : books.keySet()) {
			Book book = books.get(bookId);
			if (book.isAvailable() && book.getIsbn().equals(isbn)) {
				foundBook = book;
				break;
			}
		}
		
		return foundBook;
	}
	
	
}
