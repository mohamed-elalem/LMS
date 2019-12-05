package ui;

import business.CheckoutEntry;
import business.CheckoutRecordController;
import business.CheckoutRecordControllerImpl;
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

public class DetermineOverdueWindow extends Stage implements LibWindow {
	
	public static final DetermineOverdueWindow INSTANCE = new DetermineOverdueWindow();
	
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
	private DetermineOverdueWindow() {}
	
	TextField txtIsbn = new TextField();
	TextField txtcopy = new TextField("1");
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

        grid.add(new Label("Book ISBN"), 0, rowIdx++);
		grid.add(txtIsbn, 0, rowIdx++);	
		
		grid.add(new Label("Book copy"), 0, rowIdx++);
		grid.add(txtcopy, 0, rowIdx++);	
		
		grid.add(new Label("Result"), 0, rowIdx++);
		grid.add(txtResult, 0, rowIdx++);	
		
		Button searchBtn = new Button("Search");
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					CheckoutRecordController ctrl = new CheckoutRecordControllerImpl();
					CheckoutEntry rs = ctrl.checkOverdue(txtIsbn.getText(), Integer.parseInt(txtcopy.getText()));
					if (rs != null) {
						messageBar.setFill(Start.Colors.red);
						messageBar.setText("Overdue!");
					} else {
						messageBar.setFill(Start.Colors.green);
						messageBar.setText("Not yet");
					}
             	    
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
