package go_project;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;
public class GoControlPanel extends Pane{
	public GoControlPanel(GameLogic gameLogic) {
		super();
		this.gameLogic = gameLogic;
		this.player1_score = new Label("Player 1 Score");
		this.tf_player1_score = new TextField();
		this.player2_score = new Label("Player 2 Score");
		this.tf_player2_score = new TextField();
		this.pass = new Button("Pass");
		pass.setMaxWidth(Double.MAX_VALUE);
		this.reset = new Button("Reset");
		reset.setMaxWidth(Double.MAX_VALUE);
		
		// Binding the SimpleIntegerProperty scoreProperty in GoGameLogic to the TextField tf_score
		this.tf_player1_score.textProperty().bindBidirectional(this.gameLogic.getPlayer1Score(), new NumberStringConverter());
		this.tf_player2_score.textProperty().bindBidirectional(this.gameLogic.getPlayer2Score(), new NumberStringConverter());
		this.vb = new VBox(5);
		vb.setPadding(new Insets(10, 10, 10, 10));
		this.getChildren().add(vb);
		vb.getChildren().addAll (new Label("Control Panel"), player1_score,tf_player1_score, 
															player2_score, tf_player2_score,pass, reset);
		
		
		//listener for pass button
		pass.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				gameLogic.passButton();
			}
		});
		//listener for pass button
		reset.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				gameLogic.resetGame();
			}
		});
	}
	
	private GameLogic gameLogic;
	private VBox vb; 
	private Button pass;
	private Button reset;
	private Label player1_score;
	private Label player2_score;
	private TextField tf_player1_score;
	private TextField tf_player2_score;
}
