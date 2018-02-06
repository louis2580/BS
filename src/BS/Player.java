package BS;

import java.awt.Point;
import java.util.Scanner;

public class Player {
    private int id;
    private int lives;
    Board board;
    public boolean canMove;
    
    public static final int PLAYER_LIVES = 10; //sum of all the ships, 2 lives/ship

    /**
     * Instantiates a new Player.
     *
     * @param id the id
     */
    public Player(int id) {
    	// "Clear" the console
    	System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.printf("%n=== Joueur %s, à toi ! ==== \n", id);
        this.id = id;
        this.lives = PLAYER_LIVES;
        this.board = new Board();
        // Ajoute les ships à la création !
        board.placeShipsOnBoard();
        this.canMove = true;
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
		target = board.askForValidFiringCoordinates();

		// attaque
        Attack(target, opponent);
        
        // Déplacement
    	board.printBoard();
    	if (canMove)
    	{
    		MoveShip();
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
    		System.out.printf("\nJoueur %d : %s - vie= %d, premier pt : %s\n", this.id, ship.getName(), 
    				ship.getLife(), board.Point2Coord(ship.getPositionFrom()));
    	}
    }
    
    public void MoveShip()
    {
    	Scanner sc = new Scanner(System.in);
    	int choixNav;
        
    	System.out.println("\nNavires restants :");
    	System.out.println("0 / Pas de déplacement");
    	for (int i = 0; i < board.ships.size(); i++ )
    	{
    		Ship ship = board.ships.get(i);
    		String lastPoint = board.Point2Coord(ship.getPointShip(ship.getAllPointShip().size()-1));
    		System.out.printf("%d / %s, portée de %d, actuellement en %s -> %s\n", i+1, 
    				ship.getName(), ship.getReach(), board.Point2Coord(ship.getPositionFrom()), lastPoint);
    	}
    	
    	do {
    		choixNav = -1;
	    	System.out.print("\nChoisissez un navire à déplacer : ");
	    	if (sc.hasNextInt())
	    	{
				choixNav = sc.nextInt();
	    	} else
	    	{
				System.out.println("Saisie invalide");
				sc.reset();
				sc.nextLine();
    		}
			
		} while (!(choixNav >= 0 && choixNav <= board.ships.size()));
    	if (choixNav == 0) return; //  Si le joueur ne veut pas faire de déplacement, il entre '0'
    	
    	Ship shipToMove = board.ships.get(choixNav-1);
		Point targetPos = new Point();
		boolean tooFar;
		do {
			System.out.printf("Déplacement vers la case : ");

			// Entrer les coordonnées comme suit: a1
			
			targetPos = board.askForValidCoordinates();
			tooFar = Math.abs(targetPos.x - shipToMove.getPositionFrom().x) + Math.abs(targetPos.y - shipToMove.getPositionFrom().y)>2;
			
			if (tooFar)
			{
				System.out.println("\nCase trop éloignée. Réessayez.");
			}
		} while (tooFar || !board.isValidStartingPoint(targetPos, shipToMove.getSize(), shipToMove.isHorizontal()));
		
		shipToMove.updatePosition(targetPos, shipToMove.isHorizontal());
		board.ships.set(choixNav - 1, shipToMove);
		board.updateBoard();

    	board.printBoard();
		System.out.printf("Navire déplacé en %s\n", board.Point2Coord(targetPos));
    } 
    
    
}