package userInterface;

import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import networking.Server;

public class mainMenu extends Application {
    public static void main(String[] args) {
        //launch(args);
    	launchMenu();
    }
    
    public static void launchMenu() {
    	launch();
    }
    
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Square-Off: Start Menu");
        
        Button btn = new Button("Host Game");
        btn.setMinWidth(75);
        Button btn2 = new Button("Join Game");
        btn2.setMinWidth(75);
        Button btn3 = new Button("Options");
        btn3.setMinWidth(75);
        Button btn4 = new Button("Quit Game");
        btn4.setMinWidth(75);
        
        GridPane grid = new GridPane();
        grid.setVgap(12);
        
        grid.add(btn, 0, 14);
        grid.add(btn2, 0, 15);
        grid.add(btn3, 0, 16);
        grid.add(btn4, 0, 17);
        grid.setAlignment(Pos.CENTER);
        
        
        Scene scene1 = new Scene(grid, 960, 540);
        
        btn.setOnAction( e -> { System.out.println("Hosting new lobby"); lobbyWindow(primaryStage, scene1, "Host"); } );
        btn2.setOnAction( e -> { System.out.println("Loading existing lobbies"); jgWindow(primaryStage, scene1); } );
        btn3.setOnAction( e -> { System.out.println("Opening options"); oWindow(primaryStage, scene1); } );
        btn4.setOnAction( e -> { System.out.println("Quitting Game"); System.exit(0); } );
        
