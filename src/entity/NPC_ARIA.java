package entity;

import main.GamePanel;

import java.io.IOException;
import java.util.Random;

public class NPC_ARIA extends Entity{
    public NPC_ARIA(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }


    public void getImage() {

        up1 = setup("/npc/oldman_up_1");
        up2 = setup("/npc/oldman_up_2");
        down1 = setup("/npc/oldman_down_1");
        down2 = setup("/npc/oldman_down_2");
        left1 = setup("/npc/oldman_left_1");
        left2 = setup("/npc/oldman_left_2");
        right1 = setup("/npc/oldman_right_1");
        right2 = setup("/npc/oldman_right_2");
    }
    public void setDialogue(){
        dialogues[0] = "Welcome aboard, [Player Name].\nThe situation is dire. Aliens have taken over the ship,\n and our crewmate is completely unstable.";
        dialogues[1] = "As the only ones with our sanity intact, our mission is clear –\nnavigate through the chaos, battle the aliens,\n and deal with our deranged crewmate.";
        dialogues[2] = "Your task is to scavenge for keys and gather engine components.\nWe must repair the spacecraft for a safe journey back to Earth.\nThe fate of the entire crew depends on you.";
        dialogues[3] = "Commander Novak: Move swiftly, [Player Name].\nYour skills are our best hope. Find those keys, collect the engine parts,\n and make the ship spaceworthy again.\nEarth is counting on your success.";
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
    public void speak(){
        //Charcater specific dialogues
        super.speak();
    }
}
