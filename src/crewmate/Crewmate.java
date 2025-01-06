package crewmate;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class Crewmate extends Entity {
    GamePanel gp;

    public Crewmate(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = 3;
        name = "crewmate";
        speed = 10;
        maxLife = 42;
        life = maxLife;
        attack = 4;

        exp = 1;
        solidArea.x = 5;
        solidArea.y = 5;
        solidArea.width = 46;
        solidArea.height =45;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }

    public void getImage(){
        up1 = setup("/crewmate/crewmate_1", gp.tileSize, gp.tileSize);
        up2 = setup("/crewmate/crewmate_2", gp.tileSize, gp.tileSize);
        down1 = setup("/crewmate/crewmate_1", gp.tileSize, gp.tileSize);
        down2 = setup("/crewmate/crewmate_2", gp.tileSize, gp.tileSize);
        left1 = setup("/crewmate/crewmate_1", gp.tileSize, gp.tileSize);
        left2 = setup("/crewmate/crewmate_2", gp.tileSize, gp.tileSize);
        right1 = setup("/crewmate/crewmate_1", gp.tileSize, gp.tileSize);
        right2 = setup("/crewmate/crewmate_2", gp.tileSize, gp.tileSize);
    }
    public void setAction() {
        actionLockCounter++;

        // Change direction more frequently (every 15 frames)
        if (actionLockCounter == 15) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 12.5) {
                direction = "up";
            } else if (i > 12.5 && i <= 25) {
                direction = "down";
            } else if (i > 25 && i <= 37.5) {
                direction = "left";
            } else if (i > 37.5 && i <= 50) {
                direction = "right";
            } else if (i > 50 && i <= 62.5) {
                direction = "up";
            } else if (i > 62.5 && i <= 75) {
                direction = "down";
            } else if (i > 75 && i <= 87.5) {
                direction = "left";
            } else {
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }
    public void damageReaction(){
        actionLockCounter = 0;
        if(gp.player.direction == "down"){
            direction = "down";
        }else if(gp.player.direction == "up"){
            direction = "up";
        }
        else if(gp.player.direction == "left"){
            direction = "left";
        }
        else if(gp.player.direction == "right"){
            direction = "right";
        }
    }
}
