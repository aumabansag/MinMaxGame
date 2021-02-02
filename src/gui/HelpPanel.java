package gui;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HelpPanel extends JPanel {
	private CardPanel parent;
	private JLabel helpLabel;
	private JButton next;
	private JButton prev;
	private JButton back;
	private JButton mainMenu;
	private ImageIcon[] helpScreens;
	private int helpState;
	//0 == general 
	//1 == maze game
	//2 == boss game
	
	//private ImageIcon[] helpScreens;
	public HelpPanel(CardPanel cardPanel) {
		this.parent = cardPanel;
		init();
	}
	private void init(){
		this.setLayout(new BorderLayout());
		helpLabel = new JLabel();
		helpScreens = new ImageIcon[3];
		helpScreens[0] = new ImageIcon("src/assets/help1.png");
		helpScreens[1] = new ImageIcon("src/assets/help2.png");
		helpScreens[2] = new ImageIcon("src/assets/help3.png");
		
		setHelp();
		next = new JButton(">>");
		next.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				helpState = helpState ==  2?  0: helpState+1;	
				setHelp();
			}
			
		});
		prev = new JButton("<<");
		prev.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				helpState = helpState ==  0?  2: helpState-1;	
				setHelp();
			}
			
		});
		
		
		back = new JButton("Back");
		back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				parent.showPanel("mainMenu");
			}
			
		});
		
		JPanel southPanel = new JPanel();
		southPanel.add(prev);
		southPanel.add(next);
		southPanel.add(back);
		add(southPanel,BorderLayout.SOUTH);
		add(helpLabel, BorderLayout.CENTER);
	}
	private void setHelp(){
		helpLabel.setIcon(helpScreens[helpState]);
	}
}
