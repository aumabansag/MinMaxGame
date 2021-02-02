package bossGame;

public class Hare extends Piece{
	public Hare(int position){
		this.position = position;
		this.type = "hare";
	}
	public Hare clonePiece(){
		return new Hare(position);
	}
}
