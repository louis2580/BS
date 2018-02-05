package BS;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Player {
	
	private int id;
    private int lives;
    Board board;
    private Map<Point, Boolean> targetHistory;
    private Scanner scanner;
    public boolean canMove;
    public boolean isComputer;
    
    public static final int PLAYER_LIVES = 10; //sum of all the ships, 2 lives/ship

    /**
     * Instantiates a new HumanPlayer.
     *
     * @param id the id
     */
    public Player(int id, boolean isComputer) {
        System.out.printf("%n=== Joueur %s, à toi ! ==== \n", id);
        this.id = id;
        this.lives = PLAYER_LIVES;
        this.board = new Board(isComputer);
        this.targetHistory = new HashMap<>();
        this.scanner = new Scanner(System.in);
        this.canMove = true;
        this.isComputer = isComputer;
     // Ajoute les ships à la création !
        board.placeShipsOnBoard();
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets lives.
     *
     * @return the lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Decrement live by one.
     */
    public void decrementLiveByOne() {
        lives--;
    }

    /**
     * Turn to play.
     *
     * @param opponent the opponent
     */
    public void turnToPlay(Player opponent) {
        System.out.printf("%n%nTour du joueur %d : \n", id);

        // Déplacement
        board.printBoard();
    	if (canMove)
    	{
    		board.MoveShip();
    	} else
    	{
    		System.out.println("Déplacement impossible : vous avez été touché");
    	}

		Point target = new Point();

		do {
			System.out.printf("Tir sur la case : ");

			// Entrer les coordonnées comme suit: a1
			
			target = board.askForValidCoordinates();
		} while (target.x<0 || target.x>=10 || target.y<0 || target.y>=10);
		
		// attaque
        Attack(target, opponent);
        
    	// Montrer la liste de navires restants pour l'adversaire
        ShowListShip();
        this.canMove = true;
    }
    

    /**
     * Attack
     *
     * @param point
     * @param opponent
     */
    private void Attack(Point target, Player opponent) {
    	
    	boolean isShipHit = false;
    	int index = 0;
        for (Ship ship : opponent.board.ships)
        {
        	isShipHit = (ship.getAllPointShip().contains(target)) ? true : false;
        	
	        System.out.printf("Joueur %d, a tiré en %s",id, board.Point2Coord(target));
	        System.out.println("... et " + ((isShipHit) ? "TOUCHE!" : "manque..."));
	        
        	if(isShipHit) {
	            ship.shipWasHit(target);
	            opponent.decrementLiveByOne();
	            opponent.canMove = false;
	            break;
	        }        
        	index++;
        }
        if (isShipHit && opponent.board.ships.get(index).isSunk())
        {
        	opponent.board.ships.remove(index);
        }
    }
    
    public void ShowListShip()
    {
    	for (Ship ship : board.ships)
    	{
    		System.out.printf("\n\n\n\n\n\n\n\n\n\n\nJoueur %d : %s - vie= %d, premier pt : %s\n", this.id, ship.getName(), 
    				ship.getLife(), board.Point2Coord(ship.getPositionFrom()));
    	}
    }
    
    
}