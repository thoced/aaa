package bilou;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import org.jsfml.graphics.BlendMode;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
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
import org.jsfml.graphics.View;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;

import CoreBackground.BackgroundDrawable;
import CoreBackground.BackgroundDrawable.TypeBackground;
import CoreDrawableCalqueManager.DrawableCalque;
import CoreDrawableCalqueManager.DrawableCalqueBackground;
import CoreDrawableCalqueManager.DrawableCalqueBase;
import CoreDrawableCalqueManager.DrawableCalqueDynamic;
import CoreDrawableCalqueManager.DrawableCalqueManager;
import CoreDrawableCalqueManager.DrawableCalquePhysic;
import CoreEnnemy.Tourelle;
import CoreEnnemy.TourelleBiCanon;
import CoreFx.DoorFx;
import CoreFx.LustreFx;
import CoreFx.ManagerFx;
import CoreManager.Manager;
import CoreManagerObstacle.ObstacleManager;
import CorePlayer.PlayerManager;
import CorePlayer.SmallRobot;
import CoreQuadTree.QuadTreeNode;
import CoreTexturesManager.TexturesManager;
import Entities.EntitiesManager;
import CoreLoader.LoaderMakeMap;
import CoreLoader.LoaderMap;
import CoreLoader.LoaderTiled;
import CoreLoader.LoaderTiledException;
import CoreLoader.TiledLayerImages;
import CoreLoader.TiledLayerObjects;
import CoreLoader.TiledLayerTiles;
import CoreLoader.TiledObjectBase;
import CoreLoader.TiledObjectPolyline;
import CoreLoader.TiledObjectRectangle;
import structure.Iunitbase;
import structure.wall;

public class Framework 
{
	// arrayUnits
	private ArrayList<IGameBase> arrayElements;
	// listes des élements à supprimer du framework
	private ArrayList<IGameBase> arrayDelete;
	// listes elements venant du quadtree
	private ArrayList<IGameBase> listeElements;
	// frameclock
	private Clock frameClock = new Clock();
	// Time
	private  Time totalTime;
	// RenderWindow JSFML (SFML)
	private RenderWindow window;
	// RenderTexture
	private RenderTexture renderText,renderText2,renderFinal;
	// Sprite posteffect
	private Sprite postEffect1,postEffect2,postEffectFinal;
	// RenderState
	private RenderStates rStateBackground,rStateForeGround;
	// Shader
	private Shader shader;
	// Camera
	private Camera camera;
	// QuadTree
	private QuadTreeNode quadtree;
	// Logo
	private Logo log;
	// LoaderMap
	private LoaderMap loader;
	// Manager d'Obstacle
	private ObstacleManager obstacleManager;
	// Textures manager
	private TexturesManager texturesManager;
	// Entities manager
	private EntitiesManager entitiesManager;
	//PlayerManager
	private PlayerManager playerManager;
	// Calques Manager
	private DrawableCalqueManager calquesManager;
	// PhysicWorld
	private PhysicWorld physic;
	// Fx Manager
	private ManagerFx fxManager;

	private DrawableMap dm,dm2;
	
	private Texture charlie;
	
	private Sprite charlieSprite;
	
	// Manager
	private Manager manager;
	
	// backgrond
	private BackgroundDrawable background;
	// fps
	private int fps = 0;
	private Time fpsTime = Time.ZERO;
	
	public Framework(RenderWindow w) throws TextureCreationException
	{
		// Window
		window = w;
		//physic
		physic = new PhysicWorld();
		// RenderTexture 01
		renderText = new RenderTexture();
		renderText.create(window.getSize().x, window.getSize().y);
		
		// manager 
		Manager.setRenderTexture(renderText);
	
		
	//	renderText.setView(window.getView());
		// creation du postEffect Sprite
		postEffect1 = new Sprite(renderText.getTexture());
		
		// RenderTexture 02
		renderText2 = new RenderTexture();
		renderText2.create(window.getSize().x, window.getSize().y);
	//	renderText2.setView(window.getView());
		postEffect2 = new Sprite(renderText2.getTexture());
		
		// RenderFinal
		renderFinal = new RenderTexture();
		renderFinal.create(window.getSize().x, window.getSize().y);
	//	renderFinal.setView(window.getView());
		postEffectFinal = new Sprite(renderFinal.getTexture());
		
		// RenderState
		shader = new Shader();
		rStateBackground = new RenderStates(BlendMode.NONE);
		rStateForeGround = new RenderStates(BlendMode.NONE);
		
		
		// camera
		camera = new Camera(renderText);
		// instance quadtree
		quadtree = new QuadTreeNode(1,new FloatRect(0,0,4096,4096));
		// arrayunits
		arrayElements = new ArrayList<IGameBase>();
		// arraydelete
		arrayDelete = new ArrayList<IGameBase>();
		//ListeElement
		listeElements = new ArrayList<IGameBase>();
		// (TEST) chargement d'un niveau
		loader = new LoaderMap();
		// instance du manager d'obstacle;
		obstacleManager = new ObstacleManager();
		// instance de Textures Manager
		texturesManager = new TexturesManager();
		// instance du manager d'entitées
		//entitiesManager = new EntitiesManager();
		// instance du calquesmanager
		calquesManager = new DrawableCalqueManager();
		// player manager
		playerManager = new PlayerManager();
		// fx manager
		fxManager = new ManagerFx();
		// Manager
		manager = new Manager();
		// ajout dans le manager
		Manager.setManagerFx(fxManager);
		
		// background
		background = new BackgroundDrawable(window.getView().getSize());
		
	}
	
