
// The snakes original code comes from "https://github.com/minneapolis-edu/snake"
// from the instructor of Clara James, I have modify the snake code for the class project below.

// I have much of the help from Andre from a Learning Center from MCTC for this
// snake project code...



package com.Jessy;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;



public class SnakeGame {


    public final static int xPixelMaxDimension = 701;  //Pixels in window. 701 to have 70-pixel squares plus 1 to draw a border on last square
    public final static int yPixelMaxDimension = 701;

    private static int xSquares ;
    private static int ySquares ;

    //Getters and setters to change square location and size
    public static int getxSquares() {
        return xSquares;
    }

    public static void setxSquares() {
        SnakeGame.xSquares = xPixelMaxDimension / squareSize;
    }

    public static int getySquares() {
        return ySquares;
    }

    public static void setySquares() {
        SnakeGame.ySquares = yPixelMaxDimension / squareSize;
    }

    public static int getSquareSize() {
        return squareSize;
    }

    private static int squareSize = 70;


    //Setter to change the square size for the game
    public static void setSquareSize(int squareSize) {
        SnakeGame.squareSize = squareSize;
    }

    private static Snake snake ;

    private static Kibble kibble;

    private static Score score;


    static final int BEFORE_GAME = 1;
    static final int DURING_GAME = 2;
    static final int GAME_OVER = 3;
    static final int GAME_WON = 4;   //The values are not important. The important thing is to use the constants
    //instead of the values so you are clear what you are setting. Easy to forget what number is Game over vs. game won
    //Using constant names instead makes it easier to keep it straight. Refer to these variables
    //using statements such as SnakeGame.GAME_OVER

    private static int gameStage = BEFORE_GAME;  //use this to figure out what should be happening.
    //Other classes like Snake and DrawSnakeGamePanel will need to query this, and change it's value

    private static long clockInterval = 300; //controls game speed
    //Every time the clock ticks, the snake moves
    //This is the time between clock ticks, in milliseconds
    //1000 milliseconds = 1  second.

    //setter to change the game's speed
    public static void setClockInterval(long clockInterval) {
        SnakeGame.clockInterval = clockInterval;
    }


    static JFrame snakeFrame;

    static DrawSnakeGamePanel snakePanel;
    //Framework for this class adapted from the Java Swing Tutorial, FrameDemo and Custom Painting Demo. You should find them useful too.
    //http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FrameDemoProject/src/components/FrameDemo.java
    //http://docs.oracle.com/javase/tutorial/uiswing/painting/step2.html

    //Instantiate a new menuBar using the setJMenuBar method
    static JMenuBar menuBar = setJMenuBar();



    private static void createAndShowGUI() {
        //Create and set up the window.
        snakeFrame = new JFrame();
        snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        snakeFrame.setJMenuBar(menuBar);


        snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension);
        snakeFrame.setUndecorated(true); //hide the title bar
        snakeFrame.setVisible(true);
        snakeFrame.setResizable(false);

        snakePanel = new DrawSnakeGamePanel(snake, kibble, score);
        snakePanel.setFocusable(true);
        snakePanel.requestFocusInWindow(); //required to give this component the focus so it can generate KeyEvents

        snakeFrame.add(snakePanel);
        snakePanel.addKeyListener(new GameControls(snake));

        setGameStage(BEFORE_GAME);

