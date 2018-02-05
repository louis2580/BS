package BS;

import java.awt.List;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.text.Position;

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
		ships.add(new Ship("Porte-avion", PORTEAVION_SIZE, PORTEAVION_REACH));
		
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
	}

	private boolean askValidShipDirection() {
		Character direction;
		Scanner scanner = new Scanner(System.in);
		do {
			System.out.printf("Alignement horizontal (h) ou vertical (v) : ");
			direction = scanner.nextLine().charAt(0);
		} while (!direction.toString().equals("h") && !direction.toString().equals("H")
				&& !direction.toString().equals("v") && !direction.toString().equals("V"));
		
		return ((new Character('H')).equals(direction) || (new Character('h')).equals(direction));
		// note here: use "constant".equals(variable) so nullpointer is impossible.
		// probably not needed, but it's best practice in general.
	}

	private Point askValidStartingPoint(Ship ship, boolean horizontal) {
		
		/*
		Scanner scanner = new Scanner(System.in);
		String coord;
		char a = ' ', b = ' ';
		int x = 10, y = 10;
		Point from = new Point(x, y);
		do { 
			System.out.printf("Position de départ : ");

			// Il faut juste entrer les coordonnées comme suit: a5
			coord = scanner.nextLine();
			if (coord.length() == 2)
			{
				a = coord.charAt(0);
				b = coord.charAt(1);
				y = "abcdefghij".indexOf(a);
				x = b-'0';
				from = new Point(x, y);
				
				if (!isInsideBoard(x, y))
				{
					
					System.out.println("Coordonnées non valides !");
				}
			}
			*/

		Point from = new Point();
		
		do 
		{
			System.out.printf("Position de départ : ");
			from = askForValidCoordinates();
		} while (!(isValidStartingPoint(from, ship.getSize(), horizontal)));
		return from;
	}


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
		} while (x<0 || x>=10 || y<0 || y>=10);
		return target;
    }
    
	public boolean isValidStartingPoint(Point from, int size, boolean horizontal) {
		int line = from.x;
		int col = from.y;
		
		boolean isValid = false;
		
		
		// Si placé verticalement
		if (horizontal) {
			if (isInsideBoard(from.x, from.y) && (from.x + size <= BOARD_SIZE)) {
				for (int j = from.x; j < from.x + size; j++) {
					if ((board[line][j] != SHIP_ICON)) {
						isValid = true;
					}
				}
			} 
		} else {
			if (isInsideBoard(from.x, from.y) && (from.y + size <= BOARD_SIZE)) {
				for (int i = from.y; i < from.y + size; i++) {
					if ((board[i][col] != SHIP_ICON)) {
						isValid = true;
					}
				}
			} 
		}
		if (!isValid)
		{
			System.out.printf("Le navire sort du terrain ou chevauche un autre navire! Réessayez \n");
		}
		return isValid;
	}

	/**
	 *
	 * @param position
	 */
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
		
		ship.updatePosition(startingPoint, horizontal);
		
		printBoard();
	}

	private boolean isInsideBoard(int x, int y) {
		return (x < BOARD_SIZE && x >= 0 && y < BOARD_SIZE && y >= 0);
	}

	/////////////////////////////////////////////
	/**
	 *
	 * @param position
	 */
	private void deletePointShipOnBoard(Point del) {
		board[del.y][del.x] = SEA_ICON;
	}

	/**
	 *
	 * @param position
	 */
	private void drawShipOnBoard(Point newPoint) {
		board[newPoint.y][newPoint.x] = SHIP_ICON;
	}

	
	public String Point2Coord(Point pt)
	{
		char y = "abcdefghij".charAt(pt.y);
		return new String("("+y+","+pt.x+")");
	}

	/**
	 * ChangePosition
	 */
	void ChangePosition(String nameShip, String direction, int distance) {
		
		for (int i = 0; i < ships.size(); i++) {
			Ship ship = ships.get(i);

			if (ship.getName() == nameShip) {
				ArrayList<Point> allPointShip = ship.getAllPointShip();
				for (int i1 = 0; i1 < allPointShip.size() - 1; i1++) {
					Point curent = allPointShip.get(i1);
					switch (direction) {
					case "gauche":
						deletePointShipOnBoard(curent);
						curent.x = curent.x - distance;
						ship.setPointShip(i1, curent);
						drawShipOnBoard(curent);
						break;
					case "droite":
						deletePointShipOnBoard(curent);
						printBoard();
						curent.x = curent.x + distance;
						ship.setPointShip(i1, curent);
						drawShipOnBoard(curent);

						break;
					case "haut":
						deletePointShipOnBoard(curent);
						curent.y = curent.y - distance;
						ship.setPointShip(i1, curent);
						drawShipOnBoard(curent);
						break;
					case "bas":
						deletePointShipOnBoard(curent);
						curent.y = curent.y + distance;
						ship.setPointShip(i1, curent);
						drawShipOnBoard(curent);
						break;
					default:
						System.out.println("Rien.");
					}
				}
				printBoard();
			}
		}
	}

	/**
	 *
	 * @param position
	 */
	public static Point ToPoint(char colum, int line) {
		int columNumero = 1;
		for (int i = 0; i < BOARD_LETTERS.length; i++) {
			if (colum == BOARD_LETTERS[i]) {
				columNumero = i + 1;
			}
		}
		Point pointBegin = new Point(columNumero, line);
		return pointBegin;
	}

}