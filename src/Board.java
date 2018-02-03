package battleshipCode;

import java.awt.Point;
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

	/**
	 * Initialize ships (once).
	 *
	 */

	static {
		ships = new Ship[]{
				new Ship("Porte-avion", PORTEAVION_SIZE),
				new Ship("Croiseur", CROISEUR_SIZE),
				new Ship("Contre torpilleur", CONTRETORPILLEUR_SIZE),
				new Ship("Sous-marin", SOUSMARIN_SIZE),
				new Ship("Torpilleur", TORPILLEUR_SIZE)
		};
	}

	/**
	 * Constructor
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

		// Écris la première ligne
		for(int i = 0; i < BOARD_SIZE; i++) {
			System.out.print(BOARD_LETTERS[i] + "\t");
		}

		// Reviens à la ligne
		System.out.println();

		// Les lignes suivantes
		for(int i = 0; i < BOARD_SIZE; i++) {
			System.out.print((i+1) + "\t");
			for(int j = 0; j < BOARD_SIZE; j++) {
				System.out.print(board[i][j] + "\t");
			}
			System.out.println();
		}
	}

	public void addShip() {
		// Afficher le board 
		printBoard();
		
		// Afficher les ships
		for (int i = 0; i < ships.length; i++){
			System.out.printf("1 \t name:"+ ships[i].getName() + "\t \t size:" + ships[i].getSize());
			System.out.println();
		}

		// Placement
		for(int i = 0; i < ships.length-3; i++) {
			Ship ship = ships[i];


			Scanner sc = new Scanner(System.in);
			Scanner sc2 = new Scanner(System.in);
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
						ship.setPosition(from, to);
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
	}

	/**
	 *
	 * @param position
	 */
	private void drawShipOnBoard(Point from, Point to) {
		for(int i = (int) from.getY() - 1; i < to.getY(); i++) {
			for(int j = (int) from.getX() - 1; j < to.getX(); j++) {
				board[i][j] = SHIP_ICON;
			}
		}
		printBoard();
	}
	
	/**
	 *
	 * @param position
	 */
	private void deleteShipOnBoard(Point from, Point to) {
		for(int i = (int) from.getY() - 1; i < to.getY(); i++) {
			for(int j = (int) from.getX() - 1; j < to.getX(); j++) {
				board[i][j] = SEA_ICON;
			}
		}
		printBoard();
	}

	/**
	 * Target ship ship.
	 *
	 * @param point the point
	 * @return ship
	 */
	public Ship targetShip(Point target) {
		boolean isHit = false;
		Ship hitShip = null;

		for(int i = 0; i < ships.length; i++) {
			Ship ship = ships[i];
			if(ship.getPositionFrom() != null && ship.getPositionTo() != null) {
				if(isPositionOccupied(ship.getPositionFrom(), ship.getPositionTo())) {
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
	}

	private void updateShipOnBoard(Point point, final char result) {
		int x = (int) point.getX() - 1;
		int y = (int) point.getY() - 1;
		board[y][x] = result;
	}

	/**
	 * ChangePosition
	 */
	void ChangePosition(String nameShip, String direction, int distance) {

		for(int i = 0; i < ships.length-3; i++) {
			Ship ship = ships[i];

			if(ship.getName() == nameShip) {
				Point from = ship.getPositionFrom();
				Point to = ship.getPositionTo();
				deleteShipOnBoard(from, to);

				if(!(from.x < distance && from.y < distance)) {
					switch (direction)
					{
					case "gauche":
						from.x = from.x - distance;
						ship.setPosition(from, to);
						drawShipOnBoard(from,to);
						break;
					case "droite":
						from.x = from.x + distance;
						ship.setPosition(from, to);
						drawShipOnBoard(from,to);
						break;
					case "haut":
						from.y = from.y - distance;
						ship.setPosition(from, to);
						drawShipOnBoard(from,to);
						break;
					case "bas":
						from.y = from.y + distance;
						ship.setPosition(from, to);
						drawShipOnBoard(from,to);
						break;
					default:
						System.out.println("Rien.");       
					}
				}
				
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
	 * Distance between points double.
	 *
	 * @param from the from
	 * @param to   the to
	 * @return the double
	 */
	public static double distanceBetweenPoints(Point from, Point to) {
		double x1 = from.getX();
		double y1 = from.getY();
		double x2 = to.getX();
		double y2 = to.getY();

		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2)) + 1;
	}
}
