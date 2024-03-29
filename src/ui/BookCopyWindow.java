package ui;

import java.util.List;
import java.util.stream.Collectors;
import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BookCopyWindow extends Stage implements LibWindow {
	private boolean isInitialized = false;
	public static BookCopyWindow INSTANCE = new BookCopyWindow();
	private DataAccess dataAccess = new DataAccessFacade();
	
	private BookCopyWindow() {}
	
	@Override
	public void init() {
		GridPane gridPane = new GridPane();
		Label lblISBN = new Label("ISBN:");
		TextField txtISBN = new TextField();
		
		Label lblNumofCopy = new Label("NumofCopy:");
		TextField txtNumofCopy = new TextField();
		txtNumofCopy.setPrefWidth(40);
		Button btnAddCopy = new Button("Add a Copy");
		btnAddCopy.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent e) {
				String isbn = txtISBN.getText();
				int numOfCopy = 0;
				try {
					numOfCopy = Integer.parseInt(txtNumofCopy.getText());
				}catch(Exception ex) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("You must enter only numeric!");
					alert.show();
					return;
				}
				boolean found = false;
				List<Book> books = dataAccess.readBooksMap().values().stream().collect(Collectors.toList());
				for(Book book: books)
					if(book.getIsbn().equals(isbn)) {
						for(int i=0;i<numOfCopy;i++)
							book.addCopy();
						found = true;
						break;
					}
				if(!found) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Not Found!");
					alert.show();
					return;
				}
				dataAccess.saveBooks(books);
				
				dataAccess.readBooksMap().values().
				forEach(book -> System.out.println(book.toString()));
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setContentText("A Book Copy Saved!");
				alert.show();
				txtISBN.clear(); txtNumofCopy.clear(); txtISBN.requestFocus();
			}
		});
		
		gridPane.add(lblISBN, 0, 0);
		gridPane.add(txtISBN, 1, 0);
		gridPane.add(lblNumofCopy, 2, 0);
		gridPane.add(txtNumofCopy, 3, 0);
		gridPane.add(btnAddCopy, 4, 0);
		setTitle("Add Book Copy");
		setScene(new Scene(gridPane));
	}

	public boolean isInitialized() {
		return isInitialized;
	}
	
	public void isInitialized(boolean val) {
		isInitialized = val;
	}

}
