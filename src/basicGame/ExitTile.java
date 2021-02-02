package basicGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ExitTile implements Entity{
	
	public static final int WIDTH = 75;
	public static final int HEIGHT = 75;
	private static final String type = "exitTile";
	private int posX;
	private int posY;
	private BufferedImage skin;
	private boolean highlighted = false;
	public ExitTile(int posX, int posY){
		this.posX = posX;
		this.posY = posY;
		try{
			skin = ImageIO.read(new File("src/assets/tiles/exittile.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void tick() {
		
	}

	public void render(Graphics g) {
		g.drawImage(skin, posX, posY, WIDTH, HEIGHT, null, null);

		if(highlighted){
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(Color.WHITE);
			g2.setStroke(new BasicStroke(4));
			g2.drawRect(posX, posY, WIDTH, HEIGHT);
		}
	}
	public String getType() {
		return type;
	}
	public int getX() {
		return posX;
	}
	@Override
	public int getY(){ 
		return posY;
	}
	@Override
	public void highlight() {
		highlighted = true;
		
	}
	@Override
	public void unhighlight() {
		highlighted = false;
		
	}

}
