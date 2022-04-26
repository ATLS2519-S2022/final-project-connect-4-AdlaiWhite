
/**
 * Connect4 player interface.  Any class implementing this interface can
 * by dynamically loaded into the game as a player.
 * 
 * @author Adlai White
 *
 */
public class Greedyplayer implements Player
{
	int id;
    /**
     * Return the name of this player.
     * 
     * @return A name for this player
     */
    public String name() {
        return "Greedy";
    }

  
    /**
     * Initialize the player. The game calls this method once,
     * before any calls to calcMove().
     * 
     * @param id integer identifier for the player (can get opponent's id via 3-id);
     * @param msecPerMove time allowed for each move
     * @param rows the number of rows in the board
     * @param cols the number of columns in the board
     */
    public void init(int id, int msecPerMove, int rows, int cols) {
    	this.id = id;
    }

    
    /**
     * Called by driver program to calculate the next move.
     *  
     * @param board current connect 4 board
     * @param oppMoveCol column of opponent's most recent move; -1 if this is the first move 
     * 		  of the game; note that the board may not be empty on the first move of the game!
     * @param arb handles communication between game and player
     * @throws TimeUpException If the game determines the player has run out of time
     */
    public void calcMove(Connect4Board board, int oppMoveCol, Arbitrator arb) throws TimeUpException
    { 
    	int[] scoreV2 = new int[7]; //creating an array to hold all possible options for best score
    	
    	for (int i = 0; i <= 6; i++) //a for loop ensuring that we go through all connect 4 columns
    	{
    		if(board.isValidMove(i)) // making sure move is valid before proceeding so we don't have nay negative 1 errors
    		{
    			
    			board.move(i,id); 
    		    
        		scoreV2[i] = calcScore(board , id); // saving the score for said i column
        		
        		board.unmove(i, id);
    			
    		}
    		else 
    		{
    			scoreV2[i] = -100;
    		}
    	}
    	
    	int trueScore;
    	trueScore = -100;
    	
    	for (int x = 0; x < scoreV2.length; x++) // a for loop going through all saved scored to find the best one
    	{
    		if (scoreV2[x] > trueScore) 
    		{

    			trueScore = scoreV2[x];
    			arb.setMove(x); //ensuring that the move is set and saved
    		}
    		
    	}
    	 
    }
    
	public int calcScore(Connect4Board board, int id) // bringing in code that calculates the score
	{
		final int rows = board.numRows();
		final int cols = board.numCols();
		int score = 0;
		// Look for horizontal connect-4s.
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c <= cols - 4; c++) {
				if (board.get(r, c + 0) != id) continue;
				if (board.get(r, c + 1) != id) continue;
				if (board.get(r, c + 2) != id) continue;
				if (board.get(r, c + 3) != id) continue;
				score++;
			}
		}
		// Look for vertical connect-4s.
		for (int c = 0; c < cols; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c) != id) continue;
				if (board.get(r + 1, c) != id) continue;
				if (board.get(r + 2, c) != id) continue;
				if (board.get(r + 3, c) != id) continue;
				score++;
			}
		}
		// Look for diagonal connect-4s.
		for (int c = 0; c <= cols - 4; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c + 0) != id) continue;
				if (board.get(r + 1, c + 1) != id) continue;
				if (board.get(r + 2, c + 2) != id) continue;
				if (board.get(r + 3, c + 3) != id) continue;
				score++;
			}
		}
		for (int c = 0; c <= cols - 4; c++) {
			for (int r = rows - 1; r >= 4 - 1; r--) {
				if (board.get(r - 0, c + 0) != id) continue;
				if (board.get(r - 1, c + 1) != id) continue;
				if (board.get(r - 2, c + 2) != id) continue;
				if (board.get(r - 3, c + 3) != id) continue;
				score++;
			}
		}
		return score;
	}
    
    
    
    	
    
       
}
