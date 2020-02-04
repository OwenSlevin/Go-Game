package go_project;
//imports
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
public class GoBoard extends Pane{
	public GoBoard() {
		super();
		this.getChildren().add(new Label("The Board"));
		
		render = new Stone[7][7];
		horizontal = new Line[7];
		vertical = new Line[7];
		horizontal_t = new Translate[7];
		vertical_t = new Translate[7];
		
		initialiseLinesBackground();
		initialiseRender();
	}
	public void placePiece(final int x, final int y, final int cp) {
		render[x][y].setPiece(cp);
	}
	public Double getCellWidth(){
		return cell_width;
	}
	public Double getCellHeight(){
		return cell_height;
	}
	// overridden version of the resize method to give the board the correct size
	@Override
	public void resize(double width, double height) {
		super.resize(width,  height);
		//Size of each cell
		cell_width = width/7.0;
		cell_height = height/7.0;
		//Resize background to full size of widget
		background.setWidth(width); background.setHeight(height);
		// set a new y on the horizontal lines and translate them into place
		horizontalResizeRelocate(width);
		// set a new x on the vertical lines and translate them into place
		verticalResizeRelocate(height);
		//Resize and relocate pieces
		pieceResizeRelocate();
	}
	//private method for resizing and relocating horizontal lines
	private void horizontalResizeRelocate(final double width) {
		double halfH = cell_height/2;
		int x = 1;
		for(int i = 0; i < 7; i++) {
			horizontal_t[i].setY((cell_height * x)- halfH);
			x++;
			horizontal[i].setStartX(halfH);
			horizontal[i].setEndX(width-halfH);
		}
	}
	// private method for resizing and relocating the vertical lines
	private void verticalResizeRelocate(final double height) {
		double half = cell_width/2;
		int x = 1;		
		for(int i = 0; i < 7; i++) {
			vertical_t[i].setX((cell_width * x)- half);
			x++;
			vertical[i].setStartY(half);
			vertical[i].setEndY(height-	half);
		}
	}
	// private method for resizing and relocating all the pieces
	private void pieceResizeRelocate() {
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 7; j++) {
				render[i][j].relocate(i * cell_width, j * cell_height);
				render[i][j].resize(cell_width, cell_height);
			}
		}
	}
	
	// private method that will initialise the background and the lines
		private void initialiseLinesBackground() {
			//Background
			background = new Rectangle();
			background.setFill(Color.BEIGE);
			//Horizontal
			for(int i = 0; i < 7; i++) {
				horizontal[i] = new Line();
				horizontal[i].setStroke(Color.BLACK);
				horizontal[i].setStartX(0);horizontal[i].setStartY(0);horizontal[i].setEndY(0);
				//Transform
				horizontal_t[i] = new Translate(0, 0);
				horizontal[i].getTransforms().add(horizontal_t[i]);
			}
			//Vertical
			for(int i = 0; i < 7; i++) {
				vertical[i] = new Line();
				vertical[i].setStroke(Color.BLACK);
				vertical[i].setStartX(0);vertical[i].setStartY(0);vertical[i].setEndX(0);
				//Transform
				vertical_t[i] = new Translate(0, 0);
				vertical[i].getTransforms().add(vertical_t[i]);
			}
			getChildren().add(background);
			for(int i = 0; i < 7; i++)
			getChildren().addAll(horizontal[i]);
				
			for(int i = 0; i < 7; i++)
			getChildren().addAll(vertical[i]);
			
			initialiseRender();
		}
		// private method that will initialise everything in the render array
		private void initialiseRender() {
			for(int i = 0; i < 7; i++) {
				for(int j = 0; j< 7; j++) {
					render[i][j] = new Stone(0);
					getChildren().addAll(render[i][j]);
				}
			}
		}	
		// public method for resetting the game
		public void resetGame() {
			initialiseLinesBackground();
			initialiseRender();
		}
	
	// rectangle that makes the background of the board
	private Rectangle background;
	// arrays for the lines that makeup the horizontal and vertical grid lines
	private Line[] horizontal;
	private Line[] vertical;
	// arrays holding translate objects for the horizontal and vertical grid lines
	private Translate[] horizontal_t;
	private Translate[] vertical_t;
	// arrays for the internal representation of the board and the pieces that are
	// in place
	public Stone[][] render;
	// the width and height of a cell in the board
	private double cell_width;
	private double cell_height;
	
}
