/**
 * The mainMenu class is the initial menu a player will interact with.
 * They can host or join a game from here, as well as change any options required.
 * @author ksk523
 */

package UserInterface;

import Audio.Audio;
import Graphics.SplashSplash;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.Random;

public class mainMenu extends Application {
	static int width = 960;
	static int height = 540;
	static Audio a = new Audio();
	static Stage ps;
	static Scene ogScene;
	static Stage temps;
	static boolean isHidden = false;
	static boolean inLobby = false;
	static int aiDifficulty = 1;
	static String map = "Battleground";
	
	/**
	 * Main method for local testing of code, will be remove in final release
	 * @param args arguments passed when running main method (none of which are used)
	 */
    public static void main(String[] args) {
    	launchMenu();
    }
    
    /**
     * Prints when program is terminated - used for debugging
     * If it prints and the program is no terminated, there is a bug of some sort
     */
    @Override
    public void stop() {
        System.exit(1);
    }
    
    /**
     * Method to hide the UI at anytime
     */
    public static void hideUI() {
    	if ( !isHidden ) {
        	ps.setOnHiding( e -> {  temps = new Stage();
			temps.initStyle(StageStyle.UTILITY);
			temps.setMaxHeight(0);
			temps.setMaxWidth(0);
			temps.setX(Double.MAX_VALUE);
			temps.toBack();
			temps.show();
        	}  );
        	ps.hide();
    		isHidden = true;
    	}
    }
    
    /**
     * Method to show the UI at anytime
     */
    public static void showUI() {
    	if ( isHidden ) {
        	temps.setOnHiding( e -> {  ps.show(); ps.toFront(); }  );
        	temps.hide();
    		isHidden = false;
    		////System.err.println("show ui ran");
    	}
    	////System.err.println("show ui did nothing");
    }
    
    /**
     * Shows the splashscreen before running the menu UI
     * This method starts the actual menu of the game - the first method called
     */
    public static void launchMenu() {
    	SplashSplash splashscreen = new SplashSplash(1000);
		splashscreen.showSplash();
    	launch();
    }
    
    /**
     * This method creates and shows the initial menu the player interacts with
     */
    public void start(Stage primaryStage) throws Exception {
    	ps = primaryStage;
    	ps.setTitle("Square-Off: Start Menu");
    	
    	//ps.initStyle(StageStyle.UNDECORATED);
        
        Button btn = new Button("Host Game");
        btn.setMinWidth(100);
        Button btn2 = new Button("Join Game");
        btn2.setMinWidth(100);
        Button btn5 = new Button("Help");
        btn5.setMinWidth(100);
        Button btn4 = new Button("Quit Game");
        btn4.setMinWidth(100);
								
        ImageView logo = new ImageView( new Image("File:Files/Images/mainmenuimages/title.png") );
        ImageView square1 = new ImageView( new Image("File:Files/Images/mainmenuimages/redsquare.png", 100, 100, true, true) );
        ImageView square2 = new ImageView( new Image("File:Files/Images/mainmenuimages/greensquare.png", 100, 100, true, true) );
        ImageView square3 = new ImageView( new Image("File:Files/Images/mainmenuimages/bluesquare.png", 100, 100, true, true) );
        ImageView square4 = new ImageView( new Image("File:Files/Images/mainmenuimages/yellowsquare.png", 100, 100, true, true) );
        
        Label label = new Label("                                 ");
        Label label2 = new Label("\n\n");
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(btn, btn2, btn5, btn4);
        vbox.setSpacing(12);
        vbox.setAlignment(Pos.CENTER);
        
        GridPane grid = new GridPane();
        
        grid.add(label, 0, 0);
        grid.add(label2, 2, 0);
        grid.add(logo, 2, 1);
        grid.add(square1, 1, 1);
        grid.add(square2, 3, 1);
        grid.add(square3, 1, 3);
        grid.add(square4, 3, 3);
        grid.add(vbox, 2, 2);
        
        
        grid.setStyle("-fx-background-color: transparent;");
        
        ogScene = new Scene(grid, width, height, Color.LIGHTBLUE);
        
        btn.setOnAction( e -> { a.click(); hostLobby(); } ); //lobbyWindow("Host", (new mainMenuNetwork()) ); } );
        btn2.setOnAction( e -> { a.click(); jgWindow(); } );
        btn5.setOnAction( e -> { a.click(); helpWindow(); } );
        btn4.setOnAction( e -> { a.click(); stop(); } );
        
        ps.setScene(ogScene);
        ps.show();
                
    }
    
