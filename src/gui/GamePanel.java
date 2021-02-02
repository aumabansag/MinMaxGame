package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

import basicGame.*;
import bossGame.BossGame;

public class GamePanel extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener{
	private CardPanel parent;
	private int world;
	private int level;
	private String profile;
	private boolean isRunning = false;
	private Thread gameThread;
	
	private Game game;
	private BossGame bossGame;
	public GamePanel(CardPanel parent){
		this.parent = parent;
		init();
	}
	private void init(){
		setPreferredSize(new Dimension(800,500));
		//setBorder(BorderFactory.createLineBorder(Color.PINK));
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
	}
	public void setWorld(int world){
		this.world = world;
	}
	public void setLevel(int level){
		this.level = level;
	}
	public void setProfile(String profile){
		this.profile = profile;
	}
	public void startGame(){
		
		if(level < 10){
			bossGame = null;
			game = new Game(world, level, this);
			game.setProfile(profile);
		}
		else if(level == 10){
			game = null;
			bossGame = new BossGame(world, this);
			bossGame.setProfile(profile);
			
		}
		
		start();
	}
	public void run() {
		//this.requestFocus();
		long lastTime = System.nanoTime();
		double fps = 60.0;
		double nanoseconds = 1000000000 / fps;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(isRunning){
			long now = System.nanoTime();
			delta += (now - lastTime) / nanoseconds;
			lastTime = now;
			while(delta >= 1){
				tick();
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				frames = 0;
			}
		}
		stop();
	}
	private void start(){
		if(gameThread!= null){

			stop();
			
		}
		gameThread = new Thread(this);
		isRunning = true;
		gameThread.start();
		this.requestFocus();
	}
	public void stop(){
		isRunning = false;
		try {
			gameThread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/*
		try{
			gameThread.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}*/
	}
	private void tick(){
		if(game!= null){
			game.tick();
		}
		/*if(bossGame != null){
			bossGame.tick();
		}*/
	}
	private void render(){
		JFrame gameFrame = (JFrame)SwingUtilities.getWindowAncestor(this);
		BufferStrategy bs = gameFrame.getBufferStrategy();
		if(bs == null){
			gameFrame.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.PINK);
		g.fillRect(gameFrame.getInsets().left, gameFrame.getInsets().top, gameFrame.getWidth(), gameFrame.getHeight());
		
		if(game != null){
			game.render(g);
		}
		else if(bossGame != null){
			bossGame.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	public void mouseClicked(MouseEvent e) {
		if(game != null){
			game.mouseClicked(e.getX(), e.getY());
		}
		else if(bossGame != null){
			bossGame.mouseClicked(e.getX(), e.getY());
		}
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
		
	}
	public void mouseReleased(MouseEvent e) {
		
	}
	public void endGame(boolean win){
		if(game != null){
			game.endGame(win);
		}
		else if(bossGame != null){
			bossGame.endGame(win);
		}
	}
	public CardPanel getParent(){
		return parent;
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		if(game != null){
			game.mouseMoved(e.getX(), e.getY());
		}
		else if(bossGame != null){
			//bossGame.mouseMoved(e.getX(), e.getY());
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			 escapePrompt();
		}
		else if(e.getKeyCode() == KeyEvent.VK_R){
			restart();
		}
		
	}
	private void escapePrompt(){
		int choice = JOptionPane.showConfirmDialog(null,"Go back to level select?", "",JOptionPane.YES_NO_OPTION);
		
		if(choice == 0){
			
			parent.showPanel("levelSelect");
			parent.setProfile(profile);
			stop();
		}
		
	}
	private void restart(){
		startGame();
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
