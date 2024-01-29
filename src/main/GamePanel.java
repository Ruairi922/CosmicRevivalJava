package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable{

    //SOME SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; // need to scale as modern monitors have bigger resolutions
    public int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16; // 16 tiles horizontally
    public final int maxScreenRow = 12; // 12 tiles vertically
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD MAP SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;
    TileManager tileM = new TileManager(this);

    public KeyHandler keyH = new KeyHandler(this);

    Sound se = new Sound();
    Sound music = new Sound();

    // One of the most important aspects of a game is time
    // FPS - Frames per second
    // Need to start a game clock for this


    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;


    public Player player = new Player(this, this.keyH);
    public SuperObject obj[] = new SuperObject[15];
    public Entity npc[] = new Entity[10];

    // Game State
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueSate = 3;

    public GamePanel() throws IOException {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true); // good for better rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        aSetter.setObject();
        aSetter.setNPC();
        playMusic(0);
        gameState = playState;
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

        if(gameState == playState){
            //PLayer
            player.update();
            //Npc
            for(int i = 0; i<npc.length;i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }
        }
        if(gameState == pauseState){

        }
        player.update();
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        //DEBUGGING
        long drawStart = 0;
        if(keyH.checkDrawTime == true){
            drawStart = System.nanoTime();
        }



        // tile
        tileM.draw(g2);

        //Object
        for(int i = 0; i < obj.length; i++){
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }
        }

        //npc
        for(int i = 0; i<npc.length;i++){
            if(npc[i] != null){
                npc[i].draw(g2);
            }
        }

        //player
        player.draw(g2);

        // UI
        ui.draw(g2);
        g2.dispose();

        if(keyH.checkDrawTime == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10,400);
            System.out.println("draw time: " + passed);

        }

    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();

    }
    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
