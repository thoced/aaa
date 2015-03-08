package CoreFx;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.ContactEdge;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import bilou.PhysicWorld;
import CorePlayer.PlayerManager;
import CoreTexturesManager.TexturesManager;

public class DoorFx extends BaseFx 
{
	// sprite de l'objet
	private Sprite spriteDoor;
	private Texture textureDoor;
	// body door
	private Body bodyDoor;
	// activation
	private boolean active = false;
	// isOpening
	private boolean isOpening = false;
	// isClosing
	private boolean isClosing = false;
	// isOpen
	private boolean isOpen = false;
	// isClose
	private boolean isClosed = true;
	// course
	private float course;
	// time open
	private float timeOpen;
	// speed
	private float speed;
	// position start (pour définir la position de départ)
	private Vec2 posStart;
	// position end
	private Vec2 posEnd;
	// timeStart
	private Time timeStart;
	// time addition
	private Time timeAdd = new Clock().restart();
	
	public DoorFx(Vector2f pos,float timeOpen, float course,float speed)
	{
		super();
		
		this.course = course;
		//
		this.speed = speed;
		//
		this.timeOpen = timeOpen;
		// chargement du fichier image
		textureDoor = TexturesManager.GetTextureByName("door01.png");
		spriteDoor = new Sprite(textureDoor);
		// on positionne l'origine
		spriteDoor.setOrigin(new Vector2f(textureDoor.getSize().x /2, textureDoor.getSize().y /2));
		// on calcul le body 
		BodyDef bdef = new BodyDef();
		bdef.active = true;
		bdef.bullet = false;
		bdef.fixedRotation = true;
		bdef.position = PhysicWorld.convertToM2VEC(pos);
		bdef.type = BodyType.KINEMATIC;
		// shape
		PolygonShape shape = new PolygonShape();
		Vector2i size = textureDoor.getSize();
		// creation du shape
		shape.setAsBox((size.x / PhysicWorld.getRatioPixelMeter())/2  , (size.y / PhysicWorld.getRatioPixelMeter())/2);
		// FixtureDef
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1f;
		fixtureDef.friction = 0f;
		fixtureDef.restitution = 0f;
		fixtureDef.shape = shape;
		// creation du body
		bodyDoor = PhysicWorld.getWorldPhysic().createBody(bdef);
		// créatoin du fixture
		bodyDoor.createFixture(fixtureDef);
		// position start
		this.posStart = bodyDoor.getPosition().clone();
		// positon end
		this.posEnd = this.posStart.clone();
		this.posEnd = this.posEnd.add(new Vec2(0,-3f));
		
	}
	
	
	
	public void update(Time deltaTime) 
	{
		// TODO Auto-generated method stub
		super.update(deltaTime);
		
		// on récupère le body actor
		Body actor = PlayerManager.getSmallRobot().getBody();
		
		// test de contact avec la porte
		boolean isContact = false;	
		// récupération des contacts
		ContactEdge edge = bodyDoor.getContactList();
		while(edge!=null)
		{
			if(edge.other == actor)
			{
				isContact = true; // il existe un contact avec l'actor
				break;
				
			}	
			
			edge = edge.next;
		}
		
		if(isContact && !this.isOpening) // il existe un contact
		{
			this.isClosing = false;
			this.isOpening = true;
			this.bodyDoor.setLinearVelocity(new Vec2(0,-1f * this.speed));
			this.timeStart = new Clock().restart();
			this.isClosed = false;
		}
		
		// si la porte est en train de s'ouvrir et que l'on arrive en fin de course
		if(this.isOpening && this.bodyDoor.getPosition().sub(this.posStart).length() >= this.course)
		{
			this.bodyDoor.setLinearVelocity(new Vec2(0,0));
			this.isOpening = false;
			this.isOpen = true;
			this.timeAdd = this.timeStart;
		}
		
		// si la porte est ouverte, on compute le timer
		if(this.isOpen)
		{
			this.timeAdd = Time.add(this.timeAdd, deltaTime);
			// si la porte est ouverte de plus de 3 secondes, on fait descendre la porte
			if(this.timeAdd.asSeconds() > this.timeOpen)
			{
				this.bodyDoor.setLinearVelocity(new Vec2(0,1f * this.speed));
				this.isOpen = false;
				this.isClosing = true;
			}
		}
		
		// si la porte est en train de se fermer et que l'on arrive en fin de course
		if(this.isClosing && this.bodyDoor.getPosition().sub(this.posEnd).length() >= this.course)
		{
			this.bodyDoor.setLinearVelocity(new Vec2(0,0));
			this.posStart = this.bodyDoor.getPosition().clone();
			this.isClosing = false;
			this.isClosed = true;
		}
	
	}
	
	public void draw(RenderTarget render, RenderStates state) {
		// TODO Auto-generated method stub
		super.draw(render, state);
		// on positionne l'image
		spriteDoor.setPosition(bodyDoor.getPosition().x * PhysicWorld.getRatioPixelMeter(),bodyDoor.getPosition().y * PhysicWorld.getRatioPixelMeter());
		// affichage
		render.draw(spriteDoor);
		
	}
	
}
