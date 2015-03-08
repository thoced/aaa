package bilou;

import java.io.IOException;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.ShaderSourceException;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

public class robot implements IGameBase
{

	private Texture textureRobot;
	
	private Sprite spriteRobot;
	
	
	public void Init(Framework parent) throws IOException,
			ShaderSourceException {
		// TODO Auto-generated method stub
		
		textureRobot = new Texture();
		textureRobot.loadFromStream(robot.class.getResourceAsStream("/Textures/robot.png"));
		
		spriteRobot = new Sprite(textureRobot);
		spriteRobot.setOrigin(new Vector2f(textureRobot.getSize().x /2 ,textureRobot.getSize().y/2));
		spriteRobot.setScale(0.5f, 0.5f);
		

	}

	
	public void Update(Time deltaTime) 
	{
		// TODO Auto-generated method stub

	}

	
	public void Draw(RenderTexture backbuffer) 
	{
		// TODO Auto-generated method stub
		spriteRobot.setPosition(new Vector2f(256,256));
		backbuffer.draw(spriteRobot);
	}

	
	public FloatRect GetGlobalBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public FloatRect GetLocalBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
