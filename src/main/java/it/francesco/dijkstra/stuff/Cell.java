package it.francesco.dijkstra.stuff;

import it.francesco.dijkstra.handler.CellClickedHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {

    public static final double SIDE = 20;
    public static final Color DEFAULT = Color.WHITE;
    public static final Color PATH = Color.YELLOW;
    public static final Color STROKE_COLOR = Color.BLACK;
    public static final double STROKE_WIDTH = 0.1;

    private final int relativeX;
    private final int relativeY;
    private double potential;
    private boolean definitive;
    private Cell comeFrom;

    public Cell(int relativeX, int relativeY, double absoluteX, double absoluteY) {
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        potential = Double.POSITIVE_INFINITY;
        setX(absoluteX);
        setY(absoluteY);
        setWidth(SIDE);
        setHeight(SIDE);
        setFill(DEFAULT);
        setStroke(STROKE_COLOR);
        setStrokeWidth(STROKE_WIDTH);
        setOnMouseClicked(new CellClickedHandler(this));
    }

    public int getRelativeX() {
        return relativeX;
    }

    public int getRelativeY() {
        return relativeY;
    }

    public double getPotential() {
        return potential;
    }

    public boolean isDefinitive() {
        return definitive;
    }

    public Cell getComeFrom() {
        return comeFrom;
    }

    public void setPotential(double potential) {
        this.potential = potential;
    }

    public void setDefinitive(boolean definitive) {
        this.definitive = definitive;
    }

    public void setComeFrom(Cell comeFrom) {
        this.comeFrom = comeFrom;
    }

    public boolean isWall() {
        return getFill().equals(Grid.Status.WALL.getColor());
    }

    public void setWall(boolean isWall) {
        if(isWall) {
            if(this.equals(Grid.getInstance().getStart())) {
                Grid.getInstance().deleteStart();
            }
            else if(this.equals(Grid.getInstance().getEnd())) {
                Grid.getInstance().deleteEnd();
            }
            setFill(Grid.Status.WALL.getColor());
        }
        else {
            setFill(Cell.DEFAULT);
        }
    }

}
