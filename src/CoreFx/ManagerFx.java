package CoreFx;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Time;
import org.jsfml.window.event.Event;

import bilou.ICoreBase;

public class ManagerFx implements Drawable,ICoreBase
{
	// list des objet fx
	private List<BaseFx> listFx;
	
	public ManagerFx ()
	{
		// instance de la liste
		this.listFx = new ArrayList<BaseFx>();
	}
	
	public void insertFx(BaseFx fx)
	{
		// ajout dans la liste
		this.listFx.add(fx);
	}

	
	public void update(Time deltaTime) 
	{
		// on boucle sur la liste et on appel les methode update
		for(BaseFx fx : this.listFx)
			fx.update(deltaTime);
		
	}

	
	public void loadContent() {
		// TODO Auto-generated method stub
		
	}

	
	public void reloadContent() {
		// TODO Auto-generated method stub
		
	}

	
	public void deleteContent() {
		// TODO Auto-generated method stub
		
	}

	
	public void init() {
		// TODO Auto-generated method stub
		
	}

	
	public void catchEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

	
	public void draw(RenderTarget render, RenderStates state) 
	{
		// on boucle sur la liste et on appel les methode draw
		for(BaseFx fx : this.listFx)
			fx.draw(render, state);
		
	}
	
	
}
