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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;

public class mainMenu extends Application {
	static int width = 960;
	static int height = 540;
	static Audio a = new Audio();
	static Stage ps;
	
    public static void main(String[] args) {
        //launch(args);
    	launchMenu();
    }
    
    @Override
    public void stop() {
        System.out.println("Program Exited");
    }
    
    public static void hideUI() {
    	ps.hide();
    }
    
    public static void showUI() {
    	ps.show();
    }
    
    public static void launchMenu() {
    	SplashSplash splashscreen = new SplashSplash(1000);
		splashscreen.showSplash();
    	launch();
    }
    
    public void start(Stage primaryStage) throws Exception {
    	ps = primaryStage;
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
        
        
        Scene scene1 = new Scene(grid, width, height);
        
        btn.setOnAction( e -> { System.out.println("Hosting new lobby"); a.click(); lobbyWindow(scene1, "Host", (new mainMenuNetwork()) ); } );
        btn2.setOnAction( e -> { System.out.println("Loading existing lobbies"); a.click(); jgWindow( scene1); } );
        btn3.setOnAction( e -> { System.out.println("Opening options"); a.click(); oWindow( scene1); } );
        btn4.setOnAction( e -> { System.out.println("Quitting Game"); a.click(); System.exit(0); } );
        
        primaryStage.setScene(scene1);
        primaryStage.show();
                
    }
    
    
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
    
    @SuppressWarnings({ "rawtypes" })
    public static void refreshHLobby(Scene scene1, mainMenuNetwork net) {
    	ps.setTitle("Square-Off: Lobby");
    	
    	Button btn4 = new Button("Refresh");
    	btn4.setMinWidth(120);
        btn4.setOnAction( e -> { System.out.println("Refreshing Table"); a.click(); refreshHLobby(scene1, net); } );
    	
    	Button btn5 = new Button("Back to Main Menu");
    	btn5.setMinWidth(120);
        btn5.setOnAction( e -> { System.out.println("Returning to Main Menu"); a.click(); net.closeServer(); ps.setScene(scene1); ps.setTitle("Square-Off: Start Menu"); } );
        
        Button btn6 = new Button("Start Game");
    	btn6.setMinWidth(120);
        btn6.setOnAction( e -> { System.out.println("Starting Game"); a.click(); net.startGame(); } );//hideUI(); } );
        
        TableView table = lobbyTable(net);
        
        HBox hbox = new HBox(12);
        hbox.getChildren().addAll(btn6, btn5, btn4);
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
        
        ps.setOnCloseRequest( e -> net.closeServer() ); 
        
        Scene scene2 = new Scene(grid3, width, height);
        ps.setScene(scene2);
        ps.show();
    }
  
    @SuppressWarnings({ "rawtypes" })
    public static void refreshCLobby(Scene scene1, mainMenuNetwork net) {
    	ps.setTitle("Square-Off: Lobby");
    	
    	Button btn4 = new Button("Refresh");
    	btn4.setMinWidth(120);
        btn4.setOnAction( e -> { System.out.println("Refreshing Table"); a.click(); refreshCLobby(scene1, net); } );
    	
    	Button btn5 = new Button("Back to Main Menu");
    	btn5.setMinWidth(120);
        btn5.setOnAction( e -> { System.out.println("Returning to Main Menu"); a.click(); ps.setScene(scene1); ps.setTitle("Square-Off: Start Menu"); } );
        
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
        
        Scene scene2 = new Scene(grid3, width, height);
        ps.setScene(scene2);
        ps.show();
    } 
   
    @SuppressWarnings({ })
	public static void lobbyWindow(Scene scene1, String type, mainMenuNetwork net) {
    	//ArrayList<String> playerArrayList = new ArrayList();
    	
    	if (type.equals("Host")) {
    		//mainMenuNetwork net = new mainMenuNetwork();
    		String name = hostUsername();
    		
    		if (name==null) {
    			ps.setScene(scene1);
    			ps.setTitle("Square-Off: Start Menu");
    			return;
    		}
    		else {
    			//Dunno if I'll need this in the future
    		}

    		net.runServer();
    		net.connectToHost("127.0.0.1:4444", name);
    		
    		refreshHLobby(scene1, net);
    	}
    	else {
    		refreshCLobby(scene1, net);
    	}
    }
    
    
    
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
    
    
    
