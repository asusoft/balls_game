package BallsGame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class GameField {

    GameField (){
        createCells();
        //fillCells();
    }

    /* --------------------- Size --------------------------- */
    private final int height = 20;
    private final int width = 10;
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }



    //Get width
    public int Width() {
        return width;
    }

    //Get height
    public int Height()
    {
        return height;
    }

    /* ---------------------- Balls -------------------- */

    private ArrayList <Ball> balls = new ArrayList <Ball> (); //all balls on field

    public ArrayList<Ball> balls() {
        return balls;
    }

    /* ---------------------- Cell storage -------------------- */
    //Cells
    private Map<CellPosition, Cell> cells = new HashMap<CellPosition, Cell>();

    //Get cell
    public Cell getCell(int xPos, int yPos)
    {
        CellPosition pos = new CellPosition(xPos, yPos);

        return cells.get(pos);
    }

    //Create cells
    private void createCells()
    {
        for (int row = 0; row < height; ++row)
        {
            for (int column = 0; column < width; ++column)
            {
                CellPosition pos = new CellPosition(column, row);

                Cell cell = new Cell(pos);

                cells.put(cell.CurrentPosition(), cell);

                if (row > 0)
                {
                    Cell neighbour = getCell(column, row - 1);

                    cell.setNeighbor(neighbour, Direction.NORTH);
                }

                if (column > 0)
                {
                    Cell neighbour = getCell(column - 1, row);

                    cell.setNeighbor(neighbour, Direction.WEST);
                }
            }
        }
    }

    public boolean fillCells () {
        for (int row = 5; row < height; row++) {
            createNewLine (row);
        }
        return true;
    }

    public boolean createNewLine (int row) {

        Random random = new Random();
        Color colors[] = { Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED, Color.WHITE };

        for(int column = 0; column < width; column++) {

            int colorSelector = random.nextInt(colors.length);

            Cell cell = getCell(column, row);

            if(cell.isEmpty()){
                Ball ball = new Ball(colors[colorSelector]);
                cell.setBall(ball);
                balls.add(ball);
            } else {
                return false;
            }
        }

        return true;
    }

    /* --------------------------- Ball set ------------------------------ */

    private void createBallSet(Ball ball, ArrayList <Ball> group){
        Ball b;

        group.add(ball);
        b = ball.getCurrentCell().getNeighbor(Direction.EAST).getBall();

        if (b != null && b.getColor().equals(ball.getColor()) && !group.contains(b))
            createBallSet(b, group);

        b = ball.getCurrentCell().getNeighbor(Direction.WEST).getBall();

        if (b != null && b.getColor().equals(ball.getColor()) && !group.contains(b))
            createBallSet(b, group);

        b = ball.getCurrentCell().getNeighbor(Direction.NORTH).getBall();

        if (b != null && b.getColor().equals(ball.getColor()) && !group.contains(b))
            createBallSet(b, group);

        b = ball.getCurrentCell().getNeighbor(Direction.SOUTH).getBall();

        if (b != null && b.getColor().equals(ball.getColor()) && !group.contains(b))
            createBallSet(b, group);

    }

    public ArrayList <Ball> getBallSet (Ball ball) {
        ArrayList <Ball> set = new ArrayList<>();
        createBallSet(ball, set);

        if (set.size() >= 3) {
            return set;
        }
        return null;
    }

    public boolean deleteBallSet (ArrayList <Ball> set) {
        if (set != null){
            for (Ball b : set){
                b.getCurrentCell().removeBall();
            }
            balls.removeAll(set);
            //takeBallsDown();
            return true;
        }
        return false;
    }

    public void takeBallsDown(){
         for (int row = 0; row < height; row++) {
            for(int column = 0; column < width; column++){
                Cell cell = getCell(row, column);
                Cell belowCell = cell.getNeighbor(Direction.SOUTH);
                if(belowCell != null && belowCell.isEmpty()){
                    cell.getBall().move(Direction.SOUTH);
                }
            }
        }
    }
}