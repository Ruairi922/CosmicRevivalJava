package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Player extends Entity{

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasEngineParts = 0;
    public int hasKey = 0;



    public Player(GamePanel gp, KeyHandler keyH) throws IOException {
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 4;
        solidArea.y = 8;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 16;
        solidArea.height = 16;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 10;
        worldY = gp.tileSize * 41;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() throws IOException {
        up1 = setup("/player/boy_up_one");
        up2 = setup("/player/boy_up_two");
        down1 = setup("/player/boy_down_one");
        down2 = setup("/player/boy_down_two");
        left1 = setup("/player/boy_left_one");
        left2 = setup("/player/boy_left_two");
        right1 = setup("/player/boy_right_one");
        right2 = setup("/player/boy_right_two");
    }

    public void update(){

        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            if(keyH.upPressed){
                direction = "up";
            }else if(keyH.downPressed){
                direction = "down";
            }else if(keyH.leftPressed){
                direction = "left";
            }else if(keyH.rightPressed){
                direction = "right";
            }

        }


        // check tile collisions
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // check object collision
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        //check npc collision
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);

        // if collision is false player can move
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            if(!collisionOn){
                switch (direction){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

        }

        spriteCounter++;
        if(spriteCounter > 15){
            if(spriteNum == 1){
                spriteNum = 2;
            }else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;

        }


    }

    public void pickUpObject(int i){
        int partsRemaining =4;
        if(i != 999){

            String objectName = gp.obj[i].name;

            switch(objectName){
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You collected a key");
                    break;
                case "Door", "Door 2":
                    if(hasKey > 0){
                        gp.playSE(5);
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("You Opened Door");
                    }else{
                        gp.ui.showMessage("You need a key to open this door");
                        gp.playSE(6);
                    }
                    System.out.println("Key: " + hasKey);

                    break;
                case "Neutrino Injector", "Photon Matrix", "Quantum Stabilizer", "Warp Core Regulator":
                    gp.playSE(4);
                    hasEngineParts++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Engine Parts: " + hasEngineParts + "/" + partsRemaining  + ". You collected the " + objectName);
                    break;
                case "Engine":
                    if(hasEngineParts > 0){
                        partsRemaining--;
                        hasEngineParts--;
                    }
                    System.out.println("Key: " + hasKey);

                    break;
                case "Space Shoes":
                    gp.playSE(1);
                    speed += 2;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You have obtained Space Shoes, Your Player Speed has increased");
                break;
            }
        }
    }
    public void interactNPC(int i){
        if(i != 999){
            if(gp.keyH.enterPressed == true){
                gp.gameState = gp.dialogueSate;
                gp.npc[i].speak();
            }

        }
        gp.keyH.enterPressed = false;
    }
    public void draw(Graphics2D g2){
        //g2.setColor(Color.white);
        //g2.fillRect(x,y,gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch (direction){
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY,  null);

    }
}
