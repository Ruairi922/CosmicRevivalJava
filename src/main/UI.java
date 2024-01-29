package main;

import object.OBJ_Engine;
import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font orbitron_40;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public String currentDialogue = "";

    public UI(GamePanel gp){
        this.gp = gp;
        orbitron_40 = new Font("8514oem Regular", Font.PLAIN, 40);
    }
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(orbitron_40);
        g2.setColor(Color.white);

        //PLay state
        if(gp.gameState == gp.playState){
            //do this later
        }
        //Pause state
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
        //Dialogue state
        if(gp.gameState == gp.dialogueSate){
            drawDialogueScreen();
        }
    }
    public void drawPauseScreen(){
        gp.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
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
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width , height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    public int getXforCenteredText(String text){
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
