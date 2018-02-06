package BS;

import java.awt.Point;
import java.util.Scanner;

public class Player {
	
	private int id;
    private int lives;
    Board board;
    public boolean canMove;
    public boolean isComputer;
    
    public static final int PLAYER_LIVES = 10; //sum of all the ships, 2 lives/ship

    /**
     * Instantiates a new Player (human or computer).
     *
     * @param id the id
     */

    public Player(int id, boolean isComputer) {// "Clear" the console
	System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.printf("%n=== Joueur %s, à toi ! ==== \n", id);
        this.id = id;
        this.lives = PLAYER_LIVES;
        this.board = new Board(isComputer);
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
        
    	// "Clear" the console
    	System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    	
    	System.out.printf("%nTour du joueur %d : \n", id);
		System.out.println("Appuyez sur Entrée pour commencer...");

		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		
    	// Montrer la liste de navires restants de l'adversaire
        opponent.ShowListShip();
		System.out.println("Votre grille : ");
		board.printBoard();
        
		Point target;
		
		if (!isComputer)
		{
			target = board.askForValidFiringCoordinates();
		} else
		{
			target = board.randomShoot();
		}

		// attaque
        Attack(target, opponent);
        
        board.printBoard();
    	
        // Déplacement
    	
        
        if (canMove)
    	{
    		board.MoveShip();
    	} else
    	{
    		System.out.println("Déplacement impossible : vous avez été touché");
    	}
    	
        this.canMove = true;
        
		System.out.println("Appuyez sur Entrée pour finir votre tour...");

		scanner.nextLine();
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
        	// On regarde si la case appartient à un navire et qu'elle n'a pas déjà été touhé
        	isShipHit = (ship.getAllPointShip().contains(target) 
        			 && !ship.getTouchedPoint().contains(target));
        	
        	if(isShipHit) {
	            ship.shipWasHit(target);
	            opponent.decrementLiveByOne();
	            opponent.canMove = false;
	            break;
	        }        
        	index++;
        }

        System.out.printf("Joueur %d, a tiré en %s",id, board.Point2Coord(target));
        System.out.println("... et " + ((isShipHit) ? "TOUCHE!" : "manque..."));
        
        if (isShipHit && opponent.board.ships.get(index).isSunk())
        {
        	opponent.board.ships.remove(index);
        }
    }
    
    public void ShowListShip()
    {
    	for (Ship ship : board.ships)
    	{
    		System.out.printf("Joueur %d : %s - vie= %d\n", 
    				this.id, ship.getName(), ship.getLife());
    	}
    }
}