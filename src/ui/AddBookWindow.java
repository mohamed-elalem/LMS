package ui;

import business.BookController;
import business.BookControllerImpl;
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

public class AddBookWindow extends Stage implements LibWindow {
	
	public static final AddBookWindow INSTANCE = new AddBookWindow();
	
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
	private AddBookWindow() {}
	
	TextField txtIsbn = new TextField();
	TextField txtTitle = new TextField();
	TextArea txtAuthors = new TextArea();
	TextField txtMaxCkoLength = new TextField("21");
	TextField txtCopies = new TextField("1");
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

        Text scenetitle = new Text("Add a book");
        scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); //Tahoma
        grid.add(scenetitle, 0, rowIdx++, 2, 1);

        grid.add(new Label("ISBN"), 0, rowIdx++);
		grid.add(txtIsbn, 0, rowIdx++);	
		
		grid.add(new Label("Title"), 0, rowIdx++);
		grid.add(txtTitle, 0, rowIdx++);
		
		grid.add(new Label("Authors"), 0, rowIdx++);
		grid.add(txtAuthors, 0, rowIdx++);
		
		grid.add(new Label("Max checkout length (days)"), 0, rowIdx++);
		grid.add(txtMaxCkoLength, 0, rowIdx++);
		
		grid.add(new Label("Number of copy"), 0, rowIdx++);
		grid.add(txtCopies, 0, rowIdx++);
		
		Button saveBtn = new Button("Save");
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					BookController bookCtrl = new BookControllerImpl();
					String isbn = txtIsbn.getText();
					String title = txtTitle.getText();
					int maxCheckoutLength = Integer.parseInt(txtMaxCkoLength.getText());
					int numCopies = Integer.parseInt(txtCopies.getText());
					String authors = txtAuthors.getText();
					bookCtrl.create(isbn, title, authors, maxCheckoutLength, numCopies);
					messageBar.setFill(Start.Colors.green);
             	    messageBar.setText("Saved successful");
             	    resetInfo();
             	    
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
        hBack.getChildren().addAll(saveBtn, backBtn);
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
	
	private void resetInfo() {
		txtIsbn.setText("");
		txtTitle.setText("");
		txtAuthors.setText("");
		txtMaxCkoLength.setText("21");
		txtCopies.setText("1");
		
	}

}
