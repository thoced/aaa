package CoreFx;


import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jsfml.graphics.BlendMode;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.Vertex;
import org.jsfml.graphics.VertexArray;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

import CoreTexturesManager.TexturesManager;
import bilou.PhysicWorld;

public class LustreFx extends BaseFx 
{
	// list
	private List<Body> listNodes;
	// Position
	private Vector2f position;
	// body point fixe de la lampe
	private Body bodyPointFix;
	// body de la lampe
	private Body bodyLampe;
	// nb de nodes
	private int nbNode = 1;
	// texture de la partie fix de la lampe
	private Texture textureFix;
	// texture de la partie mobile physique
	private Texture textureMobile;
	// Texture du cone de lumiere
	private Texture textureCone;
	// sprite fix
	private Sprite spriteFix;
	// sprite mobile
	private Sprite spriteMobile;
	// sprite de cone de lumière
	private Sprite spriteCone;
	// vertexarray
	private VertexArray vectors;
	// diffsize
	private Vector2f diffsize;
	// private flaot size
	private float size;
	// RenderState pour l'abas jour
	private RenderStates stateAbajour;
	// RenderState pour le cone de lumière
	private RenderStates stateCone;
	// valeur diff
	private Vector2f off = new Vector2f(2,0);
	
	
	public LustreFx(Vector2f pos,float size)
	{
		this.size = size;
		// on inscrit la position
		this.position = pos;
		// on crée le body fix
		BodyDef def = new BodyDef();
		def.active = true;
		def.bullet = false;
		def.fixedRotation = false;
		def.type = BodyType.STATIC;
		def.position = PhysicWorld.convertToM2VEC(this.position);
		// creation du shape
		CircleShape circle = new CircleShape();
		circle.m_radius = 0.1f;
		// création du body
		bodyPointFix = PhysicWorld.getWorldPhysic().createBody(def);
		// creation du fixture def
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		// creation du fixture
		bodyPointFix.createFixture(fixtureDef);
		
		// creation des 4 nodes
		listNodes = new ArrayList<Body>();
		// ajout du bodypoint fix
		this.listNodes.add(bodyPointFix);
		
		// diff size
		diffsize = new Vector2f(0,size / this.nbNode);
		diffsize = new Vector2f(0,48f);
		// création des position
		Vector2f posNode = this.position;
		
		Body bn = this.bodyPointFix;
		
		for(int i=0;i<this.nbNode;i++)
		{
			posNode = Vector2f.add(posNode, diffsize);
			// création du node
			bn = this.createNode(posNode,bn);
			// ajout dans la liste
			this.listNodes.add(bn);
		}
	
	
		// creation du dernier node contenant la texture 
		BodyDef bdef = new BodyDef();
		bdef.position = PhysicWorld.convertToM2VEC(Vector2f.add(posNode, diffsize));
		bdef.type = BodyType.DYNAMIC;
		bdef.active = true;
		bdef.bullet = false;
		// creation du shape
		PolygonShape poly = new PolygonShape();
		poly.setAsBox(1f, 1f);
		// creation du body
		bodyLampe = PhysicWorld.getWorldPhysic().createBody(bdef);
		// création du fixturedef
		FixtureDef fd = new FixtureDef();
		fd.shape = poly;
		fd.friction = 1f;
		fd.restitution = 0.5f;
		fd.density = 1.0f;
		// crétoin du fixture
		bodyLampe.createFixture(fd);


		// creation de la dernière jointure
		DistanceJointDef distanceDef = new DistanceJointDef();
		distanceDef.initialize(bn, bodyLampe, bn.getWorldCenter(),bodyLampe.getWorldPoint(new Vec2(0f,-0.5f)));
		distanceDef.collideConnected = false;
		distanceDef.frequencyHz = 10f;
		distanceDef.dampingRatio = 10f;
		distanceDef.length = size / PhysicWorld.getRatioPixelMeter();
		// creation de la jointe
		PhysicWorld.getWorldPhysic().createJoint(distanceDef);
		
		// ajout du bodyLampe dans la liste
		//this.listNodes.add(bodyLampe);
		
		// création des texture
		// chargement de la texture du lustre fix
		textureFix = TexturesManager.GetTextureByName("lustrefix.png");
		// chargement de la texture de la partie mobile
		textureMobile = TexturesManager.GetTextureByName("lustremobil.png");
		// chargement de la texture du cone de lumière
		textureCone = TexturesManager.GetTextureByName("lumiere_lamp06.png");
		// création des sprites
		spriteFix = new Sprite(textureFix);
		spriteFix.setPosition(this.position);
		spriteMobile = new Sprite(textureMobile);
		spriteMobile.setOrigin(textureMobile.getSize().x /2, 0f);
		spriteCone = new Sprite(textureCone);
		//spriteCone.setOrigin(textureCone.getSize().x/2, 0f);
		spriteCone.setOrigin(textureCone.getSize().x/2, textureCone.getSize().y/2);
	
		// inbstance du vertexArray
		vectors = new VertexArray(PrimitiveType.LINE_STRIP);
		
		// création des renderstates
		stateAbajour = new RenderStates(BlendMode.NONE);
		stateCone = new RenderStates(BlendMode.ADD);
	
	}
	
