package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_PhotonMatrix extends Entity {

    public OBJ_PhotonMatrix(GamePanel gp){
        super(gp);
        name = "Photon Matrix";
        down1 = setup("/objects/photon_matrix", gp.tileSize, gp.tileSize);
    }
}
