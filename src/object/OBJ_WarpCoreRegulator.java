package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_WarpCoreRegulator extends SuperObject{
    GamePanel gp;
    public OBJ_WarpCoreRegulator(GamePanel gp){
        name = "Warp Core Regulator";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/warp_core_regulator.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
