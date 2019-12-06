package ui;

import business.BookCopy;
import business.CheckoutRecord;
import business.CheckoutRecordController;
import business.CheckoutRecordControllerImpl;
import business.CheckoutRecordEntry;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PrintCheckoutRecordWindow extends Stage implements LibWindow {
	
	public static final PrintCheckoutRecordWindow INSTANCE = new PrintCheckoutRecordWindow();
	
	private boolean isInitialized = false;
	
	@Override
	public boolean isInitialized() {
		return this.isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		this.isInitialized = val;
	}
	
	/* This class is a singleton */
	private PrintCheckoutRecordWindow() {}
	
	TextField txtMember = new TextField();
	TextArea txtResult = new TextArea();
	Text messageBar = new Text();

	@Override
	public void init() {
		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        int rowIdx = 0;

        Text scenetitle = new Text("Checkout record");
        scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); //Tahoma
        grid.add(scenetitle, 0, rowIdx++, 2, 1);

        grid.add(new Label("Member ID"), 0, rowIdx++);
		grid.add(txtMember, 0, rowIdx++);	
		
		 grid.add(new Label("Result"), 0, rowIdx++);
		 grid.add(txtResult, 0, rowIdx++);	
		
		Button searchBtn = new Button("Search");
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				printResultSearch(false);
			}
		});
		
		Button printBtn = new Button("Print");
		printBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				printResultSearch(true);
			}
		});
		
		Button backBtn = new Button("<= Back to Main");
        backBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		Start.hideAllWindows();
        		Start.primStage().show();
        	}
        });
        
        HBox hBack = new HBox(10);
        hBack.setAlignment(Pos.BOTTOM_LEFT);
        hBack.getChildren().addAll(searchBtn, printBtn, backBtn);
        grid.add(hBack, 0, rowIdx++);
        
       
        HBox messageBox = new HBox(10);
        messageBox.setAlignment(Pos.BOTTOM_CENTER);
        messageBox.getChildren().add(messageBar);;
        grid.add(messageBox, 0, rowIdx);
        
		Scene scene = new Scene(grid);
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
        setScene(scene);
        isInitialized(true);
		
	}

	protected void printResultSearch(boolean isPrinter) {
		try {
			CheckoutRecordController ctrl = new CheckoutRecordControllerImpl();
			CheckoutRecord checkoutRecord = ctrl.findCheckoutRecordByMember(txtMember.getText());
			if (isPrinter) {
				System.out.println(buildOutput(checkoutRecord));
			} else {
				txtResult.setText(buildOutput(checkoutRecord));
			}
			messageBar.setFill(Start.Colors.green);
     	    messageBar.setText("Member is found");
		} catch (Exception ex) {
			txtResult.setText("");
			messageBar.setFill(Start.Colors.red);
			messageBar.setText("Error! " + ex.getMessage());
		}
		
	}

	protected String buildOutput(CheckoutRecord record) {
		StringBuilder sb = new StringBuilder();
		sb.append("Member: " + record.getMember().fullName());
		sb.append("\n");
		sb.append("--------------------");
		sb.append("\n");
		sb.append("ISBN").append("\t");
		sb.append("Title").append("\t");
		sb.append("CopyNo").append("\t");
		sb.append("Checkout date").append("\t");
		sb.append("Due date").append("\n");
		sb.append("--------------------");
		sb.append("\n");
		for (CheckoutRecordEntry r : record.getCheckoutRecordEntries()) {
			BookCopy copy = r.getBookCopy();
			sb.append(copy.getBook().getIsbn()).append("\t");
			sb.append(copy.getBook().getTitle()).append("\t");
			sb.append(copy.getCopyNum()).append("\t");
			sb.append(r.getCheckoutDate()).append("\t");
			sb.append(r.getDueDate()).append("\n");
		}
		sb.append("--------------------");
		return sb.toString();
	}

}