    public static void tryToJoin(String hostName, String name, Scene scene1) {
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
    		if (net.connectToHost(hostName, name))
				lobbyWindow(scene1, "Client", net);
			else {
				Alert alert;
				alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("Error: Host doesn't exist");
				alert.setContentText("There is no host with the address: " + hostName);
				alert.setTitle("Square-Off: Joining Game");
				alert.showAndWait();
				a.click();
			}
    	}
    }
    

	public static void jgWindow(Scene scene1) {
		ps.setTitle("Square-Off: Client");
    	
    	Label label1 = new Label("Address:");
        TextField textField = new TextField ();
        HBox hb = new HBox(10);
    	
        Label label2 = new Label("Enter your Name:");
        TextField textField2 = new TextField ();
        HBox hb2 = new HBox(10);
        
    	Button btn5 = new Button("Click to Join");
    	btn5.setMinWidth(90);
        btn5.setOnAction( e -> { System.out.println("Opening Message Box"); a.click(); tryToJoin(textField.getText(), textField2.getText(), scene1); } );
    	
    	Button btn6 = new Button("Back to Main Menu");
    	btn6.setMinWidth(140);
        btn6.setOnAction( e -> { System.out.println("Returning to Main Menu"); a.click(); ps.setScene(scene1); ps.setTitle("Square-Off: Start Menu"); } );
        
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
        
        
        Scene scene3 = new Scene(grid3, width, height);
        ps.setScene(scene3);
        ps.show();
    }
	
    
	
    public static void oWindow(Scene scene1) {
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
        
        
        Scene scene4 = new Scene(grid4, width, height);
        
        btn6.setOnAction( e -> { System.out.println("Returning to Main Menu"); a.click(); ps.setScene(scene1); ps.setTitle("Square-Off: Start Menu"); } );
        btn7.setOnAction( e -> { System.out.println("Opening Video Options"); a.click(); videoWindow(scene4, width, height); ps.setTitle("Square-Off: Video Options"); } );
        btn8.setOnAction( e -> { System.out.println("Opening Audio Options"); a.click(); audioWindow(scene4); ps.setTitle("Square-Off: Audio Options"); } );
        
        ps.setScene(scene4);
        ps.show();
    }
    
    
    
    public static void setVolume(double d) {
    	System.out.println(d);
    }
    
    public static void setResolution(double d) {
    	System.out.println(((16.0/9.0) * d) + " x " + d);
    }
    
    
    public static void audioWindow(Scene scene1) {
    	
    	Button btn9 = new Button("Back to Options");
    	btn9.setMinWidth(120);
        btn9.setOnAction( e -> { System.out.println("Returning to Options"); a.click(); ps.setScene(scene1); ps.setTitle("Square-Off: Options"); } );
        
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
        btn10.setOnAction( e -> { System.out.println("Setting Volume"); a.click(); setVolume(slider.getValue()); } );
        
        hb.getChildren().addAll(slider, btn10);
        hb.setAlignment(Pos.CENTER);
        
        
        GridPane grid5 = new GridPane();
        grid5.setVgap(12);
        
        grid5.add(hb, 0, 19);
        grid5.add(btn9, 0, 20);
        grid5.setAlignment(Pos.CENTER);
        
        
        Scene scene5 = new Scene(grid5, width, height);
        ps.setScene(scene5);
        ps.show();
    }
    
    
    
    
    public static void videoWindow(Scene scene1, double w, double h) {
    	
    	Button btn9 = new Button("Back to Options");
    	btn9.setMinWidth(120);
        btn9.setOnAction( e -> { System.out.println("Returning to Options"); a.click(); width = (int) w; height = (int) h; ps.setScene(scene1); ps.setTitle("Square-Off: Options"); } );
        
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
        btn10.setOnAction( e -> { System.out.println("Setting Resolution"); a.click(); videoWindow(scene1, ((16.0/9.0) * slider.getValue()), slider.getValue());  } ); //setResolution(slider.getValue()); } );
        
        hb.getChildren().addAll(slider, btn10);
        hb.setAlignment(Pos.CENTER);
        
        
        GridPane grid5 = new GridPane();
        grid5.setVgap(12);
        
        grid5.add(currentLabel, 0, 16);
        grid5.add(hb, 0, 17);
        grid5.add(btn9, 0, 18);
        grid5.setAlignment(Pos.CENTER);
        
        
        Scene scene5 = new Scene(grid5, (int) w, (int) h);
        ps.setScene(scene5);
        ps.show();
    }    
    
    
    
}