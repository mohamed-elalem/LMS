package business;


import java.time.LocalDate;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class CheckoutRecordControllerImpl implements CheckoutRecordController {
	
	DataAccess da = new DataAccessFacade();

	@Override
	public CheckoutRecord findCheckoutRecordByMember(String memberID) throws MemberNotFoundException {
		LibraryMember member =  da.readMemberMap().get(memberID);
		if (member == null) {
			throw new MemberNotFoundException("Member not found!");
		}
		return member.getCheckoutRecord();
		
	}

	@Override
	public CheckoutRecordEntry checkOverdue(String isbn, int copyNum) 
		throws BookNotFoundException, BookCopyNotFoundException {
		Book book = da.readBooksMap().get(isbn);
		if (book == null) {
			throw new BookNotFoundException("Book not found");
		} 
		BookCopy copy = book.getCopy(copyNum);
		if (copy == null) {
			throw new BookCopyNotFoundException("Book Copy not found");
		}
		return findEntryByBookCopy(isbn, copyNum);
//		CheckoutRecordEntry entry = findEntryByBookCopy(isbn, copyNum);
//		if (entry != null && LocalDate.now().isAfter(entry.getDueDate())) {
//			return entry;
//		}
//		return null;
	}

	private CheckoutRecordEntry findEntryByBookCopy(String isbn, int copyNum) {
		for (LibraryMember m : da.readMemberMap().values()) {
			for (CheckoutRecordEntry entry : m.getCheckoutRecord().getCheckoutRecordEntries()) {
				BookCopy copy = entry.getBookCopy();
				if (copy.getCopyNum() == copyNum && 
						copy.getBook().getIsbn().equals(isbn))
					return entry;
			}
		}
		return null;
	}

}