        snakeFrame.setVisible(true);
    }

    private static void initializeGame() {
        //set up score, snake and first kibble
        xSquares = xPixelMaxDimension / squareSize;
        ySquares = yPixelMaxDimension / squareSize;

        snake = new Snake(xSquares, ySquares, squareSize);
        kibble = new Kibble(snake);
        score = new Score();
        gameStage = BEFORE_GAME;
    }

    protected static void newGame() {
        Timer timer = new Timer();
        GameClock clockTick = new GameClock(snake, kibble, snakePanel);
        timer.scheduleAtFixedRate(clockTick, 0, clockInterval);

    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initializeGame();
                createAndShowGUI();
            }
        });
    }



    public static int getGameStage() {
        return gameStage;
    }


    public static void setGameStage(int gameStage) {
        SnakeGame.gameStage = gameStage;
    }

    //Method to create the menuBar for the game
    public static JMenuBar setJMenuBar() {

        JMenuBar menue = new JMenuBar();

        //Creating first option field with start a "New Game" and "Exit" the game
        JMenu game = new JMenu("Game");
        final JMenuItem newgame = new JMenuItem("New Game");
        JMenuItem exit = new JMenuItem("Exit");

        //If the new game is clicked... start game
        newgame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                setGameStage(SnakeGame.DURING_GAME);
                // Start game and timer
                newGame();

                //reset the snake for the initial first game because
                //the user might have changed the field size of the game.
                snake.reset();

                //If it is not the initial first game reset the score
                if (SnakeGame.getGameStage() == SnakeGame.DURING_GAME) {
                    Score.resetScore();
                }
                snakePanel.repaint();

            }
        });

        //If exit is clicked...  exit the game
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Runtime.getRuntime().exit(0);
            }
        });

        //Add everything to the first option and then to the menuBar
        game.add(newgame);

        //adding separator for better look
        game.addSeparator();
        game.add(exit);
        menue.add(game);

        //Initialise the warp walls option
        JMenu type = new JMenu("Warp Walls");

        //Group the radio buttons together
        // so just one can be selected
        JRadioButtonMenuItem rbMenuItem;
        ButtonGroup group = new ButtonGroup();

        rbMenuItem = new JRadioButtonMenuItem("On");

        //if the radio button is selected turn the warp walls

        rbMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                snake.setHasWarp(true);
            }
        });

        //adding item to group and option
        group.add(rbMenuItem);
        type.add(rbMenuItem);


        rbMenuItem = new JRadioButtonMenuItem("Off");
        //set no warp walls for the game

        rbMenuItem.setSelected(true);
        rbMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                snake.setHasWarp(false);
            }
        });
        group.add(rbMenuItem);
        type.add(rbMenuItem);

        //add the option to the menuBar bar
        menue.add(type);

        //Creating a new option for the menuBar to change the size of the game
        JMenu level = new JMenu("Size");

        //using grouped radio buttons to just select one option
        ButtonGroup group2 = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem("5x5");

        //have the smallest game size as default
        rbMenuItem.setSelected(true);

        //adding action listener to change the square size to 80 for the game
        // and call the update method
        rbMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setSquareSize(100);
                updateField();
            }
        });
        //adding item to group and option
        group2.add(rbMenuItem);
        level.add(rbMenuItem);


        //adding action listener to change the square size to 50 for the game
        // and call the update method
        rbMenuItem = new JRadioButtonMenuItem("10x10");
        rbMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setSquareSize(60);
                updateField();
            }
        });
        group2.add(rbMenuItem);
        level.add(rbMenuItem);


        //adding action listener to change the square size to 30 for the game
        // and call the update method
        rbMenuItem = new JRadioButtonMenuItem("15x15");
        rbMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setSquareSize(35);
                updateField();
            }
        });
        group2.add(rbMenuItem);
        level.add(rbMenuItem);
        //adding the game size option to the menuBar
        menue.add(level);

        //Creating the option to change the speed for the game
        JMenu speed = new JMenu("Speed");

        //grouping the radio buttons
        ButtonGroup group3 = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem("Slow");

        //making the slowest speed as default
        rbMenuItem.setSelected(true);

        //If the item is selected set the clock interval that controls the speed
        //to a half second tick
        rbMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setClockInterval(300);

            }
        });
        group3.add(rbMenuItem);
        speed.add(rbMenuItem);


        rbMenuItem = new JRadioButtonMenuItem("Medium");
        //change to about a third of a second
        rbMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setClockInterval(250);
            }
        });
        group3.add(rbMenuItem);
        speed.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("Fast");
        //change to about a tenth of a second
        rbMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setClockInterval(200);
            }
        });
        group3.add(rbMenuItem);
        speed.add(rbMenuItem);
        menue.add(speed);

        //Creating instruction option to show the user
        // how to play the snake game
        JMenu help = new JMenu("Instruction");

        JMenuItem instruction = new JMenuItem("Instruction");

        instruction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Welcome to Snake Game\n\nPlease use the option from the menuBar to change the size, speed and wrap wall setting of the game\n\n" +
                        "To control the snake... use the arrow keys Up, Down, Left, and Right to\n" +
                        "control the snake movement to eat the food (mushroom)...\n\n" +
                        "The game ends when the snake either moves off the screen or moves into itself.\nThe goal is to make snake eat the food as long as possible,\n" +
                        " while game screen space is free.\n\n" +
                        "To start the game, click on New Game menuBar option");
            }
        });

        help.add(instruction);

        menue.add(help);

        return menue;

    }

    //Method to update the game's square size
    public static void updateField() {

        setxSquares();
        setySquares();
        snake.setSquareSize(getSquareSize());
        snake.setMaxX(getxSquares());
        snake.setMaxY(getySquares());
        snake.snakeSquares = new int[getxSquares()][getySquares()];
        kibble.moveKibble(snake);
    }

}



