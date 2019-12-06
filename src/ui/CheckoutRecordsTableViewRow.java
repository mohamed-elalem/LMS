package ui;

import business.Book;
import business.CheckoutRecordEntry;
import business.LibraryMember;

public class CheckoutRecordsTableViewRow {
	private Book book;
	private CheckoutRecordEntry checkoutRecordEntry;
	private LibraryMember member;

	public CheckoutRecordsTableViewRow(Book book, CheckoutRecordEntry checkoutRecordEntry, LibraryMember member) {
		this.book = book;
		this.checkoutRecordEntry = checkoutRecordEntry;
		this.member = member;
	}
	
	public String getMemberFullName() {
		return member.fullName();
	}
	
	public String getBookTitle() {
		return book.getTitle();
	}
	
	public String getCheckoutDate() {
		return checkoutRecordEntry.getCheckoutDate().toString();
	}
	
	public String getDueDate() {
		return checkoutRecordEntry.getDueDate().toString();
	}
	
	
}
