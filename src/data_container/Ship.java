package data_container;

public class Ship {
	// Constants for ships orientation
	protected static final int HORIZONTAL = 0;
	protected static final int VERTICAL = 1;

	// instance variables for a ship
	protected int row;
	protected int column;
	protected int length;
	protected int number;
	public static Ship createShip(int x, int y, int l, int o) {
		if (o == HORIZONTAL) {
			return new HShip(x,y,l);
		} else {
			return new VShip(x,y,l);
		}
	}
	
	// constructors
	protected Ship(int x, int y, int l) {
		row = x;
		column = y;
		length = l;
		update();
	}

	public Ship() {
		this(-1,-1,-1);
	}

	// getter and setter methods for the ship's row location
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		if (row < 0) {
			throw new IllegalArgumentException("Invalid ship location, enter a number between 0 and 9");
		}
		this.row = row;
	}

	// getter and setter methods for the ships column location
	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		if (column < 0) {
			throw new IllegalArgumentException("Invalid ship location, enter a number between 0 and 9");
		}
		this.column = column;
	}

	// getter and setter methods for the ships length
	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		if (length < 2) {
			throw new IllegalArgumentException("Invalid ship length, enter a number between 2 and 5");
		}
		this.length = length;
	}

	// getter and setter methods for the ships number
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	// getter and setter methods for the ships vertical or horizonal orientation
	public int getOrientation() {
		return -1;
	}

	/**
	 * Method to check if the current ship has been shot from coordinates
	 * 
	 * @param row
	 *            x coordinate, top left is (0,0)
	 * @param column
	 *            y coordinate
	 * @return whether a square of the ship has been hit or not, boolean
	 */
	public boolean isHit(int row, int column) {
		return false;
	}


	/**
	 * Updater, set coordinates for collision checks
	 */
	public void update() {
		}

	/**
	 * checks validity of self
	 * @return whether the ship is valid or not
	 */
	public boolean checkSelf() {
		return false;
	}

	/**
	 * Get X-dimension collision 
	 * @return {start, end}
	 */
	public int[] getCollisionX() {
		return new int[] {row, row};
	}
	
	/**
	 * Get Y-dimension collision 
	 * @return {start, end}
	 */
	public int[] getCollisionY() {
		return new int[] {column, column};
	}
	
	
		
}
