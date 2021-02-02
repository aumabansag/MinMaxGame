package bossGame;

public class Hound extends Piece{
	public Hound(int position){
		this.position = position;
		this.type = "hound";
	}
	public Hound clonePiece(){
		return new Hound(position);
	}
}
