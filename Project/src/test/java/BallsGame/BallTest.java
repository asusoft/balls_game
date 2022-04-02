package BallsGame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class BallTest {

    @Test
    void getColorTest(){
        Ball ball = new Ball(Color.blue);

        Assertions.assertEquals(Color.blue, ball.getColor());
    }

    /*------------------- TEST FOR getCurrentCell() FUNCTION ------------------------*/
    @Test
    void getCurrentCell(){
        Cell cell = new Cell(new CellPosition(1, 1));
        Ball ball = new Ball(Color.blue);

        ball.putInCell(cell);

        Assertions.assertEquals(cell, ball.getCurrentCell());
    }

    @Test
    void getCurrentCellNull(){
        Cell cell = new Cell(new CellPosition(1, 1));
        Ball ball = new Ball(Color.blue);

        Assertions.assertNull(ball.getCurrentCell());
    }

    /*------------------- TESTS FOR putInCell() FUNCTION ------------------------*/

    @Test
    void putInCellTest(){
        Cell cell = new Cell(new CellPosition(1, 1));
        Ball ball = new Ball(Color.blue);

        ball.putInCell(cell);

        Assertions.assertEquals(cell, ball.getCurrentCell());
    }

    @Test
    void putInCellWithoutTargetCell(){
        Cell cell = new Cell(new CellPosition(1, 1));
        Ball ball = new Ball(Color.blue);

        Assertions.assertThrows(IllegalArgumentException.class, () -> ball.putInCell(null));
    }

    @Test
    void putInNonEmptyCell(){
        Cell cell = new Cell(new CellPosition(1, 1));
        Ball ball = new Ball(Color.blue);
        Ball anotherBall = new Ball(Color.green);

        cell.setBall(ball);

        Assertions.assertThrows(IllegalArgumentException.class, () -> anotherBall.putInCell(cell));
    }

    @Test
    void putInMoreThanOneCell(){
        Cell cell = new Cell(new CellPosition(0, 1));
        Cell differentCell = new Cell(new CellPosition(1, 1));
        Ball ball = new Ball(Color.blue);

        ball.putInCell(cell);

        Assertions.assertThrows(IllegalArgumentException.class, () -> ball.putInCell(differentCell));
    }

    /*------------------- TESTS FOR removeFromCell() FUNCTION ------------------------*/
    @Test
    void removeFromCellTest(){
        Ball ball = new Ball(Color.RED);
        Cell cell = new Cell(new CellPosition(1, 1));

        ball.putInCell(cell);

        ball.removeFromCell();

        Assertions.assertNull(ball.getCurrentCell());
        Assertions.assertNull(cell.getBall());
    }

    @Test
    void removeBallFromCellWithoutCurrentCell() {
        Ball ball = new Ball(Color.blue);

        Assertions.assertThrows(IllegalArgumentException.class, () -> ball.removeFromCell());
    }


    /*------------------- TESTS FOR move() FUNCTION ------------------------*/

    @Test
    void moveTest(){
        Cell centralCell = new Cell(new CellPosition(1, 1));
        Cell westNeighbor = new Cell(new CellPosition(0, 1));
        Cell eastNeighbor = new Cell(new CellPosition(2, 1));
        Cell northNeighbor = new Cell(new CellPosition(1, 0));
        Cell southNeighbor = new Cell(new CellPosition(1, 2));

        centralCell.setNeighbor(westNeighbor, Direction.WEST);
        centralCell.setNeighbor(eastNeighbor, Direction.EAST);
        centralCell.setNeighbor(northNeighbor, Direction.NORTH);
        centralCell.setNeighbor(southNeighbor, Direction.SOUTH);

        Ball ball = new Ball(Color.blue);
        centralCell.setBall(ball);
        ball.move(Direction.EAST);


        Assertions.assertNull(centralCell.getBall());
        Assertions.assertEquals(ball, eastNeighbor.getBall());
        Assertions.assertEquals(eastNeighbor, ball.getCurrentCell());
    }

    @Test
    void moveToNonEmptyCell(){
        Cell centralCell = new Cell(new CellPosition(1, 1));
        Cell westNeighbor = new Cell(new CellPosition(0, 1));
        Cell eastNeighbor = new Cell(new CellPosition(2, 1));
        Cell northNeighbor = new Cell(new CellPosition(1, 0));
        Cell southNeighbor = new Cell(new CellPosition(1, 2));

        centralCell.setNeighbor(westNeighbor, Direction.WEST);
        centralCell.setNeighbor(eastNeighbor, Direction.EAST);
        centralCell.setNeighbor(northNeighbor, Direction.NORTH);
        centralCell.setNeighbor(southNeighbor, Direction.SOUTH);

        Ball ball = new Ball(Color.blue);
        Ball anotherBall = new Ball(Color.RED);

        ball.putInCell(centralCell);
        anotherBall.putInCell(eastNeighbor);

        Assertions.assertEquals(centralCell, ball.getCurrentCell());
        Assertions.assertEquals(ball, centralCell.getBall());
        Assertions.assertEquals(eastNeighbor, anotherBall.getCurrentCell());

        Assertions.assertThrows(IllegalArgumentException.class, () -> ball.move(Direction.EAST));
    }

    @Test
    void moveToNonExistingNeighbor(){
        Cell centralCell = new Cell(new CellPosition(1, 1));

        Ball ball = new Ball(Color.blue);

        ball.putInCell(centralCell);

        Assertions.assertThrows(NullPointerException.class, () -> ball.move(Direction.WEST));
    }

}
