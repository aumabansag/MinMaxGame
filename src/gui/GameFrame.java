package gui;

import javax.swing.JFrame;

public class GameFrame{
	private JFrame gameFrame;
	
	private CardPanel cardPanel;
	public GameFrame(){
		init();
		addPanelsToFrame();
	}
	private void init(){
		gameFrame = new JFrame("DoggoKnight");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);

		initSubpanel();
		addPanelsToFrame();
		
		gameFrame.pack();
		gameFrame.setLocationRelativeTo(null);
	}
	private void initSubpanel() {
		cardPanel = new CardPanel();
	}
	private void addPanelsToFrame() {
		gameFrame.add(cardPanel);
	}
	public void setVisible(boolean visible){
		gameFrame.setVisible(visible);
	}
}
