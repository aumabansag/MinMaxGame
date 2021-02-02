package bossGame;

import java.util.ArrayList;

public class BoardState {
	private Piece[] pieces;
	private boolean playerTurn;
	private int waitingMoves;
	public BoardState(Piece[] pieceInput, boolean playerTurn, int waitingMoves){
		this.playerTurn = playerTurn;
		pieces = new Piece[4];
		for(int i = 0; i < pieces.length; i++){
			pieces[i] = pieceInput[i].clonePiece();
		}
		this.waitingMoves = waitingMoves;
	}
	public BoardState(){
		init();
		
	}
	private void init(){
		pieces = new Piece[4];
		
		pieces[0] = new Hound(3);
		pieces[1] = new Hare(2);
		pieces[2] = new Hare(7);
		pieces[3] = new Hare(10);
		
		/*
		pieces[0] = new Hound(0);
		pieces[1] = new Hare(5);
		pieces[2] = new Hare(6);
		pieces[3] = new Hare(9);
		*/
		playerTurn = true;
		waitingMoves = 0;
	}
	public void printBoardState(){
		System.out.println(pieces[0].getPosition());
		System.out.println(pieces[1].getPosition());
		System.out.println(pieces[2].getPosition());
		System.out.println(pieces[3].getPosition());
	}
	public BoardState move(int pieceIndex, int destination){
		BoardState tempBoard = cloneBoard();
		if((tempBoard.playerTurn && pieceIndex == 0) ||(!tempBoard.playerTurn && pieceIndex !=0) ){
			if(tempBoard.validMove(pieceIndex,destination)){
				if(pieceIndex > 0 ){
					if(tempBoard.forwardMove(pieceIndex, destination)){

						tempBoard.waitingMoves = 0;
					}
					else{
						tempBoard.waitingMoves++;
					}
				}

				tempBoard.pieces[pieceIndex].move(destination);
				tempBoard.playerTurn = !playerTurn;
				return tempBoard;
			}
			else{
				//System.out.println("invalid move ");
			}
		} 
		else{
			//System.out.println("nachoturn");	
		}
		return this;
	}
	private boolean forwardMove(int hareIndex, int destination) {
		int moveDiff = destination - pieces[hareIndex].getPosition();
		if(moveDiff == 3 || moveDiff == -5 || moveDiff == -1 ){
			return true;
		}
		return false;
	}
	private boolean backwardMove(int hareIndex, int destination){
		int moveDiff = destination - pieces[hareIndex].getPosition();
		if(moveDiff == -3 || moveDiff == 5 || moveDiff == 1 ){
			return true;
		}
		return false;
	}
	private boolean validMove(int pieceIndex, int destination){
		if(pieceIndex == 0){
			Piece currPiece = pieces[0];
			int[] validMoves = getMoves(currPiece.getPosition());
			for(int i =0; i < validMoves.length; i++){
				if(validMoves[i] == destination){
					for(int j = 0; j < pieces.length; j++){
						if(pieces[j].getPosition() == validMoves[i]){
							return false;
						}
					}
					return true;
				}
			}
		}
		
		//hares cant move backwards
		else if(pieceIndex > 0){
			Piece currPiece = pieces[pieceIndex];
			int[] validMoves = getMoves(currPiece.getPosition());
			for(int i = 0; i < validMoves.length; i++){
				if(validMoves[i] == destination){
					for(int j = 0; j < pieces.length; j++){
						if(pieces[j].getPosition() == validMoves[i]){
							return false;
						}
					}
					
					if(backwardMove(pieceIndex, destination)){
						return false;
					}
					return true;
				}
			}
		}
		return false;
	}
	public BoardState cloneBoard(){
		BoardState boardState = new BoardState(pieces, playerTurn, waitingMoves);
		return boardState;
	}
	public int evaluate(){
		
		//20 means hare won
		if(passed() || waitingMoves == 10){
			return 20;
		}
		
		int[] playerMoves = getMoves(pieces[0].getPosition());
		int score = playerMoves.length;
		
		for(int i  = 0; i < playerMoves.length; i++){
			if(!validMove(0,playerMoves[i])){
				score--;
			}
		}

		return score;
	}
	private boolean passed() {
		int maxRow = getRow(pieces[0].getPosition());
		for(int i =1; i < pieces.length; i++){
			int currRow = getRow(pieces[i].getPosition());
			if(maxRow < currRow){
				maxRow = currRow;
			}
		}
		
		
		if( getRow(pieces[0].getPosition())== maxRow){
			return true;
		}
		return false;
	}
	private int getRow(int position){
		if(position == 3){
			return 1;
		}
		else if(position == 0 || position == 4 || position == 8){
			return 2;
		}
		else if(position == 1 || position == 5 || position == 9){
			return 3;
		}
		else if(position == 2 || position == 6 || position == 10){
			return 4;
		}
		else if(position == 7){
			return 5;
		}
		return -1;
	}
	public int[] getPositions(){
		int[] output = new int[pieces.length];
		for(int i = 0; i < pieces.length; i++){
			output[i] = pieces[i].getPosition();
		}
		return output;
	}
	private int[] getMoves(int position){
		if( position == 0){
			return new int[] {5,4,1,3};
		}
		else if( position == 1){
			return new int[] {5,0,2};
		}
		else if( position == 2){
			return new int[] {5,6,1,7};
		}
		else if( position == 3){
			return new int[] {0, 4,8};
		}
		else if( position == 4){
			return new int[] {5,0,8,3};
		}
		else if( position == 5){
			return new int[] {0,2,4,6,8,10,1,9};
		}
		else if( position == 6){
			return new int[] {5,2,10,7};
		}
		else if( position == 7){
			return new int[] {2,6,10};
		}
		else if( position == 8){
			return new int[] {5,4,3,9};
		}
		else if( position == 9){
			return new int[] {5,8,10};
		}	
		else if( position == 10){
			return new int[] {5,6,7,9};
		}
		else{
			return null;
		}
	}
	public ArrayList<int[]> getLegalMoves(){
		ArrayList<int[]> output = new ArrayList<int[]>();
		if(playerTurn){
			int[] validMoves = getMoves(pieces[0].getPosition());
			for(int i = 0; i < validMoves.length; i++){
				//System.out.println("checking move"+ validMoves[i]);
				if(validMove(0,validMoves[i])){
					//System.out.println("added " + 0 + " "+ validMoves[i]);
					output.add(new int[] {0,validMoves[i]});
				}
			}
		}
		else{
			for(int i = 1; i < pieces.length; i++){
				int[] validMoves = getMoves(pieces[i].getPosition());
				for(int j = 0; j < validMoves.length; j++){
					//System.out.println("checking move"+ validMoves[j]);
					if(validMove(i,validMoves[j])){
						//System.out.println("added " + i + " "+ validMoves[j]);
						output.add(new int[] {i, validMoves[j]});
					}
				}	
			}
		}
		return output;
	}
	public boolean isPlayerTurn(){
		return playerTurn;
	}
	public int getWaitingMoves(){
		return waitingMoves;
	}
}
