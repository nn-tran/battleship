package graphics;

import data_container.Data;
import data_container.PlayerAI;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class FireWindow extends Pane {
	private Data data = Data.getInstance();
	private FireGrid enemy = new FireGrid() {
		@Override
		public void injected() {
			fire.setDisable(false);
			if (data.getMode() != Data.Mode.AI) {
				nuke.setDisable(nukeUsed[data.getTurn()]);
			} else {
				nuke.setDisable(true);
			}
			self.refresh();
		}
	};
	private DisplayGrid self = new DisplayGrid();
	private Button fire = new Button("Confirm target");
	private Button nuke = new Button("Nuke");
	private boolean[] nukeUsed = { false, false };

	public FireWindow() {
		VBox full = new VBox();
		HBox grids = new HBox();
		HBox buttons = new HBox();
		full.setSpacing(20);
		grids.setSpacing(20);
		buttons.setSpacing(20);
		grids.getChildren().addAll(self, enemy);
		buttons.getChildren().addAll(fire, nuke);
		full.getChildren().addAll(grids, buttons);
		this.getChildren().addAll(full);
		fire.setDisable(true);
		nuke.setDisable(true);
		// fire button registers the point on click, attempts to shoot at that point
		// then check for any victory
		// and switch to next player
		fire.setOnAction(this::handleFire);

		// nuke button nukes a square instead of firing at it
		// uses nukeSquare
		nuke.setOnAction(this::handleNuke);
	}

	
	/**
	 * cleans up after every shot
	 */
	private void afterFire() {
		data.count();
		gameOver();
		data.switchPlayer();
		nuke.setDisable(true);
	}
	
	/**
	 * checks if a player has won, also switch to next window
	 * @return whether the turn player has won or not
	 */
	private boolean gameOver() {
		if (data.getTurnPlayer().win(data.getEnemyPlayer())) {
			nextScene();
			return true;
		}
		return false;
	}
	
	private boolean lost() {
		return data.getEnemyPlayer().win(data.getTurnPlayer());
			
	}

	/**
	 * tries to hit every square around and including target square
	 * 
	 * @param sq the target square
	 */
	private void nukeSquare(int sq) {
		if (data.getTurnPlayer().canShoot(sq)) {
			int[][] sqs = new int[3][3];
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					sqs[i + 1][j + 1] = (this.enemy.point[0] + i) + (this.enemy.point[1] + j) * 10;
				}
			}
			for (int k = 0; k < 9; k++) {
				data.getTurnPlayer().shoot(sqs[k / 3][k % 3]);
			}
		}
	}

	/**
	 * refresh the two displaying grids
	 */
	public void refresh() {
		self.refresh();
		enemy.refresh();
	}

	/**
	 * handles fire button
	 * @param e button click event
	 */
	private void handleFire(ActionEvent e) {
		fire.setDisable(true);
		enemy.refresh();
		if (data.getMode() == Data.Mode.AI && data.getTurn() == 1) {
			((PlayerAI) data.getTargetPlayer(1)).shoot(data.getTargetPlayer(0));
			afterFire();
		} else {
			int sq = this.enemy.point[0] + this.enemy.point[1] * 10;
			boolean success = data.getTurnPlayer().shoot(sq);
			afterFire();
			if (!success) return;
		}

		if (data.getMode() == Data.Mode.AI && data.getTurn() == 1
				&& !gameOver()
				&& !lost()) {
			fire.setDisable(false);
			fire.fire();
			self.refresh();
		}
		data.switchPlayer();
		data.count();
	}

	/**
	 * handles nuke button
	 * @param e button click event
	 */
	private void handleNuke(ActionEvent e) {
		enemy.refresh();
		int sq = this.enemy.point[0] + this.enemy.point[1] * 10;
		if (data.getTurnPlayer().canShoot(sq) && sq / 10 != 9 && sq / 10 != 0// cannot nuke edges
				&& sq % 10 != 9 && sq % 10 != 0) {
			nukeSquare(sq);
			nukeUsed[data.getTurn()] = true;
			afterFire();
		}
		nuke.setDisable(nukeUsed[data.getTurn()]);
		fire.setDisable(true);
		data.switchPlayer();
		data.count();
	}

	/**
	 * go to the next scene
	 * to be overridden
	 */
	public void nextScene() {}
}
