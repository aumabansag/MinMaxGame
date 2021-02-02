package bossGame;

import gui.CardPanel;
import gui.GamePanel;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class BossGame {
	private int world;
	private GamePanel gamePanel;
	private String profile;
	
	private BoardHandler boardHandler;
	public BossGame(int world, GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		this.world  = world;
		
		init();
	}
	private void init(){
		boardHandler = new BoardHandler(gamePanel,world);
	}
	public void tick(){
		boardHandler.tick();
	}
	public void render(Graphics g){
		boardHandler.render(g);
	}
	public void setProfile(String profile) {
		this.profile = profile;
		
	}
	public void mouseClicked(int x, int y) {
		boardHandler.mouseClicked(x, y);
	}
	public void endGame(boolean win) {
		if(win){
			winProtocol();
		}
		else{
			loseProtocol();
		}
		
	}private void winProtocol(){
		JOptionPane.showMessageDialog(null,"You have cleared World "+world+"!!");
		saveProgress();
		gamePanel.stop();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		CardPanel cardPanel = (CardPanel)gamePanel.getParent();
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
			gamePanel.startGame();
		}
		else{
			gamePanel.stop();
			((CardPanel)gamePanel.getParent()).showPanel("mainMenu");
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
				if(world < 4){
					maxWorld++;
					maxLevel = 1;
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

}
