package ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import business.ControllerInterface;
import business.SystemController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class Start extends Application {
	private static List<MenuItem> librarianMenuItems = new ArrayList<>();
	private static List<MenuItem> adminMenuItems = new ArrayList<>();
	private static List<MenuItem> loggedInUser = new ArrayList<>();

	public static void main(String[] args) {
		launch(args);
	}
	private static Stage primStage = null;
	public static Stage primStage() {
		return primStage;
	}
 	
	public static void setMenuItemVisibilityStatus(List<MenuItem> items, boolean visible) {
		for (MenuItem item : items) {
			item.setDisable(!visible);
		}
	}
	
	public static void updatePrimaryStage() {		
		setMenuItemVisibilityStatus(adminMenuItems, false);
		setMenuItemVisibilityStatus(librarianMenuItems, false);
		setMenuItemVisibilityStatus(loggedInUser, false);

		if (SystemController.currentAuth != null) {
			setMenuItemVisibilityStatus(loggedInUser, true);
			primStage.setTitle("Main Page - " + SystemController.currentAuth.toString());
			
			switch (SystemController.currentAuth) {
			case LIBRARIAN:
				setMenuItemVisibilityStatus(librarianMenuItems, true);
				break;
			case ADMIN:
				setMenuItemVisibilityStatus(adminMenuItems, true);
				break;
			case BOTH:
				setMenuItemVisibilityStatus(librarianMenuItems, true);
				setMenuItemVisibilityStatus(adminMenuItems, true);
				for (MenuItem menuItem : librarianMenuItems) {
					menuItem.setDisable(false);
				}
				
				for (MenuItem menuItem : adminMenuItems) {
					menuItem.setDisable(false);
				}
			}
		} else {
			primStage().setTitle("Main Page");
			for (MenuItem menuItem : librarianMenuItems) {
				menuItem.setDisable(true);
			}
			
			for (MenuItem menuItem : adminMenuItems) {
				menuItem.setDisable(true);
			}
		}
	}

	public static class Colors {
		static Color green = Color.web("#034220");
		static Color red = Color.FIREBRICK;
	}
	
	private static Stage[] allWindows = { 
		LoginWindow.INSTANCE,
		AllMembersWindow.INSTANCE,	
		AllBooksWindow.INSTANCE,
		CheckoutBookWindow.INSTANCE,
		CheckoutDetailsWindow.INSTANCE,
		CheckoutRecordEntriesWindow.INSTANCE
	};
	
	public static void hideAllWindows() {
		primStage.hide();
		for(Stage st: allWindows) {
			st.hide();
		}
	}
	
	public static void showPrimaryStageOnly() {
		hideAllWindows();
		updatePrimaryStage();
		primStage().show();
	}
	
	@Override
	public void start(Stage primaryStage) {
		primStage = primaryStage;
		primaryStage.setTitle("Main Page");
				
		VBox topContainer = new VBox();
		topContainer.setId("top-container");
		MenuBar mainMenu = new MenuBar();
		VBox imageHolder = new VBox();
		Image image = new Image("ui/library.jpg", 400, 300, false, false);

        // simply displays in ImageView the image as is
        ImageView iv = new ImageView();
        iv.setImage(image);
        imageHolder.getChildren().add(iv);
        imageHolder.setAlignment(Pos.CENTER);
        HBox splashBox = new HBox();
        Label splashLabel = new Label("The Library System");
        splashLabel.setFont(Font.font("Trajan Pro", FontWeight.BOLD, 30));
        splashBox.getChildren().add(splashLabel);
        splashBox.setAlignment(Pos.CENTER);
		
		topContainer.getChildren().add(mainMenu);
		topContainer.getChildren().add(splashBox);
		topContainer.getChildren().add(imageHolder);
		
		Menu optionsMenu = new Menu("Options");
		MenuItem login = new MenuItem("Login");
		
		login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	hideAllWindows();
    			if(!LoginWindow.INSTANCE.isInitialized()) {
    				LoginWindow.INSTANCE.init();
    			}
    			LoginWindow.INSTANCE.clear();
    			LoginWindow.INSTANCE.show();
            }
        });			
							
		MenuItem bookIds = new MenuItem("All Book Ids");
		bookIds.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
				hideAllWindows();
				if(!AllBooksWindow.INSTANCE.isInitialized()) {
					AllBooksWindow.INSTANCE.init();
				}
				ControllerInterface ci = new SystemController();
				List<String> ids = ci.allBookIds();
				Collections.sort(ids);
				StringBuilder sb = new StringBuilder();
				for(String s: ids) {
					sb.append(s + "\n");
				}
				AllBooksWindow.INSTANCE.setData(sb.toString());
				AllBooksWindow.INSTANCE.show();
            }
		});
		
		MenuItem memberIds = new MenuItem("All Member Ids");
		memberIds.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
				hideAllWindows();
				if(!AllMembersWindow.INSTANCE.isInitialized()) {
					AllMembersWindow.INSTANCE.init();
				}
				ControllerInterface ci = new SystemController();
				List<String> ids = ci.allMemberIds();
				Collections.sort(ids);
				System.out.println(ids);
				StringBuilder sb = new StringBuilder();
				for(String s: ids) {
					sb.append(s + "\n");
				}
				System.out.println(sb.toString());
				AllMembersWindow.INSTANCE.setData(sb.toString());
				AllMembersWindow.INSTANCE.show();
            }
		});	
		optionsMenu.getItems().addAll(login, bookIds, memberIds);
		
		Menu librarianMenu = new Menu("Librarian");
		MenuItem checkoutBookMenuItem = new MenuItem("Checkout a Book");
		MenuItem viewCheckoutsMenuItem = new MenuItem("View Checkouts");
		
		librarianMenuItems.add(checkoutBookMenuItem);
		librarianMenuItems.add(viewCheckoutsMenuItem);

		checkoutBookMenuItem.setOnAction(evt -> {
			hideAllWindows();
			CheckoutBookWindow.INSTANCE.show();
		});
		
		viewCheckoutsMenuItem.setOnAction(evt -> {
			hideAllWindows();
			CheckoutRecordEntriesWindow.INSTANCE.update();
			CheckoutRecordEntriesWindow.INSTANCE.show();
		});

		librarianMenu.getItems().addAll(checkoutBookMenuItem, viewCheckoutsMenuItem);

		Menu actionsMenu = new Menu("Actions");
		MenuItem logoutMenuItem = new MenuItem("Logout");
		
		loggedInUser.add(logoutMenuItem);
		
		logoutMenuItem.setOnAction(evt -> {
			SystemController.removeAuth();
			hideAllWindows();
			updatePrimaryStage();
			primStage().show();
		});
		
		actionsMenu.getItems().add(logoutMenuItem);
		
		mainMenu.getMenus().addAll(optionsMenu, librarianMenu, actionsMenu);

		Scene scene = new Scene(topContainer, 420, 375);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		primaryStage.show();
		
		updatePrimaryStage();
	}
	
}
