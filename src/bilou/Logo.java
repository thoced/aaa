package bilou;
import java.io.IOException;

import org.jsfml.graphics.BlendMode;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Shader;
import org.jsfml.graphics.ShaderSourceException;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;


public class Logo implements IGameBase 
{
	private Framework framework;
 
	private Texture logo;
	
	private Sprite logoSprite;
	
	private Sprite postEffect;
	
	private RenderTexture target;
	
	private BlendMode blend;
	
	private RenderStates states;
	
	// vitesse affichage
	
	private Shader shader;
	
	private float totalTime = 0.0f;
	
	private float time = 3.0f;
	
	
	
	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public Logo()
	{
		
	}
	
	public void Update(Time deltaTime) 
	{
		// TODO Auto-generated method stub
		
		if(totalTime < this.time)
		{
			shader.setParameter("deltaTime", totalTime);
			totalTime += deltaTime.asSeconds() * 0.25f; 
		}
		else
		{
			framework.DestroyGameBase(this);
		}
		
		

	}

	
	public void Draw(RenderTexture window) 
	{
		// TODO Auto-generated method stub
		//logoSprite.draw(target, states);
	
		target.clear();
		shader.setParameter("maTexture",Shader.CURRENT_TEXTURE);
		target.draw(logoSprite,states);
		target.display();
		window.draw(postEffect);
		
		
	}

	
	public FloatRect GetGlobalBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public FloatRect GetLocalBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void Init(Framework parent) 
	{
		
		framework = parent;
		// TODO Auto-generated method stub
		logo = new Texture();
		try 
		{
			logo.loadFromStream(Logo.class.getResourceAsStream("logo.png"));
			
			logoSprite = new Sprite(logo);
			
			logoSprite.setPosition(new Vector2f(0,0));
			
			logoSprite.setScale(0.25f, 0.25f);
			
			logoSprite.setOrigin(logo.getSize().x / 2, logo.getSize().y / 2);
			
			logoSprite.setPosition(512,384);
			
			target = new RenderTexture();
			
			try 
			{
				target.create(logo.getSize().x, logo.getSize().y);
				
				postEffect = new Sprite(target.getTexture());
				
			} catch (TextureCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		shader = new Shader();
		
		try 
		{
			shader.loadFromStream(Logo.class.getResourceAsStream("/Shaders/vertex.vert"),Shader.Type.VERTEX);
			shader.loadFromStream(Logo.class.getResourceAsStream("/Shaders/fragment.frag"),Shader.Type.FRAGMENT);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShaderSourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			framework.DestroyGameBase(this);
		}
		

		states = new RenderStates(shader);
		
		
		
		
	}

	

}
