package ui;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LibrarianWindow extends Stage implements LibWindow {
	public static final LibrarianWindow INSTANCE = new LibrarianWindow();
	private boolean isInitialized = false;
	
	private LibrarianWindow() {
		init();
	}

	@Override
	public void init() {
		System.out.println("R");
		Text text = new Text("Hello world");
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(text);
		Scene scene = new Scene(stackPane);
		setTitle("Librarian dashboard");
		setScene(scene);
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean isInitialized) {
		this.isInitialized = isInitialized;
	}

}
