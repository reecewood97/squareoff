package f2game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class mainMenu extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Square-Off: Start Menu");
        
        Button btn = new Button("Host Game");
        Button btn2 = new Button("Join Game");
        Button btn3 = new Button("  Options  ");
        Button btn4 = new Button("Quit Game");
        
        GridPane grid = new GridPane();
        grid.setVgap(12);
        
        grid.add(btn, 0, 18);
        grid.add(btn2, 0, 19);
        grid.add(btn3, 0, 20);
        grid.add(btn4, 0, 21);
        grid.setAlignment(Pos.CENTER);
        
        
        Scene scene1 = new Scene(grid, 960, 540);
        
        btn.setOnAction( e -> { System.out.println("Hosting new lobby"); hgWindow(primaryStage, scene1); } );
        btn2.setOnAction( e -> { System.out.println("Loading existing lobbies"); jgWindow(primaryStage, scene1); } );
        btn3.setOnAction( e -> { System.out.println("Opening options"); oWindow(primaryStage, scene1); } );
        btn4.setOnAction( e -> { System.out.println("Quitting Game"); System.exit(0); } );
        
        primaryStage.setScene(scene1);
        primaryStage.show();
                
    }
    
    public static void hgWindow(Stage primaryStage, Scene scene1) {
    	primaryStage.setTitle("Square-Off: Host");
    	
    	Button btn5 = new Button("Back to Main Menu");
        btn5.setOnAction( e -> { System.out.println("Returning to Main Menu"); primaryStage.setScene(scene1); primaryStage.setTitle("Square-Off: Start Menu"); } );
        
        TableView table = new TableView();
        table.setEditable(false);
        
        TableColumn playerIDCol = new TableColumn("Player ID");
        TableColumn playerNameCol = new TableColumn("Player Name");
        TableColumn readyCol = new TableColumn("Ready");
                
        table.getColumns().addAll(playerIDCol, playerNameCol, readyCol);
        
        GridPane grid2 = new GridPane();
        grid2.setVgap(12);
        
        grid2.add(table, 0, 0);
        grid2.add(btn5, 1, 1);
        grid2.setAlignment(Pos.CENTER);
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(table, btn5);
        vbox.setAlignment(Pos.CENTER);
        
        Scene scene2 = new Scene(vbox, 960, 540);
        primaryStage.setScene(scene2);
        primaryStage.show();
    }
    
    public static void jgWindow(Stage primaryStage, Scene scene1) {
    	primaryStage.setTitle("Square-Off: Client");
    	
    	Button btn6 = new Button("Back to Main Menu");
        btn6.setOnAction( e -> { System.out.println("Returning to Main Menu"); primaryStage.setScene(scene1); primaryStage.setTitle("Square-Off: Start Menu"); } );
        
        GridPane grid3 = new GridPane();
        grid3.setVgap(12);
        
        grid3.add(btn6, 0, 30);
        grid3.setAlignment(Pos.CENTER);
        
        
        Scene scene3 = new Scene(grid3, 960, 540);
        primaryStage.setScene(scene3);
        primaryStage.show();
    }
    
    public static void oWindow(Stage primaryStage, Scene scene1) {
    	primaryStage.setTitle("Square-Off: Options");
    	
    	Button btn6 = new Button("Back to Main Menu");
        btn6.setOnAction( e -> { System.out.println("Returning to Main Menu"); primaryStage.setScene(scene1); primaryStage.setTitle("Square-Off: Start Menu"); } );
        
        GridPane grid4 = new GridPane();
        grid4.setVgap(12);
        
        grid4.add(btn6, 0, 30);
        grid4.setAlignment(Pos.CENTER);
        
        
        Scene scene4 = new Scene(grid4, 960, 540);
        primaryStage.setScene(scene4);
        primaryStage.show();
    }
    
}