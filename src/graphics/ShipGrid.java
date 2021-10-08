package graphics;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ShipGrid extends DisplayGrid {
	protected int[] point1 = { -1, -1 };
	protected int[] point2 = { -1, -1 };
	protected int mouseStat = 0;
	protected int[] collision = { 0, 0 };

	public ShipGrid() {
		super();
	}

	/**
	 * method cleans up coordinates for adding ships
	 */
	private void formatPoints() {
		if (collision[0] <= 0) {
			point1[0] = point1[0] + collision[0];
			collision[0] = -collision[0];
			point2[0] = point1[0] + collision[0];
		}
		if (collision[1] <= 0) {
			point1[1] = point1[1] + collision[1];
			collision[1] = -collision[1];
			point2[1] = point1[1] + collision[1];
		}
	}

	/**
	 * registers collision using point 1 and point 2
	 * also highlights the board
	 * @param arg vertical (1) or horizontal (0)
	 * @param distance pre-calculated distance
	 */
	private void regCollision(int arg, int distance) {
		int x = arg == 1? 1 : 0;
		int y = arg == 1? 0 : 1;
		for (int i = 0; i < Math.abs(distance) + 1; i++) {
			int j = distance < 0 ? -i+1 : i+1;
			this.highlight(Color.BLACK, j*y+x+point1[0] ,j*x+y+point1[1]);
			if (i == 4) {
				collision[x] = distance < 0 ? -4 : 4;
				collision[y] = 0;
				break;
			}
			collision[x] = distance;
			collision[y] = 0;
		}
	}
	
	/**
	 * remove all points' data
	 */
	protected void resetPoints() {
		for (int i = 0; i < 2; i++) {
			point1[i] = -1;
			point2[i] = -1;
			collision[i] = 0;
		}
	}

	/**
	 * adds an interactible object to a square inside the grid
	 * @param colIndex column index of square inside grid
	 * @param rowIndex row index of square inside grid
	 */
	@Override
	protected void addInteractible(int colIndex, int rowIndex) {
		Pane pane = new Pane();
		pane.setOnMousePressed(e -> {
			if (mouseStat == 0) {
				point1[0] = colIndex - 1;
				point1[1] = rowIndex - 1;
				mouseStat = 1;
			} else if (mouseStat == 1) {
				point2[0] = colIndex - 1;
				point2[1] = rowIndex - 1;
				mouseStat = -1;// will stop interacting after 1 ship
				int vDistance = (point2[1] - point1[1]);
				int hDistance = (point2[0] - point1[0]);
				if (vDistance == 0 && hDistance == 0) {
					mouseStat = 0;
				} else {
					if (Math.abs(vDistance) > Math.abs(hDistance)) {
						regCollision(1, vDistance);
					} else {
						regCollision(0, hDistance);
					}
				}
				
			}
			formatPoints();
		});
		this.add(pane, colIndex, rowIndex);
	}


}
