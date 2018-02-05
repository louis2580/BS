package BS;

import java.util.Scanner;

/**
 * Simulates a Battleship game. 
 * 
 * @author  Adrien Perraud Louis Berger
 * @version 1.0 
 */

public class BattleshipLab
{    
	public static void main(String[] args) {
		Character vsComputer;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.printf("%nLa partie commence ! Voulez-vous jouez contre un ordinateur ? (o/n) : %n%n");
			vsComputer = sc.nextLine().charAt(0);
		} while (!vsComputer.toString().equals("o") && !vsComputer.toString().equals("O")
				&& !vsComputer.toString().equals("n") && !vsComputer.toString().equals("N"));
		if(vsComputer.toString().equals("o") || vsComputer.toString().equals("O")) {
			Game game = new Game(true);
			game.start();
		}
		else {
			Game game = new Game(false);
			game.start();
		}
	}
}

