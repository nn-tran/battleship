package graphics;

import data_container.Data;
import data_container.ScoreKeeper;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class FinishWindow extends Pane {
	private Data data = Data.getInstance();
	private Label victoryCall = new Label();

	public FinishWindow() {
		this.getChildren().addAll(victoryCall);
		this.setOnMousePressed(e -> exit());
	}

	private void recordWinner() {
		ScoreKeeper scoreKeeper = new ScoreKeeper();
		scoreKeeper.readHighScore();
		scoreKeeper.writeHighScore();
	}
	
	/**
	 * updates winner of the game
	 */
	public void updateVictory() {
		recordWinner();
		victoryCall.setText(
				"Player " + data.getTurn() + ", " + data.getTurnPlayer().getName() + " wins in " + data.getCount() + " turn(s)");
	}

	public void exit() {
		//still waiting for the file writer here
	}

}
