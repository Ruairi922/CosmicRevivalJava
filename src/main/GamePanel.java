package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable{

    //SOME SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; // need to scale as modern monitors have bigger resolutions
    public int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 16; // 16 tiles horizontally
    final int maxScreenRow = 12; // 12 tiles vertically
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();

    // One of the most important aspects of a game is time
    // FPS - Frames per second
    // Need to start a game clock for this

    Thread gameThread;
    Player player = new Player(this, this.keyH);

    // set players default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;


    public GamePanel() throws IOException {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // good for better rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();

    }


    @Override
    public void run() {
        // from implementing Runnable
        // in this method we will create a game loop which is the core of a game!!!!

        double drawInterval = 1000000000/FPS; // Means redraw the screen every 0.016667 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        // DISPLAY FPS
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer = (currentTime - lastTime);

            lastTime = currentTime;

            if(delta >= 1){
                // 1. UPDATE: update info such as character positions
                update();
                // 2. DRAW: draw the screen with the updated information
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        player.draw(g2);
        g2.dispose();
    }
}
