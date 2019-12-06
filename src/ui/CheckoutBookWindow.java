package ui;

import business.Book;
import business.LibraryMember;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CheckoutBookWindow extends Stage implements LibWindow {
	public static final CheckoutBookWindow INSTANCE = new CheckoutBookWindow();
	public boolean isInitialized = false;
	
	private CheckoutBookWindow() {
		init();
	}

	@Override
	public void init() {
		VBox root = new VBox(20);
		root.setPadding(new Insets(15, 15, 15, 15));
		root.setAlignment(Pos.CENTER);
		
		Label memberIdLabel = new Label("User ID");
		TextField memberIdTextField = new TextField();
		
		GridPane memberIdGridPane = new GridPane();
		memberIdGridPane.setVgap(10);
		
		memberIdGridPane.add(memberIdLabel, 0, 0);
		memberIdGridPane.add(memberIdTextField, 0, 1, 2, 1);
		
		Label isbnLabel = new Label("ISBN");
		TextField isbnTextField = new TextField();
		
		GridPane isbnGridPane = new GridPane();
		isbnGridPane.setVgap(10);
		
		isbnGridPane.add(isbnLabel, 0, 0);
		isbnGridPane.add(isbnTextField, 0, 1, 2, 1);
		
		Text errorMessageText = new Text();
		errorMessageText.setFill(Color.FIREBRICK);
		
		Button searchButton = new Button("Checkout");
		
		searchButton.setOnAction(evt -> {
			String memberId = memberIdTextField.getText().trim();
			LibraryMember member = LibraryMember.getMemberById(memberId);
			String isbn = isbnTextField.getText().trim();
			Book book = Book.getBookByISBN(isbn);
			
			if (member == null) {
				errorMessageText.setText("Member not found");
			} else if (book == null) {
				errorMessageText.setText("Book is not available");
			} else {
				CheckoutDetailsWindow.INSTANCE.setBook(book);
				CheckoutDetailsWindow.INSTANCE.setMember(member);
				
				Start.hideAllWindows();
				CheckoutDetailsWindow.INSTANCE.updateView();
				CheckoutDetailsWindow.INSTANCE.show();				
			}
		});
		
		Button backButton = new Button(Character.toString((char) 11176) + " Back");
		
		backButton.setOnAction(evt -> {
			Start.showPrimaryStageOnly();
		});
		
		HBox actionsLayout = new HBox(20);
		actionsLayout.getChildren().addAll(backButton, searchButton);
		root.getChildren().addAll(memberIdGridPane, isbnGridPane, errorMessageText, actionsLayout);
		Scene scene = new Scene(root);
		setTitle("Checkout a book");
		setScene(scene);
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		this.isInitialized = val;
	}

}
