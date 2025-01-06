package alien;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class Alien extends Entity {
    GamePanel gp;

    public Alien(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = 2;
        name = "alien";
        speed = 1;
        maxLife = 8;
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
        up1 = setup("/alien/alien_1", gp.tileSize, gp.tileSize);
        up2 = setup("/alien/alien_2", gp.tileSize, gp.tileSize);
        down1 = setup("/alien/alien_1", gp.tileSize, gp.tileSize);
        down2 = setup("/alien/alien_2", gp.tileSize, gp.tileSize);
        left1 = setup("/alien/alien_1", gp.tileSize, gp.tileSize);
        left2 = setup("/alien/alien_2", gp.tileSize, gp.tileSize);
        right1 = setup("/alien/alien_1", gp.tileSize, gp.tileSize);
        right2 = setup("/alien/alien_2", gp.tileSize, gp.tileSize);
    }
    public void setAction(){
        actionLockCounter++;
        if(actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if(i <= 25){
                direction = "up";
            }
            if(i > 25 && i <= 50){
                direction = "down";
            }
            if(i > 50 && i <= 75){
                direction = "left";
            }
            if(i > 75 && i <= 100){
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }
    public void damageReaction(){
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
}
