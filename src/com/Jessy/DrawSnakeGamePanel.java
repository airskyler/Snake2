package com.Jessy;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;


/** This class responsible for displaying the graphics, so the snake, grid, food, instruction text and high score
 *
 * @author Clara
 *
 */


public class DrawSnakeGamePanel extends JPanel {


    private int gameStage = SnakeGame.BEFORE_GAME;  //use this to figure out what to paint

    private Snake snake;
    private Kibble food;
    private Score score;

    //Initialize images for game graphics
    private Image mushroom;
    private Image header;
    private Image dot;
    private Image win;
    private Image startPicture;
    private Image gameOver;

    DrawSnakeGamePanel(Snake s, Kibble f, Score sc){
        this.snake = s;
        this.food = f;
        this.score = sc;
    }

    //Set up the size of the window
    public Dimension getPreferredSize() {
        return new Dimension(SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        /* Where are we at in the game? 4 phases..
         * 1. Before game starts
         * 2. During game
         * 3. Game lost aka game over
         * 4. or, game won
         */

        gameStage = SnakeGame.getGameStage();

        switch (gameStage) {
            case 1: {
                displayInstructions(g);
                break;
            }
            case 2 : {
                //hide the menuBar during the game
                SnakeGame.menuBar.setVisible(false);
                displayGame(g);
                break;
            }
            case 3: {
                //make the menuBar visible during game over and winning screen
                SnakeGame.menuBar.setVisible(true);
                displayGameOver(g);
                break;
            }
            case 4: {
                SnakeGame.menuBar.setVisible(true);
                displayGameWon(g);
                break;
            }
        }



    }


    // method for the WIN game Graphics
    private void displayGameWon(Graphics g) {
        //Display the winning image and the high score
        String writeHighScore = score.getStringHighScore();
        win = getImage("win.png");
        g.drawImage(win, 0, 0, SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension, this);
        g.setColor(Color.BLACK);
        g.drawString("YOUR HIGH SCORE = " + writeHighScore, 150, 555);

    }


    // method for the game over graphics
    private void displayGameOver(Graphics g) {
        // display game over image
        gameOver = getImage("gameOver.jpg");
        g.drawImage(gameOver, 0, 0, SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension, this);

        // display the last score and high score
        String writeScore = score.getStringScore();
        String writeHighScore = score.getStringHighScore();
        String newHighScore = score.newHighScore();
        g.setColor(Color.BLUE);
        g.drawString("SCORE = " + writeScore, 150, 555);

        g.drawString("YOUR HIGH SCORE = " + writeHighScore,150 , 585);
        g.drawString(newHighScore, 175, 470);

    }

    private void displayGame(Graphics g) {
        displaySnake(g);
        displayKibble(g);
    }

    private void displayKibble(Graphics g) {


        //Get the changing food coordinates
        int x = food.getKibbleX() * SnakeGame.getSquareSize();
        int y = food.getKibbleY() * SnakeGame.getSquareSize();

        //Draw the food as an mushroom image
        //mushroom image is smaller than snake.
        mushroom = getImage("mushroom.png");
        g.drawImage(mushroom, x+1, y+1, SnakeGame.getSquareSize() -20, SnakeGame.getSquareSize() -20, this);
    }

    private void displaySnake(Graphics g) {

        LinkedList<Point> coordinates = snake.segmentsToDraw();
        Point head = coordinates.pop();
        // display snake head image
        header = getImage("head.png");
        g.drawImage(header,(int)head.getX(), (int)head.getY(), SnakeGame.getSquareSize(), SnakeGame.getSquareSize(), this);

        // display snake's body as blue dot image
        for (Point p : coordinates) {
            dot = getImage("dot.png");
            g.drawImage(dot,(int)p.getX(), (int)p.getY(), SnakeGame.getSquareSize(), SnakeGame.getSquareSize(), this);

        }

    }

    private void displayInstructions(Graphics g) {
        // display startPicture with an image
        startPicture = getImage("startSnake.png");
        g.drawImage(startPicture, 0, 0, SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension, this);
    }

    //method to get images from the images folder to use in game
    private Image getImage(String imagePNG) {
        Image image;
        ImageIcon iia = new ImageIcon("images\\" +imagePNG);
        image = iia.getImage();
        return image;
    }

}


