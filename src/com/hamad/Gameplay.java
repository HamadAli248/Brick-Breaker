package com.hamad;

//installs all the packages for the code
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import  javax.swing.Timer;

// creates the class with all the code for game and also action and key listener for functionality
public class Gameplay extends JPanel implements KeyListener, ActionListener {

    // set the variables for the game
    private boolean play = false;
    private int score = 0;
    private  int level = 1;

    //variables for the bricks, size and the amount of bricks
    int totalBricks = 21;
    int row = 3;
    int collum = 7;


    private Timer timer;
    private  int delay = 8;

    private int playerX = 310;

    // initial position for the ball and the direction it will move
    private int ballposX =120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;

    // gameplay function so that it starts the game
    public Gameplay(){
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }

    // Designing elements so background , map/bricks , border, score, level,
    public void paint(Graphics g) {

        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //drawing map
        map.draw((Graphics2D) g);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        //Score
        g.setColor(Color.white);
        g.setFont(new Font("Times", Font.BOLD, 30));
        g.drawString("Score: " + score, 490, 30);

        // level
        g.setColor(Color.white);
        g.setFont(new Font("Times", Font.BOLD, 30));
        g.drawString("Level:" + level, 10, 30);

        // the paddle
        g.setColor(Color.blue);
        g.fillRect(playerX, 550, 100, 10);

        //Ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);



        // code so when theres no bricks left it goes onto next level
        if (totalBricks == 0) {
            play = false;
            ballYdir = 0;
            ballXdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("Times", Font.BOLD, 30));
            g.drawString("LEVEL COMPLETE", 260, 300);

            g.setFont(new Font("Times", Font.BOLD, 20));
            g.drawString("Press N To go to next Level", 230, 350);
        }

        // code so that when the ball goes below the paddle it ends the game
        if (ballposY > 570) {
            play = false;
            ballYdir = 0;
            ballXdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("Times", Font.BOLD, 30));
            g.drawString("Game Over, Score:" + score + "  Level:" + level, 190, 300);

            g.setFont(new Font("Times", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 280, 350);


        }

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // starts  the game
        timer.start();
        if(play){
            if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ballYdir = - ballYdir;
            }
            A: for(int i=0; i<map.map.length; i++){
                for(int j =0 ; j< map.map[0].length; j++ ){
                    if(map.map[i][j]> 0){
                        int brickX =j * map.brickWidth +80;
                        int brickY = i* map.brickHeight +50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect = rect;

                        if ( ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks --;
                            score+=5;
                            System.out.println(totalBricks);

                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x+brickRect.width ){
                                ballXdir = -ballXdir;
                            }
                            else{
                                ballYdir= -ballYdir;
                            }
                            break A;
                        }

                    }
                }
            }
            ballposX += ballXdir;
            ballposY += ballYdir;
            if (ballposX < 0){
                ballXdir = - ballXdir;
            }
            if (ballposY < 0){
                ballYdir = - ballYdir;
            }
            if (ballposX > 670){
                ballXdir = - ballXdir;
            }
        }

        repaint();
    }

    // looks at which button is pressed and the preforms the action
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }

        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (playerX < 10) {
                    playerX = 10;
                } else {
                    moveLeft();
                }
            }
        }

        // if game is ended then it restarts the game
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (!play) {
                    play = true;
                    ballposX = 120;
                    ballposY = 350;
                    ballXdir = -1;
                    ballYdir = -2;
                    playerX = 310;
                    score = 0;
                    totalBricks = 21;
                    level = 1;
                    map = new MapGenerator(row, collum);

                    repaint();
                }
            }

            // level complete so it generates next level

           if (e.getKeyCode() == KeyEvent.VK_N){
               if(totalBricks == 0){

                   play = true;
                   ballposX= 140;
                   ballposY =300;
                   ballXdir = -1;
                   ballYdir = -3;
                   playerX =310;
                   totalBricks =28;
                   level = level +1;
                   map = new MapGenerator(row+1,collum);

                   repaint();
               }
            }

    }

    public void nextLevel(){

    }

    public void moveRight(){
            play = true;
            playerX += 20;
        }
    public void moveLeft() {
            play = true;
            playerX -= 20;
       }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }

}
