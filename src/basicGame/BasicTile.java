package basicGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BasicTile implements Entity{
	
	public static final int WIDTH = 75;
	public static final int HEIGHT = 75;
	private static final String type = "basicTile";
	private int posX;
	private int posY;
	private int world;
	private BufferedImage skin;
	
	private boolean highlighted = false;
	
	public BasicTile(int posX, int posY, int world){
		this.posX = posX;
		this.posY = posY;
		try{
			if(world == 1){
				skin = ImageIO.read(new File("src/assets/tiles/waterwall.png"));
			}
			else if(world == 2){
				skin = ImageIO.read(new File("src/assets/tiles/earthwall.png"));
			}
			else if(world == 3){
				skin = ImageIO.read(new File("src/assets/tiles/firewall.png"));
			}
			else{
				skin = ImageIO.read(new File("src/assets/tiles/skywall.png"));
			}
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
