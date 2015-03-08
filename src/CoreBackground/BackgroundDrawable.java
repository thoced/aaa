package CoreBackground;

import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.BlendMode;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.Vertex;
import org.jsfml.graphics.VertexArray;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

import bilou.Camera;
import bilou.ICoreBase;
import CoreTexturesManager.TexturesManager;

public class BackgroundDrawable implements Drawable,ICoreBase 
{
	// sprite du Background
	private Sprite spriteBackground = null;
	// enum des backgrounds
	public static enum TypeBackground {BACKGROUND01};
	// Texture utilisée
	private Texture text;
	
	private Vector2f sizeWindow;
	
	private List<Sprite> listSprite = new ArrayList<Sprite>();
	
	
	public  BackgroundDrawable(Vector2f sizeWindow)
	{
		this.sizeWindow = sizeWindow;
		
	}
	
	
	public void draw(RenderTarget render, RenderStates state) 
	{		
		
		//for(Sprite sp : this.listSprite)
		//	render.draw(sp);
		
	}
	
	public void ChooseBackground(TypeBackground background)
	{
		switch(background)
		{
			case BACKGROUND01:
			// creation des coordonnées de texture
			this.text = TexturesManager.GetTextureByName("FondEtoile.png");
			break;
			
			
		}
		
		// on instancie plusieurs sprites
		Sprite sp = new Sprite(this.text);
		sp.setPosition(new Vector2f(0f,0f));
		sp.setTextureRect(new IntRect(0,0,(int)this.sizeWindow.x,(int)this.sizeWindow.y));
		
		Sprite sp2 = new Sprite(this.text);
		sp2.setPosition(new Vector2f(this.sizeWindow.x,0));
		sp2.setTextureRect(new IntRect(0,0,(int)this.sizeWindow.x,(int)this.sizeWindow.y));
		
		
		Sprite sp3 = new Sprite(this.text);
		sp3.setPosition(new Vector2f(-this.sizeWindow.x,0));
		sp3.setTextureRect(new IntRect(0,0,(int)this.sizeWindow.x,(int)this.sizeWindow.y));
		
		
		this.listSprite.add(sp);
		this.listSprite.add(sp2);
		this.listSprite.add(sp3);
		
		VertexArray array;
		array = new VertexArray(PrimitiveType.QUADS);
		
		RectangleShape rectangle = new RectangleShape();
		rectangle.setFillColor(Color.BLACK);
			
			
	}

	
	public void update(Time deltaTime) 
	{
		for(int i=0;i<this.listSprite.size();i++)
		{
			Sprite sp = this.listSprite.get(i);
			Vector2f currentPosition = sp.getPosition();
			Vector2f newPosition = Vector2f.add(currentPosition, new Vector2f(0.1f,0f));
			sp.setPosition(newPosition);
		}
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


}
