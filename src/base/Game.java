package base;

import data_container.Data;
import data_container.PlayerAI;
import data_container.Ship;

import java.util.Scanner;

public class Game {
	Scanner scanner = new Scanner(System.in);
	Input input = new Input();
	Data data = Data.getInstance();

	public Game() {
		// empty constructor, just in case
	}

	/**
	 * method input ships from the console
	 * @param nShip the number of ships to be added for each player
	 * @param index index of the player the ship is being added to
	 */
	public void inputShips(int nShip, int index) {
		for (int i = 0; i < nShip; i++) {
			Ship toAdd;
			toAdd = input.createShip();
			while (data.getTargetPlayer(index).checkCollide(toAdd)) {
				System.out.println("That didn't work... ");
				toAdd = input.createShip();
			}
			data.getTargetPlayer(index).addShip(toAdd);
		}
	}

	/**
	 * setup the board
	 * @param mode game mode
	 */
	private void setup(Data.Mode mode) {
		System.out.println("Player(s), choose how many ships you want: ");
		int nShip;
		nShip = input.numShips();
		data.setNumShip(nShip);
		if (mode == Data.Mode.PVP) {// PvP
			// inputing ships
			for (int i = 0; i < 2; i++) {
				System.out.println("Player " + i + ", please add the ships.");
				inputShips(nShip, i);
			}
			
		} else {// AI
			// inputing ships
			System.out.println("Player 0, please add the ships: ");
			inputShips(nShip, 0);
		}
		System.out.println("Finished setting up board.");
	}
	
	
	/**
	 * the combat loop
	 * @param mode player vs player (0) or player vs bot (1)
	 */
	public void mainLoop(Data.Mode mode) {
		while (true) {
			data.getTurnPlayer().display();
			System.out.println("\n");
			if (data.getTurn() == 0 || mode == Data.Mode.PVP) {
				System.out.println("Player " + data.getTurn() + " to move");
				if (!data.getTurnPlayer().shoot(input.shoot())) {
					continue;
				}
			}
			else {
				if (((PlayerAI) data.getTurnPlayer()).shoot(data.getEnemyPlayer())){
					System.out.println("data.AI chooses " + data.getTurnPlayer().getLastHit());
				}
			}
			
			data.getTurnPlayer().displayTarget(data.getEnemyPlayer());
			System.out.println("\n");

			if (data.getTurnPlayer().win(data.getEnemyPlayer())) {
				System.out.println("Game over, " + data.getTurn() + " wins");
				break;
			}

			data.switchPlayer();
		}
	}

	/**
	 * main method, runs the game
	 * @param args console arguments
	 */
	public static void main(String[] args) {
		Game game = new Game();
		Data data = Data.getInstance();
		Data.Mode mode = game.input.selectMode();
		if ((mode != Data.Mode.NONE)) {
			game.setup(mode);
			data.setMode(mode);
			game.mainLoop(mode);
		}
	}

}
