package bossGame;

import gui.GamePanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class BoardHandler {
	private static final int boardWidth = 800;
	private static final int boardHeight = 500;
	private static final int nodeRadius = 50;
	private JFrame gameFrame;
	private GamePanel gamePanel;
	
	private static final int nodeWidth = 50;
	private static final int nodeHeight = 50;
	private static final int nodesWide = 5;
	private static final int nodesHigh = 3;
	private static final int pieceWidth = 100;
	private static final int pieceHeight = 100;
	private boolean playerTurn = true;
	
	private BufferedImage hareSprite;
	private BufferedImage houndSprite;
	private BoardState currentBoard;
	private int[] nodeCenters;
	
	private MinMaxMover minMaxMover;
	private int world;
	
	public BoardHandler(GamePanel gamePanel, int world){
		this.gamePanel = gamePanel;
		this.gameFrame = (JFrame)SwingUtilities.getWindowAncestor(gamePanel);
		this.world = world;
		init();
	}
	private void init(){
		currentBoard = new BoardState();
		//minMaxMover = new MinMaxMover(world);
		if(world == 1){
			minMaxMover = new MinMaxMover(3);
		}
		else if(world ==2){
			minMaxMover = new MinMaxMover(7);
		}
		else if(world ==3){
			minMaxMover = new MinMaxMover(10);
		}
		else{
			minMaxMover = new MinMaxMover(16);
		}
		nodeCenters = getNodeCenters();
		
		try {
			hareSprite = ImageIO.read(new File("src/assets/evilHare.png"));
			houndSprite = (ImageIO.read(new File("src/assets/doggoKnight.png"))).getSubimage(0,50,50,50);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//currentBoard.printBoardState();
	}
	public void tick() {
		
	}
	public void render(Graphics g) {
		
		renderBoard(g);
		//int[] positions = currentBoard.getPositions();
	}
	private void renderBoard(Graphics g){

		renderEdges(g);
		renderNodes(g);
		
		renderPieces(g);
	}
	
	
	private void renderPieces(Graphics g) { 
		int[] nodeCenters = getNodeCenters();
		renderHound(g);
		renderHare(g);
	}
	private void renderHare(Graphics g) {
		for(int i = 1; i < currentBoard.getPositions().length; i++){
			int harePosition = currentBoard.getPositions()[i];
			//g.setColor(Color.RED);
			//g.fillRect(nodeCenters[harePosition*2] - pieceWidth/2,nodeCenters[harePosition*2+1] - pieceHeight/2,pieceWidth, pieceHeight);
			g.drawImage(hareSprite, nodeCenters[harePosition*2] - pieceWidth/2,nodeCenters[harePosition*2+1] - pieceHeight/2,pieceWidth, pieceHeight, null,null);
		}
	}
	private void renderHound(Graphics g) {
		int houndPosition = currentBoard.getPositions()[0];
		//g.setColor(Color.YELLOW);
		//g.fillRect(nodeCenters[houndPosition*2] - pieceWidth/2,nodeCenters[houndPosition*2+1] - pieceHeight/2,pieceWidth, pieceHeight);
		g.drawImage(houndSprite, nodeCenters[houndPosition*2] - pieceWidth/2,nodeCenters[houndPosition*2+1] - pieceHeight/2,pieceWidth, pieceHeight, null,null);
	}
	private void renderEdges(Graphics g) {

		/*
		for(int x = 0; x < nodeCenters.length; x++){
			System.out.println(nodeCenters[x]);
		}
		System.out.println("*********");
		*/
		
		drawConnection(g, nodeCenters, 0,1);
		drawConnection(g, nodeCenters, 0,3);
		drawConnection(g, nodeCenters, 0,4);
		drawConnection(g, nodeCenters, 0,5);
		drawConnection(g, nodeCenters, 1,2);
		drawConnection(g, nodeCenters, 1,5);
		drawConnection(g, nodeCenters, 2,5);
		drawConnection(g, nodeCenters, 2,6);
		drawConnection(g, nodeCenters, 2,7);
		drawConnection(g, nodeCenters, 3,4);
		drawConnection(g, nodeCenters, 3,8);
		drawConnection(g, nodeCenters, 4,5);
		drawConnection(g, nodeCenters, 4,8);
		drawConnection(g, nodeCenters, 5,6);
		drawConnection(g, nodeCenters, 5,8);
		drawConnection(g, nodeCenters, 5,9);
		drawConnection(g, nodeCenters, 5,10);
		drawConnection(g, nodeCenters, 6,7);
		drawConnection(g, nodeCenters, 6,10);
		drawConnection(g, nodeCenters, 7,10);
		drawConnection(g, nodeCenters, 8,9);
		drawConnection(g, nodeCenters, 9,10);
		
		//draw instructions
		g.setFont(new Font("Times New Roman", 24,24));
		g.setColor(Color.BLACK);
		g.drawString("Press R to restart", 50, 70);
		g.drawString("Press Esc to quit",50,100);
		
	}
	private void drawConnection(Graphics g, int[] nodeCenters, int nodeA, int nodeB){
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(5));
		g2.setColor(Color.WHITE);
		g2.drawLine(nodeCenters[nodeA*2], nodeCenters[nodeA*2+1], nodeCenters[nodeB*2], nodeCenters[nodeB*2+1]);
	}
	private int[] getNodeCenters(){
		
		int frameWidth = gameFrame.getWidth();
		int frameHeight = gameFrame.getHeight();
		int boardCornerX = (frameWidth - boardWidth)/2;
		int boardCornerY = (frameHeight - boardHeight)/2+gameFrame.getInsets().bottom;
		//int boardCornerY = (frameHeight - boardHeight)/2;
		int columnWidth = boardWidth / nodesWide;
		int rowHeight = boardHeight / nodesHigh;
		int columnMid = boardCornerX + columnWidth/2;
		int rowMid = boardCornerY + rowHeight/2;
		
		int[] centers = new int[(nodesWide *nodesHigh - 4)*2];
		
		int currIndex = 0;
		for(int i = 0; i < nodesHigh; i++){
			for(int j = 0; j < nodesWide; j++){
				//remove corners
				if(i ==0){
					if(j == 0){
						j++;
					}
					else if(j == nodesWide - 1){
						j = 0;
						i++;
					}
				}
				else if(i == nodesHigh - 1){
					if(j == 0){
						j++;
					}
					else if(j == nodesWide - 1){
						break;
					}
				}
				centers[currIndex++] =columnMid +  j*columnWidth;
				centers[currIndex++] = rowMid +  i*rowHeight;
			}
		}
		return centers;
	}
	
	private void renderNodes(Graphics g) {
		int frameWidth = gameFrame.getWidth();
		int frameHeight = gameFrame.getHeight();
		
		int boardCornerX = (frameWidth - boardWidth)/2;
		int boardCornerY = (frameHeight - boardHeight)/2+gameFrame.getInsets().bottom;
		
		
		
		g.setColor(Color.black);
		int nodeWidth = 50;
		int nodeHeight = 50;
		//draw nodes
		int columnWidth = boardWidth / nodesWide;
		int rowHeight = boardHeight / nodesHigh;
		
		int columnMid = boardCornerX + (columnWidth - nodeWidth)/2;
		int rowMid = boardCornerY + (rowHeight-nodeHeight)/2;
		
		for(int i =0; i < nodesHigh; i++){
			for(int j = 0; j < nodesWide; j++){
				if((i == 0 || i == nodesHigh-1)&& (j == 0|| j == nodesWide-1)){
					continue;
				}
				g.fillOval(columnMid + j*columnWidth, 
						rowMid + i * rowHeight,
						nodeWidth,
						nodeHeight);
			}
		}
		
	}
	public boolean playerTurn(){
		return playerTurn;
	}
	public void mouseClicked(int x, int y) {
		int nodeNumber = getClickedNode(x,y);
		boolean success = move(0,nodeNumber);
		if(!success){
			return;
		}
		int[] computerMove = minMaxMover.generateMove(currentBoard);
		
		move(computerMove[0], computerMove[1]);
				
		
		if(currentBoard.evaluate() == 0){
			gamePanel.endGame(false);
		}
		else if(currentBoard.evaluate() == 20){
			gamePanel.endGame(true);
		}
	}
	private int getClickedNode(int mouseX, int mouseY){
		for(int i = 0; i < nodeCenters.length/2; i++){
			float xGap = Math.abs((nodeCenters[i*2] - gameFrame.getInsets().left) - mouseX);
			float  yGap = Math.abs((nodeCenters[i*2+1]- gameFrame.getInsets().top) - mouseY);
			if(Math.abs(yGap) <= nodeHeight/2 && Math.abs(xGap) <= nodeWidth/2){
				return i;
			}
		}
		return -1;
	}
	private boolean move(int pieceIndex, int destination){
		BoardState temp = currentBoard.move(pieceIndex, destination);
		if(temp != currentBoard){
			currentBoard = temp;
			return true;
		}
		return false;
	}
}
