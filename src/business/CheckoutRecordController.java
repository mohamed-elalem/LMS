package business;

public interface CheckoutRecordController {

	void printRecord(String text) throws MemberNotFoundException;

	CheckoutRecordEntry checkOverdue(String text, int parseInt) throws BookNotFoundException, BookCopyNotFoundException;

}
