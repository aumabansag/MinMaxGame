package bossGame;

import java.util.ArrayList;

public class Node {
	//private ArrayList<Node> children;
	public ArrayList<Node> children;
	private BoardState boardState;
	private int depth;
	private int score;
	private int alpha;
	private int beta;
	public Node(BoardState boardState, int depth, int alpha, int beta){
		this.boardState = boardState;
		this.depth = depth;
		this.alpha = alpha;
		this.beta = beta;
		children = new ArrayList<Node>();
		generateChildren();
	}/*
	private int minMax() {
		if(depth == 0){
			return boardState.evaluate();
		}
		
		
		/*
		ArrayList<int[]> legalMoves = boardState.getLegalMoves();
		if(depth % 2 == 0){
			return legalMoves.get(min());
		}
		else{
			return legalMoves.get(max());
		}
		return 0;
	}*/
	private void generateChildren(){
		if(depth == 0){
			score = boardState.evaluate();
			return;
		}
		
		int eval = boardState.evaluate();
		if(eval == 0 || eval == 20){
			score = eval;
			return;
		}
		
		
		ArrayList<int[]> legalMoves = boardState.getLegalMoves();
		for(int i = 0; i < legalMoves.size();i++){
			int[] currMove = legalMoves.get(i);
			BoardState updated = boardState.move(currMove[0], currMove[1]);
			Node tempChild = new Node(updated, depth-1, alpha, beta);
			int tempScore = tempChild.getScore();
			
			if(depth% 2 == 1){
				alpha = tempScore > alpha ? tempScore : alpha ;
				if(beta <= alpha){
					//System.out.println("pruned" + currMove[0]+currMove[1]);
					break;
				}
			}
			else{
				beta = tempScore < beta ? tempScore : beta;
				if(beta <= alpha){
				//	System.out.println("pruned" + currMove[0]+currMove[1]);
					break;
				}
			}
			children.add(tempChild);
			//System.out.println("Added child");
//			/children.add(child);
		}
		if(children.size() == 0){
			score = boardState.evaluate();
			return;
		}	
		
		if(depth % 2 == 0){
			score = beta;
		}
		else{
			score = alpha;
		}
		
		
		/*
		//set SCore based on children
		if(depth %2 == 0){
			min();
		}
		else{
			max();
		}
		*/
	}/*
	private void min(){
		int minScore = children.get(0).getScore();
		for(int i = 1; i < children.size(); i++){
			int currScore = children.get(i).getScore();
			if(currScore < minScore){
				minScore = currScore;
			}
		}
		score = minScore;
	}
	private void max(){
		int maxScore = children.get(0).getScore();
		for(int i = 1; i < children.size(); i++){
			int currScore = children.get(i).getScore();
			if(currScore > maxScore){
				maxScore = currScore;
			}
		}
		score = maxScore;
	}
	/*
	private void min(){
		int beta = Integer.MAX_VALUE;
		
			if(beta > child.score){
				beta = child.score;
				children.add(child);
				if(beta == 0){
					break;
				}
			}
		}
		score = beta;
	}*/
	/*
	private void max(){
		int alpha = Integer.MIN_VALUE;
		ArrayList<int[]> legalMoves = boardState.getLegalMoves();
		for(int i = 0; i < legalMoves.size();i++){
			int[] currMove = legalMoves.get(i);
			Node child = new Node(boardState.move(currMove[0], currMove[1]), depth-1);
			if(alpha < child.score ){
				alpha = child.score;
				children.add(child);
				if(alpha == 20){
					break;
				}
			}
		}
		score = alpha;
	}*/
	public int countChildren(){
		return children.size();
	}
	public ArrayList<Node> getChildren(){
		return children;
	}
	public int getScore(){
		return score;
	}
	public void printNode(){
		System.out.print("\nDepth: " + depth+"\nScore: "+score+"\n");
		boardState.printBoardState();
		for(int i = 0; i < children.size(); i++){
			children.get(i).printNode();
		}
		
	}
}
