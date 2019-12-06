package business;

public interface CheckoutRecordController {

	CheckoutRecord findCheckoutRecordByMember(String text) throws MemberNotFoundException;

	CheckoutRecordEntry checkOverdue(String text, int parseInt) throws BookNotFoundException, BookCopyNotFoundException;

}
