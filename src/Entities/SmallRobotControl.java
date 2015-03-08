package Entities;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import bilou.PhysicWorld;
import CoreManagerObstacle.ObstacleManager;
import CoreManagerObstacle.ObstacleResult;

public class SmallRobotControl extends RobotBase 
{
	// class metier (controle MVC)
	
	// bounds
	private FloatRect bounds;
	// Vecteur velocité
	private Vector2f velocity = Vector2f.ZERO;
	//  vitesse
	private float speed = 256.0f;
	// acceleration
	private float acceleration = 1.0f;
	// vecteur de direction
	private Vector2f direction = Vector2f.ZERO;
	// vecteur du jump
	private Vector2f jumpVector = new Vector2f(0,-1);
	
	// la touche space est elle enfoncée
	private boolean isSpace = false;
	
	
	/**
	 * @return the isground
	 */


	public SmallRobotControl()
	{
		// creatin du body jbox2d
		bodyDef = new BodyDef();
	//	bodyDef.position.set(new Vec2(0f,0.9f));
		bodyDef.position = new Vec2(1,-15);
		bodyDef.type = BodyType.DYNAMIC;
		//bodyDef.gravityScale = 0f;
		
		
		
		body = PhysicWorld.getWorldPhysic().createBody(bodyDef);
		body.setUserData(this);
		

		// initialisation du body
	
		//body.setFixedRotation(true);
		
		/*MassData md = new MassData();
		body.getMassData(md);
		md.center.set(0f,5f);
		body.setMassData(md);
		body.applyAngularImpulse(180f);*/

		//
		fixture = new FixtureDef();
		PolygonShape poly = new PolygonShape();
		poly.setAsBox(1, 1);
		
		
		fixture.shape = poly;
		fixture.density = 1.0f;
		fixture.friction = 0.5f;
		fixture.restitution = 0.0f;
		ff = body.createFixture(fixture);
			
		
		// specifie en premier que le petit robot est selectionné
		this.setSelected(true);
		
	
		
	}		
		
		
	
	
	
	public void Update(Time elapsedTime) 
	{
		//if(!body.isAwake())
		//	this.setIsground(true);
	
		// on applique l'animation qui ne bouge pas
		//this.typeSens = SENS.PAUSE;
		
		// on verifie l'etat du clavier
		
		// si le robot est selectionné
		if(this.isSelected)
		{
	
			if(  Keyboard.isKeyPressed(Keyboard.Key.D))
			{
				// si la touche D, la direction va vers la droite
				body.applyForce(new Vec2(256,0),body.getWorldCenter());
				
				
				// sens selectionné
				this.typeSens = SENS.DROITE;
				
			
				
			}else if( Keyboard.isKeyPressed(Keyboard.Key.Q))
			{
				// si la touche Q, la direction va vers la gauche
				body.applyForce(new Vec2(-256,0),body.getWorldCenter());
				
				// sens selectionné
				this.typeSens = SENS.GAUCHE;
							
			
			}
			
			if(!this.isSpace && this.isIsground() && Keyboard.isKeyPressed(Keyboard.Key.SPACE))
			{
				body.applyLinearImpulse(new Vec2(0,-72), body.getWorldCenter());
				this.setIsground(false);
				this.isSpace = true;
			}
			else if(!Keyboard.isKeyPressed(Keyboard.Key.SPACE))
				this.isSpace = false;
		
		}
		
		// on récupère le vecteur velocity
		if(body.getLinearVelocity().lengthSquared() == 0f)
			this.typeSens = SENS.PAUSE;
		
		
		// appel de l'appelMVC
		this.UpdateAttachMVC();
		
	}

	

	
	public void UpdateMVC() 
	{
		
		
	}

	
	public void LoadContent() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the positionPlayer
	 */
	public Vector2f getPositionPlayer() {
		return positionPlayer;
	}

	
	
	public void SetEvent(Event e)
	{
		
		// TODO Auto-generated method stub	
		if(e.type == Event.Type.KEY_RELEASED)
		{
			direction = Vector2f.ZERO;
		}
	}

	
	
}
