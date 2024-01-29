package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door2 extends SuperObject{
    GamePanel gp;
    public OBJ_Door2(GamePanel gp){
        name = "Door 2";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door2.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
