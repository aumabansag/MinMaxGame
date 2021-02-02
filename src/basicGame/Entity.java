package basicGame;

import java.awt.Graphics;

public interface Entity {
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract String getType();
	public abstract int getX();
	public abstract int getY();
	public abstract void highlight();
	public abstract void unhighlight();
}
