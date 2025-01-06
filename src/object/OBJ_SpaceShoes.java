package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_SpaceShoes extends Entity {

    public OBJ_SpaceShoes(GamePanel gp){
        super(gp);
        name = "Space Shoes";
        down1 = setup("/objects/Space_shoes", gp.tileSize, gp.tileSize);
    }
}
