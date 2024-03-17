package Gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import Display.Score;
import Display.Time;

import Display.Game;



public class GameOver extends State implements Statemethods { 
    
    private static Font customFont;

    public GameOver(Game game) {
        super(game);

        try {
            // Load the external font file
            InputStream fontStream = getClass().getClassLoader().getResourceAsStream("fonts/ThaleahFat.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(50f);

            // Register the custom font with the graphics environment
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace(); // Handle font loading errors            
        }

    }
    
    @Override
    public void update() {

    }

    @Override
	public void draw(Graphics g) {
        if (customFont != null) {
            g.setFont(customFont);
            g.setColor(Color.BLACK);

            FontMetrics fm = g.getFontMetrics();
            int x = (Game.screenWidth - fm.stringWidth("GAME OVER!")) / 2;
            int y = (Game.screenHeight - fm.getHeight()) / 2 + fm.getAscent() - 40;

            g.drawString("GAME OVER!", x, y);

            y += fm.getHeight();  // Move down for the second line
            x = (Game.screenWidth - fm.stringWidth("PRESS R TO RESTART THE GAME")) / 2;
            g.drawString("PRESS R TO RESTART THE GAME", x, y);

            y += fm.getHeight(); // Move down for the third line
            x = (Game.screenWidth - fm.stringWidth("PRESS Q TO QUIT THE GAME")) / 2;
            g.drawString("PRESS Q TO QUIT THE GAME", x, y);

        }
    }

    @Override
	public void mouseClicked(MouseEvent e) {

    }

    @Override
	public void mousePressed(MouseEvent e) {

    }

    @Override
	public void mouseReleased(MouseEvent e) {

    }

    @Override
	public void mouseMoved(MouseEvent e) {

    }

    @Override
	public void keyPressed(KeyEvent e) {

    }

    @Override
	public void keyReleased(KeyEvent e) {

    }
    
}
