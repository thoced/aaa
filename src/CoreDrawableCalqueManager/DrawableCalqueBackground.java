package CoreDrawableCalqueManager;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.Vertex;
import org.jsfml.graphics.VertexArray;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

public class DrawableCalqueBackground extends DrawableCalqueBase 
{
	// VertexArray
	private VertexArray arrayVertex;
	// renderstate
	private RenderStates rs;
	// speed
	private float speed;
	
	public DrawableCalqueBackground(Texture text, String name, float posx,
			float posy,float speed) 
	{
		super(text, name, posx, posy);
		// speed
		this.speed = speed;
		// instance
		arrayVertex = new VertexArray(PrimitiveType.QUADS);
		// creation du quad
		float width = text.getSize().x;
		float height = text.getSize().y;
		// creation des position de textures
		Vector2f t1 = new Vector2f(0f,0f);
		Vector2f t2 = new Vector2f(width,0f);
		Vector2f t3 = new Vector2f(width,height);
		Vector2f t4 = new Vector2f(0f,height);
		// création des vertex
		Vertex v1 = new Vertex(new Vector2f(posx,posy),t1);
		Vertex v2 = new Vertex(new Vector2f(posx + width,posy),t2);
		Vertex v3 = new Vertex(new Vector2f(posx + width,posy + height),t3);
		Vertex v4 = new Vertex(new Vector2f(posx,posy + height),t4);
		// ajout dans le array
		arrayVertex.add(v1);
		arrayVertex.add(v2);
		arrayVertex.add(v3);
		arrayVertex.add(v4);
		// renderstate
		rs = new RenderStates(text);
		
		
	}
	
	

	
	public void draw(RenderTarget render, RenderStates state) 
	{
		// TODO Auto-generated method stub
			render.draw(arrayVertex,rs);
		
		
	}

	
	public void Update(Time deltaTime) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
