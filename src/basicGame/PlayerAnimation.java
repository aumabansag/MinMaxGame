package basicGame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PlayerAnimation {
	//private BufferedImage[] ;
	private File spriteFile;
	
	private boolean walking;
	
	private BufferedImage spritesheet;
	private BufferedImage[] walk;
	private BufferedImage[] idle;
	
	private long startTime;
	private final long delay = 200;
	
	private int currentFrame;
	
	public PlayerAnimation(File spriteFile){
		this.spriteFile = spriteFile;
		try {
			this.spritesheet = ImageIO.read(spriteFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentFrame = 0;
		startTime = System.nanoTime();
		init();
	}
	private void init(){
		idle = new BufferedImage[2];
		for(int i = 0; i < idle.length; i++){
			idle[i] = spritesheet.getSubimage(i*50,0,50,50);
		}
		
		walk = new BufferedImage[8];
		for(int i = 0; i < walk.length; i++ ){
			walk[i] = spritesheet.getSubimage(i*50,50,50,50);
		}
	}
	public void tick(){
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if(elapsed > delay){
			startTime = System.nanoTime();

			if(walking){
				currentFrame++;

				if(currentFrame == walk.length){
					walking = false;
					currentFrame = 0;
				}
			}
			else{
				currentFrame++;
				if(currentFrame == idle.length){
					currentFrame = 0;
				}
			}

		}
	}
	public void walk(){
		if(!walking){

			walking = true;

			currentFrame = 0;
		}
	}
	public void idle(){
		if(walking){

			walking = false;

			currentFrame = 0;
		}
	}
	public BufferedImage getImage(){
		if(walking){
			return walk[currentFrame];
		}
		else{
			return idle[currentFrame];
		}
	}
}
