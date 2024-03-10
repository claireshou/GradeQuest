package com.mycompany.app.classes.Display;

import java.awt.Graphics;
import java.awt.font.GraphicAttribute;

import com.mycompany.app.classes.Helpers.CollisionChecker;
import com.mycompany.app.classes.Helpers.Position;
import com.mycompany.app.classes.Helpers.AnimationConstants.PlayerConstants;
import com.mycompany.app.classes.MoveableEntity.Player;
import com.mycompany.app.classes.StaticEntity.TileManager;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200; // Updates per second to prevent the game from running too fast

    // Tile settings
    final static int originalTileSize = 16;
    final static int scale = 3;
    public final static int tileSize = originalTileSize * scale;

    // Grid size
    public final static int maxScreenCol = 16;
    public final static int maxScreenRow = 12;

    // Scaled screen size
    public final static int screenWidth = tileSize * maxScreenCol;
    public final static int screenHeight = tileSize * maxScreenRow;

    private TileManager tileManager;
    public CollisionChecker collisionChecker;
    private Player player;

    int playerX = Game.screenWidth/2 - Game.tileSize/2;
    int playerY = Game.screenHeight/2 - Game.tileSize/2;

    public Game() {
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);  
        gamePanel.requestFocus();

        startGameLoop();
    }

    private void initClasses() {
        tileManager = new TileManager(gamePanel);
        collisionChecker = new CollisionChecker(tileManager);
        player = new Player(new Position(playerX, playerY), collisionChecker);
    }

    public void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void update() {
        player.update();
    }

    public void render(Graphics g) {
        tileManager.draw(g);
        player.render(g);
    }

    @Override
    public void run() {
        double timePerFrame = 1_000_000_000.0 / FPS_SET;
        double timePerUpdate = 1_000_000_000.0 / UPS_SET;

        long previousTime = System.nanoTime();
        long lastCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            // Check frames of the game
            if (System.currentTimeMillis() - lastCheck >= 1_000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

	public void windowFocusLost() {
		player.resetDirBooleans();
	}

    public Player getPlayer() {
        return player;
    }
}
