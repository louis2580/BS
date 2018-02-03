package battleshipCode;

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
    
    public static final int PLAYER_LIVES = 10; //sum of all the ships

    /**
     * Instantiates a new Player.
     *
     * @param id the id
     */
    public Player(int id) {
        System.out.printf("%n=== Setting up everything for Player %s ==== \n", id);
        this.id = id;
        this.lives = PLAYER_LIVES;
        this.board = new Board();
        board.addShip();
        this.targetHistory = new HashMap<>();
        this.scanner = new Scanner(System.in);
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
        System.out.printf("%n%nPlayer %d, Choisissez les coordonnees que vous voulez toucher (x y) ", id);
        Scanner sc = new Scanner(System.in);
		
		// Première case = from
		System.out.println("Place your first position - Lettre");
		String str = sc.nextLine();
		char carac = str.charAt(0);
		System.out.println("Chiffre :");
		int j = sc.nextInt();
		System.out.println("Vous avez saisi la case : " + carac + j);

		Point target = Board.ToPoint(carac, j);
		sc.reset();

        while(targetHistory.get(target) != null) {
            System.out.print("This position has already been tried");
            //point = new Point(scanner.nextInt(), scanner.nextInt());
        }

        attack(target, opponent);
    }

    /**
     * Attack
     *
     * @param point
     * @param opponent
     */
    private void attack(Point target, Player opponent) {
    	
        Ship ship = opponent.board.targetShip(target);
        boolean isShipHit = (ship != null) ? true : false;

        if(isShipHit) {
            ship.shipWasHit();
            opponent.decrementLiveByOne();
        }
        targetHistory.put(target, isShipHit);
        System.out.printf("Player %d, targets (%d, %d)",
                id,
                (int)target.getX(),
                (int)target.getY());
        System.out.println("...and " + ((isShipHit) ? "HITS!" : "misses..."));
        
        // essai
        opponent.board.ChangePosition("Porte-avion", "droite", 1);
    }
}