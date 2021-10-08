package graphics;

import data_container.Data;
import data_container.ScoreKeeper;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class Menu extends VBox {
	private Data data = Data.getInstance();
	private final Label modeLabel = new Label("Choose game mode:");
	private final ToggleGroup group = new ToggleGroup();
	private final RadioButton button1 = new RadioButton("PVP");
	private final RadioButton button2 = new RadioButton("AI");
	private final Label countLabel = new Label("Enter number of ships:");
	private final String countText = "Number of ships";
	private final TextField countTextField = new TextField();
	private final TextField nameP1 = new TextField();
	private final TextField nameP2 = new TextField();
	private final Button exeMenu = new Button("Enter");
	private final ScoreKeeper scoreKeeper = new ScoreKeeper();
	private final Label lastPlayerLabel = new Label();
	private final Label highScoreLabel = new Label();


	public Menu() {
		//setting up the window here
		button1.setToggleGroup(group);
		button1.setSelected(true);
		button1.requestFocus();
		button2.setToggleGroup(group);

		VBox modeScreen = new VBox(modeLabel, button1, button2);
		this.getChildren().add(modeScreen);
		modeScreen.setSpacing(10);
		modeScreen.setPadding(new Insets(10));

		VBox nShipScreen = new VBox(countLabel, countTextField, exeMenu);
		countTextField.setMaxWidth(300);
		countTextField.setPromptText(countText);
		//countTextField.setText(countText);
		this.getChildren().add(nShipScreen);
		nShipScreen.setSpacing(10);
		nShipScreen.setPadding(new Insets(10));

		VBox nameScreen = new VBox(nameP1, nameP2);
		nameP1.setPromptText("Enter player 1's name");
		nameP2.setPromptText("Enter player 2's name");
		this.getChildren().add(nameScreen);
		nameScreen.setSpacing(10);
		nameScreen.setPadding(new Insets(10));
		
		VBox scores = new VBox(lastPlayerLabel, highScoreLabel);
		scoreKeeper.readHighScore();
		lastPlayerLabel.setText(scoreKeeper.getLatest() + ":Latest Winner");
		highScoreLabel.setText(scoreKeeper.getHighScore() + ":High Score"); 
		this.getChildren().add(scores);
		scores.setSpacing(10);
		scores.setPadding(new Insets(10));

		button1.setOnAction(e -> 
			nameP2.setVisible(true));

		button2.setOnAction(e ->
			nameP2.setVisible(false));

		exeMenu.setOnAction(e -> {
			//button takes data from radio buttons and textfields
			//then sends it to Data and move to the next window
			int numShip;
			try {
				numShip = Integer.parseInt(countTextField.getText());
			} catch (Exception ex){
				numShip = -1;
			}
			data.setNumShip(numShip);
			if (group.getSelectedToggle() == button1 && valid()) {
				data.getTargetPlayer(0).setName(nameP1.getText());
				data.getTargetPlayer(1).setName(nameP2.getText());
				data.setMode(Data.Mode.PVP);
				nextScene();
			} else if (group.getSelectedToggle() == button2 && valid()) {
				data.getTargetPlayer(0).setName(nameP1.getText());
				data.getTargetPlayer(1).setName("AI");
				data.setMode(Data.Mode.AI);
				nextScene();
			} else {
				countTextField.clear();
				nameP1.clear();
				nameP2.clear();
			}

		});
	}

	/**
	 * check if conditions are valid for playing a game
	 * @return whether the info gathered is usable or not
	 */
	private boolean valid() {
		String name1 = nameP1.getText();
		String name2 = nameP2.getText();
		return (data.getNumShip() >= 1
				&& data.getNumShip() <= 5
				&& !name1.contains(",") 
				&& !name2.contains(",")
				&& name1.length() <= 10 
				&& name2.length() <= 10);
	}

	/**
	 * go to the next scene
	 * to be overridden
	 */
	public void nextScene() {}

}
