package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_NeutrinoInjector extends Entity {

    public OBJ_NeutrinoInjector(GamePanel gp){
        super(gp);
        name = "Neutrino Injector";
        down1 = setup("/objects/neutrino_injector", gp.tileSize, gp.tileSize);
    }
}

