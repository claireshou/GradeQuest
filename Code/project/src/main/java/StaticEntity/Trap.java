package StaticEntity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import Helpers.Position;

// trap sprite from https://bdragon1727.itch.io/free-trap-platformer
// width:32, height: 22

/**
 * Represents a trap in the game.
 *
 * <p>
 * Traps are static entities that cause damage upon collision.
 * <p>
 * Traps will only despawn if a player collides with them
 */
public class Trap extends StaticEntity {

    /** The amount of damage the trap inflicts. */
    private double damage;

    private BufferedImage[][] animations; // 2d image array of the images for trap movements
    private int animationTick, animationIndex, animationSpeed = 150;

    private BufferedImage trapImage;
    
    /**
     * Constructs a new Trap.
     *
     * @param position The position of the trap.
     */
    public Trap(Position position) {
        // doesn't need despawnTimer as it would only despawn if collided with
        super(position);
        damage = 1; // default value of 1

        loadAnimations();
        loadTrapImage();
    }

    // Methods

    /**
     * Gets the amount of damage the trap inflicts.
     *
     * @return The amount of damage.
     */
    public double getDamage() {
        return damage;
    }

    @Override
    public Image getSprite() {
        // Return the sprite image associated with the trap entity
        return trapImage; // Assuming you have a field named trapImage that holds the sprite image
    }
    

    // ANIMATION METHODS FOR TRAP
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(animations[animationIndex][0], position.getX(), position.getY(), gameSettings.getTileSize() + 2, 30,
            null);
    }

    public void update() {
        updateAnimationTick();
    }

    // creates the Image array for the movement animations
    private void loadAnimations() {
        InputStream is = getClass().getResourceAsStream("/assets/trap.png");

        try {
            BufferedImage img = ImageIO.read(is);

            animations = new BufferedImage[2][1];
            for (int j = 0; j < animations.length; j++) {
                for (int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = img.getSubimage(j * 32, i * 22, 32, 22);
                }
            }

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

    // Load the trap image
    private void loadTrapImage() {
        try {
            InputStream is = getClass().getResourceAsStream("/assets/trap.png");
            trapImage = ImageIO.read(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // updates the animation array during the game loop thread
    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= 2) {
                animationIndex = 0;
            }
        }
    }
}