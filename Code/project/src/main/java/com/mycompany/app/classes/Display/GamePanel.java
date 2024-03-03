package com.mycompany.app.classes.Display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.mycompany.app.classes.Helpers.KeyboardInputs;

public class GamePanel extends JPanel {

    // Screen settings
    final int originalTileSize = 16;
    final int scale = 3;
    final int tileSize = originalTileSize * scale;

    // Grid size
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;

    // Scaled screen size
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    private BufferedImage img;

    private int xDelta = 0, yDelta = 0;
    private int playerSpeed = 2;
    private boolean moving = false;
    
    public GamePanel() {
        this.setBackground(Color.BLACK);
        this.importImg();
        this.setPanelSize();
        this.addKeyListener(new KeyboardInputs(this));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        updatePos();
        g2.drawImage(img.getSubimage(16 * 4, 24, 16, 24), xDelta, yDelta, tileSize, 72, null);
        g2.dispose();
    }

    private void importImg() {
        // Source of player sprites: https://axulart.itch.io/small-8-direction-characters
        InputStream is = getClass().getResourceAsStream("player_sprites.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(screenWidth, screenHeight);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    /**
     * Updates the player's position based on the currently pressed keys.
     * If the player is moving, it adjusts the position according to the keys being pressed.
     * If diagonal movement is detected, it normalizes the speed to maintain consistent movement.
     */
    private void updatePos() {
        if (moving) {
            if (KeyboardInputs.isUpPressed && KeyboardInputs.isRightPressed) {
                xDelta += playerSpeed / Math.sqrt(2); // Move diagonally up-right
                yDelta -= playerSpeed / Math.sqrt(2);
            } else if (KeyboardInputs.isUpPressed && KeyboardInputs.isLeftPressed) {
                xDelta -= playerSpeed / Math.sqrt(2); // Move diagonally up-left
                yDelta -= playerSpeed / Math.sqrt(2);
            } else if (KeyboardInputs.isDownPressed && KeyboardInputs.isRightPressed) {
                xDelta += playerSpeed / Math.sqrt(2); // Move diagonally down-right
                yDelta += playerSpeed / Math.sqrt(2);
            } else if (KeyboardInputs.isDownPressed && KeyboardInputs.isLeftPressed) {
                xDelta -= playerSpeed / Math.sqrt(2); // Move diagonally down-left
                yDelta += playerSpeed / Math.sqrt(2);
            } else {
                if (KeyboardInputs.isUpPressed) {
                    yDelta -= playerSpeed; // Move up
                }
                if (KeyboardInputs.isDownPressed) {
                    yDelta += playerSpeed; // Move down
                }
                if (KeyboardInputs.isLeftPressed) {
                    xDelta -= playerSpeed; // Move left
                }
                if (KeyboardInputs.isRightPressed) {
                    xDelta += playerSpeed; // Move right
                }
            }
        }
    }
}
