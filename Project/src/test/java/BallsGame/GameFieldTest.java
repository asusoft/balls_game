package BallsGame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameFieldTest {
    final int STANDARD_FIELD_HEIGHT = 20;
    final int STANDARD_FIELD_WIDTH = 10;
    @Test
    void getNeighborTest(){

        GameField field = new GameField();

        Cell centralCell = field.getCell(1, 1);
        Cell eastNeighbor = field.getCell(2, 1);
        Cell westNeighbor = field.getCell(0, 1);
        Cell northNeighbor = field.getCell(1, 0);
        Cell southNeighbor = field.getCell(1, 2);

        Assertions.assertEquals(eastNeighbor, centralCell.getNeighbor(Direction.EAST));
        Assertions.assertEquals(westNeighbor, centralCell.getNeighbor(Direction.WEST));
        Assertions.assertEquals(northNeighbor, centralCell.getNeighbor(Direction.NORTH));
        Assertions.assertEquals(southNeighbor, centralCell.getNeighbor(Direction.SOUTH));

    }

    @Test
    void getNeighborNull(){

        GameField field = new GameField();

        Cell centralCell = field.getCell(0, 0);

        Assertions.assertNull(centralCell.getNeighbor(Direction.NORTH));
    }

    @Test
    void createNewLineTest(){
        GameField field = new GameField();
        int row = 4;

        Assertions.assertTrue(field.createNewLine(row));

        for(int x = 0; x < field.Width(); ++x){
            Assertions.assertFalse(field.getCell(x, row).isEmpty());
        }

    }

    @Test
    void createNewLineNonEmptyRow(){
        GameField field = new GameField();
        field.fillCells();
        int row = field.Height() - 1;

        Assertions.assertFalse(field.createNewLine(row));
    }

    @Test
    void getBallSetTest(){
        GameField field = new GameField();


        Cell targetCell = field.getCell(6,17);
        Cell newCell1 = targetCell.getNeighbor(Direction.NORTH);
        Cell newCell2 = newCell1.getNeighbor(Direction.WEST);
        Cell newCell3 = targetCell.getNeighbor(Direction.EAST);
        Cell newCell4 = newCell3.getNeighbor(Direction.SOUTH);

        Cell newCell5 = targetCell.getNeighbor(Direction.WEST);

        Ball targetBall = new Ball(Color.blue);
        Ball newBall1 = new Ball(Color.blue);
        Ball newBall2 = new Ball(Color.blue);
        Ball newBall3 = new Ball(Color.blue);
        Ball newBall4 = new Ball(Color.blue);

        Ball newBall5 = new Ball(Color.red);


        targetCell.setBall(targetBall);
        newCell1.setBall(newBall1);
        newCell2.setBall(newBall2);
        newCell3.setBall(newBall3);
        newCell4.setBall(newBall4);
        newCell5.setBall(newBall5);



        ArrayList <Ball> set = field.getBallSet(targetBall);

        Assertions.assertTrue(set.contains(targetBall));
        Assertions.assertTrue(set.contains(newBall1));
        Assertions.assertTrue(set.contains(newBall2));
        Assertions.assertTrue(set.contains(newBall3));
        Assertions.assertTrue(set.contains(newBall4));

        Assertions.assertFalse(set.contains(newBall5));
    }

    @Test
    void getBallSetNull(){
        GameField field = new GameField();

        Cell targetCell = field.getCell(6,17);
        Cell newCell1 = targetCell.getNeighbor(Direction.NORTH);
        Cell newCell2 = newCell1.getNeighbor(Direction.WEST);
        Cell newCell3 = targetCell.getNeighbor(Direction.EAST);
        Cell newCell4 = newCell3.getNeighbor(Direction.SOUTH);

        Cell newCell5 = targetCell.getNeighbor(Direction.WEST);

        Ball targetBall = new Ball(Color.blue);
        Ball newBall1 = new Ball(Color.red);
        Ball newBall2 = new Ball(Color.green);
        Ball newBall3 = new Ball(Color.white);
        Ball newBall4 = new Ball(Color.yellow);
        Ball newBall5 = new Ball(Color.blue);

        targetCell.setBall(targetBall);
        newCell1.setBall(newBall1);
        newCell2.setBall(newBall2);
        newCell3.setBall(newBall3);
        newCell4.setBall(newBall4);
        newCell5.setBall(newBall5);

        ArrayList <Ball> set = field.getBallSet(targetBall);

        Assertions.assertNull(set);
    }

    @Test
    void deleteBallSetTest(){
        GameField field = new GameField();
        ArrayList <Ball> balls = field.balls();

        Cell targetCell = field.getCell(6,17);
        Cell newCell1 = targetCell.getNeighbor(Direction.NORTH);
        Cell newCell2 = newCell1.getNeighbor(Direction.WEST);
        Cell newCell3 = targetCell.getNeighbor(Direction.EAST);
        Cell newCell4 = newCell3.getNeighbor(Direction.SOUTH);

        Cell newCell5 = targetCell.getNeighbor(Direction.WEST);

        Ball targetBall = new Ball(Color.blue);
        Ball newBall1 = new Ball(Color.blue);
        Ball newBall2 = new Ball(Color.blue);
        Ball newBall3 = new Ball(Color.blue);
        Ball newBall4 = new Ball(Color.blue);
        Ball newBall5 = new Ball(Color.red);

        balls.add(targetBall);
        balls.add(newBall1);
        balls.add(newBall2);
        balls.add(newBall3);
        balls.add(newBall4);
        balls.add(newBall5);

        Assertions.assertTrue(balls.contains(targetBall));
        Assertions.assertTrue(balls.contains(newBall1));
        Assertions.assertTrue(balls.contains(newBall2));
        Assertions.assertTrue(balls.contains(newBall3));
        Assertions.assertTrue(balls.contains(newBall4));
        Assertions.assertTrue(balls.contains(newBall5));

        targetCell.setBall(targetBall);
        newCell1.setBall(newBall1);
        newCell2.setBall(newBall2);
        newCell3.setBall(newBall3);
        newCell4.setBall(newBall4);
        newCell5.setBall(newBall5);

        ArrayList <Ball> set = field.getBallSet(targetBall);

        field.deleteBallSet(set);

        Assertions.assertNull(targetCell.getBall());
        Assertions.assertNull(newCell1.getBall());
        Assertions.assertNull(newCell2.getBall());
        Assertions.assertNull(newCell3.getBall());
        Assertions.assertNull(newCell4.getBall());

        Assertions.assertFalse(balls.contains(targetBall));
        Assertions.assertFalse(balls.contains(newBall1));
        Assertions.assertFalse(balls.contains(newBall2));
        Assertions.assertFalse(balls.contains(newBall3));
        Assertions.assertFalse(balls.contains(newBall4));

        Assertions.assertTrue(balls.contains(newBall5));
    }
}
