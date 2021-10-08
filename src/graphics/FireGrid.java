package graphics;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class FireGrid extends DisplayGrid {
	protected int[] point = new int[2];
	protected int mouseStat = 0;

	public FireGrid() {
		super();
		point[0] = -1;
		point[1] = -1;
	}

	/**
	 * adds clickable object to grid
	 * highlights itself when clicked
	 * and registers to point[]
	 */
	@Override
	protected void addInteractible(int colIndex, int rowIndex) {
		Pane pane = new Pane();
		pane.setOnMousePressed(e -> {
			point[0] = colIndex - 1;
			point[1] = rowIndex - 1;
			refresh();
			highlight(Color.BLACK, colIndex, rowIndex);
			injected();
		});
		this.add(pane, colIndex, rowIndex);
	}

	public void injected() {
		//to be used by FireWindow for additional functionality on click
	}

	/**
	 * different refresh for the enemy display
	 * instead of the turn player's
	 */
	@Override
	public void refresh() {
		clear();
		for (int i = 0; i < 100; i++) {
			if (data.getTurnPlayer().getSqHit().contains(i) && data.getEnemyPlayer().getSqNotHit().contains(i)) {
				this.highlight(Color.BLUE, i % 10 + 1, i / 10 + 1);
			} else if (data.getTurnPlayer().getSqHit().contains(i)) {
				this.highlight(Color.BLACK, i % 10 + 1, i / 10 + 1);
			} else {
				this.highlight(Color.WHITE, i % 10 + 1, i / 10 + 1);
				addInteractible(i % 10 + 1, i / 10 + 1);
			}
		}
	}
}
