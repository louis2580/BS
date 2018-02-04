package BS;

import java.awt.List;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.text.Position;

public class Board {
	private char[][] board;
	private static final Ship[] ships;

	private static final char[] BOARD_LETTERS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
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

	public static final int PORTEAVION_REACH = 5;
	public static final int CROISEUR_REACH = 4;
	public static final int CONTRETORPILLEUR_REACH = 3;
	public static final int SOUSMARIN_REACH = 3;
	public static final int TORPILLEUR_REACH = 2;


	/**
	 * Initialize ships (once).
	 *
	 */
	static {
		ships = new Ship[]{
				new Ship("Porte-avion", PORTEAVION_SIZE, PORTEAVION_REACH),
				new Ship("Croiseur", CROISEUR_SIZE, CROISEUR_REACH)
				/*new Ship("Contre torpilleur", CONTRETORPILLEUR_SIZE, CONTRETORPILLEUR_REACH),
				new Ship("Sous-marin", SOUSMARIN_SIZE, SOUSMARIN_REACH),
				new Ship("Torpilleur", TORPILLEUR_SIZE, TORPILLEUR_REACH)*/
		};
	}

	/**
	 * Constructor (once)
	 */
	public Board() {
		board = new char[BOARD_SIZE][BOARD_SIZE];

		for(int i = 0; i < BOARD_SIZE; i++) {
			for(int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = SEA_ICON;
			}
		}
	}

	/**
	 * Print board.
	 */
	public void printBoard() {
		System.out.print("\t");
		for(int i = 0; i < BOARD_SIZE; i++) {
			System.out.print(BOARD_LETTERS[i] + "\t");
		}
		// Reviens à la ligne
		System.out.println();

		for(int i = 0; i < BOARD_SIZE; i++) {
			System.out.print((i+1) + "\t");
			for(int j = 0; j < BOARD_SIZE; j++) {
				System.out.print(board[i][j] + "\t");
			}
			System.out.println();
		}
	}


	void placeShipsOnBoard() {
		System.out.printf("%nAlright - Time to place out your ships%n%n");
		// Afficher les ships à placer
		for (int i = 0; i < ships.length; i++){
			System.out.printf("1 \t name:"+ ships[i].getName() + "\t \t size:" + ships[i].getSize());
			System.out.println();
		}

		printBoard();
		for(Ship ship : ships) { //awesome for-each loop is better here
			boolean horizontal = askValidShipDirection();
			Point startingPoint = askValidStartingPoint(ship, horizontal);
			placeValidShip(ship, startingPoint, horizontal);
		}
	}

	private boolean askValidShipDirection() {
		Character direction;
		Scanner scanner = new Scanner(System.in);
		do {
			System.out.println("Do you want to place the ship horizontally (H) or vertically (V)?");
			direction = scanner.nextLine().charAt(0);

		}
		while ( !(new Character('H')).equals(direction) && !(new Character('V')).equals(direction));
		return (new Character('H')).equals(direction);
		//note here: use "constant".equals(variable) so nullpointer is impossible.
		//probably not needed, but it's best practice in general.
	}


	private Point askValidStartingPoint(Ship ship, boolean horizontal) {
		Scanner scanner = new Scanner(System.in);
		Point from = new Point();
		do { //note: do-while more useful here
			System.out.printf("%nEnter position of %s (length  %d): ", ship.getName(), ship.getSize());

			// Il faut juste entrer les coordonnées comme suit (1;2)
			from = new Point(scanner.nextInt(), scanner.nextInt());

		}
		while(!(isValidStartingPoint(from, ship.getSize(), horizontal)));
		return from;
	}

	private boolean isValidStartingPoint(Point from, int size, boolean horizontal) {
		int line = from.y -1;
		int col = from.x -1;

		if(horizontal) {
			if(isInsideBoard(from.x -1,from.y - 1) && (from.x + size -1 <= BOARD_SIZE)) {
				for(int j = from.x -1; j < from.x + size -1; j++) {
					if(!(board[line][j] == SHIP_ICON)) {
						return true;
					}
				}
			}
		} else {
			if(isInsideBoard(from.x -1,from.y - 1) && (from.y + size -1 <= BOARD_SIZE)) {
				for(int i = from.y -1; i < from.y + size -1; i++) {
					if(!(board[i][col] == SHIP_ICON)) {
						return true;
					}
				}
			}
		}
		System.out.printf("Out of Bords or there's already a ship here ! Try again \n");
		return false;
	}

	/**
	 *
	 * @param position
	 */
	private void placeValidShip(Ship ship, Point startingPoint, boolean horizontal) {
		int xDiff = 0;
		int yDiff = 0;
		if(horizontal) {
			xDiff = 1;
		} else {
			yDiff = 1;
		}
		for(int i = 0; i < ship.getSize() ; i++) {
			Point currentPoint = new Point(startingPoint.y -1 + i*yDiff, startingPoint.x -1 +i*xDiff);
			ship.pushPointShip(currentPoint);
			board[currentPoint.x][currentPoint.y] = SHIP_ICON;
		}  
		printBoard();
	}

	private boolean isInsideBoard(int x, int y){
		return x <= BOARD_SIZE && x >= 0
				&& y <= BOARD_SIZE && y >= 0
				&& x <= BOARD_SIZE && x >= 0
				&& y <= BOARD_SIZE && y >= 0;
	}
















	/////////////////////////////////////////////
	/**
	 *
	 * @param position
	 */
	private void deletePointShipOnBoard(Point del) {
		board[del.x][del.y] = SEA_ICON;
	}

	/**
	 *
	 * @param position
	 */
	private void drawShipOnBoard(Point newPoint) {
		board[newPoint.x][newPoint.y] = SHIP_ICON;
	}

