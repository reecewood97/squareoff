package UserInterface;

import java.util.ArrayList;
import java.util.Optional;
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
    public static void main(String[] args) {
        //launch(args);
    	launchMenu();
    }
    
    @Override
    public void stop() {
        System.out.println("Program Exited");
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
    
    
    
    @SuppressWarnings({ "rawtypes" })
	public static void lobbyWindow(Stage primaryStage, Scene scene1, String type) {
    	//ArrayList<String> playerArrayList = new ArrayList();
    	mainMenuNetwork net = new mainMenuNetwork();
    	
    	if (type.equals("Host")) {
    		
    		String name = hostUsername();
    		
    		if (name==null) {
    			primaryStage.setScene(scene1);
    			primaryStage.setTitle("Square-Off: Start Menu");
    			return;
    		}
    		else {
    			//Dunno if I'll need this in the future
    		}

    		net.runServer();
    		net.connectToHost("127.0.0.1:123", name);
    		
        	primaryStage.setTitle("Square-Off: Lobby");
        	
        	Button btn5 = new Button("Back to Main Menu");
        	btn5.setMinWidth(120);
            btn5.setOnAction( e -> { System.out.println("Returning to Main Menu"); net.closeServer(); primaryStage.setScene(scene1); primaryStage.setTitle("Square-Off: Start Menu"); } );
            
            Button btn6 = new Button("Start Game");
        	btn6.setMinWidth(120);
            btn6.setOnAction( e -> { System.out.println("Starting Game"); net.startGame(); primaryStage.hide(); } );
            
            TableView table = lobbyTable(net);
            
            HBox hbox = new HBox(12);
            hbox.getChildren().addAll(btn6, btn5);
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
            
            primaryStage.setOnCloseRequest( e -> net.closeServer() ); 
            
            Scene scene2 = new Scene(grid3, 960, 540);
            primaryStage.setScene(scene2);
            primaryStage.show();
    	}
    	else {
        	primaryStage.setTitle("Square-Off: Lobby");
        	
        	Button btn5 = new Button("Back to Main Menu");
        	btn5.setMinWidth(120);
            btn5.setOnAction( e -> { System.out.println("Returning to Main Menu"); primaryStage.setScene(scene1); primaryStage.setTitle("Square-Off: Start Menu"); } );
            
            TableView table = lobbyTable(net);
            
            VBox vbox = new VBox(12);
            vbox.getChildren().addAll(table, btn5);
            vbox.setMaxWidth(710);
            vbox.setAlignment(Pos.CENTER);

            GridPane grid3 = new GridPane();
            
            Label label3 = new Label("");
            
            grid3.add(label3, 1, 0);
            grid3.add(vbox, 0, 0);
            grid3.setAlignment(Pos.CENTER);
            
            Scene scene2 = new Scene(grid3, 960, 540);
            primaryStage.setScene(scene2);
            primaryStage.show();
    	}
    }
    
    
    
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static TableView<Players> lobbyTable(mainMenuNetwork net) {
		ArrayList<String> playerArrayList = net.getPlayers();
		ObservableList<Players> playerList = FXCollections.observableArrayList();
        
		for (int x=0; x<playerArrayList.size(); x++) {
			if (x==0)
				playerList.add(new Players("(Host) " + String.valueOf(x+1), playerArrayList.get(x)));
			else if (x==1)
				playerList.add(new Players("(Human) " + String.valueOf(x+1), playerArrayList.get(x)));
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
        btn7.setOnAction( e -> { System.out.println("Opening Video Options"); videoWindow(primaryStage, scene4, 960, 540); primaryStage.setTitle("Square-Off: Video Options"); } );
        btn8.setOnAction( e -> { System.out.println("Opening Audio Options"); audioWindow(primaryStage, scene4); primaryStage.setTitle("Square-Off: Audio Options"); } );
        
        primaryStage.setScene(scene4);
        primaryStage.show();
    }
    
    
    
    public static void setVolume(double d) {
    	System.out.println(d);
    }
    
    public static void setResolution(double d) {
    	System.out.println(((16.0/9.0) * d) + " x " + d);
    }
    
    
    public static void audioWindow(Stage primaryStage, Scene scene1) {
    	
    	Button btn9 = new Button("Back to Options");
    	btn9.setMinWidth(120);
        btn9.setOnAction( e -> { System.out.println("Returning to Options"); primaryStage.setScene(scene1); primaryStage.setTitle("Square-Off: Options"); } );
        
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
        btn10.setOnAction( e -> { System.out.println("Setting Volume"); setVolume(slider.getValue()); } );
        
        hb.getChildren().addAll(slider, btn10);
        hb.setAlignment(Pos.CENTER);
        
        
        GridPane grid5 = new GridPane();
        grid5.setVgap(12);
        
        grid5.add(hb, 0, 19);
        grid5.add(btn9, 0, 20);
        grid5.setAlignment(Pos.CENTER);
        
        
        Scene scene5 = new Scene(grid5, 960, 540);
        primaryStage.setScene(scene5);
        primaryStage.show();
    }
    
    
    
    
    public static void videoWindow(Stage primaryStage, Scene scene1, double width, double height) {
    	
    	Button btn9 = new Button("Back to Options");
    	btn9.setMinWidth(120);
        btn9.setOnAction( e -> { System.out.println("Returning to Options"); primaryStage.setScene(scene1); primaryStage.setTitle("Square-Off: Options"); } );
        
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
        
        Label currentLabel = new Label( ((int) width) + " x " + ( (int) height ));
        
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
        btn10.setOnAction( e -> { System.out.println("Setting Resolution"); videoWindow(primaryStage, scene1, ((16.0/9.0) * slider.getValue()), slider.getValue());  } ); //setResolution(slider.getValue()); } );
        
        hb.getChildren().addAll(slider, btn10);
        hb.setAlignment(Pos.CENTER);
        
        
        GridPane grid5 = new GridPane();
        grid5.setVgap(12);
        
        grid5.add(currentLabel, 0, 16);
        grid5.add(hb, 0, 17);
        grid5.add(btn9, 0, 18);
        grid5.setAlignment(Pos.CENTER);
        
        
        Scene scene5 = new Scene(grid5, (int) width, (int) height);
        primaryStage.setScene(scene5);
        primaryStage.show();
    }    
    
    
    
}