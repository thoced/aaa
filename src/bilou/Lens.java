package bilou;

import java.awt.Rectangle;
import java.awt.image.Raster;
import java.io.IOException;

import org.jsfml.graphics.BlendMode;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Shader;
import org.jsfml.graphics.ShaderSourceException;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;
import org.jsfml.graphics.Transform;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

public class Lens implements IGameBase 
{

	// Shader du lens
	private Shader shaderLens;
	// RenderStates
	private RenderStates renderStates;
	private RenderStates render2;
	// Texture lens
	private Texture textureLens;
	// Sprite 
	private Sprite spriteLens;
	
	
	public void Init(Framework parent) throws IOException, ShaderSourceException
	{
		
		// chargement du Shader
		shaderLens = new Shader();
		shaderLens.loadFromStream(Lens.class.getResourceAsStream("/Shaders/VertexShaderLens.vert"),Shader.Type.VERTEX);
		shaderLens.loadFromStream(Lens.class.getResourceAsStream("/Shaders/PixelShaderLens.frag"),Shader.Type.FRAGMENT);
		//RenderStates
		renderStates = new RenderStates(shaderLens);
		// chargement de la texture
		textureLens = new Texture();
		textureLens.loadFromStream(Lens.class.getResourceAsStream("/Textures/lens.png"));
		// Sprite
		spriteLens = new Sprite(textureLens);
		spriteLens.setPosition(new Vector2f(128,128));
		spriteLens.setOrigin(new Vector2f(textureLens.getSize().x/2,textureLens.getSize().y/2));
	//	spriteLens.setScale(new Vector2f(0.25f,0.25f));
		
		//render2 = new RenderStates(BlendMode.);
		
	}

	
	public void Update(Time deltaTime) 
	{
		// TODO Auto-generated method stub
		spriteLens.setPosition(Vector2f.add(spriteLens.getPosition(),new Vector2f(1,0)));
	}

	private Vector2f Vector2f(Vector2f position, Vector2f vector2f) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void Draw(RenderTexture backbuffer) 
	{
		// on récupère la texture du backbuffer;
		shaderLens.setParameter("backtexture",backbuffer.getTexture());
		//shaderLens.setParameter("backtexture", spriteLens.getTransform());
		// envoie de la texture lens
		shaderLens.setParameter("texture", Shader.CURRENT_TEXTURE);
		// appel au shader pour l'affichage
		backbuffer.draw(spriteLens);

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
