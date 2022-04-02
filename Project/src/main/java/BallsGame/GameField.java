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
        fillCells();
    }

    /* --------------------- Size --------------------------- */
    private final int height = 20;
    private final int width = 10;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int score;

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
        for (int i = 0; i < height; ++i)
        {
            for (int j = 0; j < width; ++j)
            {
                CellPosition pos = new CellPosition(j, i);

                Cell cell = new Cell(pos);

                cells.put(cell.CurrentPosition(), cell);

                if (i > 0)
                {
                    Cell neighbour = getCell(j, i - 1);

                    cell.setNeighbor(neighbour, Direction.NORTH);
                }

                if (j > 0)
                {
                    Cell neighbour = getCell(j - 1, i);

                    cell.setNeighbor(neighbour, Direction.WEST);
                }
            }
        }
    }

    private boolean fillCells () {
        for (int row = 0; row < 5; row++) {
            createNewLine (row);
        }
        return true;
    }

    public boolean createNewLine (int row) {

        Random random = new Random();
        Color colors[] = { Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED, Color.WHITE };

        for(int column = 0; column < 10; column++) {

            int colorSelector = random.nextInt(colors.length);

            Cell cell = getCell(row, column);

            if(cell.isEmpty()){
                Ball ball = new Ball(colors[colorSelector]);
                cell.setBall(ball);
                balls.add(ball);
            } else {
                throw new IllegalArgumentException("Cell is not empty!");
            }
        }

        return true;
    }

    /* --------------------------- Ball set ------------------------------ */

    private void ballSet(Ball ball, ArrayList <Ball> group){
        Ball b;

        group.add(ball);
        b = ball.getCurrentCell().getNeighbor(Direction.EAST).getBall();

        if (b != null && b.getColor().equals(ball.getColor()) && !group.contains(b))
            ballSet(b, group);

        b = ball.getCurrentCell().getNeighbor(Direction.WEST).getBall();

        if (b != null && b.getColor().equals(ball.getColor()) && !group.contains(b))
            ballSet(b, group);

        b = ball.getCurrentCell().getNeighbor(Direction.NORTH).getBall();

        if (b != null && b.getColor().equals(ball.getColor()) && !group.contains(b))
            ballSet(b, group);

        b = ball.getCurrentCell().getNeighbor(Direction.SOUTH).getBall();

        if (b != null && b.getColor().equals(ball.getColor()) && !group.contains(b))
            ballSet(b, group);

    }

    public ArrayList <Ball> getBallSet (Ball ball) {
        ArrayList <Ball> set = new ArrayList<>();
        ballSet(ball, set);

        if (set.size() >= 3 && set.size() <= 10) {
            return set;
        }
        return null;
    }

    public boolean deleteBallSet (Ball ball) {
        ArrayList <Ball> set = getBallSet(ball);
        ArrayList <Ball> fallingBalls = null;
        if (set != null){
            score += set.size();
            setScore(score);
            for (Ball b : set){
                Ball fallingBall = b.getCurrentCell().getNeighbor(Direction.NORTH).getBall();
                fallingBalls.add(fallingBall);
                b.getCurrentCell().removeBall();
            }
            balls.removeAll(set);
            if(fallingBalls != null){takeBallsDown(fallingBalls);}
            return true;
        }
        return false;
    }

    public void takeBallsDown( ArrayList <Ball> fallingBalls){

        for (Ball fallingBall : fallingBalls){
            while (fallingBall.getCurrentCell().getNeighbor(Direction.EAST).isEmpty()){
                fallingBall.move(Direction.EAST);
            }
        }

/*      for (int row = 0; row < height; row++) {
            for(int column = 0; column < width; column++){
                Cell cell = getCell(row, column);
                Cell belowCell = cell.getNeighbor(Direction.SOUTH);
                if(belowCell != null && belowCell.isEmpty()){
                    cell.getBall().move(Direction.SOUTH);
                }
            }
        } */
    }
}