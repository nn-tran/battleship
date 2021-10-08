package data_container;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private int num = -1;
	private List<Integer> sqNotHit = new ArrayList<>();
	private List<Integer> sqHit = new ArrayList<>();
	private ArrayList<Ship> shipList = new ArrayList<>();
	private int lastHit;
	

	private int[][] board = new int[10][10];
	private String name = "Anonymous";

	public Player() {
		for (int i = 0; i < 100; i++) {
			board[i / 10][i % 10] = -1;
		}
	}

	public Player(int num) {
		this();
		this.num = num;
	}

	public Player(String name) {
		this();
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * changes name for this player
	 * @param newName the new name to set
	 */
	public void setName(String newName) {
		if (newName!=null && !newName.equals("") && !newName.contains(",") ) {
			System.out.println(newName);
			this.name = newName;
		}
	}

	/**
	 * check if a square is valid for firing at
	 * @param sq the square to test
	 * @return whether it should be shot at or not
	 */
	public boolean canShoot(int sq) {
		return (!(getSqHit().contains(sq) || sq < 0 || sq > 99));
	}
	
	/**
	 * makes a shot for player
	 * @param sq the square to fire at
	 * @return whether the shot was successful
	 */
	public boolean shoot(int sq) {
		if (!canShoot(sq)) {
			lastHit = -1;
			return false;
		} else {
			// otherwise, add new square to list
			getSqHit().add(sq);
			lastHit = sq;
			return true;
		}
	}
	
	/**
	 * checks if the ship can be added to the current board or not (report false for no errors)
	 * @param ship the ship to check
	 * @return whether it is a valid ship to add to ship list
	 */
	public boolean checkCollide(Ship ship) {
		try {
			ship.setNumber(shipList.size());
			if (!ship.checkSelf() || ship.getLength() < 2) {
				// prevent negative ships, loop doesn't run so check returns false (not
				// colliding)
				return true;
			}
			for (int i = ship.getCollisionX()[0]; i <= ship.getCollisionX()[1]; i++) {
				for (int j = ship.getCollisionY()[0]; j <= ship.getCollisionY()[1]; j++) {
					if (!(board[i][j] == ship.getNumber() || board[i][j] == -1)) {
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {// most important to return true when out of bounds
			return true;
		}
	}

	/**
	 * adds a ship to ship list
	 * put the ship on the 'physical' board as well
	 * @param ship the ship to be added
	 */
	public void addShip(Ship ship) {
		for (int i = ship.getCollisionX()[0]; i <= ship.getCollisionX()[1]; i++) {
			for (int j = ship.getCollisionY()[0]; j <= ship.getCollisionY()[1]; j++) {
				board[i][j] = ship.getNumber();
			}
		}
		shipList.add(ship);
		boardUpdate();
	}

	/**
	 * displays board for this player
	 */
	public void display() {
		System.out.println();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (board[i][j] == -1) {
					System.out.print("  .");
				} else
					System.out.print("  " + board[i][j]);
			}
			System.out.println();
		}
		
	}

	/**
	 * displays board of target player as seen by this player
	 * @param player usually the enemy to check for hits
	 */
	public void displayTarget(Player player) {
		System.out.println();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (this.sqHit.contains(i * 10 + j) && player.sqNotHit.contains(i * 10 + j)) {
					System.out.print("  H");
				} else if (this.sqHit.contains(i * 10 + j)) {
					System.out.print("  x");
				} else {
					System.out.print("  .");
				}
			}
			System.out.println();
		}
	}

	/**
	 * checks if successfully destroyed all ships of target player
	 * @param player to check against
	 * @return whether current player has successfully destroyed all ships or not
	 */
	public boolean win(Player player) {
		return (this.sqHit.containsAll(player.sqNotHit));
	}

	/**
	 * updates lists to check for a victory
	 */
	public void boardUpdate() {
		int len = shipList.size();
		for (int i = 0; i < 100; i++) {
			if (this.sqNotHit.contains(i)) {
				continue;
			}
			for (Ship ship : shipList) {
				boolean hit = ship.isHit(i / 10, i % 10);
				if (hit) {
					this.sqNotHit.add(i);
				}
			}
		}
	}

	/**
	 * @return the board
	 */
	public int[][] getBoard() {
		return board;
	}

	/**
	 * @return the player number
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num the player number to set
	 */
	public void setNum(int num) {
		this.num = num;
	}
	
	/**
	 * get size of ship list, used for numbering
	 * @return current number of ships in ship list
	 */
	public int getShipListSize() {
		return shipList.size();
	}

	/**
	 * @return the squares this player have shot at
	 */
	public List<Integer> getSqHit() {
		return sqHit;
	}

	/**
	 * @return the squares that must not get hit
	 * i.e. if all squares in here are also in the enemy's sqHit
	 * the enemy has won the game.
	 */
	public List<Integer> getSqNotHit() {
		return sqNotHit;
	}

	
	public int getLastHit() {
		return lastHit;
	}

	public void setLastHit(int lastHit) {
		this.lastHit = lastHit;
	}
}
