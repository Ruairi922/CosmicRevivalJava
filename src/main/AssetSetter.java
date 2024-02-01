package main;

import alien.Alien;
import entity.NPC_ARIA;
import object.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        gp.obj[0] = new OBJ_PhotonMatrix(gp);
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_NeutrinoInjector(gp);
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;

        gp.obj[2] = new OBJ_Door(gp);
        gp.obj[2].worldX = 23 * gp.tileSize;
        gp.obj[2].worldY = 10 * gp.tileSize;

        //gp.obj[3] = new OBJ_Door();
        //gp.obj[3].worldX = 24 * gp.tileSize;
        //gp.obj[3].worldY = 10 * gp.tileSize;

        gp.obj[4] = new OBJ_Door(gp);
        gp.obj[4].worldX = 10 * gp.tileSize;
        gp.obj[4].worldY = 12 * gp.tileSize;

        gp.obj[5] = new OBJ_Door(gp);
        gp.obj[5].worldX = 38 * gp.tileSize;
        gp.obj[5].worldY = 14 * gp.tileSize;

        //gp.obj[6] = new OBJ_Door();
        //gp.obj[6].worldX = 39 * gp.tileSize;
        //gp.obj[6].worldY = 14 * gp.tileSize;

        gp.obj[7] = new OBJ_QuantumStabilizer(gp);
        gp.obj[7].worldX = 38 * gp.tileSize;
        gp.obj[7].worldY = 42 * gp.tileSize;

        gp.obj[8] = new OBJ_WarpCoreRegulator(gp);
        gp.obj[8].worldX = 17 * gp.tileSize;
        gp.obj[8].worldY = 17 * gp.tileSize;

        gp.obj[9] = new OBJ_Key(gp);
        gp.obj[9].worldX = 12 * gp.tileSize;
        gp.obj[9].worldY = 34 * gp.tileSize;

        gp.obj[10] = new OBJ_SpaceShoes(gp);
        gp.obj[10].worldX = 10 * gp.tileSize;
        gp.obj[10].worldY = 6 * gp.tileSize;

        gp.obj[11] = new OBJ_Key(gp);
        gp.obj[11].worldX = 8 * gp.tileSize;
        gp.obj[11].worldY = 43 * gp.tileSize;




    }
    public void setNPC(){
        gp.npc[0] =  new NPC_ARIA(gp);
        gp.npc[0].worldX = gp.tileSize*12;
        gp.npc[0].worldY = gp.tileSize*42;
    }
    public void setAlien(){
        gp.alien[0] = new Alien(gp);
        gp.alien[0].worldX = gp.tileSize*31;
        gp.alien[0].worldY = gp.tileSize*40;

        gp.alien[1] = new Alien(gp);
        gp.alien[1].worldX = gp.tileSize*31;
        gp.alien[1].worldY = gp.tileSize*41;
    }
}
