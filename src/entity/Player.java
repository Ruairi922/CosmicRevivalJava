package entity;

import main.CollisionChecker;
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
        attackArea.width = 36;
        attackArea.height = 36;


        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 10;
        worldY = gp.tileSize * 41;
        speed = 2;
        direction = "down";

        //Player status
        level = 1;
        maxLife = 6;
        life = maxLife;
        strength = 1;
        dexterity = 1; //more dexterity means less damage recieved when he gets attacked
        //attack = getAttack();

    }
    public void setDefaultPositions(){
        worldX = gp.tileSize * 10;
        worldY = gp.tileSize * 41;
        direction = "down";

    }
    public void restoreLife(){
        life = maxLife;
        invincible = false;
    }
    /*public int getAttack(){
        return attack = strength;
    }*/

    public void getPlayerImage() throws IOException {
        up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);

    }
    public void getPlayerAttackImage(){
        attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
    }

    public void update(){
        if(attacking == true){
            attacking();
        }
        else if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            } /*else if (keyH.enterPressed) {
                attacking = true;
                attacking();
            }
            attacking = false;*/


            // check tile collisions
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // check object collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //check npc collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // check alien collision
            int alienIndex = gp.cChecker.checkEntity(this, gp.alien);
            contactAlien(alienIndex);

            // check crewmate collision
            int crewmateIndex = gp.cChecker.checkEntity(this, gp.crewmate);
            contactCrewmate(crewmateIndex);

            // check event
            gp.eHandler.checkEvent();


            // if collision is false player can move
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                if (!collisionOn && keyH.enterPressed == false) {
                    switch (direction) {
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

            gp.keyH.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 15) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;

            }
        }

        //outside key if statment
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(life <= 0){
            gp.gameState = gp.gameOverState;
            //gp.playSE(11);

        }


    }
    public void attacking(){
        spriteCounter++;

        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;

            //SAVE CURRENT WorldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //ADJUST PLAYERS WORLDX FOR ATTACK AREA
            switch(direction){
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }

            //attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            //CHeck alien collision with updated worldX, worldY, solidArea
            int alienIndex = gp.cChecker.checkEntity(this, gp.alien);
            damageAlien(alienIndex);

            //CHeck crewmate collision with updated worldX, worldY, solidArea
            int crewmateIndex = gp.cChecker.checkEntity(this, gp.crewmate);
            damageCrewmate(crewmateIndex);

            //after check restore original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;





        }
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;

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
                    gp.ui.addMessage("You collected a key");
                    break;
                case "Door", "Door 2":
                    if(hasKey > 0){
                        gp.playSE(5);
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.addMessage("You Opened Door");
                    }else{
                        gp.ui.addMessage("You need a key to open this door");
                        gp.playSE(6);
                    }
                    System.out.println("Key: " + hasKey);

                    break;
                case "Neutrino Injector", "Photon Matrix", "Quantum Stabilizer", "Warp Core Regulator":
                    gp.playSE(4);
                    hasEngineParts++;
                    gp.obj[i] = null;
                    gp.ui.addMessage("Engine Parts: " + hasEngineParts + "/" + partsRemaining  + ". You collected the " + objectName);
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
                    gp.ui.addMessage("You have obtained Space Shoes, \nYour Player Speed has increased");
                    break;
                case "winPanel":
                    if(hasEngineParts == 4 && gp.amountAliensChange == 0 && gp.amountCrewmatesChange == 0){
                        gp.ui.gameFinished = true;
                        gp.stopMusic();
                    }else{
                        gp.ui.gameFinished = false;
                        gp.gameState = gp.dialogueSate;
                        gp.ui.currentDialogue = "You must collect all engine parts, and kill all enemies\nbefore fixing the engine!\nOnly then is your mission complete";
                    }
                    break;
            }
        }
    }
    public void interactNPC(int i){

        if(gp.keyH.enterPressed == true){
            if(i != 999){
                gp.gameState = gp.dialogueSate;
                gp.npc[i].speak();
            }
            else{
                gp.playSE(9);
                attacking = true;
            }
        }
    }
    public void contactAlien(int i){
        if(i != 999){
            if(invincible == false){
                gp.playSE(8);
                life -= 1;
                invincible = true;
            }
        }
    }

    public void contactCrewmate(int i){
        if(i != 999){
            if(invincible == false){
                gp.playSE(8);
                life -= 2;
                invincible = true;
            }
        }
    }

    public void damageAlien(int i){
        if(i != 999){
            if(gp.alien[i].invincible == false){
                if(strength == 2){
                    gp.playSE(7);
                    gp.alien[i].life -= 2;
                }else if(strength == 3) {
                    gp.playSE(7);
                    gp.alien[i].life -= 3;
                }else if(strength == 4){
                        gp.playSE(7);
                        gp.alien[i].life -= 4;
                }else{
                    gp.playSE(7);
                    gp.alien[i].life -= 1;
                }


                gp.alien[i].invincible = true;
                gp.alien[i].damageReaction();

                if(gp.alien[i].life <= 0){
                    gp.alien[i].dying = true;
                    gp.ui.addMessage("killed the " + gp.alien[i].name + "!");
                    gp.ui.addMessage("Exp + " + gp.alien[i].exp);
                    exp += gp.alien[i].exp;
                    gp.amountAliensChange--;
                    checkLevelUp();

                }
            }
        }
    }
    public void damageCrewmate(int i){
        if(i != 999){
            if (!gp.crewmate[i].invincible) {
                gp.playSE(7);
                int damage = (strength >= 2 && strength <= 8) ? strength : 1;
                gp.crewmate[i].life -= damage;

                gp.crewmate[i].invincible = true;
                gp.crewmate[i].damageReaction();

                if(gp.crewmate[i].life <= 0){
                    gp.crewmate[i].dying = true;
                    gp.ui.addMessage("killed the Derranged" + gp.crewmate[i].name + "!");
                    gp.ui.addMessage("Exp + " + gp.crewmate[i].exp);
                    exp += 4;
                    gp.crewmate[i] = null;
                    gp.amountCrewmatesChange--;
                    checkLevelUp();

                }
            }
        }
    }

    public void checkLevelUp() {
        if(exp >= nextLevelExp){
            level++;
            nextLevelExp +=3;
            maxLife += 1;
            life = maxLife;
            strength+=1;
            dexterity++;
            //attack++; //= getAttack();

            gp.playSE(10);
            gp.gameState = gp.dialogueSate;
            gp.ui.currentDialogue = "You are level " + level + " now!\n" + "you are stronger";


        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction){
            case "up":
                if(attacking == false){
                    if(spriteNum == 1){image = up1;}
                    if(spriteNum == 2){image = up2;}
                }
                if(attacking == true){
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNum == 1){image = attackUp1;}
                    if(spriteNum == 2){image = attackUp2;}
                }
                break;
            case "down":
                if(attacking == false){
                    if(spriteNum == 1){image = down1;}
                    if(spriteNum == 2){image = down2;}
                }
                if(attacking == true){
                    if(spriteNum == 1){image = attackDown1;}
                    if(spriteNum == 2){image = attackDown2;}
                }
                break;
            case "left":
                if(attacking == false){
                    if(spriteNum == 1){image = left1;}
                    if(spriteNum == 2){image = left2;}
                }
                if(attacking == true){
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum == 1){image = attackLeft1;}
                    if(spriteNum == 2){image = attackLeft2;}
                }
                break;
            case "right":
                if(attacking == false){
                    if(spriteNum == 1){image = right1;}
                    if(spriteNum == 2){image = right2;}
                }
                if(attacking == true){
                    if(spriteNum == 1){image = attackRight1;}
                    if(spriteNum == 2){image = attackRight2;}
                }
                break;
        }
        if(invincible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);
        //reset opacity
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //Debug
        //g2.setFont(new Font("Arial", Font.PLAIN, 26));
        //g2.setColor(Color.white);
        //g2.drawString("invincible" + invincibleCounter, 10, 400);
    }
}
