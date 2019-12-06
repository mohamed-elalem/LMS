package ui;

import business.CheckoutRecordController;
import business.CheckoutRecordControllerImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
		
		
		
		Button searchBtn = new Button("Search");
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					CheckoutRecordController ctrl = new CheckoutRecordControllerImpl();
					ctrl.printRecord(txtMember.getText());
					messageBar.setFill(Start.Colors.green);
             	    messageBar.setText("Printed successful");
				} catch (Exception ex) {
					messageBar.setFill(Start.Colors.red);
        			messageBar.setText("Error! " + ex.getMessage());
				}
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
        hBack.getChildren().addAll(searchBtn, backBtn);
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

}