    /**
     * This method is used to get the name of the host
     * @return a string of the host's name or null if nothing is entered/player presses the cancel button
     */
    public static String hostUsername() {
    	while (true) {
    		TextInputDialog dialog = new TextInputDialog();
    		dialog.setTitle("Square-Off: Hosting Game");
    		dialog.setHeaderText("Setting your user name");
    		dialog.setContentText("Please enter your name:");

    		Optional<String> result = dialog.showAndWait();
    		a.click();

    		if (result.isPresent() && !(result.get().isEmpty())) {
    			if ( result.get().matches("^([A-Za-z]|[0-9])+$") && (result.get().length() < 62) ) {
    				return result.get();
    			}
    			else if (result.get().length() > 61) {
    	    		Alert alert;
    				alert = new Alert(AlertType.WARNING);
    				alert.setHeaderText("Error: Name too long");
    				alert.setContentText("The maximum name length is 61 alphanumeric characters");
    				alert.setTitle("Square-Off: Hosting Game");
    				alert.showAndWait();
    				a.click();
    			}
    			else {
    	    		Alert alert;
    				alert = new Alert(AlertType.WARNING);
    				alert.setHeaderText("Error: Invalid Characters");
    				alert.setContentText("Your name can only contain alphanumeric characters");
    				alert.setTitle("Square-Off: Hosting Game");
    				alert.showAndWait();
    				a.click();
    			}
    			
    		}
    		else {
    			return null;
    		}
    	}
	} 
    
