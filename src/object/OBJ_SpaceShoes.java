package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_SpaceShoes extends SuperObject{
    GamePanel gp;
    public OBJ_SpaceShoes(GamePanel gp){
        name = "Space Shoes";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Space_shoes.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
