package BS;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
	private char[][] board;
	public ArrayList<Ship> ships;
	private boolean isComputerBoard;

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
	public Board(boolean isComputerBoard) {
		this.isComputerBoard = isComputerBoard;

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

	/**
	 * Place player board.
	 */
	void placeShipsOnBoard() {

		if(isComputerBoard) {
			for(Ship ship : ships) { 
				boolean horizontal = (Math.random() < 0.5);
				Point startingPoint = new Point();
				do 
				{
					startingPoint.x = (int) Math.floor(Math.random()*9);
					startingPoint.y = (int) Math.floor(Math.random()*9);
				} while (!(isValidStartingPoint(startingPoint, ship.getSize(), horizontal)));
				placeValidShip(ship, startingPoint, horizontal);
			}
		}
		else {
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
	}

	/**
	 * Direction of the ship.
	 */
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

	/**
	 * Ask startingPoint.
	 */
	private Point askValidStartingPoint(Ship ship, boolean horizontal) {
		Point from = new Point();
		do 
		{
			System.out.printf("Position de départ : ");
			from = askForValidCoordinates();
		} while (!(isValidStartingPoint(from, ship.getSize(), horizontal)));
		return from;
	}

	/**
	 * Check the starting point format.
	 */
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
		if (!(isValid) && !(isComputerBoard))
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

	public void MoveShip()
	{
		int choixNav;
		boolean tooFar;
		Point targetPos = new Point();

		// If ordinateur
		if(isComputerBoard) {
			do {
				choixNav = (int) Math.floor(Math.random()*5);

			} while (!(choixNav >= 0 && choixNav <= ships.size()));

			if (choixNav == 0) return; //  l'ordinateur choisit de ne pas se déplacer
			Ship shipToMove = ships.get(choixNav-1);

			do {
				targetPos.x = (int) Math.floor(Math.random()*9);
				targetPos.y = (int) Math.floor(Math.random()*9);

				tooFar = Math.abs(targetPos.x - shipToMove.getPositionFrom().x) + Math.abs(targetPos.y - shipToMove.getPositionFrom().y)>2;
			} while (tooFar || !isValidStartingPoint(targetPos, shipToMove.getSize(), shipToMove.isHorizontal()));

			shipToMove.updatePosition(targetPos, shipToMove.isHorizontal());
			ships.set(choixNav - 1, shipToMove);

			printBoard();
			System.out.printf("Navire déplacé en %s\n", Point2Coord(targetPos));
		}
		// Else player
		else {
			Scanner sc = new Scanner(System.in);
			System.out.println("\nNavires restants :");
			System.out.println("0 / Pas de déplacement");
			for (int i = 0; i < ships.size(); i++ )
			{
				Ship ship = ships.get(i);
				String lastPoint = Point2Coord(ship.getPointShip(ship.getAllPointShip().size()-1));
				System.out.printf("%d / %s, portée de %d, actuellement en %s -> %s\n", i+1, 
						ship.getName(), ship.getReach(), Point2Coord(ship.getPositionFrom()), lastPoint);
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

			} while (!(choixNav >= 0 && choixNav <= ships.size()));

			if (choixNav == 0) return; //  Si le joueur ne veut pas faire de déplacement, il entre '0'

			Ship shipToMove = ships.get(choixNav-1);
			do {
				System.out.printf("Déplacement vers la case : ");

				// Entrer les coordonnées comme suit: a1

				//targetPos = askForValidCoordinates();
				tooFar = Math.abs(targetPos.x - shipToMove.getPositionFrom().x) + Math.abs(targetPos.y - shipToMove.getPositionFrom().y)>2;

				if (tooFar)
				{
					System.out.println("\nCase trop éloignée. Réessayez.");
				}
			} while (tooFar || !isValidStartingPoint(targetPos, shipToMove.getSize(), shipToMove.isHorizontal()));

			shipToMove.updatePosition(targetPos, shipToMove.isHorizontal());
			ships.set(choixNav - 1, shipToMove);

			printBoard();
			System.out.printf("Navire déplacé en %s\n", Point2Coord(targetPos));
		}
	}

	private boolean isInsideBoard(int x, int y) {
		return (x < BOARD_SIZE && x >= 0 && y < BOARD_SIZE && y >= 0);
	}


	public String Point2Coord(Point pt)
	{
		char y = "abcdefghij".charAt(pt.y);
		return new String("("+y+","+pt.x+")");
	}
}