package BS;

public class Game {
	private Player[] players;

	/**
	 * Instantiates a new Game.
	 */
	public Game(boolean vsComputer) {
		if(vsComputer) {
			players = new Player[]{
					new Player(1,false),
					new Player(2,true),
			};
		}else {
			players = new Player[]{
					new Player(1,false),
					new Player(2,false),
			};
		}
	}

	/**
	 * Start.
	 */
	public void start() {
		int i = 0;
		int j = 1;
		int size = players.length;
		Player player = null;

		while(players[0].board.ships.size() > 0 && players[1].board.ships.size() > 0) {
			players[i++ % size].turnToPlay(players[j++ % size]);
			player = (players[0].getLives() < players[1].getLives()) ?
					players[1] :
						players[0];
		}

		System.out.printf("\n***********************\n"
				+ "*   ANEANTISSEMENT    *\n"
				+ "* Joueur %d l'emporte! *\n"
				+ "***********************\n",player.getId());
	}
}
