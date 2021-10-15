package data_container;

public class VShip extends Ship {

	private int[] xCollision = new int[2];
	
	public VShip(int x, int y, int l) {
		super(x, y, l);
		update();
	}

	@Override
	public int getOrientation() {
		return VERTICAL;
	}
	
	@Override
	public boolean isHit(int row, int column) {
		return (row >= this.xCollision[0] && row <= this.xCollision[1] && this.column == column);
	}
	
	/**
	 * Returns X-dimension collision
	 * @return {start, end}
	 */
	@Override
	public int[] getCollisionX() {
		return xCollision;
	}

	/**
	 * @param colX the new x collision
	 */
	public void setCollisionX(int[] colX) {
		this.xCollision = colX;
	}

	/**
	 * Updater, set coordinates for collision checks
	 */
	@Override
	public void update() {
		setCollisionX(new int[] { row, row + length - 1});
	}
	
	@Override
	public boolean checkSelf() {
		return getCollisionX()[0] < getCollisionX()[1];
	}
	
	
}
