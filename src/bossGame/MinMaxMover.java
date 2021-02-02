package bossGame;

public class MinMaxMover {
	private int depth;
	private MinMaxTree mmTree;
	public MinMaxMover(int depth){
		this.depth = depth;
	}
	public int[] generateMove(BoardState boardState){
		mmTree = new MinMaxTree(boardState.cloneBoard(),depth);
		int[] output = mmTree.getMove();
		/*
		int[] output = new int[2];//first int is the piece to move, second int is the destination\
		output[0] = 1;
		output[1] = 1;*/
		return output;
	}
}
