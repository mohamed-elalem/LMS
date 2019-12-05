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
	CheckoutRecord() {
		entries = new ArrayList<>();
	}
	
	public List<CheckoutRecordEntry> getCheckoutRecordEntries() {
		return entries;
	}
	
	public void addCheckoutRecordEntry(CheckoutRecordEntry checkoutRecordEntry) {
		entries.add(checkoutRecordEntry);
	}
}
