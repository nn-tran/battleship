package data_container;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataTest {

	private Ship testedShip0 = Ship.createShip(0, 0, 5, 0);//Horizontal 5-length ship at (0,0)
	private Ship testedShip0b = Ship.createShip(0, 0, 5, 1);//Vertical 5-length ship at (0,0)
	private Ship testedShip0c = Ship.createShip(-1, -1, 2, 1);//Illegal ship
	private Ship testedShip1 = Ship.createShip(4, 4, 2, 1);//Vertical 2-length ship at (4,4)
	
	
	@Test
	public void shipTest() {
		assertTrue(testedShip0.isHit(0, 0));
		assertTrue(testedShip0.isHit(0, 4));
		assertTrue(testedShip0.isHit(0, 2));
		assertTrue(testedShip1.isHit(5, 4));
		assertFalse(testedShip0.isHit(1, 1));
		assertFalse(testedShip1.isHit(1, 4));
		
	}

	@Test
	public void playerTest0() {
		Data testedData = new Data();
		testedData.init(Data.Mode.PVP);
		Player testedPlayer0 = testedData.getTargetPlayer(0);
		Player testedPlayer1 = testedData.getTargetPlayer(1);

		testedPlayer0.addShip(testedShip0);
		testedPlayer1.addShip(testedShip1);
		assertFalse(testedPlayer0.win(testedPlayer1));
		assertFalse(testedPlayer1.win(testedPlayer0));
		testedPlayer0.shoot(0);//player 0 shoots 0-0
		testedPlayer0.shoot(20);
		testedPlayer0.shoot(44);//player 0 shoots 4-4
		testedPlayer0.shoot(2);//player 0 shoots 0-2
		testedPlayer0.shoot(54);//player 0 shoots 5-4
		//game should be over, player 0 has won
		assertFalse(testedPlayer1.win(testedPlayer0));
		assertTrue(testedPlayer0.win(testedPlayer1));
	}
	
	@Test
	public void playerTest1() {
		Data testedData = new Data();
		testedData.init(Data.Mode.PVP);
		Player testedPlayer0 = testedData.getTargetPlayer(0);
		Player testedPlayer1 = testedData.getTargetPlayer(1);
		testedPlayer0.addShip(testedShip0);
		testedPlayer1.addShip(testedShip1);
		assertFalse(testedPlayer0.win(testedPlayer1));
		assertFalse(testedPlayer1.win(testedPlayer0));
		testedData.switchPlayer();
		testedPlayer1.shoot(0);//player 1 shoots 0-0
		testedPlayer1.shoot(1);//player 1 shoots 0-1
		testedPlayer1.shoot(2);//player 1 shoots 0-2
		testedPlayer1.shoot(3);//player 1 shoots 0-3
		testedPlayer1.shoot(4);//player 1 shoots 0-4
		assertTrue(testedPlayer1.win(testedPlayer0));
		assertFalse(testedPlayer0.win(testedPlayer1));

	}
	
	@Test
	public void aiTest() {
		Data testedData = new Data();
		testedData.setNumShip(1);
		testedData.init(Data.Mode.AI);
		Player testedPlayer0 = testedData.getTargetPlayer(0);
		Player testedPlayer1 = testedData.getTargetPlayer(1);
		testedPlayer0.addShip(testedShip0);
		assertFalse(testedPlayer0.win(testedPlayer1));
		assertFalse(testedPlayer1.win(testedPlayer0));
		testedData.switchPlayer();
		for (int i = 0; i < 5; i++) {
			((PlayerAI) testedPlayer1).shoot(testedPlayer0);
		}
		assertTrue(testedPlayer1.win(testedPlayer0));
		assertFalse(testedPlayer0.win(testedPlayer1));

	}
	
	@Test
	public void playerNameTest() {
		Player testedPlayer0 = new Player();
		testedPlayer0.setName("Michael Jackson");
		Player testedPlayer1 = new Player("Dead Michael Jackson");
		assertEquals("Dead Michael Jackson", testedPlayer1.getName());
		assertEquals("Michael Jackson", testedPlayer0.getName());
	}
	
	@Test
	public void collisionTest() {
		Data testedData = new Data();
		testedData.init(Data.Mode.PVP);
		Player testedPlayer0 = testedData.getTargetPlayer(0);
		assertEquals(0, testedPlayer0.getShipListSize());
		testedPlayer0.addShip(testedShip0);
		assertEquals(1, testedPlayer0.getShipListSize());
		if (!testedPlayer0.checkCollide(testedShip0b)) 
			testedPlayer0.addShip(testedShip0b);
		if (!testedPlayer0.checkCollide(testedShip0c)) 
			testedPlayer0.addShip(testedShip0c);
		assertEquals(1, testedPlayer0.getShipListSize());
	}
	
	
}
