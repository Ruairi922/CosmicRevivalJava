package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Engine extends SuperObject{
    GamePanel gp;
    public OBJ_Engine(GamePanel gp){
        name = "engine";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/engine.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
