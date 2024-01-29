package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_PhotonMatrix extends SuperObject{
    GamePanel gp;
    public OBJ_PhotonMatrix(GamePanel gp){
        name = "Photon Matrix";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/photon_matrix.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
