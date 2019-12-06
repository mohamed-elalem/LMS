package ui;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.CheckoutRecordEntry;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CheckoutDetailsWindow extends Stage implements LibWindow {
	public static final CheckoutDetailsWindow INSTANCE = new CheckoutDetailsWindow();
	private LibraryMember member;
	private Book book;
	private boolean isInitialized = false;
	
	private CheckoutDetailsWindow() {
//		init();
	}
	@Override
	public void init() {
		VBox root = new VBox(30);
		root.setPadding(new Insets(20, 20, 20, 20));
		root.setAlignment(Pos.CENTER);
		HBox detailsLayout = new HBox(30);
		detailsLayout.setAlignment(Pos.CENTER);
		
		VBox memberDetailsLayout = new VBox(5);
		Text memberName = new Text(String.format("%s %s", member.getFirstName(), member.getLastName()));
		Text memberAddress = new Text(String.format("%s %s, %s", member.getAddress().getCity(), member.getAddress().getState(), member.getAddress().getZip()));
		Text memberPhoneNumber = new Text(member.getTelephone());
		
		memberDetailsLayout.getChildren().addAll(memberName, memberAddress, memberPhoneNumber);
		
		VBox bookDetailsLayout = new VBox(5);
		Text bookTitle = new Text(book.getTitle());
		Text bookIsbn = new Text(book.getIsbn());
		Text availableCopies = new Text(String.format("%d available copies", book.getAvailableCopiesCount()));
		Text rentDuration = new Text(String.format("%d rent days", book.getMaxCheckoutLength()));
		bookDetailsLayout.getChildren().addAll(bookTitle, bookIsbn, availableCopies, rentDuration);
		
		
		HBox actionsLayout = new HBox(20);
		actionsLayout.setAlignment(Pos.BOTTOM_CENTER);
		Button backButton = new Button(Character.toString((char) 11176) + " Back");
		backButton.setOnAction(evt -> {
			Start.hideAllWindows();
			Start.updatePrimaryStage();
			Start.primStage().show();
		});

		Button confirmButton = new Button("Confirm");
		confirmButton.setOnAction(evt -> {
			LocalDate today = LocalDate.now();
			LocalDate dueDate = today.plusDays((long) book.getMaxCheckoutLength());
			BookCopy bookCopy = book.getNextAvailableCopy();
			System.out.println(Arrays.toString(book.getCopies()));
			System.out.println(bookCopy);

			bookCopy.changeAvailability();
			CheckoutRecord memberCheckoutRecord = member.getCheckoutRecord();
			memberCheckoutRecord.addCheckoutRecordEntry(CheckoutRecordEntry.createCheckoutRecordEntry(memberCheckoutRecord, today, dueDate, bookCopy));
			Start.hideAllWindows();
			CheckoutBookWindow.INSTANCE.show();
			
			DataAccess da = new DataAccessFacade();
			HashMap<String, LibraryMember> members = da.readMemberMap();
			HashMap<String, Book> books = da.readBooksMap();
			
			members.put(member.getMemberId(), member);
			books.put(book.getIsbn(), book);
			DataAccessFacade.updateMembersData(members);
			
			DataAccessFacade.updateBooksData(books);
			
			System.out.println(bookCopy);
		});
		
		actionsLayout.getChildren().addAll(backButton, confirmButton);
		
		detailsLayout.getChildren().addAll(memberDetailsLayout, bookDetailsLayout);
		
		root.getChildren().addAll(detailsLayout,  actionsLayout);
		
		Scene scene = new Scene(root);
		setScene(scene);
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	
	public void updateView() {
		init();
	}
	
	public LibraryMember getMember() {
		return member;
	}
	public void setMember(LibraryMember member) {
		this.member = member;
	}

	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	
}
