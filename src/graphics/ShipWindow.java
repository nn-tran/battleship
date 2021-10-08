package graphics;

import data_container.Data;
import data_container.Ship;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ShipWindow extends Pane {
	private Data data = Data.getInstance();
	private int[] numShip = new int[2];
	private ShipGrid grid = new ShipGrid();
	private Button addShip = new Button("Add Ship");
	private Button endTurn = new Button("End Turn");
	private boolean isTurnEnd = false;
	private Button reset = new Button("Reset");
	private Scene scene;

	public ShipWindow() {
		//setting up window here
		VBox v = new VBox();
		HBox h = new HBox();
		v.setSpacing(20);
		h.setSpacing(20);
		h.setPadding(new Insets(0, 0, 0, 50));
		v.getChildren().addAll(grid, h);
		h.getChildren().addAll(addShip, endTurn, reset);
		this.getChildren().addAll(v);
		endTurn.setDisable(true);
		addShip.setOnAction(this::handleAddShip);

		endTurn.setOnAction(this::handleEndTurn);

		reset.setOnAction(e -> {//reset to enter a fresh ship
			grid.refresh();
			grid.mouseStat = 0;
			grid.resetPoints();
		});
	}

	public void setNumShips(){
		for (int i = 0; i < 2; ++i ) {
			numShip[i] = data.getNumShip();
		}

	}

	/**
	 * go to the next scene
	 * to be overridden
	 */
	public void nextScene() {}


	/**
	 * handler for the addShip button
	 * @param e button clicked event
	 */
	private void handleAddShip(ActionEvent e) {//button adds ship based on clicks
		endTurn.setDisable(true);
		grid.mouseStat = 0;
		Ship toAdd;
		if (grid.collision[0] == 0) {// vertical ship
			toAdd = Ship.createShip(grid.point1[1], grid.point1[0], grid.collision[1] + 1, 1);
		} else if (grid.collision[1] == 0) {// horizontal ship
			toAdd = Ship.createShip(grid.point1[1], grid.point1[0], grid.collision[0] + 1, 0);
		} else {
			toAdd = new Ship();
		}
		toAdd.setNumber(data.getTurnPlayer().getShipListSize());
		if (!data.getTurnPlayer().checkCollide(toAdd)) {
			data.getTurnPlayer().addShip(toAdd);
		} else {
			numShip[data.getTurn()]++;//if a ship is not valid, up ship count
		}
		grid.refresh();
		numShip[data.getTurn()]--;
		System.out.println(numShip[0] + " " + numShip[1]);
		if (numShip[data.getTurn()] <= 0) {//adding ships end when local numShip is zero
			addShip.setDisable(true);
			grid.refresh();
			endTurn.setDisable(false);

		}
	}

	/**
	 * handler for the endTurn button
	 * @param e button clicked event
	 */
	private void handleEndTurn(ActionEvent e) {//button ends turn for current player and switch player
		if (!isTurnEnd) {
			endTurn.setDisable(true);
			data.switchPlayer();
			reset.fire();
			addShip.setDisable(false);
			isTurnEnd = true;
			endTurn.setText("Finish");
			if (data.getMode() == Data.Mode.AI) {
				numShip[1] = 0;
				endTurn.setDisable(false);
				endTurn.fire();//AI automatically presses its own button
			}
		} else {
			data.switchPlayer();
			nextScene();
		}
	}
}
