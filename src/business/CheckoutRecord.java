package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1214744538117113769L;
	private List<CheckoutRecordEntry> entries;
	private LibraryMember member;
	CheckoutRecord(LibraryMember member) {
		entries = new ArrayList<>();
		setMember(member);
	}
	
	public List<CheckoutRecordEntry> getCheckoutRecordEntries() {
		return entries;
	}
	
	public void addCheckoutRecordEntry(CheckoutRecordEntry checkoutRecordEntry) {
		entries.add(checkoutRecordEntry);
	}

	public LibraryMember getMember() {
		return member;
	}

	public void setMember(LibraryMember member) {
		this.member = member;
	}
}
