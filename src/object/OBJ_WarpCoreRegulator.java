package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_WarpCoreRegulator extends Entity {

    public OBJ_WarpCoreRegulator(GamePanel gp){
        super(gp);
        name = "Warp Core Regulator";
        down1 = setup("/objects/warp_core_regulator", gp.tileSize, gp.tileSize);
    }
}


