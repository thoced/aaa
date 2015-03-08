package bilou;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Time;
import org.jsfml.window.event.Event;

public interface ICoreBase 
{
	public abstract void update(Time deltaTime);
	
	//public abstract void draw(RenderTexture render,RenderStates state);
	
	public abstract void loadContent();
	
	public abstract void reloadContent();
	
	public abstract void deleteContent();
	
	public abstract void init();
	
	public abstract void catchEvent(Event e);
	
}
