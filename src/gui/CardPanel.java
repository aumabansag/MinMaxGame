package gui;

import java.awt.*;

import javax.swing.JPanel;

public class CardPanel extends JPanel {
	private CardLayout cardLayout;
	
	private MainMenuPanel mainMenuPanel;
	private LevelSelectPanel levelSelectPanel;
	private GamePanel gamePanel;
	private HelpPanel helpPanel;
	
	public CardPanel(){
		init();
	}
	private void init() {
		setPreferredSize(new Dimension(800,500));
		cardLayout = new CardLayout();
		setLayout(cardLayout);
		
		initSubpanels();
	}
	private void initSubpanels(){
		mainMenuPanel = new MainMenuPanel(this);
		levelSelectPanel = new LevelSelectPanel(this);
		gamePanel = new GamePanel(this);
		helpPanel = new HelpPanel(this);
		
		add("mainMenu", mainMenuPanel);
		add("levelSelect", levelSelectPanel);
		add("game",gamePanel);
		add("help", helpPanel);
	}
	public void showPanel(String panelName){
		cardLayout.show(this, panelName);
		repaint();
		revalidate();
	}
	public void setWorld(int world){
		gamePanel.setWorld(world);
	}
	public void setLevel(int level){
		gamePanel.setLevel(level);
	}
	public void setProfile(String profile){
		levelSelectPanel.setProfile(profile);
		gamePanel.setProfile(profile);
	}
	public void startGame(){
		gamePanel.startGame();
	}
}
