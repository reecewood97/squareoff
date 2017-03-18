/**
 * The mainMenu class is the initial menu a player will interact with.
 * They can host or join a game from here, as well as change any options required.
 * @author ksk523
 */

package UserInterface;

import java.util.ArrayList;
import java.util.Optional;
import Audio.Audio;
import Graphics.SplashSplash;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class mainMenu extends Application {
	static int width = 960;
	static int height = 540;
	static Audio a = new Audio();
	static Stage ps;
	static Scene ogScene;
	static Stage temps;
	static boolean inLobby = false;
	
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
    	ps.toBack();
    	/*
    	ps.setOnHiding( e -> {  temps = new Stage();
    							temps.initStyle(StageStyle.UTILITY);
    							temps.setMaxHeight(0);
    							temps.setMaxWidth(0);
    							temps.setX(Double.MAX_VALUE);
    							temps.show();
        }  );
    	ps.hide();
    	*/
    }
    
    /**
     * Method to show the UI at anytime
     */
    public static void showUI() {
    	ps.toFront();
    	//temps.setOnHiding( e -> {  ps.show(); }  );
    	//temps.hide();
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
        
        Button btn = new Button("Host Game");
        btn.setMinWidth(100);
        Button btn2 = new Button("Join Game");
        btn2.setMinWidth(100);
        Button btn3 = new Button("Options");
        btn3.setMinWidth(100);
        Button btn5 = new Button("Help");
        btn5.setMinWidth(100);
        Button btn4 = new Button("Quit Game");
        btn4.setMinWidth(100);
        
        GridPane grid = new GridPane();
        
        grid.setVgap(12);
        
        grid.add(btn, 0, 11);
        grid.add(btn2, 0, 12);
        grid.add(btn3, 0, 13);
        grid.add(btn5, 0, 14);
        grid.add(btn4, 0, 15);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: transparent;");
        
        ogScene = new Scene(grid, width, height, Color.LIGHTBLUE);
        
        btn.setOnAction( e -> { a.click(); lobbyWindow("Host", (new mainMenuNetwork()) ); } );
        btn2.setOnAction( e -> { a.click(); jgWindow(); } );
        btn3.setOnAction( e -> { a.click(); oWindow(); } );
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
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Square-Off: Hosting Game");
		dialog.setHeaderText("Setting your player name");
		dialog.setContentText("Please enter your name:");

		Optional<String> result = dialog.showAndWait();
		a.click();

		if (result.isPresent() && !(result.get().isEmpty())) {
			return result.get();
		} else {
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
    	
    	Button btn4 = new Button("Refresh");
    	btn4.setMinWidth(120);
        btn4.setOnAction( e -> { a.click(); refreshHLobby(net); } );
    	
    	Button btn5 = new Button("Back to Main Menu");
    	btn5.setMinWidth(120);
        btn5.setOnAction( e -> { a.click(); inLobby=false; net.closeServer(); ps.setScene(ogScene); ps.setTitle("Square-Off: Start Menu"); } );
        
        Button btn6 = new Button("Start Game");
    	btn6.setMinWidth(120);
        btn6.setOnAction( e -> { a.click();  inLobby=false; net.startGame(); } ); //hideUI(); } );
        
        TableView table = lobbyTable(net);
        
        HBox hbox = new HBox(12);
        hbox.getChildren().addAll(btn6, btn5, btn4);
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
    	
    	Button btn4 = new Button("Refresh");
    	btn4.setMinWidth(120);
        btn4.setOnAction( e -> { a.click(); refreshCLobby(net); } );
    	
    	Button btn5 = new Button("Back to Main Menu");
    	btn5.setMinWidth(120);
        btn5.setOnAction( e -> { a.click(); inLobby=false; net.Disconnect(); ps.setScene(ogScene); ps.setTitle("Square-Off: Start Menu"); } );
        
        TableView table = lobbyTable(net);
        
        HBox hbox = new HBox(12);
        hbox.getChildren().addAll(btn5, btn4);
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
     * @param type string used to determine whether the player is a host or client
     * @param net class which handles the networking of the player
     */
	public static void lobbyWindow(String type, mainMenuNetwork net) {
    	//ArrayList<String> playerArrayList = new ArrayList();
    	
    	if (type.equals("Host")) {
    		//mainMenuNetwork net = new mainMenuNetwork();
    		String name = hostUsername();
    		
    		if (name==null) {
    			ps.setScene(ogScene);
    			ps.setTitle("Square-Off: Start Menu");
    			//net.closeServer();
    			return;
    		}
    		else {
    			//Dunno if I'll need this in the future
    		}

    		net.runServer();
    		//net.connectToHost("127.0.0.1:4444", name);
    		net.connectToHost("127.0.0.1", name);
    		
    		//refreshHLobby(net);
    		/*
    		while (!net.inGame()) {
    			refreshHLobby(scene1, net);
    			try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("Error refreshing lobby in host");
				}
    		}
    		*/
    		inLobby = true;
    		
    		Task task = new Task<Void>() {
    			  @Override
    			  public Void call() throws Exception {
    			    while (inLobby) {
    			      Platform.runLater(new Runnable() {
    			        @Override
    			        public void run() {
    			        	if ( net.isConnected() ) 
    			        		refreshHLobby(net);
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
    	else {
    		inLobby = true;
    		
    		Task task = new Task<Void>() {
    			  @Override
    			  public Void call() throws Exception {
    			    while (inLobby) {
    			      Platform.runLater(new Runnable() {
    			        @Override
    			        public void run() {
    			        	if ( net.isConnected() )
    			        		refreshCLobby(net);
    			        	else {
    			        		inLobby = false;
    			        		ps.setScene(ogScene);
    			        		ps.setTitle("Square-Off: Start Menu");
    			        	}
    			        	/*
    			        	try {
    			        		refreshCLobby(net);
    			        	}
    			        	catch (NullPointerException e) {
    			        		inLobby = false;
    			        		ps.setScene(ogScene);
    			        		ps.setTitle("Square-Off: Start Menu");
    			        	}
    			        	*/
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
    	mainMenuNetwork net = new mainMenuNetwork();

    	if (name.equals("")) {
    		Alert alert;
			alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Error: Invalid Name");
			alert.setContentText("You must enter a valid name.");
			alert.setTitle("Square-Off: Joining Game");
			alert.showAndWait();
			a.click();
    	}
    	else {
    		if (net.connectToHost(hostAddress, name))
				lobbyWindow("Client", net);
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
	 * Options menu for the game, handles video/audio options
	 */
    public static void oWindow() {
    	ps.setTitle("Square-Off: Options");
    	
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
        
        grid4.setAlignment(Pos.CENTER);
        grid4.setStyle("-fx-background-color: transparent;");
        
        Scene scene4 = new Scene(grid4, width, height, Color.LIGHTBLUE);
        
        btn6.setOnAction( e -> { a.click(); ps.setScene(ogScene); ps.setTitle("Square-Off: Start Menu"); } );
        btn7.setOnAction( e -> { a.click(); videoWindow(width, height); ps.setTitle("Square-Off: Video Options"); } );
        btn8.setOnAction( e -> { a.click(); audioWindow(); ps.setTitle("Square-Off: Audio Options"); } );
        
        ps.setScene(scene4);
        ps.show();
    }
    
    
    /**
     * sets the volume of the game
     * @param d new volume level
     */
    public static void setVolume(double d) {
    	//System.out.println(d);
    }
    
    /**
     * sets the resolution of the game (always in 16:9 aspect ratio)
     * @param d new resolution of the game
     */
    public static void setResolution(double d) {
    	//System.out.println(((16.0/9.0) * d) + " x " + d);
    }
    
    /**
     * Audio options window
     */
    public static void audioWindow() {
    	
    	Button btn9 = new Button("Back to Options");
    	btn9.setMinWidth(120);
        btn9.setOnAction( e -> { a.click(); ps.setScene(ogScene); ps.setTitle("Square-Off: Options"); } );
        
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(75);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        
        
        HBox hb = new HBox(10);
    	Button btn10 = new Button("Set Volume");
    	btn10.setMinWidth(140);
        btn10.setOnAction( e -> { a.click(); setVolume(slider.getValue()); } );
        
        hb.getChildren().addAll(slider, btn10);
        hb.setAlignment(Pos.CENTER);
        
        
        GridPane grid5 = new GridPane();
        grid5.setVgap(12);
        
        grid5.add(hb, 0, 19);
        grid5.add(btn9, 0, 20);
        grid5.setAlignment(Pos.CENTER);
        
        grid5.setAlignment(Pos.CENTER);
        grid5.setStyle("-fx-background-color: transparent;");
        
        Scene scene5 = new Scene(grid5, width, height, Color.LIGHTBLUE);
        ps.setScene(scene5);
        ps.show();
    }
    
    /**
     * Video options window
     * @param w new width of window
     * @param h new height of window
     */
    public static void videoWindow(double w, double h) {
    	
    	Button btn9 = new Button("Back to Options");
    	btn9.setMinWidth(120);
        btn9.setOnAction( e -> { a.click(); width = (int) w; height = (int) h; ps.setScene(ogScene); ps.setTitle("Square-Off: Options"); } );
        
        Slider slider = new Slider();
        slider.setMin(480);
        slider.setMax(1080);
        slider.setValue( (int) height );
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        //slider
        
        Label currentLabel = new Label( ((int) w) + " x " + ( (int) h ));
        
        /*
        opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    cappuccino.setOpacity(new_val.doubleValue());
                    opacityValue.setText(String.format("%.2f", new_val));
            }
        });
        */
        
        HBox hb = new HBox(10);
    	Button btn10 = new Button("Set Resolution");
    	btn10.setMinWidth(140);
        btn10.setOnAction( e -> { a.click(); videoWindow(((16.0/9.0) * slider.getValue()), slider.getValue());  } ); //setResolution(slider.getValue()); } );
        
        hb.getChildren().addAll(slider, btn10);
        hb.setAlignment(Pos.CENTER);
        
        
        GridPane grid5 = new GridPane();
        grid5.setVgap(12);
        
        grid5.add(currentLabel, 0, 16);
        grid5.add(hb, 0, 17);
        grid5.add(btn9, 0, 18);
        grid5.setAlignment(Pos.CENTER);
        
        grid5.setAlignment(Pos.CENTER);
        grid5.setStyle("-fx-background-color: transparent;");
        
        
        Scene scene5 = new Scene(grid5, (int) w, (int) h, Color.LIGHTBLUE);
        ps.setScene(scene5);
        ps.show();
    }    
    
    /**
     * Help window - controls etc
     */
    public static void helpWindow() {
    	ps.setTitle("Square-Off: Help");
    	
    	Button btn6 = new Button("Back to Main Menu");
        
        
        GridPane grid4 = new GridPane();
        grid4.setVgap(12);
        
        grid4.add(btn6, 0, 23);
        grid4.setAlignment(Pos.CENTER);

        grid4.setAlignment(Pos.CENTER);
        grid4.setStyle("-fx-background-color: transparent;");
        
        Scene scene4 = new Scene(grid4, width, height, Color.LIGHTBLUE);
        
        btn6.setOnAction( e -> { a.click(); ps.setScene(ogScene); ps.setTitle("Square-Off: Start Menu"); } );
        
        ps.setScene(scene4);
        ps.show();
    }
    
}