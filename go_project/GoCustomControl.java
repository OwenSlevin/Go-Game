package go_project;
//imports
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
public class GoCustomControl extends Control{
	public GoCustomControl(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
		//setThe default skin and generate a board
		setSkin(new GoCustomControlSkin(this));
		this.setStyle("-fx_background-color: yellow;");
		getChildren().add(gameLogic.getBoard());
		
		// add a mouse clicked listener that will try to place a piece on
		// the Go board
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			// overridden method to handle a mouse event
			@Override
			public void handle(MouseEvent event) {
				gameLogic.placePiece(event.getX(), event.getY());		
			}
		});

		// add a key press listener that will reset the board if space is
		// pressed
		setOnKeyPressed(new EventHandler<KeyEvent>() {
			// overridden handle method for key events
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.SPACE)
					gameLogic.resetGame();
			}
		});
	}
	// overridden version of the resize method
	@Override
	public void resize(double width, double height) {
		// call the super class method and resize the board
		super.resize(width, height);
		gameLogic.getBoard().resize(width, height);
	}

	public GoBoard getBoard(){
		return this.gameLogic.getBoard();
	}
	
	GameLogic gameLogic;
}