	/**
	 * Target ship ship.
	 *
	 * @param point the point
	 * @return ship
	 */
	/*public Ship targetShip(Point target) {
		boolean isHit = false;
		Ship hitShip = null;

		for(int i = 0; i < ships.length; i++) {
			Ship ship = ships[i];
			if(ship.getPositionFrom() != null && ship.getPositionTo() != null) {

				if(isCaseOccupied(target.x, target.y)) {
					isHit = true;
					hitShip = ship;
					break;
				}

			}
		}
		final char result = isHit ? SHIP_IS_HIT_ICON : SHOT_MISSED_ICON;
		updateShipOnBoard(target, result);
		printBoard();

		return (isHit) ? hitShip : null;
	}*/


	/*private void updateShipOnBoard(Point point, final char result) {
		int x = (int) point.getX() - 1;
		int y = (int) point.getY() - 1;
		board[y][x] = result;
	}*/

	/**
	 * ChangePosition
	 */
	void ChangePosition(String nameShip, String direction, int distance) {

		for(int i = 0; i < ships.length-1; i++) {
			Ship ship = ships[i];

			if(ship.getName() == nameShip) {
				ArrayList<Point> allPointShip = ship.getAllPointShip();
				for(int i1 = 0; i1 < allPointShip.size()-1 ; i1++) {
					Point curent = allPointShip.get(i1);
					switch (direction)
					{
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
				columNumero = i+1;
			}
		}
		Point pointBegin = new Point(columNumero, line);
		return pointBegin;
	}


	/**
	 *
	 * @param position
	 * @return
	 */
	private boolean isPositionOccupied(Point from, Point to) {
		boolean isOccupied = false;

		outer:
			for(int i = (int) from.getY() - 1; i < to.getY(); i++) {
				for(int j = (int) from.getX() - 1; j < to.getX(); j++) {
					if(board[i][j] == SHIP_ICON) {
						isOccupied = true;
						break outer;
					}
				}
			}


		return isOccupied;
	}

	/**
	 *
	 * @param position
	 * @return
	 */
	private boolean isCaseOccupied(int x, int y) {
		boolean isOccupied = false;
		if(board[x][y] == SHIP_ICON) {
			isOccupied = true;
		}
		return isOccupied;
	}
}

/**
 * Distance between points double.
 *
 * @param from the from
 * @param to   the to
 * @return the double
 */
/*public static double distanceBetweenPoints(Point from, Point to) {
	double x1 = from.getX();
	double y1 = from.getY();
	double x2 = to.getX();
	double y2 = to.getY();

	return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2)) + 1;
}*/

/*public void addShip() {
// Afficher le board 
printBoard();

// Afficher les ships à placer
for (int i = 0; i < ships.length; i++){
	System.out.printf("1 \t name:"+ ships[i].getName() + "\t \t size:" + ships[i].getSize());
	System.out.println();
}

// Phase de placement ATTENTION j'ai laissé volontairement à 1 seul placement//
for(int i = 0; i < ships.length-4; i++) {
	Ship ship = ships[i];

	// A refaire la méthode des 2 scanners //
	Scanner sc = new Scanner(System.in);
	Scanner sc2 = new Scanner(System.in);
	////////////////////////////////////////

	Point from = new Point();
	Point to = new Point();

	boolean isShipPlacementLegal = false;
	System.out.printf("Position of %s (size  %d): ", ship.getName(), ship.getSize());

	while(!isShipPlacementLegal) {
		try {
			// Première case = from
			System.out.println("Place your first position");
			System.out.println("Saisissez une lettre :");
			String str = sc.nextLine();
			char carac = str.charAt(0);
			System.out.println("Saisissez un chiffre :");
			int j = sc.nextInt();
			System.out.println("Vous avez saisi la case : " + carac + j);

			// Deuxième case = to
			System.out.println("Placer la dernière position - Saisissez une lettre:");
			String str2 = sc2.nextLine();
			char carac2 = str2.charAt(0);
			System.out.println("Saisissez un chiffre :");
			int j2 = sc2.nextInt();
			System.out.println("Vous avez saisi la case : " + carac2 + j2);

			from = ToPoint(carac, j);
			to = ToPoint(carac2, j2);
			sc.reset();
			sc2.reset();

			while(ship.getSize() != distanceBetweenPoints(from, to)) {
				System.out.printf("The ship currently being placed on the board is of length: %d. Change your coordinates and try again \n" ,
						ship.getSize());

				// Première case = from
				System.out.println("Place your first position");
				System.out.println("Saisissez une lettre :");
				char carac3 = sc.next().charAt(0);
				System.out.println("Saisissez un chiffre :");
				j = sc.nextInt();
				System.out.println("Vous avez saisi la case : " + carac + j);

				// Deuxième case = to
				System.out.println("Placer la dernière position");
				System.out.println("Saisissez une lettre :");
				char carac4 = sc2.next().charAt(0);
				System.out.println("Saisissez un chiffre :");
				j2 = sc2.nextInt();
				System.out.println("Vous avez saisi la case : " + carac2 + j2);

				from = ToPoint(carac3, j);
				to = ToPoint(carac4, j2);
				sc.reset();
				sc2.reset();
			}

			if(!isPositionOccupied(from,to)) {
				drawShipOnBoard(from, to);

				// Déclaration d'un tableau de points / contiendra toutes les cases du bateau
				Point allShipPoints[] = new Point[ships[i].getSize()];

				for(int i = 0; i < ships[i].getSize() -1; i++) {

				}

				ship.setPosition(allShipPoints);
				isShipPlacementLegal = true;
			}
			else {
				System.out.println("A ship in that position already exists - try again");
			}

		} catch(IndexOutOfBoundsException e) {
			System.out.println("Invalid coordinates - Outside board");
		}
	}

}
}*/