        primaryStage.setScene(scene1);
        primaryStage.show();
                
    }
    
    
    public static String hostUsername() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Square-Off: Hosting Game");
		dialog.setHeaderText("Setting your player name");
		dialog.setContentText("Please enter your name:");

		Optional<String> result = dialog.showAndWait();

		if (result.isPresent() && !(result.get().isEmpty())) {
			return result.get();
		} else {
			return null;
		}
	} 
    
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void lobbyWindow(Stage primaryStage, Scene scene1, String type) {
    	ArrayList<String> playerList = new ArrayList<String>();
    	
    	if (type.equals("Host")) {
    		
    		String name = hostUsername();
    		
    		if (name==null) {
    			primaryStage.setScene(scene1);
    			primaryStage.setTitle("Square-Off: Start Menu");
    			return;
    		}
    		else {
    			playerList.add(name);
    		}
    		
    		//Server s = new Server(123);
    		mainMenuNetwork net = new mainMenuNetwork();
    		//net.runServer(s);
    		
        	primaryStage.setTitle("Square-Off: Lobby");
        	
        	Button btn5 = new Button("Back to Main Menu");
        	btn5.setMinWidth(120);
            btn5.setOnAction( e -> { System.out.println("Returning to Main Menu"); net.closeServer(new Server(123)); primaryStage.setScene(scene1); primaryStage.setTitle("Square-Off: Start Menu"); } );
            
            Button btn6 = new Button("Start Game");
        	btn6.setMinWidth(120);
            btn6.setOnAction( e -> { System.out.println("Starting Game"); net.runServer(new Server(123)); } );
            
            TableView table = new TableView();
            table.setEditable(false);
            
            TableColumn playerIDCol = new TableColumn("Player ID");
            TableColumn playerNameCol = new TableColumn("Player Name");
                    
            table.getColumns().addAll(playerIDCol, playerNameCol);
            
            HBox hbox = new HBox(12);
            hbox.getChildren().addAll(btn6, btn5);
            hbox.setAlignment(Pos.CENTER);
            
            VBox vbox = new VBox(12);
            vbox.getChildren().addAll(table, hbox);
            vbox.setAlignment(Pos.CENTER);
            
            Scene scene2 = new Scene(vbox, 960, 540);
            primaryStage.setScene(scene2);
            primaryStage.show();
    	}
    	else {
        	primaryStage.setTitle("Square-Off: Lobby");
        	
        	Button btn5 = new Button("Back to Main Menu");
        	btn5.setMinWidth(120);
            btn5.setOnAction( e -> { System.out.println("Returning to Main Menu"); primaryStage.setScene(scene1); primaryStage.setTitle("Square-Off: Start Menu"); } );
            
            TableView table = new TableView();
            table.setEditable(false);
            
            TableColumn playerIDCol = new TableColumn("Player ID");
            TableColumn playerNameCol = new TableColumn("Player Name");
                    
            table.getColumns().addAll(playerIDCol, playerNameCol);
            
            VBox vbox = new VBox(12);
            vbox.getChildren().addAll(table, btn5);
            vbox.setAlignment(Pos.CENTER);
            
            Scene scene2 = new Scene(vbox, 960, 540);
            primaryStage.setScene(scene2);
            primaryStage.show();
    	}
    }
    
    
    
    
    
    
    public static void tryToJoin(String hostName, String name, Stage primaryStage, Scene scene1) {
    	mainMenuNetwork net = new mainMenuNetwork();

    	if (name.equals("")) {
    		Alert alert;
			alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Error: Invalid Name");
			alert.setContentText("You must enter a valid name.");
			alert.setTitle("Square-Off: Joining Game");
			alert.showAndWait();
    	}
    	else {
    		if (net.connectToHost(hostName, name))
				lobbyWindow(primaryStage, scene1, "Client");
			else {
				Alert alert;
				alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("Error: Host doesn't exist");
				alert.setContentText("There is no host with the address: " + hostName);
				alert.setTitle("Square-Off: Joining Game");
				alert.showAndWait();
			}
    	}
    }
    

	public static void jgWindow(Stage primaryStage, Scene scene1) {
    	primaryStage.setTitle("Square-Off: Client");
    	
    	Label label1 = new Label("Address:");
        TextField textField = new TextField ();
        HBox hb = new HBox(10);
    	
        Label label2 = new Label("Enter your Name:");
        TextField textField2 = new TextField ();
        HBox hb2 = new HBox(10);
        
    	Button btn5 = new Button("Click to Join");
    	btn5.setMinWidth(90);
        btn5.setOnAction( e -> { System.out.println("Opening Message Box"); tryToJoin(textField.getText(), textField2.getText(), primaryStage, scene1); } );
    	
    	Button btn6 = new Button("Back to Main Menu");
    	btn6.setMinWidth(140);
        btn6.setOnAction( e -> { System.out.println("Returning to Main Menu"); primaryStage.setScene(scene1); primaryStage.setTitle("Square-Off: Start Menu"); } );
        
        hb.getChildren().addAll(label1, textField, btn5);
        hb.setAlignment(Pos.CENTER);
        
        hb2.getChildren().addAll(label2, textField2);
        hb2.setAlignment(Pos.CENTER);
        
        VBox vbox = new VBox(12);
        vbox.getChildren().addAll(hb2, hb, btn6);
        vbox.setAlignment(Pos.CENTER);
        
        GridPane grid3 = new GridPane();
        grid3.setVgap(12);
        
        Label label3 = new Label("");
        
        grid3.add(label3, 0, 0);
        grid3.add(vbox, 0, 19);
        grid3.setAlignment(Pos.CENTER);
        
        
        Scene scene3 = new Scene(grid3, 960, 540);
        primaryStage.setScene(scene3);
        primaryStage.show();
    } 

    
    
    
    public static void oWindow(Stage primaryStage, Scene scene1) {
    	primaryStage.setTitle("Square-Off: Options");
    	
    	Button btn6 = new Button("Back to Main Menu");
    	btn6.setMinWidth(120);
        
        Button btn7 = new Button("Video Options");
        btn7.setMinWidth(120);
        
        Button btn8 = new Button("Audio Options");
        btn8.setMinWidth(120);
        
        
        GridPane grid4 = new GridPane();
        grid4.setVgap(12);
        
        grid4.add(btn7, 0, 17);
        grid4.add(btn8, 0, 18);
        grid4.add(btn6, 0, 19);
        grid4.setAlignment(Pos.CENTER);
        
        
        Scene scene4 = new Scene(grid4, 960, 540);
        
        btn6.setOnAction( e -> { System.out.println("Returning to Main Menu"); primaryStage.setScene(scene1); primaryStage.setTitle("Square-Off: Start Menu"); } );
        btn7.setOnAction( e -> { System.out.println("Opening Video Options"); extraWindow(primaryStage, scene4); primaryStage.setTitle("Square-Off: Video Options"); } );
        btn8.setOnAction( e -> { System.out.println("Opening Audio Options"); extraWindow(primaryStage, scene4); primaryStage.setTitle("Square-Off: Audio Options"); } );
        
        primaryStage.setScene(scene4);
        primaryStage.show();
    }
    
    
    
    
    
    
    
    public static void extraWindow(Stage primaryStage, Scene scene1) {
    	//primaryStage.setTitle("Square-Off: Temporary");
    	
    	Button btn9 = new Button("Back to Options");
    	btn9.setMinWidth(120);
        btn9.setOnAction( e -> { System.out.println("Returning to Options"); primaryStage.setScene(scene1); primaryStage.setTitle("Square-Off: Options"); } );
        
        GridPane grid5 = new GridPane();
        grid5.setVgap(12);
        
        grid5.add(btn9, 0, 23);
        grid5.setAlignment(Pos.CENTER);
        
        
        Scene scene5 = new Scene(grid5, 960, 540);
        primaryStage.setScene(scene5);
        primaryStage.show();
    }
    
    
    
    
    
    
    
    
}