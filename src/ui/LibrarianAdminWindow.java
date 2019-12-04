package ui;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LibrarianAdminWindow extends Stage implements LibWindow {
	public static final LibrarianAdminWindow INSTANCE = new LibrarianAdminWindow();
	private boolean isInitialized = false;
	
	private LibrarianAdminWindow() {
		init();
	}

	@Override
	public void init() {
		Text text = new Text("Hello world");
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(text);
		Scene scene = new Scene(stackPane);

		setTitle("Librarian Admin dashboard");
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
