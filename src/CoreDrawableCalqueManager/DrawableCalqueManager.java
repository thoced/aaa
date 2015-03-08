package CoreDrawableCalqueManager;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Time;
import org.jsfml.window.event.Event;

import CoreTexturesManager.TexturesManager;
import bilou.Camera;
import bilou.ICoreBase;

public class DrawableCalqueManager implements ICoreBase,Drawable
{
	// liste de Layers
	private List<DrawableCalqueBase> listCalques;
	
	public DrawableCalqueManager()
	{
		// instance de la liste des calques
		this.listCalques = new ArrayList<DrawableCalqueBase>();
	}
	
	
	public void update(Time deltaTime)
	{
		// TODO Auto-generated method stub
		for(DrawableCalqueBase calque : this.listCalques)
		{
			// affichage des calques
			calque.Update(deltaTime);
		}
	}

	
	public void InsertCalque(DrawableCalqueBase calque)
	{
		// ajout dans la liste
		if(calque!=null)
		this.listCalques.add(calque);
	}
	
	public void InsertCalque(String pathTexture,String name,int posx,int posy)
	{
		// on récupère la texture via le textures manager
		Texture text = TexturesManager.GetTextureByName(pathTexture);
		
		// on créer un calque
		DrawableCalque calque = new DrawableCalque(text,name,posx,posy);
		
		// on ajoute dans la liste
		this.listCalques.add(calque);
		
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

	
	public void draw(RenderTarget render, RenderStates state) {
		// TODO Auto-generated method stub
		
				for(DrawableCalqueBase calque : this.listCalques)
				{
					// affichage des calques
					calque.draw(render, state);
				}
		
	}
	
	
}
