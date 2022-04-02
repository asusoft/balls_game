package BallsGame;

import java.awt.Color;
import java.util.*;

import org.jetbrains.annotations.NotNull;


public class Cell {
    /*------------------------ Neighbor cell implementation --------------------------------------------------*/
    //Neighbor cell
    private Map<Direction, Cell> neighbors = new HashMap<>();

    //Get the neighbor
    public Cell getNeighbor(Direction dir)
    {
        if (dir == null)
            return null;

        return neighbors.get(dir);
    }

    //Set the neighbor
    void setNeighbor(Cell neighbor, Direction dir)
    {
        if (neighbors.putIfAbsent(dir, neighbor) != null  || dir == null || neighbor == null)
        {
            throw new IllegalStateException();
        }

        if (neighbor.getNeighbor(dir.getOppositeDirection()) == null)
        {
            neighbor.setNeighbor(this, dir.getOppositeDirection());
        }
    }

    public boolean isEmpty(){
        if(getBall() == null){
            return true;
        } else{
            return  false;
        }
    }

    /* ----------------- Ball ownership ------------------ */

    private Ball storedBall = null; //ball in a cell

    //Set ball
    void setBall(Ball ball)
    {
        if (ball == null || (ball.getCurrentCell() != null && ball.getCurrentCell() != this)
                || storedBall != null)
        {
            throw new IllegalArgumentException("Illegal ball!");
        }

        storedBall = ball;

        if (ball.getCurrentCell() == null)
        {
            ball.putInCell(this);
        }
    }

    //Remove ball from cell
    void removeBall()
    {
        if (storedBall != null)
        {
            Ball ball = storedBall;

            storedBall = null;
            if(ball.getCurrentCell() == this){
                ball.removeFromCell();
            }
        }
    }

    //Get ball stored in the cell
    public Ball getBall()
    {
        return storedBall;
    }

    /* --------------------------- Cell position ------------------------------ */
    private final CellPosition currentPosition;

    public CellPosition CurrentPosition() {return currentPosition;}

    Cell(CellPosition cellPosition)
    {
        if (cellPosition == null)
        {
            throw new IllegalArgumentException();
        }

        currentPosition = cellPosition;
    }

}

