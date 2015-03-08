package CoreDrawableCalqueManager;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

import bilou.Camera;
import bilou.PhysicWorld;
import CoreTexturesManager.TexturesManager;

public class DrawableCalquePhysic extends DrawableCalqueBase 
{
	// liste des points
	private List<Vec2> listPoints;
	// BOdy
	private Body body;
	
	public DrawableCalquePhysic(Texture text, String name, float posx,
			float posy,float masse,String nameFilePhy) throws IOException 
{
		super(text, name, posx, posy);
		// TODO Auto-generated constructor stub
		
		// on ouvre le fichier calque
		// on crée le chemin complet
		InputStream stream = DrawableCalquePhysic.class.getResourceAsStream("/Textures/" + nameFilePhy);
		byte[] buffer = new byte[stream.available()];
		stream.read(buffer);
		String bufferPhy = new String(buffer);
		// on récupère chaque ligne du fichier
		String[] lines = bufferPhy.split("\r");
		// pour chaque ligne on créer le point
		this.listPoints = new ArrayList<Vec2>();
		
		for(String line : lines)
		{
			// pour chaque ligne on récupère les coordonnées x et y
			String[] xy = line.split(",");
			float x = Float.parseFloat(xy[0]);
			float y = Float.parseFloat(xy[1]);
			// pour chaque point on ajoute l'offset de l'image
			// pour chaque point on transforme en repère mètre (box2d)
			float bx = x / PhysicWorld.getRatioPixelMeter();
			float by = y / PhysicWorld.getRatioPixelMeter();
			Vec2 p = new Vec2(bx,by);
			this.listPoints.add(p);
		}
		
		// création de l'objet physique
		BodyDef bDef = new BodyDef();
		bDef.active = true;
		bDef.type = BodyType.DYNAMIC;
		bDef.position = new Vec2((posx + text.getSize().x /2) / PhysicWorld.getRatioPixelMeter(),(posy + text.getSize().y /2) / PhysicWorld.getRatioPixelMeter());
		bDef.bullet = false;
	//	bDef.gravityScale = 0f;
		
		
		// creation du body
		this.body = PhysicWorld.getWorldPhysic().createBody(bDef);
		this.body.setUserData("ground");
		this.body.setFixedRotation(false);
		MassData md = new MassData();
		md.mass = masse;
		this.body.setMassData(md);
		// création du shape
		ChainShape cs = new ChainShape();
		
		PolygonShape ps = new PolygonShape();
		Vec2[] vectors = new Vec2[this.listPoints.size()];
		this.listPoints.toArray(vectors);
		cs.createLoop(vectors,vectors.length);
		
		ps.set(vectors, vectors.length);
		
		
		// on crée la fixture
		FixtureDef fixture = new FixtureDef();
		fixture.shape = ps;
		fixture.friction = 0.9f;
		fixture.restitution = 0.0f;
		fixture.density = 0.2f;
		
		
		// on applique le tout
		this.body.createFixture(fixture);
		
		// modification de l'origine de la texture
		this.imageCalque.setOrigin(new Vector2f(this.imageCalque.getTexture().getSize().x /2,this.imageCalque.getTexture().getSize().y /2));

	}

	

	
	public void Update(Time deltaTime) 
	{
		// TODO Auto-generated method stub
		Vector2f v = new Vector2f(this.body.getPosition().x * PhysicWorld.getRatioPixelMeter(),this.body.getPosition().y * PhysicWorld.getRatioPixelMeter());
		this.imageCalque.setPosition(v);
		this.imageCalque.setRotation((float)((this.body.getAngle() * 180) / Math.PI) % 360);
		
		
	}

	
	public void draw(RenderTarget render, RenderStates state) {
		// TODO Auto-generated method stub
				FloatRect result = Camera.GetBoundsVisible().intersection(this.imageCalque.getGlobalBounds());
				if(result!=null)
					render.draw(imageCalque);
		
	}

}
