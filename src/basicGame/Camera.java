package basicGame;

import java.awt.*;

import javax.swing.JFrame;

public class Camera {
	private int posX;
	private int posY;
	private Player player;
	private int frameWidth;
	private int frameHeight;
	private int levelWidth;
	private int levelHeight;
	public Camera(int posX, int posY, Player player, JFrame gameFrame){
		this.posX = posX;
		this.posY = posY;
		this.player = player;
		this.frameWidth = gameFrame.getWidth()-gameFrame.getInsets().left - gameFrame.getInsets().right;
		this.frameHeight= gameFrame.getHeight()-gameFrame.getInsets().top - gameFrame.getInsets().bottom;
	}
	public void tick(){
		//added tween so smooth out collision correction

		//System.out.println(player.getX());
		int tempX = player.getX() - frameWidth/2;
		
		if(tempX < 0){
			posX = 0;
		}
		else if(tempX > levelWidth- frameWidth){
			posX= levelWidth - frameWidth;
		}
		else{
			posX= tempX;
		}
		
		int tempY = player.getY() - frameHeight/2;
		if(tempY < 0){
			posY = 0;
		}
		else if(tempY > levelHeight - frameHeight){
			posY = levelHeight - frameHeight;
		}
		else{
			posY = tempY;
		}
				
		
		//clamp on edges
		/*
		if(posX < 0){
			
			posX = 0;
			System.out.println(posX);
			
		}
		if(posX > levelWidth - gameFrame.getWidth()){
			posX = levelWidth -gameFrame.getWidth();
		}
		if(posY < 0){
			posY = 0;
		}
		if(posY > levelHeight - gameFrame.getHeight()){
			posY = levelHeight - gameFrame.getHeight();
		}
		*/
	}

	public int getPosX(){
		return posX;
	}
	public int getPosY(){
		return posY;
	}
	public void setPosX(int posX){
		this.posX = posX;
	}
	public void setPosY(int posY){
		this.posY = posY;
	}
	public void setBounds(int levelWidth, int levelHeight){
		this.levelWidth = levelWidth;
		this.levelHeight = levelHeight;
	}
}
