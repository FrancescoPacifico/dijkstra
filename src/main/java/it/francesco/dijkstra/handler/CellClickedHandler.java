package it.francesco.dijkstra.handler;

import it.francesco.dijkstra.stuff.Cell;
import it.francesco.dijkstra.stuff.Grid;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class CellClickedHandler implements EventHandler<MouseEvent> {

    private final Cell cell;

    public CellClickedHandler(Cell cell) {
        this.cell = cell;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            switch(Grid.getInstance().getStatus()) {
                case START -> Grid.getInstance().setStart(cell);
                case END -> Grid.getInstance().setEnd(cell);
                case WALL -> {
                    cell.setWall(!cell.isWall());
                    Grid.getInstance().setLastClicked(cell);
                }
            }
        }
    }

}
