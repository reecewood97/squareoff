package UserInterface;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class mainMenu extends Application {
    public static void main(String[] args) {
        launch(args);
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
        
        btn.setOnAction( e -> { System.out.println("Hosting new lobby"); hgWindow(primaryStage, scene1); } );
        btn2.setOnAction( e -> { System.out.println("Loading existing lobbies"); jgWindow(primaryStage, scene1); } );
        btn3.setOnAction( e -> { System.out.println("Opening options"); oWindow(primaryStage, scene1); } );
        btn4.setOnAction( e -> { System.out.println("Quitting Game"); System.exit(0); } );
        
        primaryStage.setScene(scene1);
        primaryStage.show();
                
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void hgWindow(Stage primaryStage, Scene scene1) {
    	primaryStage.setTitle("Square-Off: Host");
    	
    	Button btn5 = new Button("Back to Main Menu");
    	btn5.setMinWidth(120);
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
    
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void jgWindow(Stage primaryStage, Scene scene1) {
    	primaryStage.setTitle("Square-Off: Client");
    	
    	Button btn5 = new Button("Click to Enter hostname");
    	btn5.setMinWidth(140);
        btn5.setOnAction( e -> { System.out.println("Opening Message Box"); infoBox("Enter the Host Name", "Joining Game..."); } );
    	
    	Button btn6 = new Button("Back to Main Menu");
    	btn6.setMinWidth(140);
        btn6.setOnAction( e -> { System.out.println("Returning to Main Menu"); primaryStage.setScene(scene1); primaryStage.setTitle("Square-Off: Start Menu"); } );
        
        GridPane grid3 = new GridPane();
        grid3.setVgap(12);
        
        grid3.add(btn5, 0, 20);
        grid3.add(btn6, 0, 21);
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