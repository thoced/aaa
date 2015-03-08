package structure;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Time;

public interface Iunitbase
{
	public abstract void update(Time deltaTime);
	
	public abstract void draw(RenderWindow window);
	
}
