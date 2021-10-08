package data_container;

import java.util.Random;

public class PlayerAI extends Player{
	public PlayerAI() {// empty constructor
	}

	/**
	 * shoot a player
	 * guaranteed to hit a 'ship square', but randomly
	 * @param toShoot target player
	 * @return the square the AI chooses to shoot at
	 */
	public boolean shoot(Player toShoot) {
		Random rand = new Random();
		while (!win(toShoot)) {
			int targetIndex = rand.nextInt(toShoot.getSqNotHit().size());
			int target = toShoot.getSqNotHit().get(targetIndex);
			if (shoot(target)) {
				return true;
			}
		}
		setLastHit(-1);
		return false;
	}
	
	/**
	 * board generator, add ships to board randomly, AI will have the same amount of
	 * ships as the player
	 * @param numShips the number of ships to be added
	 */
	public void boardGen(int numShips) {
        Random rand = new Random();
        int i = 0;
		while (i < numShips) {
			int num = rand.nextInt(100);
			int x = num / 10;
			int y = num % 10;
			int o = rand.nextInt(2);
			int l = rand.nextInt(3) + 2;
			Ship toAdd = Ship.createShip(x, y, l, o);
			if (checkCollide(toAdd)) {
				continue;
			}
			addShip(toAdd);
            i++;
		}
	}
    
}
