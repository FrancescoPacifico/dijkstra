package it.francesco.dijkstra.app;

import it.francesco.dijkstra.handler.KeyPressedHandler;
import it.francesco.dijkstra.stuff.Grid;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static final String TITLE = "PATHING";
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final double HORIZONTAL_FREE_SPACE_PERCENTAGE = 10;
    public static final double VERTICAL_FREE_SPACE_PERCENTAGE = 10;

    @Override
    public void start(Stage stage) throws IOException {
        stage = new Stage();
        Group root = new Group();
        Grid grid = Grid.getInstance();
        root.getChildren().addAll(grid.getAllCells());
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(TITLE);
        stage.show();
        stage.addEventHandler(KeyEvent.KEY_PRESSED, new KeyPressedHandler());
    }

    public static void main(String[] args) {
        launch();
    }
}