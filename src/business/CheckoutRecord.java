package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable{
	
	private static final long serialVersionUID = 6852260191526204209L;
	
	private LibraryMember member;
	private List<CheckoutEntry> entries = new ArrayList<>();
	
	public CheckoutRecord(LibraryMember member) {
		this.member = member;
		this.member.setCheckoutRecord(this);
	}

	public LibraryMember getMember() {
		return member;
	}

	public void setMember(LibraryMember member) {
		this.member = member;
	}

	public List<CheckoutEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<CheckoutEntry> entries) {
		this.entries = entries;
	}

	@Override
	public String toString() {
		return "CheckoutRecord [member=" + member + ", entries=" + entries + "]";
	}

	public void addEntry(BookCopy copy, LocalDate checkoutDate, LocalDate dueDate) {
		// check if existing bookCopy into entries, bookCopy.isAvailable?
		CheckoutEntry entry = new CheckoutEntry(this, checkoutDate, dueDate, copy);
		entries.add(entry);
	}
	
	

}
