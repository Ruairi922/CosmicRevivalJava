package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_QuantumStabilizer extends SuperObject{
    GamePanel gp;
    public OBJ_QuantumStabilizer(GamePanel gp){
        name = "Quantum Stabilizer";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Quantum Stabilizer.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
