package CoreEnnemy;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Time;
import org.jsfml.window.event.Event;

import bilou.ICoreBase;
import bilou.PhysicWorld;

public class FireBall implements Drawable,ICoreBase
{
	// sprite de la ball de feu
	private Sprite spriteBall;
	// corps de la ball
	// position
	private Vec2 pos;
	private Body   bodyBall;
	// vecteur de direction
	private Vec2  velocity;
	// time life
	private Time timeLife = Time.ZERO;
	// private parent
	private TourelleBiCanon parent;
	
	
	
	public FireBall(TourelleBiCanon parent,Sprite spriteBall,Vec2 pos,Vec2 velocity) {
		super();
		
		this.spriteBall = spriteBall;
		
		this.velocity = velocity;
		
		this.pos = pos;
		
		this.parent = parent;
		
		// on crée le body
		BodyDef def = new BodyDef();
		def.active = true;
		def.bullet = false;
		def.type = BodyType.DYNAMIC;
		def.gravityScale = 0.0f;
		def.position = this.pos;
		// création du shape
		CircleShape shape = new CircleShape();
		shape.m_radius = 0.5f;
		// on crée le body
		this.bodyBall = PhysicWorld.getWorldPhysic().createBody(def);
		// on crée le fixture def
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 1f;
		fixtureDef.restitution = 1f;
		fixtureDef.shape = shape;
		// fixture
		this.bodyBall.createFixture(fixtureDef);
		
		// on pulse la ball
		this.bodyBall.setLinearVelocity(this.velocity);
	}

	

	
	public void update(Time deltaTime) 
	{
		this.timeLife = Time.add(this.timeLife, deltaTime);
		// si la vie dépasse 
		if(this.timeLife.asSeconds() > 5f)
		{
			// on détruit la boule
			this.parent.destroyBall(this);
			PhysicWorld.getWorldPhysic().destroyBody(this.bodyBall);
		}
		
	}
	
	

	
	public void loadContent() {
		// TODO Auto-generated method stub
		
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

	
	public void draw(RenderTarget render, RenderStates state) 
	{
		// affichage de la ball
		this.spriteBall.setPosition(PhysicWorld.convertToPixelsVEC(this.bodyBall.getPosition()));
		render.draw(this.spriteBall);
		
	}

}
