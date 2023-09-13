package clone.chess.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ChessTest extends Application {

    private static final int BOARD_SIZE = 8;
    private static final int SQUARE_SIZE = 80;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);

        // Создаем доску
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Rectangle square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
                if ((row + col) % 2 == 0) {
                    square.setFill(Color.WHITE);
                } else {
                    square.setFill(Color.BLACK);
                }
                grid.add(square, col, row);
            }
        }

        // Создаем сцену и добавляем доску
        Scene scene = new Scene(grid, BOARD_SIZE * SQUARE_SIZE, BOARD_SIZE * SQUARE_SIZE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chess Board");
        primaryStage.show();
    }
}
