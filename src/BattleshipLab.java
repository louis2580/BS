package battleshipCode;

import battleshipCode.Board;


/**
 * Simulates a Battleship game. 
 * 
 * @author  Adrien Perraud
 * @version 1.0 
 */

public class BattleshipLab
{    
	public static void main(String[] args) {
		/*Board board = new Board();
		board.printBoard();
		board.addShip();*/
		
		Game game = new Game();
        game.start();
	}
}

