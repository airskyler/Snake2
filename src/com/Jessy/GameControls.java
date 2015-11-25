package com.Jessy;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GameControls implements KeyListener {


    Snake snake;

    GameControls(Snake s){
        this.snake = s;
    }

    public void keyPressed(KeyEvent ev) {
        //keyPressed events are for catching events like function keys, enter, arrow keys
        //We want to listen for arrow keys to move snake
        //Has to id if user pressed arrow key, and if so, send info to Snake object

        if (ev.getKeyCode() == KeyEvent.VK_DOWN) {
            //System.out.println("snake down");
            snake.snakeDown();
        }
        if (ev.getKeyCode() == KeyEvent.VK_UP) {
            //System.out.println("snake up");
            snake.snakeUp();
        }
        if (ev.getKeyCode() == KeyEvent.VK_LEFT) {
            //System.out.println("snake left");
            snake.snakeLeft();
        }
        if (ev.getKeyCode() == KeyEvent.VK_RIGHT) {
            //System.out.println("snake right");
            snake.snakeRight();
        }

    }



    @Override
    public void keyTyped(KeyEvent e) {
    }  // Don't need this method but it is required to implement this method


    @Override
    public void keyReleased(KeyEvent e) {

    }

}



