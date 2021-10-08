package data_container;

public class HShip extends Ship {
    private int[] yCollision = new int[2];

    public HShip(int x, int y, int l) {
        super(x, y, l);
        update();
    }


    @Override
    public int getOrientation() {
        return HORIZONTAL;
    }

    @Override
    public boolean isHit(int row, int column) {
        return (column >= this.yCollision[0] && column <= this.yCollision[1] && this.row == row);
    }

    /**
     * Returns Y-dimension collision
     *
     * @return {start, end}
     */
    @Override
    public int[] getCollisionY() {
        return yCollision;
    }

    /**
     * @param coly the new y collision
     */
    public void setCollisionY(int[] coly) {
        this.yCollision = coly;
    }


    /**
     * Updater, set coordinates for collision checks
     */
    @Override
    public void update() {
        setCollisionY(new int[]{column, column + length - 1});
    }

    @Override
    public boolean checkSelf() {
        return getCollisionY()[0] < getCollisionY()[1];
    }

}
