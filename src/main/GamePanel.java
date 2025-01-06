package main;

import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;
    EnvironmentManager eManager = new EnvironmentManager(this);

    public Player player = new Player(this, this.keyH);
    public Entity obj[] = new Entity[15];
    public Entity npc[] = new Entity[10];
    public Entity alien[] = new Entity[11];

    public Entity crewmate[] = new Entity[1];
    ArrayList<Entity> entityList = new ArrayList<>();

    // Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueSate = 3;
    public final int characterState = 4;
    public final int gameOverState = 5;
    public final int controlsState = 6;

    public final int winState = 7;

    public final int amountAliens = alien.length;
    public int amountAliensChange = alien.length;

    public final int amountCrewmates = crewmate.length;
    public int amountCrewmatesChange = crewmate.length;





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
        aSetter.setAlien();
        aSetter.setCrewmate();
        eManager.setup();
        playMusic(0);
        gameState = titleState;
    }

    public void retry(){
        player.setDefaultValues();
        player.setDefaultPositions();
        player.restoreLife();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setAlien();
        aSetter.setCrewmate();
        amountAliensChange = alien.length;
        player.hasEngineParts = 0;
    }

    public void restart(){
        player.setDefaultValues();
        player.setDefaultPositions();
        player.restoreLife();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setAlien();
        aSetter.setCrewmate();
        amountAliensChange = alien.length;
        player.hasEngineParts = 0;

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

            for(int i = 0; i<crewmate.length;i++){
                if(crewmate[i] != null){
                    crewmate[i].update();
                }
            }

            for(int i = 0; i<alien.length;i++){
                if(alien[i] != null){
                    if(alien[i].alive == true && alien[i].dying == false){
                        alien[i].update();
                    }
                    if(alien[i].alive == false){
                        alien[i] = null;
                    }

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

        //Title screen
        if(gameState == titleState){
            ui.draw(g2);
        }

        //Others
        else{
            // tile
            tileM.draw(g2);

            //ADD ENTITIES TO LIST
            entityList.add(player);
            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }

            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }
            for(int i = 0; i < alien.length; i++){
                if(alien[i] != null){
                    entityList.add(alien[i]);
                }
            }

            for(int i = 0; i < crewmate.length; i++){
                if(crewmate[i] != null){
                    entityList.add(crewmate[i]);
                }
            }



            //SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            //Draw entities
            for(int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }
            //empty entity list
            entityList.clear();

            eManager.draw(g2);

            // UI
            ui.draw(g2);
            g2.dispose();
        }



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
