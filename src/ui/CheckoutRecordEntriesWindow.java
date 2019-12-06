package ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import business.Book;
import business.CheckoutRecordEntry;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CheckoutRecordEntriesWindow extends Stage implements LibWindow {
	public static final CheckoutRecordEntriesWindow INSTANCE = new CheckoutRecordEntriesWindow(); 
	private boolean isInitialized = false;

	@Override
	public void init() {
		VBox root = new VBox(20);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(20, 20, 20, 20));
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> members = da.readMemberMap();
		
		List<CheckoutRecordEntry> checkoutRecordEntries = new ArrayList<>();
		for (String memberId : members.keySet()) {
			checkoutRecordEntries.addAll(members.get(memberId).getCheckoutRecord().getCheckoutRecordEntries());
		}
		
		Collections.sort(checkoutRecordEntries);
		
		TableView tableView = new TableView();
		TableColumn<String, CheckoutRecordsTableViewRow> memberNameColumn = new TableColumn<>("Member Name");
		memberNameColumn.setCellValueFactory(new PropertyValueFactory<>("memberFullName"));
		
		TableColumn<String, CheckoutRecordsTableViewRow> bookTitleColumn = new TableColumn<>("Book Title");
		bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
		
		TableColumn<String, CheckoutRecordsTableViewRow> checkoutDateColumn = new TableColumn<>("Checkout Date");
		checkoutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkoutDate"));
		
		TableColumn<String, CheckoutRecordsTableViewRow> dueDateColumn = new TableColumn<>("Due Date");
		dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
	
		tableView.getColumns().addAll(memberNameColumn, bookTitleColumn, checkoutDateColumn, dueDateColumn);
		
		for (CheckoutRecordEntry checkoutRecordEntry : checkoutRecordEntries) {
			CheckoutRecordsTableViewRow row = new CheckoutRecordsTableViewRow(checkoutRecordEntry.getBookCopy().getBook(), checkoutRecordEntry, checkoutRecordEntry.getCheckoutRecord().getMember());
			tableView.getItems().add(row);
		}
		
		Button backButton = new Button(Character.toString((char) 11176) + " Back");
		backButton.setOnAction(evt -> {
			Start.hideAllWindows();
			Start.updatePrimaryStage();
			Start.primStage().show();
		});
		
		root.getChildren().addAll(tableView, backButton);
		
		
		Scene scene = new Scene(root);
		setScene(scene);
	}
	
	public void update() {
		init();
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
	}

}
