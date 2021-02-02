package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MainMenuPanel extends JPanel{
	private CardPanel parent;
	
	private JPanel midPanel;
	
	private JButton play;
	private JComboBox<String> profiles;
	private JButton help;
	private JButton quit;
	
	private Font font = new Font("Times New Roman", 50,30);
	public MainMenuPanel(CardPanel parent){
		this.parent = parent;
		init();
	}
	/*
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		BufferedImage bi;
		try {
			bi = ImageIO.read(new File("src/assets/menu.png"));
			
			g.drawImage(bi,0,0,this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		background.setIcon(new ImageIcon(getClass().getResource("src/assets/menu.png")));
	}*/
	private void init() {
		setLayout(new BorderLayout());
		//setBorder(BorderFactory.createLineBorder(Color.BLACK));
		initComponents();
	}
	private void initComponents(){
		play = new JButton("Play as");
		play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				parent.showPanel("levelSelect");
				parent.setProfile((String)profiles.getSelectedItem());
			}
		});
		
		
		profiles = new JComboBox<String>();
		readProfiles();
		profiles.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JComboBox<String> combo = (JComboBox<String>) e.getSource();
				String selection = (String) combo.getSelectedItem();
				
				if(selection.equals("New Profile")){
					createProfile();
				}
				else if(selection.equals("Delete Profile")){
					deleteProfile();
				}
			}
		});
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		profiles.setRenderer(listRenderer);
		
		help = new JButton("Help");
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				parent.showPanel("help");
			}
		});

		quit = new JButton("Quit");
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});

			/*
		Icon menubg = new ImageIcon("src/assets/menu.png");
		background = new JLabel(menubg);
		
		*/
		
		midPanel = new JPanel(){
			 @Override
			    public void paintComponent(Graphics g) { 
				 	super.paintComponent(g);
					BufferedImage bi;
					try {
						bi = ImageIO.read(new File("src/assets/menu.png"));
						
						g.drawImage(bi,0,0,this);
					} catch (IOException e) {
						e.printStackTrace();
					}
			    }
		};
		midPanel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
		midPanel.setLayout(new GridLayout(4,1,40,40));
		midPanel.setPreferredSize(new Dimension(350,350));
		
		midPanel.add(play);
		midPanel.add(profiles);
		midPanel.add(help);
		midPanel.add(quit);
		
		setFont(midPanel);

		add(midPanel);

	}
	private void setFont(JPanel midPanel) {
		Component[] contents = midPanel.getComponents();
		for(int i =0; i < contents.length; i++){
			contents[i].setFont(font);
		}
	}
	private void readProfiles(){
		profiles.removeAllItems();
		File file = new File("src/saves");
		String[] files = file.list();
		
		if(files.length == 0){
			createProfile();
		}
		else{
			for(int i=0; i<files.length; i++) {
		        if(files[i].substring((files[i].length()-4), files[i].length()).equals(".sav")){
		        	profiles.addItem(files[i].substring(0, files[i].length()-4));
		        }
		     }
			profiles.addItem("New Profile");
			profiles.addItem("Delete Profile");
		}
	}
	private void createProfile(){
		String input = (String)JOptionPane.showInputDialog(null, "Enter name:");
		if(input != null && input.trim().length() > 0){
			File newSave = new File("src/saves/"+input+".sav");
			try {
				if(newSave.exists()){
					JOptionPane.showMessageDialog(null,"Profile already exists!");
				}
				else if(input.equals("New Profile") || input.equals("Delete Profile")){
					JOptionPane.showMessageDialog(null,"Invalid Name!");
				}
				else{
					BufferedWriter bw = new BufferedWriter(new FileWriter(newSave));
					bw.write("1\n1");
					bw.flush();
					bw.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else{
			JOptionPane.showMessageDialog(null,"Name cannot be blank!");
		}
		readProfiles();
	}
	private void deleteProfile(){
		String input = (String) JOptionPane.showInputDialog(null, "Enter profile to delete:");
		if(input != null && input.trim().length() > 0){
			File toDelete = new File("src/saves/" + input+ ".sav");
			try{
				if(toDelete.delete()){
					JOptionPane.showMessageDialog(null,input+" has been deleted");
				}
				else{
					JOptionPane.showMessageDialog(null, "Profile not found");
				}
			}catch(SecurityException e){
				e.printStackTrace();
			}
		}
		readProfiles();
	}
}
