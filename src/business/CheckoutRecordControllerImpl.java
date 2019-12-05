package business;


import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class CheckoutRecordControllerImpl implements CheckoutRecordController {
	
	DataAccess da = new DataAccessFacade();

	@Override
	public void printRecord(String memberID) throws MemberNotFoundException {
		LibraryMember member =  da.readMemberMap().get(memberID);
		if (member == null) {
			throw new MemberNotFoundException("Member not found!");
		}
		
		//System.out.println(member);
		
	}

}