	private Body createNode(Vector2f pos,Body previousBody)
	{
		// crétion du body def
		BodyDef def = new BodyDef();
		def.position = PhysicWorld.convertToM2VEC(pos);
		def.active = true;
		def.type = BodyType.DYNAMIC;
		def.fixedRotation = false;
		
		// création du shape
		CircleShape circle = new CircleShape();
		circle.m_radius = 0.1f;
		// body
		Body body = PhysicWorld.getWorldPhysic().createBody(def);
		// FixtureDef
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.friction = 1.0f;
		fixtureDef.density = 1.0f;

		// fixture
		body.createFixture(fixtureDef);
		body.setFixedRotation(false);
		// on crée la jointure avec l'ancien body
		DistanceJointDef distanceDef = new DistanceJointDef();
		distanceDef.initialize(previousBody, body, previousBody.getWorldCenter(), body.getWorldCenter());
		distanceDef.collideConnected = false;
		distanceDef.frequencyHz = 10f ;
		distanceDef.dampingRatio = 10f;
		distanceDef.length = size / PhysicWorld.getRatioPixelMeter();
		// création de la jointure
		PhysicWorld.getWorldPhysic().createJoint(distanceDef);
		// retour du body
		return body;
	}
	
	private VertexArray createSegment(VertexArray in,Vector2f posStart,Vector2f posEnd)
	{
		// a partir des valeurs on créer deux valeur aux extrémitées
		Vector2f a1,a2,b1,b2;
	
		return null;
	}

	/* (non-Javadoc)
	 * @see CoreFx.BaseFx#draw(org.jsfml.graphics.RenderTarget, org.jsfml.graphics.RenderStates)
	 */
	
	public void draw(RenderTarget render, RenderStates state)
	{
		// TODO Auto-generated method stub
		super.draw(render, state);
		
		// affichage de la texture du cone de lumière
		spriteCone.draw(render, stateCone);	
		// affichage du texture fix
	//	spriteMobile.draw(render, stateAbajour);
		
		vectors.clear();
		// dessin du fil
		for(Body b : this.listNodes)
		{
			Vertex v = new Vertex(new Vector2f(b.getPosition().x * PhysicWorld.getRatioPixelMeter(),b.getPosition().y * PhysicWorld.getRatioPixelMeter()),new Color(128,128,128));
			// ajout dans le vertexarray
			vectors.add(v);
		}
		
		// ajout du dernier vertex 
		Vertex v = new Vertex(new Vector2f(bodyLampe.getWorldCenter().x * PhysicWorld.getRatioPixelMeter(),bodyLampe.getWorldCenter().y * PhysicWorld.getRatioPixelMeter()),new Color(128,128,128));
				// ajout dans le vertexarray
		vectors.add(v);
		
		render.draw(vectors,state);
	}

	/* (non-Javadoc)
	 * @see CoreFx.BaseFx#update(org.jsfml.system.Time)
	 */
	
	public void update(Time deltaTime) 
	{
		// TODO Auto-generated method stub
		super.update(deltaTime);
		
		spriteMobile.setRotation((float)((bodyLampe.getAngle() * 180f) / Math.PI) % 360f);
		spriteMobile.setPosition(PhysicWorld.convertToPixels(bodyLampe.getPosition()));
		
		spriteCone.setRotation((float)((bodyLampe.getAngle() * 180f) / Math.PI) % 360f);
		spriteCone.setPosition(PhysicWorld.convertToPixels(bodyLampe.getPosition()));
		
	}

}


