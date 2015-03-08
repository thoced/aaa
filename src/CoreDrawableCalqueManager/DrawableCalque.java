package CoreDrawableCalqueManager;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

import bilou.Camera;

public class DrawableCalque extends DrawableCalqueBase
{
	
	
	public DrawableCalque(Texture text,String name,float posx,float posy)
	{
		// appel a la class de base
		super(text,name,posx,posy);
	}
	

	
	public void Update(Time deltaTime) {
		// TODO Auto-generated method stub
		
	}

	
	public void draw(RenderTarget render, RenderStates state) {
		// affichage du calque
				FloatRect result = Camera.GetBoundsVisible().intersection(this.imageCalque.getGlobalBounds());
				if(result!=null)
					render.draw(imageCalque);
		
	}
	
}
