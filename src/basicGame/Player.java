package basicGame;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

public class Player implements Entity {
	private int posX;
	private int posY;
	private int velX;
	private int velY;
	private static final String type = "player";
	public static final int WIDTH = 75;
	public static final int HEIGHT = 75;
	
	private int tickCounter;
	private PlayerAnimation playerAnimation;
	private boolean moving;
	//private static final float moveDuration = 0.5f;
	public Player(int posX, int posY){
		this.posX = posX;
		this.posY = posY;
		
		playerAnimation = new PlayerAnimation(new File("src/assets/doggoknight.png"));
	}
	public void tick() {
		if(tickCounter == 0){
			moving = false;
			velX = 0;
			velY = 0;
		}
		if(moving){
			posX+=velX;
			posY+=velY;
			tickCounter--;
		}
		//System.out.println(tickCounter);
		playerAnimation.tick();
		
	}
	public void render(Graphics g) {
		//g.setColor(Color.BLUE);
		//g.fillRect(posX, posY, WIDTH, HEIGHT);
		g.drawImage(playerAnimation.getImage(), posX, posY, WIDTH, HEIGHT,null,null);
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
	public boolean move(String direction){
		//System.out.println(distance);
		if(moving || direction == null){
			return false;
		}
		playerAnimation.walk();
		if(direction.equals("up")){
			goUp();
		}
		else if(direction.equals("down")){
			goDown();
		}
		else if(direction.equals("right")){
			goRight();
		}
		else if(direction.equals("left")){
			goLeft();
		}
		return true;
		//System.out.println("moving " + direction+ distance);
	}
	private void goUp(){
		moving = true;
		velY = -5;
		tickCounter = HEIGHT / -velY; 
	}
	private void goDown(){
		moving = true;
		velY = 5;
		tickCounter =  HEIGHT / velY; 
	}
	private void goLeft(){
		moving = true;
		velX = -5;
		tickCounter = WIDTH / -velX; 
	}
	private void goRight(){
		moving = true;
		velX = 5;
		tickCounter = WIDTH / velX; 
	}
	@Override
	public void highlight() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void unhighlight() {
		// TODO Auto-generated method stub
		
	}
}
