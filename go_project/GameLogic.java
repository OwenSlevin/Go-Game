package go_project;

import java.util.Timer;
import java.util.TimerTask;

//imports
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
public class GameLogic {
	Timer timer = new Timer();
	public GameLogic(GoBoard goBoard) {
		super();
		this.goBoard = 	goBoard;
		this.player1_score = 0;
        this.player2_score = 0;
		//Making a SimpleIntegerProperty which will bind to the TextField in the controlPanel
		this.scoreProperty1 = new SimpleIntegerProperty(this.player1_score);
		this.scoreProperty2 = new SimpleIntegerProperty(this.player2_score);
		in_play = true;
        current_player = 2;
        opposing = 1;
        passCount = 0;
        prevCopy = new GoBoard();
        this.scoreProperty1.setValue(this.player1_score);
		this.scoreProperty2.setValue(this.player2_score);
		//chainID = 0;
		resetGame();
	}
	public GoBoard getBoard() {
		return goBoard;
	}
	//method to reset go board and variables----------------------------------------------------
	public void resetGame() {
		player1_score = 0;
		player2_score = 0;
		player1_prisoner = 0;
		player2_prisoner = 0;
		in_play = true;
        current_player = 2;
        opposing = 1;
        passCount = 0;
        this.scoreProperty1 = new SimpleIntegerProperty(this.player1_score);
		this.scoreProperty2 = new SimpleIntegerProperty(this.player2_score);
		this.scoreProperty1.setValue(this.player1_score);
		this.scoreProperty2.setValue(this.player2_score);
		goBoard.resetGame();
	}
	private void copyBoard() {
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 7; j++) {
				prevCopy.render[i][j].setPiece(goBoard.render[i][j].getPiece());
			}
		}
	}
	//public method to place piece is correct coordinates---------------------------------------
	public void placePiece(double x, double y) {
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 7; j++) {
				prevCopy.render[i][j].setPiece(0);
			}
		}
		double cell_width= goBoard.getCellWidth();
		double cell_height= goBoard.getCellHeight();
		final int cellx = (int) (x/cell_width);
		final int celly = (int) (y/cell_height);
		
		if(!in_play)
			return;
		if(getPiece(cellx,celly)!=0)
			return;
		if (!canMove(cellx, celly))
            return;
		if(ko()==true) {
			for(int i = 0; i < 7; i++) {
				for(int j = 0; j < 7; j++) {
			goBoard.render[i][j].setPiece(prevCopy.render[i][j].getPiece());
				}
			}
		}
			
		
	    this.goBoard.placePiece(cellx, celly, current_player);
	    passCount=0;
	    capture();
	    updateScores();
	    determineEndgame();
	    //this.assignChain(cellx, celly);
	    //this.capture(cellx, celly);
	    swapPlayers();
	  //Sets a 60 second timer to each play
	    timer.schedule(new TimerTask() {
	    	  @Override
	    	  public void run() {
	    		  swapPlayers();
	    		  
	    		  System.out.println("Time is up, next player!");
	    	  }
	    	}, 2*60*1000);
	    
	    if (current_player == 2) {
    		System.out.println("It is Blacks turn");
    	}
    	else{
    		System.out.println("It is Whites turn");
    	}
	    
	    copyBoard();
	    if (current_player == 2) {
    		System.out.println("It is Blacks turn");
    	}
    	else{
    		System.out.println("It is Whites turn");
    	}
	    
	}
	
	
	// private method that determines who won the game-------------------------------------------
    private void determineWinner() {
        updateScores();
        in_play = false;
        System.out.println("Game Over!");
        
        if (player1_score == player2_score) {
            System.out.println("Draw");
        }
        else if (player1_score > player2_score) {
            System.out.println("Player 1 is the winner!");
        }
        else {
            System.out.println("Player 2 is the winner!");
        }
    }
    // private method for updating the player scores---------------------------------------------
    private void updateScores() {
    	player1_score = 0;
    	player2_score = 0;
    	for(int i = 0; i<7; i++) {
    		for(int j = 0; j<7; j++) {
    			
    			if(goBoard.render[i][j].getPiece()==1) {
    				player2_score++;
    			}
    			else if(goBoard.render[i][j].getPiece()==2) {
    				player1_score++;
    			}
    			else {
    				if(determineLiberties(i,j)==0) {
    					if (current_player==1)
    						player2_score++;
    					else if(current_player==2)
    						player1_score++;
    				}
    				swapPlayers();
    				if(determineLiberties(i,j)==0) {
    					if (current_player==1)
    						player2_score++;
    					else if(current_player==2)
    						player1_score++;
    				}
    				swapPlayers();
    			}
    		}
    	}
    	player1_score = player1_score + player1_prisoner;
    	player2_score = player2_score + player2_prisoner;
    	
    	this.scoreProperty1.setValue(player1_score);
    	this.scoreProperty2.setValue(player2_score);
  
    }
    //private method that will determine liberties------------------------------------------------
    private int determineLiberties(final int x, final int y) {
    	int liberties = 0;

        if (getPiece(x - 1, y) == 0 || getPiece(x - 1, y) == current_player)
            liberties++;
        if (getPiece(x + 1, y) == 0 || getPiece(x + 1, y) == current_player)
        	liberties++;
        if (getPiece(x, y - 1) == 0 || getPiece(x, y - 1) == current_player)
        	liberties++;
        if (getPiece(x, y + 1) == 0 || getPiece(x, y + 1) == current_player)
        	liberties++;

        return (liberties);
    }
    //private method to get piece value-----------------------------------------------------------
    private int getPiece(final int x, final int y) {
        if (this.checkArea(x, y))
            return (goBoard.render[x][y].getPiece());
        return (-1);
    }
    //-------------------------------------------------------------------------------------------
    private boolean checkArea(final int x, final int y) {
        if ((x >= 0 && x < 7) && (y >= 0 && y < 7)) {
            return (true);
        }
        return (false);
    }
    //Determine if it is a valid move------------------------------------------------------------
    public boolean canMove(int x, int y){
    	if(goBoard.render[x][y].getPiece() != 0)
            return (false);
        if (determineLiberties(x, y) <= 0)
            return (false);
        return (true);
    }
    
    
    // private method for swapping the players----------------------------------------------------
    private void swapPlayers() {
    	if(current_player == 2) {
    		current_player = 1;
    		opposing = 2;
    	}
    	else if(current_player == 1) {
    		current_player = 2;
    		opposing = 1;
    	}
    }

	// This method is called when binding the SimpleIntegerProperty scoreProperty in this class to the TextField tf_score in controlPanel
	public IntegerProperty getPlayer1Score() {
		return scoreProperty1;
	}
	public IntegerProperty getPlayer2Score() {
		return scoreProperty2;
	}
	//This method is called when a player presses the pass button-------------------------------
	public void passButton() {
		if(!in_play)return;
		passCount = passCount +1;
		if(current_player == 2) {
			player2_prisoner++;;
			swapPlayers();
			
		}
		else if(current_player == 1) {
			player1_prisoner++;
			swapPlayers();
		}
		updateScores();
		determineEndgame();
	}
	//public method to check if the game is still in play---------------------------------------
	public void determineEndgame() {
		int temp = current_player; //variable to hold the current player
		boolean playable = false; // variable to determine if game is finished
		if(passCount == 2)
			determineWinner();
		
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 7; j++) {
				if(goBoard.render[i][j].getPiece()== 0) {
					if(determineLiberties(i,j)>0) {
						playable = true;
					}
					else {
						current_player = opposing;
						if(determineLiberties(i,j)>0) {
							playable = true;
						}
					}
					
				}
				current_player = temp;
			}
		}
		if(playable == false) {
			determineWinner();
		}
		else return;
	}
	// get the current player----------------------------------------------------------------
	public int getPlayer() {
		return current_player;
	}
	// public method for KO rule-------------------------------------------------------------
	private boolean ko() {
		boolean b = true;
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 7; j++) {
				if(prevCopy.render[i][j].getPiece()!=goBoard.render[i][j].getPiece())
					b = false;
			}
		}
		return b;
	}
	//method to determine if a piece has been captured--------------------------------------
		public void capture() {
			player1_prisoner = 0;
			player2_prisoner = 0;
			for (int i = 0; i < 7; i++) {
				for(int j = 0; j < 7; j++) {
					if(determineLiberties(i,j)==0) {
						if(current_player == 1) {
							player2_prisoner++;
						}
						else {
							player1_prisoner++;
						}
						goBoard.render[i][j].setPiece(0);
					}
					swapPlayers();
					if(determineLiberties(i,j)==0) {
						if(current_player == 1) {
							player2_prisoner++;
						}
						else {
							player1_prisoner++;
						}
						goBoard.render[i][j].setPiece(0);
					}
					swapPlayers();
				}
			}
			updateScores();
		}
		
	/* method for capturing a chain********************************************************************
	private void checkCapture(final int x, final int y, final int player) {
        if (!this.checkChunk(x, y, player)) {
            for (GoPiece piece : this.pieceChunk) {
                this.hasCaptured = true;
                piece.setPiece(Go.GAME_EMPTY_SPACE);
            }
        }
    }

    private boolean checkChunk(final int x, final int y, final int player) {
        this.pieceChunk = new ArrayList<GoPiece>();
        this.chunkHasLiberty = false;

        if (this.getPiece(x, y) == player) {
            this.pieceChunk.add(this.goBoard.pieces[x][y]);
        }
        for (int i = 0; i < this.pieceChunk.size(); ++i) {
            this.checkPiece(this.pieceChunk.get(i).getX(), this.pieceChunk.get(i).getY(), player);
        }
        return (this.chunkHasLiberty);
    }
	
	//method to capture pieces------------------------------------------------------------------
	private void capture(int i, int j) {
		int countLiberties = 0;
    	for(int x=0;x<7;x++) {
    		for(int y=0;y<7;y++) {
    			 		 
    			 if(goBoard.render[x][y].getChain() == goBoard.render[i][j].getChain()) {
    				countLiberties = countLiberties + determineLiberties(x,y);
    			 }
    		}
    	}
    	
    	if(countLiberties == 0) {
    		for(int x=0;x<7;x++) {
	    		for(int y=0;y<7;y++) {
	    			int chain = this.goBoard.render[i][j].getChain();
	    			if(this.goBoard.render[x][y].getChain()==chain) {
	    				this.goBoard.render[x][y].setPiece(0);
	    				if(current_player == 2) {
	    					player1_score = player1_score+1;
	    				}
	    				else{
	    					player2_score = player2_score+1;
	    				}
	    			}
	    			
	    		}
    		}
    	}
	}
    //Assign the stone to a chain---------------------------------------------------------------
    public void assignChain(int x, int y) {
    	if (determineLiberties(x, y) == 4) {
    		chainID=chainID+1;
    		goBoard.render[x][y].createChain(chainID);
    	}
    	else if(x==0&&determineLiberties(x, y) == 3) {
    			chainID=chainID+1;
	    		goBoard.render[x][y].createChain(chainID);
    		}
    	else if(y==0&&determineLiberties(x, y) == 3) {
			chainID=chainID+1;
    		goBoard.render[x][y].createChain(chainID);
		}
    		
    	Stone[] surrounding = new Stone[4];
    	
    		// GET NEIGHBORS 
    		//CHECKS TO AVOID ARRAY OUT OF BOUNDS
    		if (x > 0) {
    		 surrounding[0] = goBoard.render[x - 1][y];
    	    }
    	    if (x < 7) {
    	    	surrounding[1] = goBoard.render[x + 1][y];
    	    }
    	    if (y > 0) {
    	    	surrounding[2] = goBoard.render[x][y - 1];
    	    }
    	    if (y < 7) {
    	    	surrounding[3] = goBoard.render[x][y + 1];
    	    }
    	
	
    	for (Stone s : surrounding) {
    		 if (s == null) {
    	            return;
    	        }
    		 
    		 if(s.getPiece()==current_player) {
    			 this.goBoard.render[x][y].catchChain(s);
    		 }
    		 if(s.getPiece()==current_player && s.getChain() != goBoard.render[x][y].getChain()) {
    			 s.setChain(goBoard.render[x][y].getChain());
    		 }
    	}
    }
	*/
		
	//************************************************************************************

	//private int chainID;
	private GoBoard prevCopy;	
	private GoBoard goBoard;
	// the current player who is playing and who is his opposition
    private int current_player;
    private int opposing;
    // is the game currently in play
    private boolean in_play;
    // current scores of player 1 and player 2
    private Integer player1_score;
    private Integer player2_score;
    private int player1_prisoner;
    private int player2_prisoner;
    private IntegerProperty scoreProperty1; 
	private IntegerProperty scoreProperty2; 
	public int passCount;
}
