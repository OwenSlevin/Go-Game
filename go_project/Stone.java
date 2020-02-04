package go_project;
//imports
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.transform.Translate;
public class Stone extends Group {
	// default constructor for the class
		public Stone(int player) {
			this.player = player;
			piece = new Ellipse();
			t = new Translate();
			piece.getTransforms().add(t);
			if(player == 1) {
				piece.setFill(Color.WHITE);
				piece.setStroke(Color.BLACK);
			}else if(player == 2){
				piece.setFill(Color.BLACK);
			}
			else
				piece.setFill(Color.TRANSPARENT);
			getChildren().addAll(piece);
		}
			
		// overridden version of the resize method to give the piece the correct size
		@Override
		public void resize(double width, double height) {
						
			piece.setCenterX(width / 2.0);
	        piece.setCenterY(height / 2.0);
		    piece.setRadiusX(width / 2.0);
		    piece.setRadiusY(height / 2.0);
		}
		
		// overridden version of the relocate method to position the piece correctly
		@Override
		public void relocate(double x, double y) {
			super.relocate(x, y);
			t.setX(x); t.setY(y);
		}
			
		// public method that will swap the colour and type of this piece
		public void swapPiece() {

		}
			
		// method that will set the piece type
		public void setPiece(final int type) {
			if(type==1) {
				piece.setVisible(true);
				player = 1;
				piece.setFill(Color.WHITE);
				piece.setStroke(Color.BLACK);
			}
			else if(type==2){
				piece.setVisible(true);
				player = 2;
				piece.setFill(Color.BLACK);
			}
			else {
				piece.setFill(Color.TRANSPARENT);
				piece.setStroke(Color.TRANSPARENT);
			}
		}
			
		// returns the type of this piece
		public int getPiece() {
			return player;
		}
		public int createChain(int c) {
			chain = c;
			return chain;
		}
		public int getChain() {
			return chain;
		}
		public void setChain(int c) {
			chain = c;
		}
		public void catchChain(Stone s) {
			this.chain = s.getChain();
		}
		public void setLiberties(int lib) {
			liberties = lib;
		}
		public int getLiberties() {
			return liberties;
		}
			
		// private fields
		private int player;		// the player that this piece belongs to
		private Ellipse piece;	// ellipse representing the player's piece
		private Translate t;	// translation for the player piece
		private int chain;
		private int liberties;
}
