package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.swing.*;
public class LevelSelectPanel extends JPanel{
	private CardPanel parent;
	
	private String profile;
	
	private JPanel containerPanel;
	private JPanel worldPanel;
	private JPanel levelPanel;
	private JPanel backPanel;
	
	private CardLayout levelSelectLayout;
	
	private JButton[] worlds;
	private JButton[] levels;
	private boolean showingWorlds = true;
	
	private int maxWorld;
	private int maxLevel;
	public LevelSelectPanel(CardPanel parent){
		this.parent = parent;
		init();
	}
	private void init(){
		setPreferredSize(new Dimension(800,500));
		((FlowLayout) getLayout()).setVgap(0);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		initSubpanels();
	}
	private void initSubpanels(){
		//**********************************start of container panel
		containerPanel = new JPanel();
		levelSelectLayout = new CardLayout();
		containerPanel.setLayout(levelSelectLayout);
		
		worldPanel = new JPanel();
		((FlowLayout) worldPanel.getLayout()).setVgap(0);
		worlds = new JButton[4];
		for(int i = 0; i < worlds.length; i++){
			final int index  = i+1;
			worlds[i] = new JButton("World "+(i+1));
			worlds[i].setPreferredSize(new Dimension(200,450));
			worlds[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					setWorld(index);
				}
			});
			worldPanel.add(worlds[i]);
		}
		//setWorldIcons
		Icon water = new ImageIcon(this.getClass().getResource("/assets/buttons/water.png"));
		worlds[0].setIcon(water);
		
		Icon earth = new ImageIcon(this.getClass().getResource("/assets/buttons/earth.png"));
		worlds[1].setIcon(earth);
		
		Icon fire = new ImageIcon(this.getClass().getResource("/assets/buttons/fire.png"));
		worlds[2].setIcon(fire);
		
		Icon air = new ImageIcon(this.getClass().getResource("/assets/buttons/air.png"));
		worlds[3].setIcon(air);
		
		
		
		Font levelFont = new Font("Times New Roman", 64,64);
		levelPanel = new JPanel();
		levelPanel.setLayout(new GridLayout(2,5));
		levelPanel.setPreferredSize(new Dimension(800,450));
		levels = new JButton [10];
		for(int i = 0; i < levels.length; i++){
			final int index  = i+1;
			levels[i] = new JButton(""+(i+1));
			levels[i].setFont(levelFont);
			levels[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					setLevel(index);
					parent.startGame();
				}
			});
			levelPanel.add(levels[i]);	
		}
		
		

		containerPanel.add("worlds",  worldPanel);
		containerPanel.add("levels", levelPanel);
		//*********************end of containerPanel*********************
		
		
		
		backPanel = new JPanel();
		backPanel.setPreferredSize(new Dimension(800,50));
		((FlowLayout)backPanel.getLayout()).setVgap(0);
		
		JButton back = new JButton("Back");
		back.setPreferredSize(new Dimension(800,50));
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(showingWorlds){
					parent.showPanel("mainMenu");
				}
				else{
					showPanel("worlds");
				}
			}
		});
		backPanel.add(back);
		
		this.add(containerPanel);
		this.add(backPanel);
	}
	private void showPanel(String panelName){
		if(panelName.equals("worlds")){
			levelSelectLayout.show(containerPanel,"worlds");
			showingWorlds = true;
		}
		else if(panelName.equals("levels")){
			levelSelectLayout.show(containerPanel, "levels");
			showingWorlds = false;
		}
	}
	private void setWorld(int index){
		parent.setWorld(index);
		if(index >= maxWorld){
			lockLevels();
		}
		else{
			unlockLevels();
		}
		showPanel("levels");
		
	}
	private void setLevel(int index){
		parent.setLevel(index);
		parent.showPanel("game");
	}
	public void setProfile(String profile){
		this.profile = profile;
		setLockedLevels();
		lockWorlds();
		lockLevels();
		showPanel("worlds");
	}
	private void setLockedLevels(){
		File saveFile = new File("src/saves/"+this.profile+".sav");
		try{
			BufferedReader br = new BufferedReader(new FileReader(saveFile));
			maxWorld = Integer.parseInt(br.readLine());
			maxLevel = Integer.parseInt(br.readLine());
			br.close();
		}
		catch(IOException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Save file is corrupted");
		}
	}
	private void lockLevels(){
		unlockLevels();
		for(int i = maxLevel; i < 10; i++){
			levels[i].setEnabled(false);
		}
	}
	private void unlockLevels(){
		for(int i = 0; i < 10; i++){
			levels[i].setEnabled(true);
		}
	}
	private void lockWorlds(){
		unlockWorlds();
		for(int i = maxWorld; i < 4; i++){
			worlds[i].setEnabled(false);
		}
	}
	private void unlockWorlds(){
		for(int i = 0; i < 4; i++){
			worlds[i].setEnabled(true);
		}
	}
}
