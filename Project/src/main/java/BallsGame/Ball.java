package BallsGame;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Ball {

    private Cell cell;
    private Color color;

    public Color getColor() {
        return this.color;
    }

    public void setColor (Color col) {
        color = col;
    }

    public Cell getCell() {
        return cell;
    }

    void setCell(Cell cell) {
        this.cell = cell;
    }

    public void move(@NotNull Direction direction){
        if(!this.cell.neighborCell(direction).isEmpty())throw new IllegalArgumentException("Cell is not empty");

        this.setCell(cell.neighborCell(direction));

    }
}
