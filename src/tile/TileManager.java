package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[25];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/map01.txt");
    }

    public void getTileImage(){
        setup(0,"ship_floor", false);
        setup(1,"wall", true);
        //setup(2,"water01", true);
        //setup(3,"earth", false);
        setup(4,"stars", true);
        //setup(5,"road", false);
        //setup(6,"cabin", false);
        //setup(7,"concrete_path", false);
        setup(8,"ship_wall", true);
        setup(9,"planet1", false);
        setup(10,"comet", false);
        setup(11, "planet2", false);
        setup(12, "control_panel_front", true);
        setup(13, "chair_left", true);
        setup(14, "chair_right", true);
        setup(15, "bed_front", true);
        setup(16, "control_panel_left", true);
        setup(17, "control_panel_right", true);
        setup(18, "bedHosp", true);
        setup(19, "desk", true);
        setup(20, "sink", true);
        setup(21, "hospwall1", true);
        setup(22, "hospwall2", true);
        setup(23, "hospfloor", false);
        setup(24, "planet3", false);

    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            InputStream imageStream = getClass().getResourceAsStream("/tiles/" + imageName + ".png");

            if (imageStream == null) {
                throw new IOException("Failed to load image: " + imageName);
            }

            tile[index].image = ImageIO.read(imageStream);
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();

                while(col < gp.maxWorldCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch(Exception e){

        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize> gp.player.worldX - gp.player.screenX && worldX - gp.tileSize< gp.player.worldX + gp.player.screenX && worldY + gp.tileSize> gp.player.worldY - gp.player.screenY && worldY - gp.tileSize< gp.player.worldY + gp.player.screenY){
                g2.drawImage(tile[tileNum].image, screenX, screenY,  null);

            }

            worldCol++;

            // Check if we reached the end of the row
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
