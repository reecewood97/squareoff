package Graphics;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WeaponsMenu extends Application {

	Stage primaryStage;
	String[] weaponArray = {"Bomb","Ball","Grenade"};
	int currentWeapon = 0;
	ImageView image1;
	
	  @Override
	  public void start(Stage primaryStage) {
		  
		this.primaryStage = primaryStage;
		primaryStage.initStyle(StageStyle.UNDECORATED);
		
		Platform.setImplicitExit(false);
		
	    Button btn = new Button();
	    
	    
	    btn.setText("Select Weapon");
	    
	    Button btn2 = new Button();
	    btn2.setText("Exit");
	    
	    Button right = new Button();
	    right.setText(">");
	    
	    Button left = new Button();
	    left.setText("<");
	    
	    //Image image1 = new Image("Files/Images/speaker1.png");
	    image1 = new ImageView();
	    image1.setFitHeight(50);
	    image1.setFitWidth(50);
	    BorderPane root = new BorderPane();
	    root.setPadding(new Insets(10));
	    Image image2 = new Image(new File("Files/Images/" + weaponArray[currentWeapon] 
	    		+ ".png").toURI().toString());
	    
	    image1.setImage(image2);
	   
	    root.setTop(btn);
	    root.setCenter(image1);
	    root.setBottom(btn2);
	    root.setLeft(left);
	    root.setRight(right);
	    BorderPane.setAlignment(btn,Pos.CENTER);
	    BorderPane.setAlignment(btn2,Pos.CENTER);
	    BorderPane.setAlignment(image1,Pos.CENTER);
	    BorderPane.setAlignment(left,Pos.CENTER);
	    BorderPane.setAlignment(right,Pos.CENTER);
	    btn.setOnAction( e -> exit());
	    btn2.setOnAction( e -> exit());
	    left.setOnAction( e -> cycleLeft());
	    right.setOnAction( e -> cycleRight());
	    
	    
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
	    double height = screenSize.getHeight(); 
	    double width = screenSize.getWidth();
	    
	    //image1.setImage(new Image("Files/Images/speaker1.png"));
	    Scene scene = new Scene(root, width/8, height/4);
	    primaryStage.setX(0);
	    primaryStage.setY(0);
	    primaryStage.setTitle("Weapons");
	    primaryStage.setScene(scene);
	    primaryStage.show();
  }

  public void showMenu() {
	  System.out.println("show2");
	  primaryStage.show();
	  System.out.println("show2");
  }
  
  public static void launchMenu() {
	  System.out.println("launch1");
  	launch();
  	System.out.println("launch2");
  }
  
  public Stage getStage(){
	  
	  return primaryStage;
  }
  
  public void exit(){
	  System.out.println("hide1");
	  primaryStage.close();
	  System.out.println("hide2");
  }
  
  public void cycleRight(){
	  
	  if (currentWeapon == 2){
		  currentWeapon = 0;
	  }
	  else{
		  currentWeapon = currentWeapon + 1;
	  }
	  
	  Image image = new Image(new File("Files/Images/" +
			  weaponArray[currentWeapon] + ".png").toURI().toString());
	  image1.setImage(image);
	  
	  
  }
  

  public void cycleLeft(){
	  
	  if (currentWeapon == 0){
		  currentWeapon = 2;
	  }
	  else{
		  currentWeapon = currentWeapon - 1;
	  }
	  
	  Image image = new Image(new File("Files/Images/" +
			  weaponArray[currentWeapon] + ".png").toURI().toString());
	  image1.setImage(image);
	  
	  
  }
}