package BallsGame;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class GameField extends JPanel{
    private static final int CELL_SIZE = 30;
    private static final int GAP = 2;
    private static final int FONT_HEIGHT = 15;
    int panelWidth = (int) (CELL_SIZE * Width());
    int panelHeight = (int) (CELL_SIZE * Height());

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color GRID_COLOR = Color.LIGHT_GRAY;

    public int getCellSize(){return CELL_SIZE;}

    GameField (){
        createCells();


        setFocusable(true);
        setVisible(true);
        setPreferredSize(new Dimension(panelWidth,panelHeight));
        setBackground (BACKGROUND_COLOR);
    }

    /* --------------------- Size --------------------------- */
    private final int height = 20;
    private final int width = 10;
    private int score;

    public int getScore() {
        return score;
    }

    private void setScore(int score) {
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
    private Map<CellPosition, Cell> cells = new HashMap<CellPosition, Cell>();

    public Cell getCell(int column, int row)
    {
        CellPosition pos = new CellPosition(column, row);

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
        for (int row = 15; row < height; row++) {
            createLine (row);
        }
        return true;
    }

    public boolean createLine (int row) {

        Random random = new Random();
        Color colors[] = { Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED, Color.MAGENTA };

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

    public boolean createNewLine(){
        for(Ball ball : balls()){
            Cell northNeighbor = ball.getCurrentCell().getNeighbor(Direction.NORTH);
            if(( northNeighbor != null) && (northNeighbor.isEmpty())) {
                ball.move(Direction.NORTH);
            }
            else {
                return false;
            }
        }
        createLine(height -1);
        return true;
    }

    /* --------------------------- Ball set ------------------------------ */

    private void createBallSet(Ball ball, @NotNull ArrayList <Ball> group){
        Ball neighbor;

        group.add(ball);

        if(ball.getCurrentCell().getNeighbor(Direction.EAST) != null){
            neighbor = ball.getCurrentCell().getNeighbor(Direction.EAST).getBall();
            if (neighbor != null && neighbor.getColor().equals(ball.getColor()) && !group.contains(neighbor))
                createBallSet(neighbor, group);
        }

        if(ball.getCurrentCell().getNeighbor(Direction.WEST) != null){
            neighbor = ball.getCurrentCell().getNeighbor(Direction.WEST).getBall();
            if (neighbor != null && neighbor.getColor().equals(ball.getColor()) && !group.contains(neighbor))
                createBallSet(neighbor, group);
        }

        if(ball.getCurrentCell().getNeighbor(Direction.NORTH) != null){
            neighbor = ball.getCurrentCell().getNeighbor(Direction.NORTH).getBall();
            if (neighbor != null && neighbor.getColor().equals(ball.getColor()) && !group.contains(neighbor))
                createBallSet(neighbor, group);
        }

        if(ball.getCurrentCell().getNeighbor(Direction.SOUTH) != null){
            neighbor = ball.getCurrentCell().getNeighbor(Direction.SOUTH).getBall();
            if (neighbor != null && neighbor.getColor().equals(ball.getColor()) && !group.contains(neighbor))
                createBallSet(neighbor, group);
        }
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
            ArrayList <Ball> fallingSet = new ArrayList<>();

            for(Ball ball_to_delete : set){
                int delColumn = ball_to_delete.getCurrentCell().CurrentPosition().X();
                int delRow = ball_to_delete.getCurrentCell().CurrentPosition().Y();
                for(Ball ball : balls){
                    int column = ball.getCurrentCell().CurrentPosition().X();
                    int row = ball.getCurrentCell().CurrentPosition().Y();
                    if(delColumn == column && delRow > row){
                        fallingSet.add(ball);
                    }
                }
            }

            for (Ball ball : set){
                ball.getCurrentCell().removeBall();
            }
            balls.removeAll(set);
            setScore(score + set.size());

            if(fallingSet != null){
              takeBallsDown(fallingSet);
            }
            return true;
        }
        return false;
    }

    private void takeBallsDown(@NotNull ArrayList <Ball> fallingSet){
        for(Ball fallingBall : fallingSet){
            if(fallingBall.getCurrentCell() != null){
                int fallingColumn = fallingBall.getCurrentCell().CurrentPosition().X();
                int fallingRow = fallingBall.getCurrentCell().CurrentPosition().Y();

                for(int i = fallingRow; i < height; ++i){
                    Cell cell = getCell(fallingColumn, i);
                    if(cell.isEmpty()){
                        fallingBall.getCurrentCell().removeBall();
                        cell.setBall(fallingBall);
                    }
                }
            }
        }
    }

    public boolean isGameOver(){
        int firstRow = 0;
        for (int column = 0; column < width; column++) {
            Cell cell = getCell(column, firstRow);
            if (!cell.isEmpty()) {
                return true;
            }
        }
        return false;
    }


    /*---------------------------------COMPONENT-----------------------------------------*/

    private void drawGrid (@NotNull Graphics g) {

        g.setColor(GRID_COLOR);

        g.drawLine(0,0,panelWidth,0);
        g.drawLine(0, 0, 0,panelHeight);
        g.drawLine(panelWidth,0,panelWidth,panelHeight);
        g.drawLine(0,panelHeight,panelWidth,panelHeight);
        g.drawLine(panelWidth,0,panelWidth,panelHeight);

        for(int x = 1; x< Width(); x++){
            int i = CELL_SIZE * x;
            g.drawLine(i, 0, i, panelHeight);
        }

        for(int y = 1; y < Height(); y++){
            int i = CELL_SIZE * y;
            g.drawLine(0, i, panelWidth, i);
        }
    }

    private void drawBall (@NotNull Graphics g, @NotNull Ball ball, @NotNull Point lefTop) {
        g.setColor(ball.getColor());

        g.drawOval(lefTop.x + CELL_SIZE/8 + 2, lefTop.y + CELL_SIZE/4 + 2 * FONT_HEIGHT - 2, 20, 20);
        g.fillOval(lefTop.x + CELL_SIZE/8 + 2, lefTop.y + CELL_SIZE/4 + 2 * FONT_HEIGHT - 2, 20, 20);

        g.setColor(Color.BLACK);
    }


    private @NotNull Point leftTopCell (@NotNull CellPosition pos) {
        int left = GAP + CELL_SIZE * (pos.X());
        int top = GAP + CELL_SIZE * (pos.Y()-1);

        return new Point(left, top);
    }

    public void paintComponent (@NotNull Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        Point lefTop;

        for(int row = 0; row < Height(); row++){
            for(int column = 0; column < Width(); column++){
                Cell cell = getCell(column, row);
                CellPosition pos = cell.CurrentPosition();
                Ball ball = cell.getBall();

                if(ball != null){
                    lefTop = leftTopCell(pos);
                    drawBall(g, ball, lefTop);
                }
            }
        }
//
//        g.drawString("SCORE: ", 10, 30);
//        g.drawString(Integer.toString(getScore()), 150, 30);

    }
}