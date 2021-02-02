package basicGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FragileTile implements Entity {

	public static final int WIDTH = 75;
	public static final int HEIGHT = 75;
	private static final String type = "fragileTile";
	private int posX;
	private int posY;
	private int world;
	private BufferedImage skin;
	private boolean steppedOn = false;
	private boolean highlighted = false;
	public FragileTile(int posX, int posY, int world) {
		this.posX = posX;
		this.posY = posY;
		this.world = world;
		try{
			if(world == 1){
				skin = ImageIO.read(new File("src/assets/tiles/watercracked.png"));
			}
			else if(world == 2){
				skin = ImageIO.read(new File("src/assets/tiles/earthcracked.png"));
			}
			else if(world == 3){
				skin = ImageIO.read(new File("src/assets/tiles/firecracked.png"));
			}
			else{
				skin = ImageIO.read(new File("src/assets/tiles/skycracked.png"));
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

	public int getY() {
		return posY;
	}
	public boolean crack(){
		if(steppedOn){
			return true;
		}
		else{
			steppedOn = true;
			try{
				if(world == 1){
					skin = ImageIO.read(new File("src/assets/tiles/waterdestroyed.png"));
				}
				else if(world == 2){
					skin = ImageIO.read(new File("src/assets/tiles/earthdestroyed.png"));
				}
				else if(world == 3){
					skin = ImageIO.read(new File("src/assets/tiles/firedestroyed.png"));
				}
				else{
					skin = ImageIO.read(new File("src/assets/tiles/skydestroyed.png"));
				}
			}catch(IOException e){
				e.printStackTrace();
			}
			return false;
		}
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
