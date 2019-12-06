package ui;

import java.util.Collection;
import java.util.stream.Collectors;
import business.Address;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LibraryMemberWindow extends Stage implements LibWindow {
	private boolean isInitialized = false;
	public static LibraryMemberWindow INSTANCE = new LibraryMemberWindow();
	private DataAccess dataAccess = new DataAccessFacade();
	private TableView<LibraryMember> tableView = new TableView<LibraryMember>();
	private TextField txtMemberID, txtFirstname, txtLastname, 
	txtStreet, txtCity, txtState, txtZip, txtPhoneNumber, txtSearch;
	
	private LibraryMemberWindow() {}
	@Override
	public void init() {
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10));
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		
		Label lblMemberID = new Label("MemberID:");
		txtMemberID = new TextField(); txtMemberID.setId("MemberID");
		gridPane.add(lblMemberID, 0, 0);
		gridPane.add(txtMemberID, 1, 0);
		
		Label lblFirstname = new Label("Firstname:");
		txtFirstname = new TextField(); txtFirstname.setId("Firstname");
		gridPane.add(lblFirstname, 0, 1);
		gridPane.add(txtFirstname, 1, 1);
		
		Label lblLastname = new Label("Lastname:");
		txtLastname = new TextField(); txtLastname.setId("Lastname");
		gridPane.add(lblLastname, 0, 2);
		gridPane.add(txtLastname, 1, 2);
		
		Label lblStreet = new Label("Street:");
		txtStreet = new TextField(); txtStreet.setId("Street");
		gridPane.add(lblStreet, 0, 3);
		gridPane.add(txtStreet, 1, 3);
		
		Label lblCity = new Label("City:");
		txtCity = new TextField(); txtCity.setId("City");
		gridPane.add(lblCity, 0, 4);
		gridPane.add(txtCity, 1, 4);
		
		Label lblState = new Label("State:");
		txtState = new TextField(); txtState.setId("State");
		gridPane.add(lblState, 0, 5);
		gridPane.add(txtState, 1, 5);
		
		Label lblZip = new Label("Zip:");
		txtZip = new TextField(); txtZip.setId("Zip");
		gridPane.add(lblZip, 0, 6);
		gridPane.add(txtZip, 1, 6);
		
		Label lblPhoneNumber = new Label("Phone Number:");
		txtPhoneNumber = new TextField(); txtPhoneNumber.setId("Phone Number");
		gridPane.add(lblPhoneNumber, 0, 7);
		gridPane.add(txtPhoneNumber, 1, 7);
		
		HBox hBoxButton = new HBox(10);
		Button btnNew = new Button("New");
		btnNew.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Clear & Set Focus
				clearData();
			}
		});
		Button btnSave = new Button("Save");
		btnSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				//Validation
				if(!validateComponent(new TextField[]{txtMemberID,
						txtFirstname, txtLastname, txtStreet, txtCity, 
						txtState, txtZip, txtPhoneNumber})) return;
				
				String memberId = txtMemberID.getText();
				String fname = txtFirstname.getText();
				String lname = txtLastname.getText();
				String tel = txtPhoneNumber.getText();
				
				//Address
				String street = txtStreet.getText();
				String city = txtCity.getText();
				String state = txtState.getText();
				String zip = txtZip.getText();
				Address add = new Address(street, city, state, zip);
				LibraryMember member = new LibraryMember(memberId, fname, lname, tel, add);
				dataAccess.saveNewMember(member);
				
				populateData(dataAccess.readMemberMap().values());
				clearData();
				
				//Alert Message
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setContentText("Library Member Saved!");
				alert.show();
			}
		});
		Button btnSearch = new Button("Search");
		btnSearch.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String search = txtSearch.getText();
				Collection<LibraryMember> members = dataAccess.readMemberMap().values();
				if(!search.isEmpty())
					members = members.stream().
							filter(member -> member.getMemberId().equals(search)).
							collect(Collectors.toList());
				populateData(members);
			}
		});
		hBoxButton.getChildren().addAll(btnSearch, btnNew, btnSave);
		hBoxButton.setAlignment(Pos.BOTTOM_RIGHT);
		
		HBox hBoxSearch = new HBox();
		Label lblSearch = new Label("Search By ID");
		txtSearch = new TextField(); 
		hBoxSearch.getChildren().addAll(lblSearch, txtSearch);
		hBoxSearch.setAlignment(Pos.BOTTOM_LEFT);
		gridPane.add(hBoxSearch, 0, 8);
		gridPane.add(hBoxButton, 1, 8);
		
		//TableView
		populateData(dataAccess.readMemberMap().values());
		
		gridPane.add(tableView, 0, 9, 2, 1);
		setTitle("Library Member");
		setScene(new Scene(gridPane));
		//isInitialized(true);
	}
	
	public boolean isInitialized() {
		return isInitialized;
	}
	
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	
	private void populateData(Collection<LibraryMember> members) {
		ObservableList<LibraryMember> member = FXCollections.observableArrayList(members);
		tableView.getColumns().clear(); tableView.getItems().clear();
		tableView.getColumns().addAll(getMemberIDColumn(), getFirstnameColumn(),
				getLastnameColumn(), getTelColumn(), getAddressColumn());
		tableView.getItems().addAll(member);
		tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
	}
	
	private void clearData() {
		txtMemberID.clear();
		txtFirstname.clear();
		txtLastname.clear();
		txtStreet.clear();
		txtCity.clear();
		txtState.clear();
		txtZip.clear();
		txtPhoneNumber.clear();
		txtSearch.clear();
		txtMemberID.requestFocus();
	}
	
	//Table Columns
	public TableColumn<LibraryMember, String> getMemberIDColumn() {
        TableColumn<LibraryMember, String> streetCol = new TableColumn<>("MemberID");
        PropertyValueFactory<LibraryMember, String> streetCellValueFactory = new PropertyValueFactory<>("memberId");
        streetCol.setCellValueFactory(streetCellValueFactory);
        return streetCol;
    } 
	
	public TableColumn<LibraryMember, String> getFirstnameColumn() {
        TableColumn<LibraryMember, String> streetCol = new TableColumn<>("Firstname");
        PropertyValueFactory<LibraryMember, String> streetCellValueFactory = new PropertyValueFactory<>("firstName");
        streetCol.setCellValueFactory(streetCellValueFactory);
        return streetCol;
    } 
	
	public TableColumn<LibraryMember, String> getLastnameColumn() {
        TableColumn<LibraryMember, String> streetCol = new TableColumn<>("Lastname");
        PropertyValueFactory<LibraryMember, String> streetCellValueFactory = new PropertyValueFactory<>("lastName");
        streetCol.setCellValueFactory(streetCellValueFactory);
        return streetCol;
    } 
	
	public TableColumn<LibraryMember, String> getTelColumn() {
        TableColumn<LibraryMember, String> streetCol = new TableColumn<>("Tel");
        PropertyValueFactory<LibraryMember, String> streetCellValueFactory = new PropertyValueFactory<>("telephone");
        streetCol.setCellValueFactory(streetCellValueFactory);
        return streetCol;
    }
	
	public TableColumn<LibraryMember, Address> getAddressColumn() {
        TableColumn<LibraryMember, Address> streetCol = new TableColumn<>("Address");
        PropertyValueFactory<LibraryMember, Address> streetCellValueFactory = new PropertyValueFactory<>("address");
        streetCol.setCellValueFactory(streetCellValueFactory);
        return streetCol;
    }
	
	private boolean validateComponent(TextField[] txts) {
		for(TextField txt: txts)
			if(txt.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText(txt.getId() + " cannot be empty!");
				alert.show();
				txt.requestFocus();
				return false;
			}
		return true;
	}

}
