package BallsGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import org.jetbrains.annotations.NotNull;


public class Cell {

    /**
     * Ball
     */
    private Ball ball;

    public Ball getBall() {
        return ball;
    }

    public Ball removeBall() {
        var tmp = ball;
        ball = null;
        return tmp;
    }

    public void putBall(Ball ball) {
        if(!isEmpty()) throw new IllegalArgumentException("Cell is not empty");

        ball.setCell(this);
        this.ball = ball;
    }

    /**
     * Neighbor
     */

    private Map<Direction, Cell> neighborCells = new EnumMap<>(Direction.class);

    public Cell neighborCell(@NotNull Direction direction) {
        return neighborCells.get(direction);
    }

    void setNeighbor(@NotNull Cell cell, @NotNull Direction direction) {
        if(neighborCells.containsKey(direction) && neighborCells.containsValue(cell)) return;
        if(neighborCells.containsKey(direction)) throw new IllegalArgumentException();
        neighborCells.put(direction, cell);
        if(cell.neighborCell(direction.getOppositeDirection()) == null) {
            cell.setNeighbor(this, direction.getOppositeDirection());
        }
    }

    public Direction isNeighbor(@NotNull Cell cell) {
        for(var i : neighborCells.entrySet()) {
            if(i.getValue().equals(cell)) return i.getKey();
        }
        return null;
    }

    public boolean isEmpty(){
        if(getBall() != null){
            return true;
        } else{
            return  false;
        }
    }

}
