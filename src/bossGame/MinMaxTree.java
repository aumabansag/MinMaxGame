package bossGame;

import java.util.ArrayList;

public class MinMaxTree {
	private int depth;
	private Node root;
	private BoardState activeBoard;
	public MinMaxTree(BoardState boardState, int depth){
		this.depth = depth;
		activeBoard = boardState;
		init();
	}
	private void init(){
		generateTree();
	}
	private void generateTree(){
		root = new Node(activeBoard, depth, -1, 21);
		//printTree();
	}
	public void printTree(){
		/*
		System.out.println(root.countChildren()+"children");
		for(int i = 0; i < root.children.size(); i ++){
			System.out.println(root.children.get(i).countChildren());
		}*/
		root.printNode();
	}
	//iterate through children of getMove, get Minimum rated
	public int[] getMove(){
		ArrayList<Node> children = root.getChildren();
		int i;
		for(i = 0; i < children.size(); i++){
			if(children.get(i).getScore() == root.getScore()){
				break;
			}
		}
		
		ArrayList<int[]> legalMoves = activeBoard.getLegalMoves();
		return legalMoves.get(i);
	}
}
