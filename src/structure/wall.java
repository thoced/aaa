package structure;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

public class wall implements Iunitbase
{
	private RectangleShape rect;
	private float rotation = 0.0f;
	private float speed = 10.0f;
	
	public wall(FloatRect fr)
	{
		rect = new RectangleShape(new Vector2f(fr.width,fr.height));
		
		rect.setFillColor(Color.GREEN);
		
		rect.setOrigin(new Vector2f(fr.width/2,fr.height/2));
		
	}

	
	public void update(Time deltaTime) {
		// TODO Auto-generated method stub
		//rect.setRotation(rotation * deltaTime.asSeconds());
		//rotation += speed;
	}

	
	public void draw(RenderWindow window) {
		// TODO Auto-generated method stub
		window.draw(rect);
	}
	
	
	
}
