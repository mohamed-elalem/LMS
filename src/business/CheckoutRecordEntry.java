package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutRecordEntry implements Serializable, Comparable<CheckoutRecordEntry> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2716705831330652172L;
	private CheckoutRecord checkoutRecord;
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private BookCopy bookCopy;

	private CheckoutRecordEntry(CheckoutRecord checkoutRecord, LocalDate checkoutDate, LocalDate dueDate, BookCopy bookCopy) {
		this.setCheckoutRecord(checkoutRecord);
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.bookCopy = bookCopy;
	}
	
	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}
	
	public LocalDate getDueDate() {
		return dueDate;
	}
	
	public static CheckoutRecordEntry createCheckoutRecordEntry(CheckoutRecord checkoutRecord, LocalDate checkoutDate, LocalDate dueDate, BookCopy bookCopy) {
		return new CheckoutRecordEntry(checkoutRecord, checkoutDate, dueDate, bookCopy);
	}

	@Override
	public int compareTo(CheckoutRecordEntry arg0) {
		if (checkoutDate.isAfter(arg0.getCheckoutDate())) {
			return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "CheckoutRecordEntry [checkoutDate=" + checkoutDate + ", dueDate=" + dueDate + ", bookCopy=" + bookCopy
				+ "]";
	}

	public CheckoutRecord getCheckoutRecord() {
		return checkoutRecord;
	}

	public void setCheckoutRecord(CheckoutRecord checkoutRecord) {
		this.checkoutRecord = checkoutRecord;
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}
}
