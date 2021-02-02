package bossGame;

public abstract class Piece {
	protected int position;
	protected String type;
	public abstract Piece clonePiece();
	public void move(int destination){
		position = destination;
	}
	public int getPosition(){
		return position;
	}
	public String getType(){
		return type;
	}
}
