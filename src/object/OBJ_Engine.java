package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Engine extends Entity {

    public OBJ_Engine(GamePanel gp){
        super(gp);
        name = "engine";
        down1 = setup("/objects/engine", gp.tileSize, gp.tileSize);
    }
}
