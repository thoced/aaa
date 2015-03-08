package CoreDrawableCalqueManager;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

// class de base pour les drawable calque, permet ainsi d'afficher différent calque, dynamique, static

public abstract class DrawableCalqueBase implements Drawable
{
	// sprite du layer à afficher
		protected Sprite imageCalque;
		// nom du calque
		protected String name;
		
		public DrawableCalqueBase(Texture text,String name,float posx,float posy)
		{
			// creation du sprite depuis une reference texture
			imageCalque = new Sprite(text);
			// position du sprite
			imageCalque.setPosition(new Vector2f(posx,posy));
			// nom du calque
			this.name = name;
		}
		
	
		//methode abstract du update
		public abstract void Update(Time deltaTime);
}
