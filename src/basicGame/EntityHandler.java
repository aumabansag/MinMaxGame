package basicGame;

import gui.GamePanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class EntityHandler {
	private ArrayList<Entity> entityList;
	private int tilesWide;
	private int tilesHigh;
	private int xOffset;
	private int yOffset;
	
	private Camera camera;
	private GamePanel gamePanel;
	private JFrame gameFrame;
	private String texture;
	public EntityHandler(GamePanel gamePanel){
		this.gamePanel = gamePanel;
		gameFrame = (JFrame)SwingUtilities.getWindowAncestor(gamePanel);
		init();		
	}
	private void init(){
		entityList = new ArrayList<Entity>();
	}
	public void tick() {
		for(int i = 0; i < entityList.size(); i++){
			Entity currEntity = entityList.get(i);
			currEntity.tick();
		}
		camera.tick();
	}

	private boolean checkCollision(FragileTile fTile) {
		Player player = getPlayer();
		Rectangle hitbox = new Rectangle(player.getX(), player.getY(), Player.WIDTH, Player.HEIGHT);
		Rectangle enemyHitbox = new Rectangle(fTile.getX(), fTile.getY(), FragileTile.WIDTH, FragileTile.HEIGHT);
		return hitbox.intersects(enemyHitbox);
	}
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.translate(-camera.getPosX(), -camera.getPosY());
		
		for(int i = 0; i < entityList.size(); i++){
			entityList.get(i).render(g);
		}
		
		g2.translate(camera.getPosX(), camera.getPosY());
		
	}
	public void loadLevel(int world, int level){
		File levelFile = new File("src/levels/"+world+"-"+level+".lvl");
		try{
			BufferedReader br = new BufferedReader(new FileReader(levelFile));
			tilesWide = Integer.parseInt(br.readLine());
			tilesHigh = Integer.parseInt(br.readLine());
			int playerX = Integer.parseInt(br.readLine());
			int playerY = Integer.parseInt(br.readLine());
			
			//load map
			for(int i = 0; i < tilesHigh; i++){
				String currLine = br.readLine();
				for(int j = 0; j < tilesWide; j++){
					char currChar = currLine.charAt(j);
					if(currChar == '#'){
						addEntity(new BasicTile(j*BasicTile.WIDTH+xOffset, i * BasicTile.HEIGHT+yOffset, world));
					}
					else if(currChar == '*'){
						addEntity(new FragileTile(j*FragileTile.WIDTH+xOffset, i *FragileTile.HEIGHT+yOffset, world));
					}
					else if(currChar == '|'){
						addEntity(new ExitTile(j*ExitTile.WIDTH+xOffset, i *ExitTile.HEIGHT+yOffset));
					}
					else if(currChar == '.'){
						addEntity(new FloorTile(j*ExitTile.WIDTH+xOffset, i *ExitTile.HEIGHT+yOffset, world));
					}
				}
			}
			br.close();
			
			//loadPlayer
			addEntity(new Player(playerX * BasicTile.WIDTH + xOffset, playerY * BasicTile.HEIGHT + yOffset));
			//start Camera
			camera = new Camera(0,0,getPlayer(), gameFrame);
			camera.setBounds(tilesWide*BasicTile.WIDTH, tilesHigh*BasicTile.HEIGHT);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void addEntity(Entity entity){
		entityList.add(entity);
	}
	public void removeEntity(Entity entity){
		entityList.remove(entity);
	}
	public void setOffset(int xOffset, int yOffset){
		this.xOffset=  xOffset;
		this.yOffset = yOffset;
	}
	public void mouseClicked(int mouseX, int mouseY){
		int destX = (camera.getPosX()+mouseX)/BasicTile.WIDTH;
		int destY = (camera.getPosY()+mouseY)/BasicTile.HEIGHT;
		boolean validMove = isMoveValid(destX, destY);
		
		String moveDirection = getMoveDirection(destX, destY);
		int distance = getDistance(destX, destY);
		
		if(validMove){
			for(int i = 0; i < distance;){
				if(getPlayer().move(moveDirection)){
					if(landedInHole(moveDirection)){
						gamePanel.endGame(false);
						//System.out.println("DEAD");
						break;
					}
					else if(won(moveDirection)){
						gamePanel.endGame(true);
						//System.out.println("Winner Winner");
						break;
					}
					i++;
				}
			}
		}else{
			//System.out.println("Cannot");
		}
		//System.out.println(mouseX/BasicTile.WIDTH + " "+ mouseY/BasicTile.HEIGHT);
		
	}
	private boolean landedInHole(String moveDirection){
		Player player = getPlayer();
		int playerX = player.getX()/BasicTile.WIDTH;
		int playerY = player.getY()/BasicTile.HEIGHT;
		
		if(moveDirection.equals("up")){
			playerY--;
		}
		else if(moveDirection.equals("down")){
			playerY++;
		}
		else if(moveDirection.equals("left")){
			playerX--;
		}
		else if(moveDirection.equals("right")){
			playerX++;
		}
		
		for(int i= 0; i < entityList.size(); i++){
			Entity curr = entityList.get(i);
			if(curr.getType().equals("fragileTile")){
				FragileTile fTile = (FragileTile) curr;
				int fTileX = fTile.getX()/FragileTile.WIDTH;
				int fTileY = fTile.getY()/FragileTile.HEIGHT;
				if(playerX == fTileX && playerY == fTileY){
					return fTile.crack();
				}
			}
		}
		return false;
	}
	private boolean won(String moveDirection){
		Player player = getPlayer();
		int playerX = player.getX()/BasicTile.WIDTH;
		int playerY = player.getY()/BasicTile.HEIGHT;
		
		if(moveDirection.equals("up")){
			playerY--;
		}
		else if(moveDirection.equals("down")){
			playerY++;
		}
		else if(moveDirection.equals("left")){
			playerX--;
		}
		else if(moveDirection.equals("right")){
			playerX++;
		}
		
		for(int i= 0; i < entityList.size(); i++){
			Entity curr = entityList.get(i);
			if(curr.getType().equals("exitTile")){
				ExitTile eTile = (ExitTile) curr;
				int eTileX = eTile.getX()/ExitTile.WIDTH;
				int eTileY = eTile.getY()/ExitTile.HEIGHT;
				if(playerX == eTileX && playerY == eTileY){
					return true;
				}
			}
		}
		return false;
	}
	private int getDistance(int destX, int destY) {
		Player player = getPlayer();
		int playerX = player.getX()/BasicTile.WIDTH;
		int playerY = player.getY()/BasicTile.HEIGHT;
		
		if(destX == playerX){
			if(destY >= playerY){
				return destY - playerY;
			}
			return playerY - destY;
		}
		else if(destY == playerY){
			if(destX >= playerX){
				return destX - playerX;
			}
			return playerX-destX;
		}
		return 0;
	}
	private Player getPlayer(){
		for(int i = 0; i < entityList.size(); i++){
			Entity currEntity = entityList.get(i);
			if(currEntity.getType().equals("player")){
				return (Player)currEntity;
			}
		}
		return null;
	}
	private boolean isMoveValid(int destTileX, int destTileY){
		Player player = getPlayer();
		if(player.getX()/BasicTile.WIDTH == destTileX || player.getY()/BasicTile.HEIGHT == destTileY){
			if(isPathClear(destTileX, destTileY)){
				return true;	
			}
			
		}
		return false;
	}
	//assuming the dest and player are on the same row/column, check for obstacles
	private boolean isPathClear(int destX, int destY){
		Player player = getPlayer();
		int playerTileX = player.getX()/BasicTile.WIDTH; 
		int playerTileY = player.getY()/BasicTile.HEIGHT; 
		
		for(int i = 0; i < entityList.size(); i++){
			Entity curr = entityList.get(i);
			if(curr.getType().equals("basicTile")){
				int basicTileX = curr.getX()/BasicTile.WIDTH;
				int basicTileY = curr.getY()/BasicTile.HEIGHT;
				if(basicTileX == playerTileX){
					if(basicTileY <= destY && playerTileY <= basicTileY){
						return false;
					}
					else if(basicTileY <= playerTileY && destY <= basicTileY){
						return false;
					}
				}
				else if(basicTileY == playerTileY){
					if(basicTileX <= destX && playerTileX <= basicTileX){
						return false;
					}
					else if(basicTileX <= playerTileX && destX <= basicTileX){
						return false;
					}
				}
			}
		}
		return true;
	}
	private String getMoveDirection(int destX, int destY){
		Player player = getPlayer();
		int playerX = player.getX()/BasicTile.WIDTH;
		int playerY = player.getY()/BasicTile.HEIGHT;
		if(playerX == destX){
			if(playerY < destY){
				return "down";
			}
			else if(playerY > destY){
				return "up";
			}
		}
		else if(playerY == destY){
			if(playerX < destX){
				return "right";
			}
			else if(playerX > destX){
				return "left";
			}
		}
		return null;
	}
	public void setTexture(String texture){
		this.texture = texture;
	}
	public void mouseMoved(int x, int y) {
		highlightHovered(x, y);
		
	}
	private void highlightHovered(int x, int y){
		//System.out.println("moved");
		x += camera.getPosX();
		y += camera.getPosY();
		for(int i = 0; i < entityList.size(); i++){
			Entity curr = entityList.get(i);
			curr.unhighlight();
			if(!curr.getType().equals("playerTile")){
				int xDistance = x-curr.getX();
				int yDistance = curr.getY()-y;
				if(xDistance < BasicTile.WIDTH && xDistance >= 0){
					if(yDistance < BasicTile.WIDTH && yDistance >= 0){
						curr.highlight();
					}
				}
			}
		}
	}
}
