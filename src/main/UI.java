package main;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import entity.Entity;
import object.OBJ_Engine;
import object.OBJ_Heart;
import object.OBJ_Key;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    private ImageLoader imageLoader = new ImageLoader();
    Font orbitron_40;
    BufferedImage heart_full, heart_half, heart_blank;

    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;


    public String currentDialogue = "";
    public int commandNum = 0;

    public UI(GamePanel gp){
        this.gp = gp;
        orbitron_40 = new Font("Terminal", Font.PLAIN, 40);

        //create HUB object
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }



    public void addMessage(String text){
        //message = text;
        //messageOn = true;
        message.add(text);
        messageCounter.add(0);

    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(orbitron_40);
        g2.setColor(Color.white);

        //Title State
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        //PLay state
        if(gp.gameState == gp.playState){
            drawPlayerLife();
            drawMessage();
        }
        //Pause state
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
        //Dialogue state
        if(gp.gameState == gp.dialogueSate){
            drawDialogueScreen();
        }
        //Character state
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
        }

        //Game over state
        if(gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }

        //Game over state
        if(gp.gameState == gp.controlsState){
            drawControlsScreen();
        }

        if(gameFinished == true){
            gp.gameState = gp.winState;
            drawWinScreen();
            //gameFinished = false;
        }
    }

    public void drawGameOverScreen(){
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text = "";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70));

        text = "MISSION FAILED";
        //SHADOW
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text, x, y);

        //MAIN
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        //OPTIONS
        //retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-40, y);
        }

        //back to title screen
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-40, y);
        }
    }

    public void drawPlayerLife(){
        //gp.player.life = 5;
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        //Draw max life
        while(i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y ,null);
            i++;
            x += gp.tileSize;
        }
        //reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;
        //Draw current life
        while(i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
    }
    public void drawMessage(){
        int messageX =gp.tileSize;
        int messageY = gp.tileSize*4;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16F));
        for(int i = 0; i < message.size(); i ++){

            if(message.get(i) != null){
                g2.setColor(Color.white);
                g2.drawString(message.get(i),messageX, messageY);

                int counter = messageCounter.get(i) +1;
                messageCounter.set(i, counter);
                messageY += 50;

                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }

    }

    public void drawTitleScreen(){
        //game name title
        g2.setColor(new Color(34, 65, 123));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Cosmic Revival";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;
        // shadow
        g2.setColor(Color.black);
        g2.drawString(text,x+8,y+8);

        //Main color
        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        //Shapceship Image
        x = gp.screenWidth/2-(gp.tileSize*2)/2;
        y = gp.tileSize*5;
        BufferedImage spaceshipImage = imageLoader.loadImage("/images/spaceship_icon.jpg");

        g2.drawImage(spaceshipImage, x, y, gp.tileSize*2, gp.tileSize*2, null);

        //menu section
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
        text = "START MISSION";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "CONTROLS";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">", x-gp.tileSize, y);
        }
    }

    public void drawControlsScreen(){
        //game name title
        g2.setColor(new Color(255, 255, 255, 200));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Controls";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*2;
        // shadow
        g2.setColor(Color.lightGray);
        g2.drawString(text,x+8,y+8);

        //Main color
        g2.setColor(Color.black);
        g2.drawString(text,x,y);

        //Shapceship Image
        x = gp.screenWidth/2-(gp.tileSize*2)/2;
        y = gp.tileSize*3;
        BufferedImage spaceshipImage = imageLoader.loadImage("/images/game-controller.png");

        g2.drawImage(spaceshipImage, x, y, gp.tileSize*2, gp.tileSize*2, null);

        //menu section
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
        text = "Walk forwad:      w  /  ↑";
        x = getXforCenteredText(text);
        y += gp.tileSize*3;
        g2.drawString(text, x, y);

        text = "Walk down:      s  /  ↓";
        x = getXforCenteredText(text);
        y += gp.tileSize-10;
        g2.drawString(text, x, y);

        text = "Walk left:      a  /  ←";
        x = getXforCenteredText(text);
        y += gp.tileSize-10;
        g2.drawString(text, x, y);

        text = "Walk right:      d  /  →";
        x = getXforCenteredText(text);
        y += gp.tileSize-10;
        g2.drawString(text, x, y);

        text = "Punch / Interact:      ENTER";
        x = getXforCenteredText(text);
        y += gp.tileSize-10;
        g2.drawString(text, x, y);

        text = "Pause:      p";
        x = getXforCenteredText(text);
        y += gp.tileSize-10;
        g2.drawString(text, x, y);

        text = "Stats:      c";
        x = getXforCenteredText(text);
        y += gp.tileSize-10;
        g2.drawString(text, x, y);

        text = "Press ENTER to go back";
        x = getXforCenteredText(text);
        y += gp.tileSize-10;
        g2.drawString(text, x, y);

    }
    public void drawPauseScreen(){
        Color c = new Color(0,0,0, 200);
        g2.setColor(c);
        g2.fillRoundRect(0, 0, gp.screenWidth , gp.screenHeight, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        gp.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
        text = "RESUME";
        x = getXforCenteredText(text);
        y += gp.tileSize*2;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "CONTROLS";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "OUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">", x-gp.tileSize, y);
        }
    }
    public void drawDialogueScreen(){

        //Window for convo
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth- (gp.tileSize*4);
        int height = gp.tileSize*4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16F));
        x+= gp.tileSize;
        y += gp.tileSize;
        for(String sent : currentDialogue.split("\n")){
            g2.drawString(sent, x, y);
            y += 40;
        }

    }
    public void drawCharacterScreen(){
        //Create frame
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*8;
        final int frameHeight = gp.tileSize*9;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX +20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 38;

        // names
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Speed", textX, textY);
        textY += lineHeight;
        g2.drawString("keys", textX, textY);
        textY += lineHeight;
        g2.drawString("Engine Parts", textX, textY);
        textY += lineHeight;
        g2.drawString("Aliens left", textX, textY);
        textY += lineHeight;
        g2.drawString("Crazy crewmate", textX, textY);
        textY += lineHeight;

        //values
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.speed +"/6");
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.hasKey);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.hasEngineParts + "/4");
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.amountAliensChange + "/" + gp.amountAliens);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.amountCrewmatesChange + "/" + gp.amountCrewmates);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;


    }
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width , height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    public void drawWinScreen(){
        g2.setColor(new Color(34, 65, 123));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text = "";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50));

        text = "MISSION SUCCESS!!";
        //SHADOW
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = 55;
        g2.drawString(text, x, y);

        //MAIN
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        g2.setFont(g2.getFont().deriveFont(20f));

        text = "Creator - Ruairi Mulhall";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);

        text = "Music - OutThere by yd";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);

        text = "Sound Effects by- Konita Tutorials and RyiSnow";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(30f));
        text = "Thank you for playing!!";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);

        //Shapceship Image
        x = gp.screenWidth/2-(gp.tileSize*2)/2;
        y = gp.tileSize*7;
        BufferedImage spaceshipImage = imageLoader.loadImage("/images/spaceship_icon.jpg");

        g2.drawImage(spaceshipImage, x, y, gp.tileSize*2, gp.tileSize*2, null);
    }

    public int getXforCenteredText(String text){
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

    public int getXforAlignToRightText(String text, int tailX){
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }

}
