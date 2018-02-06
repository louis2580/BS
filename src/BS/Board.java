package BS;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
	private char[][] board;
	public ArrayList<Ship> ships;

	private static final char[] BOARD_LETTERS = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j' };
	private static final int BOARD_SIZE = 10;
	private static final char SEA_ICON = '~';
	public static final char SHIP_ICON = 'O';
	public static final char SHIP_IS_HIT_ICON = 'X';
	public static final char SHOT_MISSED_ICON = 'M';

	public static final int PORTEAVION_SIZE = 5;
	public static final int CROISEUR_SIZE = 4;
	public static final int CONTRETORPILLEUR_SIZE = 3;
	public static final int SOUSMARIN_SIZE = 3;
	public static final int TORPILLEUR_SIZE = 2;

	// Portées
	public static final int PORTEAVION_REACH = 2;
	public static final int CROISEUR_REACH = 2;
	public static final int CONTRETORPILLEUR_REACH = 2;
	public static final int SOUSMARIN_REACH = 4;
	public static final int TORPILLEUR_REACH = 5;

	/**
	 * Initialize ships (once).
	 *
	 */
	/*
			new Ship("Croiseur", CROISEUR_SIZE, CROISEUR_REACH),
			new Ship("Contre torpilleur", CONTRETORPILLEUR_SIZE, CONTRETORPILLEUR_REACH),
			new Ship("Sous-marin", SOUSMARIN_SIZE, SOUSMARIN_REACH), 
			new	Ship("Torpilleur", TORPILLEUR_SIZE, TORPILLEUR_REACH)
	*/
		

	/**
	 * Constructor (once)
	 */
	public Board() {
		board = new char[BOARD_SIZE][BOARD_SIZE];

		ships = new ArrayList<Ship>();
		ships.add(new Ship("Porte-avion", 	PORTEAVION_SIZE, 		PORTEAVION_REACH));
		ships.add(new Ship("Croiseur", 		CROISEUR_SIZE, 			CROISEUR_REACH));
		ships.add(new Ship("Destroyer", 	CONTRETORPILLEUR_SIZE, 	CONTRETORPILLEUR_REACH));
		ships.add(new Ship("Sous-marin", 	SOUSMARIN_SIZE, 		SOUSMARIN_REACH));
		ships.add(new Ship("Torpilleur", 	TORPILLEUR_SIZE, 		TORPILLEUR_REACH));
		
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[j][i] = SEA_ICON;
			}
		}
	}

	/**
	 * Print board.
	 */
	public void printBoard() {
		System.out.print("\n\t");

		// Affichage de la grille

		for (int i = 0; i < BOARD_SIZE; i++) {
			System.out.print((i) + "  ");
		}
		// Reviens à la ligne
		System.out.println();
		System.out.println();

		for (int i = 0; i < BOARD_SIZE; i++) {
			System.out.print(BOARD_LETTERS[i] + "\t");
			for (int j = 0; j < BOARD_SIZE; j++) {
				System.out.print(board[i][j] + "  ");
			}
			System.out.println();
		}
		System.out.println();
	}

	void placeShipsOnBoard() {
		System.out.printf("%nPlacez vos bateaux : %n%n");
		// Afficher les ships à placer
		for (int i = 0; i < ships.size(); i++){
			System.out.printf("1 \t Type : "+ ships.get(i).getName() + "\t Taille : " + ships.get(i).getSize() + "\t Portée : " + ships.get(i).getReach());
			System.out.println();
		}

		printBoard();
		for(Ship ship : ships) { //awesome for-each loop is better here
			System.out.printf("\n * Position du %s (taille  %d) * \n", ship.getName(), ship.getSize());
			boolean horizontal = askValidShipDirection();
			Point startingPoint = askValidStartingPoint(ship, horizontal);
			placeValidShip(ship, startingPoint, horizontal);
		}

		printBoard();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Appuyez sur Entrée pour finir votre tour...");
		scanner.nextLine();
	}

	// Appelé pour demander en boucle une orientation au joueur tant qu'il entre une valeur incorrecte
	
	private boolean askValidShipDirection() {
		Character direction;
		Scanner scanner = new Scanner(System.in);
		do {
			System.out.printf("Alignement horizontal (h) ou vertical (v) : ");
			direction = scanner.nextLine().charAt(0);
		} while (!direction.toString().equals("h") && !direction.toString().equals("H")
				&& !direction.toString().equals("v") && !direction.toString().equals("V"));
		
		scanner.reset();
		return ((new Character('H')).equals(direction) || (new Character('h')).equals(direction));
		// note here: use "constant".equals(variable) so nullpointer is impossible.
		// probably not needed, but it's best practice in general.

	}
	
	// Appelé pour demander en boucle une position initiale au joueur tant qu'il entre une valeur incorrecte
	
	private Point askValidStartingPoint(Ship ship, boolean horizontal) {
		
		Point from = new Point();
		
		do 
		{
			System.out.printf("Position de départ : ");
			from = askForValidCoordinates();
		} while (!(isValidStartingPoint(from, ship.getSize(), horizontal)));
		return from;
	}

	// Appelé pour demander en boucle une coordonnée au joueur tant qu'il entre une valeur incorrecte
	
    public Point askForValidCoordinates()
    {
        Scanner sc = new Scanner(System.in);
		String coord;
		char a=' ', b=' ';
		int x=10, y=10;
		Point target = new Point(x, y);

		do {
			// Entrer les coordonnées comme suit: a1
	    	coord = sc.nextLine();
			if (coord.length() == 2)
			{
				a = coord.charAt(0);
				b = coord.charAt(1);
				y = "abcdefghij".indexOf(a);
				x = b-'0';
				target = new Point(x, y);
				
				if (!isInsideBoard(x, y))
				{
					System.out.println("Coordonnées non valides !");
				}
			}
			sc.reset();
		} while (!isInsideBoard(x, y));

		return target;
    }
    
    // Vérifie si le navire peut être placé au point 'from' : dans la grille, pas sur un autre bateau...
	public boolean isValidStartingPoint(Point from, int size, boolean horizontal) {
		int line = from.x;
		int col = from.y;
		
		boolean isValid = true;
		
		
		// Si placé horizontalement
		if (horizontal) {
			if (isInsideBoard(from.x, from.y) && (from.x + size <= BOARD_SIZE)) {
				for (int j = from.x; j < from.x + size; j++) {
					if ((board[col][j] != SEA_ICON)) { 
						// Si il y a un autre bateau en dessous, autre que celui-ci
						isValid = false;
					}
				}
			} else 
			{
				// Si le navire dépasse du terrain
				isValid = false;
			}
		} else {
			if (isInsideBoard(from.x, from.y) && (from.y + size <= BOARD_SIZE)) {
				for (int i = from.y; i < from.y + size; i++) {
					if ((board[i][line] != SEA_ICON)) {
						isValid = false;
					}
				}
			} else
			{
				isValid = false;
			}
		}
		if (!isValid)
		{
			System.out.printf("Le navire sort du terrain ou chevauche un autre navire! Réessayez \n");
		}
		return isValid;
	}

	// place les navires sur le board. Appelé seulement au début
	
	private void placeValidShip(Ship ship, Point startingPoint, boolean horizontal) {
		int xDiff = 0;
		int yDiff = 0;
		/*
		 * 	  1 2 3
		 *    ------> x
		 *  a|
		 *  b|
		 *   V y
		 */
		if (horizontal) {
			xDiff = 1;
		} else {
			yDiff = 1;
		}

		for (int i = 0; i < ship.getSize(); i++) {
			Point currentPoint = new Point(startingPoint.x + i * xDiff, startingPoint.y+ i * yDiff);
			ship.pushPointShip(currentPoint);
			board[currentPoint.y][currentPoint.x] = SHIP_ICON;
		}
		
		// Met à jour les coordonnées des points du navire
		ship.updatePosition(startingPoint, horizontal);
	}
	
	// Met à jour la grille : appelé à chaque tour
	public void updateBoard()
	{
		for (int i = 0; i < BOARD_SIZE; i++)
		{
			for (int j = 0; j < BOARD_SIZE; j++)
			{
				board[i][j] = SEA_ICON;
			}
		}
		for (Ship ship : ships)
		{
			for (Point p : ship.getAllPointShip())
			{
				board[p.y][p.x] = SHIP_ICON;
			}
			for (Point p : ship.getTouchedPoint())
			{
				board[p.y][p.x] = SHIP_IS_HIT_ICON;
			}
		}
	}

	private boolean isInsideBoard(int x, int y) {
		return (x < BOARD_SIZE && x >= 0 && y < BOARD_SIZE && y >= 0);
	}

	// Appelé pour demander en boucle une coordonnée de tir au joueur 
	// tant qu'il entre une valeur incorrecte ou hors de portée
	
	public Point askForValidFiringCoordinates()
	{
		Point target = new Point();
		
		boolean inRange = false;
		do {
			System.out.printf("Tir sur la case : ");
			// Entrer les coordonnées comme suit: a1
			
	    	target = askForValidCoordinates();
			inRange = isInRange(target);

			if (!inRange)
			{
				System.out.println("Case hors de portée !");
			}
		} while (!inRange);
		
		return target;
	}
	
	// Regarde si un point est à portée d'au moins un des navires
	
    public boolean isInRange(Point p)
    {
    	boolean inRange = false;
    	ArrayList<Point> shootablePoints = new ArrayList<Point>();
    	Point startPoint, endPoint;
    	
    	for (Ship ship : ships)
    	{
    		shootablePoints.clear();
    		shootablePoints.addAll(ship.getAllPointShip());
    		startPoint = ship.getPositionFrom();							// Premier point du navire
    		endPoint = ship.getPointShip(ship.getAllPointShip().size() - 1); // Dernier point du navire
    		for (int i = 1; i <= ship.getReach(); i++)
    		{
    			if (ship.isHorizontal())
    			{
    				shootablePoints.add(new Point (startPoint.x - i, startPoint.y));
    				shootablePoints.add(new Point (endPoint.x + i, endPoint.y));
    			} else
    			{
    				shootablePoints.add(new Point (startPoint.x, startPoint.y - i));
    				shootablePoints.add(new Point (endPoint.x, endPoint.y + i));
    			}
    		}
    		if (shootablePoints.contains(p))
    		{
    			inRange = true;
    		}
    	}
    	return inRange;
    }

    
	public String Point2Coord(Point pt)
	{
		char y = "abcdefghij".charAt(pt.y);
		return new String("("+y+","+pt.x+")");
	}

}