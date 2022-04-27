package it.francesco.dijkstra.stuff;

import it.francesco.dijkstra.app.Main;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Grid {

    public enum Status {

        START(Color.GREEN),
        END(Color.RED),
        WALL(Color.BLUE),
        COMPUTE(null);

        private final Color color;

        Status(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }

    private static Grid instance;

    public static final int HORIZONTAL_CELLS = (int) (Main.WIDTH * (100 - Main.HORIZONTAL_FREE_SPACE_PERCENTAGE) / 100 / Cell.SIDE);
    public static final int VERTICAL_CELLS = (int) (Main.HEIGHT * (100 - Main.VERTICAL_FREE_SPACE_PERCENTAGE) / 100 / Cell.SIDE);

    private final Cell[][] grid;
    private Status status;
    private Cell start;
    private Cell end;
    private Cell lastClicked;

    private Grid() {
        grid = new Cell[VERTICAL_CELLS][HORIZONTAL_CELLS];
        double absoluteY = Main.HEIGHT * Main.VERTICAL_FREE_SPACE_PERCENTAGE / 100 / 2;
        for(int relativeY = 0; relativeY < grid.length; relativeY++) {
            double absoluteX = Main.WIDTH * Main.HORIZONTAL_FREE_SPACE_PERCENTAGE / 100 / 2;
            for(int relativeX = 0; relativeX < grid[relativeY].length; relativeX++) {
                grid[relativeY][relativeX] = new Cell(relativeX, relativeY, absoluteX, absoluteY);
                absoluteX += Cell.SIDE;
            }
            absoluteY += Cell.SIDE;
        }
        status = Status.START;
        lastClicked = grid[0][0];
    }

    public static Grid getInstance() {
        if(instance == null)
            instance = new Grid();
        return instance;
    }

    public Status getStatus() {
        return status;
    }

    public Cell getStart() {
        return start;
    }

    public Cell getEnd() {
        return end;
    }

    public Cell getLastClicked() {
        return lastClicked;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStart(Cell start) {
        if(this.start != null) {
            this.start.setFill(Cell.DEFAULT);
            this.start.setPotential(Double.POSITIVE_INFINITY);
        }
        this.start = start;
        this.start.setFill(Status.START.getColor());
        this.start.setPotential(0);
        if(end == this.start)
            end = null;
    }

    public void setEnd(Cell end) {
        if(this.end != null) {
           this.end.setFill(Cell.DEFAULT);
        }
        this.end = end;
        this.end.setFill(Status.END.getColor());
        if(start == this.end) {
            start.setPotential(Double.POSITIVE_INFINITY);
            start = null;
        }
    }

    public void setLastClicked(Cell lastClicked) {
        this.lastClicked = lastClicked;
    }

    public List<Cell> getAllCells() {
        return Arrays.stream(grid).flatMap(Arrays::stream).collect(Collectors.toList());
    }

    public Cell getCell(int relativeX, int relativeY) {
        return grid[relativeY][relativeX];
    }

    public void deleteStart() {
        start = null;
    }

    public void deleteEnd() {
        end = null;
    }

    private List<Cell> getAdjacentCells(Cell cell) {
        List<Cell> adjacentCells = new ArrayList<>();
        int startX = cell.getRelativeX() - 1;
        int startY = cell.getRelativeY() - 1;
        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 3; x++) {
                try {
                    if(grid[startY + y][startX + x] != cell) {
                        adjacentCells.add(grid[startY + y][startX + x]);
                    }
                }
                catch(ArrayIndexOutOfBoundsException ignored) {

                }
            }
        }
        return adjacentCells;
    }

    private double getDistance(Cell cell1, Cell cell2) {
        double cell1X = cell1.getX() + cell1.getWidth() / 2;
        double cell1Y = cell1.getY() + cell1.getHeight() / 2;
        double cell2X = cell2.getX() + cell2.getWidth() / 2;
        double cell2Y = cell2.getY() + cell2.getHeight() / 2;
        return Math.sqrt(Math.pow(cell1X - cell2X, 2) + Math.pow(cell1Y - cell2Y, 2));
    }

    private void drawPath() {
        for(Cell current = Grid.getInstance().getEnd().getComeFrom(); current != Grid.getInstance().getStart(); current = current.getComeFrom()) {
            current.setFill(Cell.PATH);
        }
    }

    public void compute() {
        Cell min = getAllCells().stream()
                .filter(c -> !c.isDefinitive() && !c.isWall())
                .min(Comparator.comparingDouble(Cell::getPotential)).get();
        min.setDefinitive(true);
        if(min == Grid.getInstance().getEnd()) {
            drawPath();
            return;
        }
        List<Cell> adjacentCells = getAdjacentCells(min);
        adjacentCells.forEach(c -> {
            double newPotential = min.getPotential() + getDistance(min, c);
            if(newPotential < c.getPotential()) {
                c.setPotential(newPotential);
                c.setComeFrom(min);
            }
        });
        compute();
    }

    public void reset() {
        getAllCells().forEach(c -> {
            c.setPotential(Double.POSITIVE_INFINITY);
            c.setDefinitive(false);
            c.setComeFrom(null);
            c.setFill(Cell.DEFAULT);
        });
        start = null;
        end = null;


    }

}