	public void Update()
	{
		fps++;
		
		Time deltaTime = frameClock.restart();
		
		fpsTime = Time.add(fpsTime, deltaTime);
		
		if(fpsTime.asSeconds() > 1.0f)
		{
			System.out.println("fps : " + String.valueOf(fps));
			fps=0;
			fpsTime = Time.ZERO;
		}
		
		
		
		
		// update camera
		camera.update(deltaTime);
		
		// update du calquemanager
		calquesManager.update(deltaTime);
		
		// update du entities manager
		//entitiesManager.update(deltaTime);
		playerManager.update(deltaTime);
		
		// update du fxmanager
		fxManager.update(deltaTime);
		
		// suppression des élements
		arrayElements.removeAll(arrayDelete);
		
		// update du physic
		physic.update(deltaTime);
		
		// update du Background
		background.update(deltaTime);
		
		// update du manager
		manager.update(deltaTime);
		
				
	}
	
	public void Draw(RenderWindow window)
	{
		
		
		FloatRect zone = Camera.GetBoundsVisible();

		// on efface le backbuffer
		renderText.clear(Color.BLACK);

	
		renderText.setView(window.getDefaultView());
		renderText.draw(background);
		renderText.display();

		// appel a la methode draw de l'entites manager
				renderText.setView(camera.getView());
				//entitiesManager.draw(renderText,rStateForeGround);
				playerManager.draw(renderText, rStateForeGround);
				renderText.display();
		
		// appel a la methode draw du fx manager
		renderText.setView(camera.getView());
		//entitiesManager.draw(renderText,rStateForeGround);
		fxManager.draw(renderText, rStateBackground);
		renderText.display();
		
		
		
		// on affiche les drawable calques
		renderText.setView(camera.getView());
		calquesManager.draw(renderText,rStateBackground);
		renderText.display();
		
		
		
		// Manager
		renderText.setView(camera.getView());
		manager.draw(renderText, rStateBackground);
		renderText.display();
		
		
		
		// affichage dans la fenetre principale (écran)
		window.clear(new Color(3,32,48));
		
		// Affichage du Background qui ne bouge pas
		window.draw(postEffect1);
		window.display();
		
	}
	
	public void CatchEvent(Event event)
	{
		//if(event.type == Event.Type.MOUSE_MOVED)return;
		
		if(event.type == Event.Type.MOUSE_MOVED)
		{
			camera.Move(Camera.Zero);
			
			if(event.asMouseEvent().position.x > 1024-64)
				camera.Move(Camera.Right);
			else if(event.asMouseEvent().position.x < 64)
				camera.Move(Camera.Left);
			
			if(event.asMouseEvent().position.y > 768-64)
				camera.Move(Camera.Up);
			else if(event.asMouseEvent().position.y < 64)
				camera.Move(Camera.Down);
		
		}
		
		if(event.type == Event.Type.KEY_PRESSED)
		{
			if(event.asKeyEvent().key == Keyboard.Key.O)
			{
				camera.ZoomIn();
			}
			else if(event.asKeyEvent().key == Keyboard.Key.P)
			{
				camera.ZoomOut();
			}
			
			// changement de player
			if(event.asKeyEvent().key == Keyboard.Key.G)
			{
				entitiesManager.SwitchPlayer();
			}
		}
		
		// catch pour l'entities manager
				//entitiesManager.catchEvent(event);
	}
	
	public void DestroyGameBase(IGameBase base)
	{
		arrayDelete.add(base);
	}
	
