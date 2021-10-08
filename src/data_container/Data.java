package data_container;


public final class Data {// Singleton Object, for GUI
    private static Data instance = null;
    private int count = 0;
    private int enemy = 1;
    private Mode mode;
    private int nShip = 0;
    private final Player[] player = new Player[2];
    private int turn = 0;

    public enum Mode {NONE, PVP, AI}

    Data() {
        player[0] = new Player();
        player[1] = new Player();
    }

    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    /**
     * increases count by 1. Called each turn
     */
    public void count() {
        count++;
    }

    /**
     * @return the turn count
     */
    public int getCount() {
        return count;
    }

    /**
     * @return the mode
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(Mode mode) {
        this.mode = mode;
        this.init(mode);
    }

    /**
     * @return the number of ships for all players
     */
    public int getNumShip() {
        return nShip;
    }

    /**
     * sets number of ships for both players
     *
     * @param toSet the number of ships
     */
    public void setNumShip(int toSet) {
        nShip = toSet;
    }

    /**
     * @return the turn number
     */
    public int getTurn() {
        return turn;
    }

    /**
     * @return the player playing
     */
    public Player getTurnPlayer() {
        return player[turn];
    }

    public Player getEnemyPlayer() {
        return player[enemy];
    }

    /**
     * @param index index of player to return, 0 or 1
     * @return the player
     */
    public Player getTargetPlayer(int index) {
        return player[index];
    }

    /**
     * set generated player board, if required
     *
     * @param mode game mode
     */
    public void init(Mode mode) {
        if (mode == Mode.AI) {
            player[1] = new PlayerAI();
            ((PlayerAI) player[1]).boardGen(nShip);
        }
    }

    /**
     * switches current turn player and current enemy
     */
    public void switchPlayer() {
        enemy = turn;
        turn = 1 - turn;
    }


}
