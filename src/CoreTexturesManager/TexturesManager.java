package CoreTexturesManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Hashtable;

import javax.swing.JOptionPane;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;
import org.jsfml.system.Time;
import org.jsfml.window.event.Event;

import bilou.ICoreBase;

public class TexturesManager implements ICoreBase
{
	// hash des textures
	private static Hashtable<String,Texture> hashTextures;
	// Texture blanck pour éviter le plantage
	private static Texture blankTexture = null;
	
	// manager - permet a la methode static d'appeller une methode de l'objet
	private static TexturesManager manager;
	
	
	public TexturesManager()
	{
		// instance du hashtextures
		hashTextures = new Hashtable<String,Texture>();
		
		manager = this;
	}
	
	// methode static de récupération d'un objet texture sur base du nom
	public static Texture GetTextureByName(String name)
	{
		// le nom de la texture n'existe pas, on la charge
		if(!hashTextures.containsKey(name))
			manager.LoadTexture(name);
		
		// la nom de la texture n'existe pas, on recharge la methode loadcontent
		if(!hashTextures.containsKey(name))
			manager.loadContent();
		
		// si la texture n'existe toujours pas on retourne la texture blank
		if(!hashTextures.containsKey(name))
			return blankTexture;
		
		return hashTextures.get(name);
	}
	
	
	
	private void LoadTexture(String name) 
	{
		// TODO Auto-generated method stub
		try 
		{
			Texture text = new Texture();
			text.loadFromStream(TexturesManager.class.getResourceAsStream("/Textures/" + name));
			//String userdir = System.getProperty("user.dir");
			//File f = new File(userdir + "/bin/Textures/" + name);
			//JOptionPane.showMessageDialog(null, userdir);
			//text.loadFromFile(f.toPath());
			hashTextures.put(name, text);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void update(Time deltaTime) {
		// TODO Auto-generated method stub
		
	}


	
	public void loadContent() 
	{
		
		try
		{
			// chargement des texture
			if(blankTexture == null)
			{
				// création de la texture blank
				blankTexture = new Texture();
				blankTexture.create(32, 32);
				
			}
			
			// création de la texture players
			if(!hashTextures.containsKey("playerSmallRobot")) // si celle-ci n'existe pas
			{
				Texture text01 = new Texture();
				text01.loadFromStream(TexturesManager.class.getResourceAsStream("/Textures/ANIM_smallbot_run_still_jump_death.png"));
				// ajout dans le hash textures
				hashTextures.put("playerSmallRobot", text01);
			}
			
			// création de la texture players
			if(!hashTextures.containsKey("playerBigRobot")) // si celle-ci n'existe pas
			{
				Texture text02 = new Texture();
				text02.loadFromStream(TexturesManager.class.getResourceAsStream("/Textures/robotpattesBALLBIG.png"));
							// ajout dans le hash textures
				hashTextures.put("playerBigRobot", text02);
			}
						
			
		} catch (IOException | TextureCreationException e) {
			
			e.printStackTrace();
		}
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
