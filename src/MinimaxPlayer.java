
/**
 * Connect4 player interface.  Any class implementing this interface can
 * by dynamically loaded into the game as a player.
 * 
 * @author Adlai White
 *
 */
public class MinimaxPlayer implements Player
{
	int id;
	int opponent_id;
    /**
     * Return the name of this player.
     * 
     * @return A name for this player
     */
    public String name() {
        return "Minnie";
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
    	this.id = id; //id is your player's id, opponent's id is 3-id
    	opponent_id = 3-id;
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
    	 if (board.isFull()) //making sure the board is not full before proceeding
    	 { 
    		 throw new Error ("Complaint! The board is full!"); 
    	 }
    	 //intializing important variables that we need
    	 int col = 0; 
    	 int maxDepth = 1;
    	 int[] scoreV2 = new int[7];
    	 
    	 while (!arb.isTimeUp() && maxDepth <= board.numEmptyCells()) //making sure the time has not run out while the bot calculates best options
    	 {
    		 for (int i = 0; i <= 6; i++) 
    	    	{
    			 scoreV2[i] = -100000; //setting score super low to ensure no errors
    			 
    	    		if(board.isValidMove(i)) // making sure the move is valid
    	    		{
    	    			board.move(i, id);
    	    			
    	    			scoreV2[i] = minimax(board, maxDepth, true, arb);
    	        		
    	        		board.unmove(i, id);
    	    		
    	    		}
    	    		
    	    	}
    		 
    		 	int trueScore;
    	    	trueScore = -100;
    	    	
    	    	for (int x = 0; x < scoreV2.length; x++) //a for loop that runs through all scores to find the best one
    	    	{
    	    		if (scoreV2[x] > trueScore) 
    	    		{

    	    			trueScore = scoreV2[x];
    	    			arb.setMove(x);
    	    		}
    	    		
    	    	}
    
    		 maxDepth ++; //making sure that the depth is increase so when process is done again the necessary bounds will be updated properly
    	 }
    	  
    }
    	
public int minimax (Connect4Board board, int depth, boolean isMaximizing, Arbitrator arb) 
{
	
	if (depth == 0  || board.isFull() || arb.isTimeUp()) //setting up bounds that ensure everything is in order
	{
		return score(board);
	}
//	if isMaximizing then
//	bestScore = -1000
//	for each possible next move do
//		board.move(...)
//		bestScore = Math.max(bestScore, minimax(child, depth - 1, FALSE)) 
//		board.unmove(...)
//	return bestScore
	int bestScore;
	
	if (isMaximizing) 
	{
		bestScore = -1000;
		
		for (int i = 0; i <= 6; i++) 
		{
			if(board.isValidMove(i)) 
			{
				
				board.move(i,id);
				
				bestScore = Math.max(bestScore, minimax(board, depth - 1, false, arb));
				
				board.unmove(i, id);
			}
		}
		return bestScore;
	}
	
	
	
	
	

//else /* minimizing player */ 
//	bestScore = 1000
//	for each possible next move do
//		board.move(...)    	
//		bestScore = Math.min(bestScore, minimax(child, depth - 1, TRUE)) 
//		board.unmove(...)
//	return bestScore	

	else
	{
		bestScore = 1000;
		
		for (int i = 0; i <= 6; i++) 
		{
			if(board.isValidMove(i)) {
				
				board.move(i, opponent_id);
				
				bestScore = Math.min(bestScore, minimax(board, depth - 1, true, arb));
				
				board.unmove(i, opponent_id);
			}
			
		}
		return bestScore;
	}
	
	
	
	
}	
	public int score(Connect4Board board) 
	{
    	return calcScore(board, id) - calcScore(board, opponent_id);
	
	}
    	  	
    
    
	public int calcScore(Connect4Board board, int id)
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