    public static String mapChoice() {
    	List<String> choices = new ArrayList<>();
    	choices.add("Battleground");
    	choices.add("Pyramid");
    	choices.add("X");
    	choices.add("Smile");
    	choices.add("Sandwich");
    	choices.add("No Hiding");
    	choices.add("Pot luck");

    	ChoiceDialog<String> dialog = new ChoiceDialog<>("Battleground", choices);
    	dialog.setTitle("Square-Off: Map Selection");
    	dialog.setHeaderText("Selecting a Map to play on");
    	dialog.setContentText("Please choose a map:");

    	// Traditional way to get the response value.
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()) {
    		String mapChoice = null;
    		switch(result.get()){
    		case "Battleground": mapChoice = "Battleground"; break;
    		case "Pyramid": mapChoice = "Pyramid"; break;
    		case "X": mapChoice = "X"; break;
    		case "Smile": mapChoice = "Smile"; break;
    		case "Sandwich": mapChoice = "Sandwich"; break;
    		case "No Hiding": mapChoice = "No Hiding"; break;
    		case "Pot luck": mapChoice = "Pot luck"; break;
    		
    		default: System.err.println("Error selecting maps in MainMenu"); break;
    		}
    		return mapChoice;
    	}
    	else {
    		return null;
    	}
    }
    
    /**
     * This method creates the lobby for the host (one with a start button)
     * It is run whenever a new player joins the lobby - refreshing the lobby
     * @param net class which handles the networking of the player (both server and client sides)
     */
    @SuppressWarnings({ "rawtypes" })
    public static void refreshHLobby(mainMenuNetwork net) {
    	ps.setTitle("Square-Off: Lobby");
    	
    	Button btn5 = new Button("Back to Main Menu");
    	btn5.setMinWidth(120);
        btn5.setOnAction( e -> { a.click(); inLobby=false; net.closeServer(); showUI(); ps.setScene(ogScene); ps.setTitle("Square-Off: Start Menu"); } );
        
        Button btn6 = new Button("Start Game");
    	btn6.setMinWidth(120);
        btn6.setOnAction( e -> { a.click(); net.startGame(); } );
        
        TableView table = lobbyTable(net);
        
        final ToggleGroup group = new ToggleGroup();
        
        RadioButton rb1 = new RadioButton("Easy AI");
        rb1.setToggleGroup(group);
        RadioButton rb2 = new RadioButton("Normal AI");
        rb2.setToggleGroup(group);
        RadioButton rb3 = new RadioButton("Hard AI");
        rb3.setToggleGroup(group);
        
        if (aiDifficulty == 1)
        	rb1.setSelected(true);
        else if (aiDifficulty == 2)
        	rb2.setSelected(true);
        else
        	rb3.setSelected(true);
        
        net.setAIDifficulty(aiDifficulty);
        
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                Toggle old_toggle, Toggle new_toggle) {
                    if (group.getSelectedToggle() != null) {
                        if (rb1.isSelected())
                        	aiDifficulty = 1;
                        else if (rb2.isSelected())
                        	aiDifficulty = 2;
                        else
                        	aiDifficulty = 3;
                    }                
                }
        });
        
        btn6.requestFocus();
        
        Label label1 = new Label("AI Difficulty:");
        
        HBox hbox2 = new HBox();
        VBox vbox2 = new VBox();
        
        vbox2.getChildren().add(label1);
        vbox2.getChildren().add(rb1);
        vbox2.getChildren().add(rb2);
        vbox2.getChildren().add(rb3);
        vbox2.setSpacing(10);

        hbox2.getChildren().add(vbox2);
        hbox2.setSpacing(50);
        hbox2.setPadding(new Insets(20, 10, 10, 20));
        
        VBox vbox4 = new VBox();
        vbox4.getChildren().addAll(hbox2);
        //vbox4.setSpacing(50);
        //vbox4.setPadding(new Insets(20, 10, 10, 20));
        
        HBox hbox = new HBox(12);
        hbox.getChildren().addAll(btn6, btn5);
        hbox.setAlignment(Pos.CENTER);
        
        VBox vbox = new VBox(12);
        vbox.getChildren().addAll(table, hbox);
        vbox.setMaxWidth(710);
        vbox.setAlignment(Pos.CENTER);
        
        
        GridPane grid3 = new GridPane();
        
        grid3.setAlignment(Pos.CENTER);
        grid3.setStyle("-fx-background-color: transparent;");
        
        Label label3 = new Label("");
        
        grid3.add(label3, 1, 0);
        grid3.add(vbox, 0, 0);
        grid3.add(vbox4, 2, 0);
        //grid3.add(hbox3, 2, 0);
        grid3.setAlignment(Pos.CENTER);
        
        ps.setOnCloseRequest( e -> net.closeServer() ); 
        
        Scene scene2 = new Scene(grid3, width, height, Color.LIGHTBLUE);
        ps.setScene(scene2);
        ps.show();
    }
  
    /**
     * This method creates the lobby for the client (one without a start button)
     * It is run whenever a new player joins the lobby - refreshing the lobby
     * @param net class which handles the networking of the player (just the client side)
     */
    @SuppressWarnings({ "rawtypes" })
    public static void refreshCLobby(mainMenuNetwork net) {
    	ps.setTitle("Square-Off: Lobby");
    	
    	Button btn5 = new Button("Back to Main Menu");
    	btn5.setMinWidth(120);
        btn5.setOnAction( e -> { a.click(); inLobby=false; net.Disconnect(); ps.setScene(ogScene); ps.setTitle("Square-Off: Start Menu"); } );
        
        TableView table = lobbyTable(net);
        
        HBox hbox = new HBox(12);
        hbox.getChildren().addAll(btn5);
        hbox.setAlignment(Pos.CENTER);
        
        VBox vbox = new VBox(12);
        vbox.getChildren().addAll(table, hbox);
        vbox.setMaxWidth(710);
        vbox.setAlignment(Pos.CENTER);

        GridPane grid3 = new GridPane();
        
        Label label3 = new Label("");
        
        grid3.add(label3, 1, 0);
        grid3.add(vbox, 0, 0);
        grid3.setAlignment(Pos.CENTER);
        
        grid3.setStyle("-fx-background-color: transparent;");
        
        ps.setOnCloseRequest( e -> net.Disconnect() ); 
        
        Scene scene2 = new Scene(grid3, width, height, Color.LIGHTBLUE);
        ps.setScene(scene2);
        ps.show();
    } 

    
    
    /**
     * This method is run when entering a lobby
     * It determines whether you're a host or client and sets up the lobby accordingly
     */
	public static void hostLobby() {
		String name = hostUsername();
    		

    	if (name==null) {
    		ps.setScene(ogScene);
    		ps.setTitle("Square-Off: Start Menu");
    		return;
    	}
    	
    	map = mapChoice();
    	
    	if (map==null) {
    		ps.setScene(ogScene);
    		ps.setTitle("Square-Off: Start Menu");
    		return;
    	}
    	
    	mainMenuNetwork net = new mainMenuNetwork(map);
    	net.runServer();
    	net.connectToHost("localhost", name);
    		
    	inLobby = true;
    		
    	Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws Exception {
				while (inLobby) {
					//System.err.println("host STILL IN WHILE LOOP");
					//System.err.println("host isConnected: " + net.isConnected());
					//System.err.println("host inGame: " + net.inGame());
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if (net.isConnected() && !net.inGame()) {
								// //System.err.println("host if");
								showUI();
								refreshHLobby(net);
							} else if (net.isConnected() && net.inGame()) {
								//System.err.println("host else if");
								hideUI();
							} else {
								//System.err.println("host else");
								//System.err.println("host isConnected: " + net.isConnected());
								//System.err.println("host inGame: " + net.inGame());
								net.resetServer();
								net.connectToHost("localhost", name);
								showUI();
							}
						}
					});
					Thread.sleep(750);
				}
				return null;
			}
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();

	}
    
    
    
    
    /**
     * This method is run when entering a lobby
     * It determines whether you're a host or client and sets up the lobby accordingly
     * @param type string used to determine whether the player is a host or client
     * @param net class which handles the networking of the player
     */
	public static void clientLobby(mainMenuNetwork net) {
		inLobby = true;

		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws Exception {
				while (inLobby) {
					//System.err.println("client STILL IN WHILE LOOP");
					//System.err.println("client isConnected: " + net.isConnected());
					//System.err.println("client inGame: " + net.inGame());
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if (net.isConnected() && !net.inGame()) {
								// //System.err.println("client if");
								showUI();
								refreshCLobby(net);
							} else if (net.isConnected() && net.inGame()) {
								//System.err.println("client else if");
								hideUI();
							} else {
								//System.err.println("client else");
								//System.err.println("client isConnected: " + net.isConnected());
								//System.err.println("client inGame: " + net.inGame() + "should be irrelevent now");
								inLobby = false;
								showUI();
								ps.setScene(ogScene);
								ps.setTitle("Square-Off: Start Menu");

							}
						}
					});
					Thread.sleep(750);
				}
				return null;
			}
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}
    
    
    /**
     * This method creates the player table in the lobby
     * @param net class which handles the networking of the player - used to get the players in the lobby
     * @return a table with the current players in the lobby is returned
     */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static TableView<Players> lobbyTable(mainMenuNetwork net) {
		ArrayList<String> playerArrayList = net.getPlayers();
		ObservableList<Players> playerList = FXCollections.observableArrayList();
		
		for (int x=0; x<playerArrayList.size(); x++) {
			if (x==0)
				playerList.add(new Players("(Host) " + String.valueOf(x+1), playerArrayList.get(x)));
			else
				playerList.add(new Players(String.valueOf(x+1), playerArrayList.get(x)));
		}
        
        TableView<Players> table = new TableView<Players>();
        table.setEditable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<Players, String> playerIDCol = new TableColumn<>("playerID");
        TableColumn<Players, String> playerNameCol = new TableColumn<>("playerName");
        playerIDCol.setMinWidth(300);
        playerNameCol.setMinWidth(400);
        playerIDCol.setResizable(false);
        playerNameCol.setResizable(false);
        playerIDCol.impl_setReorderable(false);
        playerNameCol.impl_setReorderable(false);
        playerIDCol.setStyle( "-fx-alignment: CENTER;");
        playerNameCol.setStyle( "-fx-alignment: CENTER;");
        
        playerIDCol.setCellValueFactory(new PropertyValueFactory<>("playerID"));
        playerNameCol.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        
        table.setItems(playerList);
        table.getColumns().addAll(playerIDCol, playerNameCol);
        
        return table;
    }
    
    
    /**
     * Method to handle clients attempts when joining a server
     * Handles incorrect names and invalid addresses appropriately
     * @param hostAddress address the client is trying to join 
     * @param name name of the client
     */
    public static void tryToJoin(String hostAddress, String name) {
    	mainMenuNetwork net = new mainMenuNetwork(map);

    	if (name.equals("")) {
    		Alert alert;
			alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Error: Invalid Name");
			alert.setContentText("You must enter a valid name.");
			alert.setTitle("Square-Off: Joining Game");
			alert.showAndWait();
			a.click();
    	}
		else if (name.length() > 61) {
    		Alert alert;
			alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Error: Name too long");
			alert.setContentText("The maximum name length is 61 alphanumeric characters");
			alert.setTitle("Square-Off: Joining Game");
			alert.showAndWait();
			a.click();
		}
		else if ( !name.matches("^([A-Za-z]|[0-9])+$") ) {
    		Alert alert;
			alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Error: Invalid Characters");
			alert.setContentText("Your name can only contain alphanumeric characters");
			alert.setTitle("Square-Off: Joining Game");
			alert.showAndWait();
			a.click();
		}
    	else {
    		if (net.connectToHost(hostAddress, name) == 0)
				clientLobby(net);
    		else if (net.connectToHost(hostAddress, name) == 2) {
				Alert alert;
				alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("Error: Name in use");
				alert.setContentText("There is already a client in the server with the name: " + name);
				alert.setTitle("Square-Off: Joining Game");
				alert.showAndWait();
				a.click();
    		}
			else {
				Alert alert;
				alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("Error: Host doesn't exist");
				alert.setContentText("There is no host with the address: " + hostAddress);
				alert.setTitle("Square-Off: Joining Game");
				alert.showAndWait();
				a.click();
			}
    	}
    }
    
    /**
     * The method is run when trying to join a lobby
     */
	public static void jgWindow() {
		ps.setTitle("Square-Off: Client");
    	
    	Label label1 = new Label("Address:");
        TextField textField = new TextField ();
        HBox hb = new HBox(10);
    	
        Label label2 = new Label("Enter your Name:");
        TextField textField2 = new TextField ();
        HBox hb2 = new HBox(10);
        
    	Button btn5 = new Button("Click to Join");
    	btn5.setMinWidth(90);
        btn5.setOnAction( e -> { a.click(); tryToJoin(textField.getText(), textField2.getText()); } );
    	
    	Button btn6 = new Button("Back to Main Menu");
    	btn6.setMinWidth(140);
        btn6.setOnAction( e -> { a.click(); ps.setScene(ogScene); ps.setTitle("Square-Off: Start Menu"); } );
        
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
        
        grid3.setAlignment(Pos.CENTER);
        grid3.setStyle("-fx-background-color: transparent;");
        
        Scene scene3 = new Scene(grid3, width, height, Color.LIGHTBLUE);
        ps.setScene(scene3);
        ps.show();
    }
    
    /**
     * Help window - controls etc
     */
    public static void helpWindow() {
    	ps.setTitle("Square-Off: Help");
    	
    	Button btn6 = new Button("Back to Main Menu");
        
    	Label label = new Label("Square-Off is an artillery strategy game.\nYou are a Square and are about to Square off with opposing Squares.\nYour aim is to knock off any other Squares in the map by targetting the blocks beneath them.\nWhoever is the last Square standing wins!\n\nYou move left when you press the 'a' key and right when you press the 'd' key.\nYou can also jump with the 'w' key.\nYou can select different weapons, mute the music and exit in game via the buttons at the top of the game.\n\nGood Luck and may the best Square win!");
    	label.setFont(new Font("Arial", 15));
    	
        VBox vbox = new VBox();
        vbox.getChildren().addAll(label, btn6);
        vbox.setSpacing(45);
        vbox.setAlignment(Pos.CENTER);
    	
        GridPane grid4 = new GridPane();
        grid4.setVgap(12);
        
        grid4.add(vbox, 0, 11);
        grid4.setAlignment(Pos.CENTER);
        grid4.setStyle("-fx-background-color: transparent;");
        
        Scene scene4 = new Scene(grid4, width, height, Color.LIGHTBLUE);
        
        btn6.setOnAction( e -> { a.click(); ps.setScene(ogScene); ps.setTitle("Square-Off: Start Menu"); } );
        
        ps.setScene(scene4);
        ps.show();
    }
    
}