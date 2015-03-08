package Entities;

import org.jsfml.system.Time;
import org.jsfml.window.event.Event;

public abstract class EntitieBase extends ModelMVC
{
	public abstract void Update(Time elapsedTime);
	
	public abstract void LoadContent();
	
	public abstract void SetEvent(Event e);
	
	

}
