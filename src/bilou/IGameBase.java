package bilou;

import java.io.IOException;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.ShaderSourceException;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Time;

public abstract interface IGameBase 
{

	public abstract  void Init(Framework parent) throws IOException, ShaderSourceException;
	
	public abstract void Update(Time deltaTime);
	
	public abstract void Draw(RenderTexture backbuffer);
	
	public abstract FloatRect GetGlobalBounds();
	
	public abstract FloatRect GetLocalBounds();

}
