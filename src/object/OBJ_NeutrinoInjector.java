package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_NeutrinoInjector extends SuperObject{
    GamePanel gp;
    public OBJ_NeutrinoInjector(GamePanel gp){
        name = "Neutrino Injector";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/neutrino_injector.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
