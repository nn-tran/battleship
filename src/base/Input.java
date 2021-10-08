package base;

import java.util.Scanner;

import data_container.Data;
import data_container.Ship;
import java.lang.String;

public class Input {
	private Scanner scanner = new Scanner(System.in);
	private final int X = 10;
	private final int Y = 10;
	private final String INVALID = "Not within board area, please input valid number: ";

	public Input() {//empty constructor
	}

	/**
	 * Method takes input from console and get the number of ships to be placed
	 * 
	 * @return the number of ships, integer
	 */
	public int numShips() {
		int numShips;
		System.out.print("How many ships do you want? ");
		numShips = scanner.nextInt();
		while (numShips < 1) {
			System.out.println("No, try again ");
			numShips = scanner.nextInt();
		}
		return numShips;
	}

	/**
	 * Read numbers from console
	 * @param msg intro message
	 * @param err error message
	 * @param floor floor number inclusive
	 * @param ceil number inclusive
	 * @return the result number
	 */
	private int askForNumber(String msg, String err, int floor, int ceil) {
		int res;
		System.out.print(msg);
		res = scanner.nextInt();
		while (res > ceil || res < floor) {
			System.out.print(err);
			res = scanner.nextInt();
		}
		return res;
	}
	
	/**
	 * Method asks the user to enter a coordinate to fire at does not check for
	 * duplicates
	 * 
	 * @return the coordinates as an int array of size 2 i.e. {x,y}
	 */
	public int shoot() {
		int row = askForNumber("Select x: ", INVALID, 0, X);
		int column = askForNumber("Select y: ", INVALID, 0, Y);
		return row * Y + column;
	}
	
	/**
	 * Ask user for ship length, row number, column number, and orientation for each
	 * ship
	 * 
	 * @return the ship with all of its data, except number
	 */
	public Ship createShip() {
		int length = askForNumber("Please input the size of the ship between 2-5: ", "Not valid length, try again: ", 2, 5);
		int row = askForNumber("Select a row: ", INVALID, 0, X);
		int column = askForNumber("Select a column: ", INVALID, 0, Y);
		int orientation = askForNumber("Select an orientation,\n" + "0 for horizontal or 1 for vertical: ",
			"Not valid orientation", 0, 1);
		return Ship.createShip(row, column, length, orientation);
	}

	/**
	 * Method asks for the mode to play, or to exit.
	 * @return one of {0,1,2} for PvP, PvAI, or to quit
	 */
	public Data.Mode selectMode() {
		int mode = askForNumber("Type 0 to play against Player,\n1 to play against AI,\nor 2 to quit: ",
				"Please enter 0, 1, or 2: ", 0, 2);
		if (mode == 0) return Data.Mode.PVP;
		else if (mode == 1) return Data.Mode.AI;
		else return Data.Mode.NONE;
	}
	
}
