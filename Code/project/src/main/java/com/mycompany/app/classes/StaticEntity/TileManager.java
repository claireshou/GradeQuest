package com.mycompany.app.classes.StaticEntity;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import com.mycompany.app.classes.BoardData;
import com.mycompany.app.classes.Display.Game;
import com.mycompany.app.classes.Display.GamePanel;

/**
 * The TileManager class manages the tiles used in the game.
 * It loads tile sprites and map data, and provides methods to draw the tiles on the game panel.
 */
public class TileManager {
    
    GamePanel gamePanel;
    Tile[] tile; // Stores the tile sprites
    int mapTileNum[][]; // Stores the map data that indicates which tile to use    

    /**
     * Constructs a TileManager object with the specified GamePanel.
     * 
     * @param gamePanel the GamePanel object to associate with the TileManager
     */
    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tile = new Tile[48]; // Assuming 48 tiles are used, adjust as needed
        mapTileNum = new int[GamePanel.maxScreenRow][GamePanel.maxScreenCol];

        getTileImage();
        loadMap("mapTest.txt");
    }

    /**
     * Loads the tile sprites from the resource files.
     * The tile sprites are sourced from https://limezu.itch.io/moderninteriors
     */
    private void getTileImage() {
        try {
            // Load the tile sprites
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("tileFloor.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("tileWallN.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("tileWallS.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("tileWallE.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("tileWallW.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("tileCornerNW.png"));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("tileCornerNE.png"));

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("tileCornerSW.png"));

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("tileCornerSE.png"));

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("tileRoomWall.png"));

            tile[10] = new Tile();
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("tileRoomWallBotMid.png"));

            tile[11] = new Tile();
            tile[11].image = ImageIO.read(getClass().getResourceAsStream("tileRoomWallBotRightCorner.png"));

            tile[12] = new Tile();
            tile[12].image = ImageIO.read(getClass().getResourceAsStream("tileRoomWallTopMid.png"));

            tile[13] = new Tile();
            tile[13].image = ImageIO.read(getClass().getResourceAsStream("tileRoomWallTopRightCorner.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the tiles to the game screen.
     * 
     * @param g the Graphics object to draw the tiles on
     */
    public void draw(Graphics g) {
        
        int col = 0, row = 0, x = 0, y = 0;
        
        while (col < GamePanel.maxScreenCol && row < GamePanel.maxScreenRow) {
            int tileNum = mapTileNum[row][col];
            g.drawImage(tile[tileNum].image, x, y, GamePanel.tileSize, GamePanel.tileSize, null);
            
            col++;
            x += GamePanel.tileSize;

            if (col == GamePanel.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += GamePanel.tileSize;
            }
        }
    }

    /**
     * Loads the map data from the specified file.
     * 
     * @param filePath the path of the file containing the map data
     */
    public void loadMap(String filePath) {

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0, row = 0;
            while (col < GamePanel.maxScreenCol && row < GamePanel.maxScreenRow) {
                String line = br.readLine();
                String numbers[] = line.split(" ");
                while (col < GamePanel.maxScreenCol) {
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[row][col] = num;
                    col++;
                }
                if (col == GamePanel.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
