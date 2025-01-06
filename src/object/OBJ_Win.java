package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Win extends Entity {

    public OBJ_Win(GamePanel gp){
        super(gp);
        name = "winPanel";
        down1 = setup("/objects/winPanel", gp.tileSize, gp.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
