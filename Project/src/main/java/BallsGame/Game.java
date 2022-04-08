package BallsGame;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Game extends JFrame{

    private final GameField gameField;
    private int score;
    private Timer timer;

    Game(){
        gameField = new GameField();
        gameField.fillCells();

        setTitle("Шарики");
        JPanel content = (JPanel)getContentPane();
        content.setLayout(new FlowLayout());
        content.add(gameField);

        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(Color.LIGHT_GRAY);
        scorePanel.setPreferredSize(new Dimension(100, 100));

        String s=Integer.toString(score);
        JLabel jlabel = new JLabel(s);
        JLabel jlabel_2 = new JLabel("Score");
        jlabel_2.setFont(new Font("Showcard Gothic",1,20));
        jlabel.setFont(new Font("Showcard Gothic",1,20));
        scorePanel.add(jlabel_2);
        scorePanel.add(jlabel);

        content.add(scorePanel);

        pack();
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        timer = new Timer(90000, new actionTimeOut());
        timer.setRepeats(true);
        timer.start();

        gameField.addMouseListener(new mouseClickedAdapter());
    }

    private class mouseClickedAdapter extends MouseAdapter {

        public void mouseClicked(@NotNull MouseEvent event) {
            int column = event.getX()/gameField.getCellSize();
            int row = event.getY()/gameField.getCellSize();

            System.out.print(column);
            System.out.print(",");
            System.out.print(row);
            System.out.println("-");

            Cell cell = gameField.getCell(column, row);
            Ball ball = cell.getBall();
            String str = new String();

            if (ball != null)
            {
                if(ball.getColor() == Color.BLUE){str = "BLUE";}
                if(ball.getColor() == Color.YELLOW){str = "YELLOW";}
                if(ball.getColor() == Color.RED){str = "RED";}
                if(ball.getColor() == Color.MAGENTA){str = "PURPLE";}
                if(ball.getColor() == Color.GREEN){str = "GREEN";}
                System.out.println(str);
                ArrayList<Ball> set = gameField.getBallSet(ball);
                gameField.deleteBallSet(set);
                repaint();
            }
        }
    }

    class actionTimeOut implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameField.isGameOver()){
                gameField.createNewLine();
                score = gameField.getScore();
                gameField.repaint();
            }
            else {
               gameField.removeMouseListener(new mouseClickedAdapter());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game();
            }
        });
    }
}
