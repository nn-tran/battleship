package graphics;

import data_container.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DisplayGrid extends GridPane {
	protected Data data = Data.getInstance();
	protected ObservableList<Node> labelList;

	public DisplayGrid() {
		char[] letterList = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
		for (int i = 1; i < 11; i++) {
			Label label = new Label("\n      " + letterList[i - 1]);
			GridPane.setConstraints(label, i, 0);
			this.getChildren().add(label);
		}

		for (int i = 1; i < 11; i++) {
			Label label = new Label(String.format("    %d    ", i));
			GridPane.setConstraints(label, 0, i);
			this.getChildren().add(label);
		}
		labelList = FXCollections.observableArrayList(this.getChildren());
		this.refresh();
	}

	/**
	 * adds an interactible square to the grid
	 * meant to be overridden by child classes
	 * @param colIndex column index of grid to add to
	 * @param rowIndex row index of grid to add to
	 */
	protected void addInteractible(int colIndex, int rowIndex) {
		//empty since DisplayGrid itself should not be interacted with
	}

	/**
	 * creates a square with pre-determined color
	 * @param toFill color to fill square
	 * @return the square as a Rectangle object
	 */
	public final Rectangle makeColorSquare(Color toFill) {
		Rectangle res = new Rectangle(50, 50);
		res.setFill(toFill);
		res.setStroke(Color.BLACK);
		return res;
	}

	/**
	 * highlights a square in the grid
	 * @param color color to hightlight with
	 * @param colIndex column index of square
	 * @param rowIndex row index of square
	 */
	public void highlight(Color color, int colIndex, int rowIndex) {
		this.add(makeColorSquare(color), colIndex, rowIndex);
	}

	/**
	 * clears the grid of everything except the labels
	 */
	public void clear() {
		this.getChildren().clear();
		for (int i = 0; i < 10; i++) {
			this.add(labelList.get(i), i + 1, 0);
			this.add(labelList.get(i + 10), 0, i + 1);
		}
	}

	/**
	 * put elements back into the grid according to the logic board
	 */
	public void refresh() {
		clear();
		for (int i = 0; i < 100; i++) {
			if (data.getEnemyPlayer().getSqHit().contains(i) && data.getTurnPlayer().getSqNotHit().contains(i)) {
				this.highlight(Color.RED, i % 10 + 1, i / 10 + 1);
			} else if (data.getTurnPlayer().getSqNotHit().contains(i)) {
				this.highlight(Color.BLUE, i % 10 + 1, i / 10 + 1);
			} else if (data.getEnemyPlayer().getSqHit().contains(i)) {
				this.highlight(Color.BLACK, i % 10 + 1, i / 10 + 1);
			} else {
				this.highlight(Color.WHITE, i % 10 + 1, i / 10 + 1);
				addInteractible(i % 10 + 1, i / 10 + 1);
			}
		}
	}

}
