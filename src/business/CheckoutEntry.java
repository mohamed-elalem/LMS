package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutEntry implements Serializable {

	private static final long serialVersionUID = -3512807040363490487L;
	
	private CheckoutRecord checkoutRecord;
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private BookCopy bookCopy;
	
	public CheckoutEntry(CheckoutRecord record, LocalDate checkoutDate, LocalDate dueDate, BookCopy bookCopy) {
		this.checkoutRecord = record;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.bookCopy = bookCopy;
	}

	public CheckoutRecord getCheckoutRecord() {
		return checkoutRecord;
	}

	public void setCheckoutRecord(CheckoutRecord checkoutRecord) {
		this.checkoutRecord = checkoutRecord;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}

	public void setBookCopy(BookCopy bookCopy) {
		this.bookCopy = bookCopy;
	}

	@Override
	public String toString() {
		return "CheckoutEntry [checkoutRecord=" + checkoutRecord + ", checkoutDate=" + checkoutDate + ", dueDate="
				+ dueDate + ", bookCopy=" + bookCopy + "]";
	}
	
	
	
	

}
