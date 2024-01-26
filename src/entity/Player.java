package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) throws IOException {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() throws IOException {
        try {
            up1 = read(getClass().getClassLoader().getResourceAsStream("player/boy_up_1.png"));
            up2 = read(getClass().getClassLoader().getResourceAsStream("player/boy_up_2.png"));
            down1 = read(getClass().getClassLoader().getResourceAsStream("player/boy_down_1.png"));
            down2 = read(getClass().getClassLoader().getResourceAsStream("player/boy_down_2.png"));
            left1 = read(getClass().getClassLoader().getResourceAsStream("player/boy_left_1.png"));
            left2 = read(getClass().getClassLoader().getResourceAsStream("player/boy_left_2.png"));
            right1 = read(getClass().getClassLoader().getResourceAsStream("player/boy_right_1.png"));
            right2 = read(getClass().getClassLoader().getResourceAsStream("player/boy_right_2.png"));

            if (up1 == null || up2 == null || down1 == null || down2 == null ||
                    left1 == null || left2 == null || right1 == null || right2 == null) {
                throw new IOException("Failed to load player images");
            }
        } catch (IOException e) {
            // Handle the exception or rethrow if necessary
            throw new IOException("Error loading player images", e);
        }
    }
    public void update(){
        if(keyH.upPressed){
            direction = "up";
            y -= speed;
        }else if(keyH.downPressed){
            direction = "down";
            y += speed;
        }else if(keyH.leftPressed){
            direction = "left";
            x -= speed;
        }else if(keyH.rightPressed){
            direction = "right";
            x += speed;
        }
    }
    public void draw(Graphics2D g2){
        //g2.setColor(Color.white);
        //g2.fillRect(x,y,gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch (direction){
            case "up":
                image = up1;
                break;
            case "down":
                image = down1;
                break;
            case "left":
                image = left1;
                break;
            case "right":
                image = right1;
                break;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

    }
}
