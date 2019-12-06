package business;

import java.io.Serializable;
import java.util.HashMap;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private CheckoutRecord checkoutRecord;
	
	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;
		checkoutRecord = new CheckoutRecord(this);
	}
	
	public String getMemberId() {
		return memberId;
	}

	
	public CheckoutRecord getCheckoutRecord() {
		return checkoutRecord;
	}
	
	public void setCheckoutRecord(CheckoutRecord checkoutRecord) {
		this.checkoutRecord = checkoutRecord;
	}
	

	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress();
	}
	
	public static LibraryMember getMemberById(String id) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> members = da.readMemberMap();
		if (members.containsKey(id)) {
			return members.get(id);
		}
		return null;
	}
	
	
	public String fullName() {
		return getFirstName() + " " + getLastName();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
