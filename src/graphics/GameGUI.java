package graphics;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameGUI extends Application {
    Stage window;
    Scene menuScene;
    Scene shipWindowScene;
    Scene fireWindowScene;
    Scene finishWindowScene;

    Menu menu;
    ShipWindow shipWindow;
    FireWindow fireWindow;
    FinishWindow finishWindow;

    /**
     * start method, runs the game
     * @param primaryStage the display window
     */
    public void start(Stage primaryStage) {
        window = primaryStage;
        menu = new Menu() {
            @Override
            public void nextScene() {
                window.setScene(shipWindowScene);
                shipWindow.setNumShips();
            }
        };
        shipWindow = new ShipWindow() {
            @Override
            public void nextScene() {
                window.setScene(fireWindowScene);
                fireWindow.refresh();
            }
        };
        fireWindow = new FireWindow() {
            @Override
            public void nextScene() {
                window.setScene(finishWindowScene);
                finishWindow.updateVictory();
            }
        };
        finishWindow = new FinishWindow();
        menuScene = new Scene(menu, 300, 350);
        shipWindowScene = new Scene(shipWindow, 600, 600);
        fireWindowScene = new Scene(fireWindow, 1200, 600);
        finishWindowScene = new Scene(finishWindow, 400, 100);

        window.setScene(menuScene);
        window.setTitle("Battleship");
        window.show();
    }

    /**
     * main method, launches the application
     *
     * @param args console arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

}
