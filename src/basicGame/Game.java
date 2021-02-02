package basicGame;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import gui.CardPanel;
import gui.GamePanel;

public class Game{
	private EntityHandler handler;
	private GamePanel parent;
	private int world;
	private int level;
	private String profile;
	public Game(int world, int level, GamePanel parent) {
 
		this.parent = parent;
		this.world = world;
		this.level = level;
		init(world, level);
		
	}
	private void init(int world, int level){
		handler = new EntityHandler(parent);
		JFrame windowAncestor = (JFrame)SwingUtilities.getWindowAncestor(parent);
		handler.setOffset(windowAncestor.getInsets().left, windowAncestor.getInsets().top);
		handler.loadLevel(world, level);
		handler.setTexture("earth");
	}
	public void mouseClicked(int mouseX, int mouseY){
		handler.mouseClicked(mouseX, mouseY);
	}
	public void tick(){
		handler.tick();
	}
	public void render(Graphics g){
		handler.render(g);
	}
	public void endGame(boolean win){
		if(win){
			winProtocol();
		}
		else{
			loseProtocol();
			
		}
	}
	private void winProtocol(){
		JOptionPane.showMessageDialog(null,"You have cleared Stage "+world +"-"+level+"!!");
		saveProgress();
		parent.stop();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		CardPanel cardPanel = (CardPanel)parent.getParent();
		cardPanel.showPanel("levelSelect");
		cardPanel.setProfile(profile);
	}
	private void loseProtocol(){
		int choice = JOptionPane.showConfirmDialog(null,"Try Again?", "DEAD",JOptionPane.YES_NO_OPTION);
		try {
			//give time for the old instance to end, before making a new Game Instance
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(choice == 0){
			parent.startGame();
		}
		else{
			parent.stop();
			((CardPanel)parent.getParent()).showPanel("mainMenu");
		}
	}
	private void saveProgress(){
		File saveFile = new File("src/saves/"+profile+".sav");
		try {
			BufferedReader br = new BufferedReader(new FileReader(saveFile));
			int maxWorld = Integer.parseInt(br.readLine());
			int maxLevel = Integer.parseInt(br.readLine());
			br.close();
			BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile));
			if(world == maxWorld){
				if(world < 4 && level == 10){
					maxWorld++;
					maxLevel = 1;
				}
				else if(level == maxLevel && level < 10){
					maxLevel++;
				}
			}
			bw.write(""+maxWorld);
			bw.newLine();
			bw.write(""+maxLevel);
			bw.flush();
			bw.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	public void setProfile(String profile){
		this.profile = profile;
	}
	public void mouseMoved(int x, int y) {
		handler.mouseMoved(x , y);	
	}
}