	public void LoadContent()// appel a la methode draw de l'entites manager
	{
		
	
		ElementBase element = new ElementBase(new Vector2f(64,64),new Vector2f(128,128));
		ElementBase element2 = new ElementBase(new Vector2f(64,64),new Vector2f(192,128));
		ElementBase element3 = new ElementBase(new Vector2f(64,64),new Vector2f(2048,128));
		
		Random rand = new Random();
		
		
		
		// chargement de la map
		loader.LoadContent();
		// chargement des élementsz
		//arrayElements.addAll(loader.getListElement());
		for(IGameBase a : loader.getListElement())
			this.quadtree.InsertElement(a);
		
		
		
		// textures manager loadcontent
		texturesManager.loadContent();
		// entities manager loadcontent
		//entitiesManager.loadContent();
		// Création des robot
		
		
		
		//LoaderTiled tiled = new LoaderTiled();
		LoaderMakeMap tiled = new LoaderMakeMap();
		try 
		{
			// chargement de la map
			tiled.Load(LoaderTiled.class.getResourceAsStream("/Maps/mapbroyeur2.json"));
			// création d'une texture (tileset)
			//Texture text = new Texture();
			// chargement de la texture
			//text.loadFromStream(LoaderTiled.class.getResourceAsStream("/Textures/tilesetblavier.png"));
			
			try 
			{
				// creation de la drawablemap (background)
				dm = new DrawableMap();
				// creation de la drawable map (foreground)
				dm2 = new DrawableMap();
				
			} catch (ShaderSourceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			// création du drawable calque manager
			// -----------------------------------
			// -----------------------------------
			// -----------------------------------
			
			
			for(TiledLayerImages calque : tiled.getListLayersImages())
			{
				
				float posx = calque.getPosx();
				float posy = calque.getPosy();
				String nameCalque = calque.getName();
			
				// on ajoute dans le drawablemanager
				
				DrawableCalqueBase c = null;
				
				if(calque.getTypeCalque().equals("statique"))
				{
					// c'est un simple calque static
					Texture t = TexturesManager.GetTextureByName(nameCalque);
					c = new DrawableCalque(t, nameCalque, posx, posy);
				}
				else
				{
					if(calque.getTypeCalque().equals("dynamique"))
					{
						
						// on récupère les information dynamic
						float speed = calque.getSpeed();
						float targetX = calque.getTargetX();
						float targetY = calque.getTargetY();
						// on récupère les valeurs de rotation et sens_rotation
						boolean rotation = calque.isRotation();
						boolean sens_rotation = calque.isSensRotation();
						// c'est un calque dynamic
						Texture t = TexturesManager.GetTextureByName(nameCalque);
						c = new DrawableCalqueDynamic(t,nameCalque,posx,posy,speed,targetX,targetY,rotation,sens_rotation);
					}
					
					// calque physique ayant des propriété physique (ouverture du fichier .phy)
					if(calque.getTypeCalque().equals("physique"))
					{
						// on récupère les informations de masse
						float masse = calque.getMasse();
						// on récupère les informations de danger
						boolean danger = calque.isDanger();
						// on créer le nom de fichier .phy
						String[] temp = nameCalque.split("\\.");
						// on récupère la première partie et on ajoute l'extension phy
						String namePhy = temp[0] + ".phy";
						// on crée l'objet calque physique
						Texture t = TexturesManager.GetTextureByName(nameCalque);
						c = new DrawableCalquePhysic(t,nameCalque,posx,posy,masse,namePhy);
						
						
					}
					
					if(calque.getTypeCalque().equals("background"))
					{
						// récupération de la vitesse
						float speed = calque.getSpeed();
						// on crée la texture
						Texture t = TexturesManager.GetTextureByName(nameCalque);
						c = new DrawableCalqueBackground(t,nameCalque,posx,posy,speed);
					}
				}
				
				// on insert dans le calquesManager
				calquesManager.InsertCalque(c);
			}
			
			
			
			// reception des obstacle via les objet7
			TiledLayerObjects layerObject = tiled.getListLayersObjects().get(0);
			
			// chargement dans le manager d'obstacle
			if(layerObject != null)
			{
				// pour chaque objet dans le layer objects
				for(TiledObjectBase base : layerObject.getDataObjects())
				{
					
					if(base.getTypeObjects() == TiledObjectBase.Type.RECTANGLE)
					{
					// on ajoute les obstacle dans le m.anager d'obstacle
						TiledObjectRectangle r = (TiledObjectRectangle)base;
						obstacleManager.InsertObstacle(r.getX(),r.getY(), r.getWidth(), r.getHeight(),r.getType());
						
						
					}
					
					if(base.getTypeObjects() == TiledObjectBase.Type.POLYLINE)
					{
						// on ajoute un polyline comme obstacle
						TiledObjectPolyline p = (TiledObjectPolyline)base;
						
						obstacleManager.InsertObstacle(p.getListPoint(),p.getX(),p.getY(),p.getType());
						
					}
				}
				
				
			
			}
		} catch (LoaderTiledException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated catch block
		System.out.println(tiled);
		
		// Chargement du background
		background.ChooseBackground(TypeBackground.BACKGROUND01);
		
	//	arrayElements.add(lens);
		//arrayElements.add(rob);
		
		// test du fx
		LustreFx lustre = new LustreFx(new Vector2f(6300,36f),16f);
		Manager.getManagerFx().insertFx(lustre);
		
		lustre = new LustreFx(new Vector2f(6100f,36f),16f);
		Manager.getManagerFx().insertFx(lustre);
		
		DoorFx door = new DoorFx(new Vector2f(6000f,168f),3f,3f,12f);
		Manager.getManagerFx().insertFx(door);
		
		Tourelle tourelle = new Tourelle(new Vector2f(1100f,600f));
		Manager.getManagerFx().insertFx(tourelle);
		
		TourelleBiCanon tourelle2 = new TourelleBiCanon(new Vector2f(1000,400f));
		Manager.getManagerFx().insertFx(tourelle2);
		
		
		
		
		
		//lustre = new LustreFx(new Vector2f(648f,1128f),16f);
		//Manager.getManagerFx().insertFx(lustre);
	}
	
	public void ReleaseContent()
	{
		
	}
}
