package it.francesco.dijkstra.handler;

import it.francesco.dijkstra.stuff.Cell;
import it.francesco.dijkstra.stuff.Grid;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyPressedHandler implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent keyEvent) {
        if(keyEvent.getEventType().equals(KeyEvent.KEY_PRESSED)) {
            try {
                switch(keyEvent.getCode()) {
                    case S -> {
                        if (Grid.getInstance().getStatus() != Grid.Status.COMPUTE)
                            Grid.getInstance().setStatus(Grid.Status.START);
                    }
                    case E -> {
                        if (Grid.getInstance().getStatus() != Grid.Status.COMPUTE)
                            Grid.getInstance().setStatus(Grid.Status.END);
                    }
                    case W -> {
                        if (Grid.getInstance().getStatus() != Grid.Status.COMPUTE)
                            Grid.getInstance().setStatus(Grid.Status.WALL);
                    }
                    case ENTER -> {
                        if (Grid.getInstance().getStatus() != Grid.Status.COMPUTE && Grid.getInstance().getStart() != null && Grid.getInstance().getEnd() != null) {
                            Grid.getInstance().setStatus(Grid.Status.COMPUTE);
                            Grid.getInstance().compute();
                        }
                    }
                    case R -> {
                        Grid.getInstance().setStatus(Grid.Status.START);
                        Grid.getInstance().reset();
                    }
                    case UP -> {
                        if (Grid.getInstance().getStatus().equals(Grid.Status.WALL)) {
                            int x = Grid.getInstance().getLastClicked().getRelativeX();
                            int y = Grid.getInstance().getLastClicked().getRelativeY();
                            Cell next = Grid.getInstance().getCell(x, y - 1);
                            next.setWall(true);
                            Grid.getInstance().setLastClicked(next);
                        }
                    }
                    case DOWN -> {
                        if (Grid.getInstance().getStatus().equals(Grid.Status.WALL)) {
                            int x = Grid.getInstance().getLastClicked().getRelativeX();
                            int y = Grid.getInstance().getLastClicked().getRelativeY();
                            Cell next = Grid.getInstance().getCell(x, y + 1);
                            next.setWall(true);
                            Grid.getInstance().setLastClicked(next);
                        }
                    }
                    case LEFT -> {
                        if (Grid.getInstance().getStatus().equals(Grid.Status.WALL)) {
                            int x = Grid.getInstance().getLastClicked().getRelativeX();
                            int y = Grid.getInstance().getLastClicked().getRelativeY();
                            Cell next = Grid.getInstance().getCell(x - 1, y);
                            next.setWall(true);
                            Grid.getInstance().setLastClicked(next);
                        }
                    }
                    case RIGHT -> {
                        if (Grid.getInstance().getStatus().equals(Grid.Status.WALL)) {
                            int x = Grid.getInstance().getLastClicked().getRelativeX();
                            int y = Grid.getInstance().getLastClicked().getRelativeY();
                            Cell next = Grid.getInstance().getCell(x + 1, y);
                            next.setWall(true);
                            Grid.getInstance().setLastClicked(next);
                        }
                    }
                }
            }
            catch(ArrayIndexOutOfBoundsException ignored) {

            }
        }
    }

}
