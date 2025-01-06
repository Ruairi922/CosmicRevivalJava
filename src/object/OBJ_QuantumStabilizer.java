package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_QuantumStabilizer extends Entity {

    public OBJ_QuantumStabilizer(GamePanel gp){
        super(gp);
        name = "Quantum Stabilizer";
        down1 = setup("/objects/Quantum Stabilizer", gp.tileSize, gp.tileSize);
    }
}
