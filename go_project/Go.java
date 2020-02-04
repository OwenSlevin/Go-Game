package go_project;

//imports
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class Go extends Application{
	// overridden init method
		public void init(){
			
			playButton = new Button("Play");
			howTo = new Button("How To Play");
			quitButton = new Button("Quit");
			playButton.setMaxWidth(Double.MAX_VALUE);
			howTo.setMaxWidth(Double.MAX_VALUE);
			quitButton.setMaxWidth(Double.MAX_VALUE);
			menu_lb = new Label("GO GAME");
			//I was just messing around with fonts*******************************************8
			menu_lb.setFont(Font.font("Verdana", FontWeight.BOLD,40));
			menu_lb.setTextFill(Color.FIREBRICK);
			menu = new VBox();
			menu.setSpacing(20);
			menu.setAlignment(Pos.CENTER);
			menu.getChildren().addAll (menu_lb, playButton, howTo, quitButton);					
			//Menu color
			menu.setStyle("-fx-background-color: #EEE8AA;");
			
			bp_layout = new BorderPane();				    // create layout
			board = new GoBoard();					        // creates a board
			gameLogic = new GameLogic(board); 	        	// create gameLogic and pass board to gameLogic so gameLogic can call methods from board
			customControl = new GoCustomControl(gameLogic); // create customControl and pass gameLogic to customControl so customControl can call methods from gameLogic
			controlPanel = new GoControlPanel(gameLogic);   // create controlPanel and pass gameLogic to controlPanel so customControl can call methods from gameLogic
	
		
			menu.setPadding(new Insets(100, 100, 100, 100));
			bp_layout.setCenter(menu);
			
	}

	// overridden start method
	public void start(Stage primaryStage) {
		// set a title, size and the stack pane as the root of the scene graph
		primaryStage.setTitle("Go");
		
		
		primaryStage.setScene(new Scene(bp_layout, 500, 400));
		
		//Listener for play button
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				bp_layout.setCenter(customControl);
				bp_layout.setRight(controlPanel);
				controlPanel.setStyle("-fx-background-color: #EEE8AA;");
				System.out.println("Black starts");
			}
		});
		//Listener for help button
		howTo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//Example of how to do rules****************************************************
				rules = new VBox();
				Label howTo_lb = new Label("How To Play");
				howTo_lb.setFont(Font.font("Verdana", FontWeight.BOLD,20));
				howTo_lb.setTextFill(Color.FIREBRICK);
				Text tb_rule1 = new Text("1. Place your first piece anywhere on the board");
				Text tb_rule2 = new Text("2. Completely surround empty spaces to score");
				Text tb_rule3 = new Text("3. You can also capture an opponents piece as a prisoner");
				Text tb_rule4 = new Text("4. A players piece is captured when it is surrounded by opposing pieces");
				Text tb_rule5 = new Text("5. If you cant make a move you can pass");
				Text tb_rule6 = new Text("6. If you pass the opponent take one of your pieces as prisoner");
				Text tb_rule7 = new Text("7. If the pass is used twice in a row the game ends");
				Text tb_rule8 = new Text("8. The score is determined by territory controlled and prisoners captured");
				Text tb_rule9 = new Text("9. The winner is the player with the highest score");
				
				rules.setStyle("-fx-background-color: #EEE8AA;");
				rules.setSpacing(10);
				rules.setPadding(new Insets(50, 50, 50, 50));
				rules.getChildren().addAll (howTo_lb,tb_rule1,tb_rule2,tb_rule3,tb_rule4,tb_rule5,tb_rule6,tb_rule7,tb_rule8,
											tb_rule9,playButton, quitButton);
				bp_layout.setCenter(rules);
				
			}
		});
		//Listener for quit button
		quitButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.close();
				
			}
		});
		
		
		//primaryStage.setScene(new Scene(bp_layout, 500, 400));
		primaryStage.show();
	}
	// overridden stop method
	public void stop() {

	}

		// entry point into our program for launching our javafx applicaton
	public static void main(String[] args) {		
		launch(args);
	}

	// private fields 
	private BorderPane bp_layout;
	private GoCustomControl customControl;
	private GoControlPanel controlPanel;
	private GameLogic gameLogic; 
	private GoBoard board; 
	private Button playButton;
	private Button howTo;
	private Button quitButton;
	private VBox menu;
	private VBox rules;
	private Label menu_lb;
}
