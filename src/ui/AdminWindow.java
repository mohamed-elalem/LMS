package ui;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminWindow extends Stage implements LibWindow {
	public static final AdminWindow INSTANCE = new AdminWindow();
	private boolean isInitialized = false;
	
	private AdminWindow() {
		init();
	}

	@Override
	public void init() {
		Text text = new Text("Hello world");
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(text);
		Scene scene = new Scene(stackPane);
		setScene(scene);
		setTitle("Admin dashboard");
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
