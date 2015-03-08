package CorePlayer;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.Mouse.Button;

import bilou.PhysicWorld;
import CoreGrapnel.Grapnel;
import CoreGrapnel.ManagerGrapnel;
import CoreManager.Manager;
import CoreTexturesManager.TexturesManager;
import Entities.SmallRobotControl;
import Entities.RobotBase.SENS;

public class SmallRobot extends RobotBase 
{
	
		
	// PHYSIQUE - CONTROL
		
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
		// Grapnel
		private Grapnel grapnel;
		// clic gauche enfoncé
		private boolean clikLeft = false;
		// Manager Grapnel
		private ManagerGrapnel managerGrapnel;
		// private le grappin est lancé
		private boolean isGrapin = false;
		// isAlive
		private boolean isDead = false;
		
		
	
	public SmallRobot(Vector2f pos)
	{
		// creatin du body jbox2d
				bodyDef = new BodyDef();
			//	bodyDef.position.set(new Vec2(0f,0.9f));
				bodyDef.position = new Vec2(pos.x / PhysicWorld.getRatioPixelMeter(),pos.y / PhysicWorld.getRatioPixelMeter());
				bodyDef.type = BodyType.DYNAMIC;
				//bodyDef.gravityScale = 0f;
				
			
				body = PhysicWorld.getWorldPhysic().createBody(bodyDef);
				body.setUserData(this);
				body.setFixedRotation(true);
			
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
				fixture.friction = 1f;
				fixture.restitution = 0.0f;
				ff = body.createFixture(fixture);
					
				
				// specifie en premier que le petit robot est selectionné
				this.setSelected(true);
	}
		
	
	public void update(Time deltaTime) {
		
		// TODO Auto-generated method stub
		super.update(deltaTime);
		
		
		
		// PHYSIQUE ET CONTROL
		// si le robot est selectionné
				if(this.isSelected)
				{
					
					this.typeSens = SENS.PAUSE;
				
					// Grapnel
					if(!this.clikLeft && Mouse.isButtonPressed(Button.LEFT))
					{
						// on appuye sur le clic gauche, on place la varaibleclic gauche à true
						this.clikLeft = true;
						if(managerGrapnel == null)
							managerGrapnel = new ManagerGrapnel(this.body.getPosition());
						// ajout du manager grapnel dans le manager general
						Manager.getListManager().add(managerGrapnel);
						// modification du SlowMotion
						PhysicWorld.setSlowMotion(true);
						
						
					}
					else if(this.clikLeft &&  !Mouse.isButtonPressed(Button.LEFT))
					{
						// le clik gauche n'est plus enfoncé
						this.clikLeft = false;
						
						if(this.managerGrapnel != null)
						{
							// on récupère le point de contact
							Vec2 pointContact = this.managerGrapnel.getRayCast().getPointContact();
							// on récupère le body contact
							Body bodyContact = this.managerGrapnel.getRayCast().getBodyContact();
							// on détruit le grapnel
							if(grapnel != null)
							{
								grapnel.destroyGrapnel();
							}
							// on lance le grapnel
							grapnel = new Grapnel(body,bodyContact,pointContact,2);
							Manager.getListManager().remove(this.managerGrapnel);
							// modification du slowmotion
							PhysicWorld.setSlowMotion(false);
							// on précise que le grappin est lancé
							this.isGrapin = true;
							
						}
					}
					
					if(Mouse.isButtonPressed(Button.RIGHT))
					{
						// on détruit le grapnel
						if(grapnel != null)
						{
							grapnel.destroyGrapnel();
							// on précise que le grappin est annulé
							this.isGrapin = false;
						}
					}
	
					if((this.isGrapin && this.isground) || (!this.isGrapin) )
					{
						if(  Keyboard.isKeyPressed(Keyboard.Key.D))
						{
							// si la touche D, la direction va vers la droite
							body.applyForce(new Vec2(256,0),body.getWorldCenter());
			
							// sens selectionné
							this.typeSens = SENS.DROITE;
							// si le sens est différent, on possitionne les indice d'animation
							if(this.backupSens != this.typeSens)
								indAnim = 0;
							// on sauvegarde le se96ns
							this.backupSens = this.typeSens;
						
		
						
						}else if( Keyboard.isKeyPressed(Keyboard.Key.Q))
						{
							// si la touche Q, la direction va vers la gauche
							body.applyForce(new Vec2(-256,0),body.getWorldCenter());
							
							// sens selectionné
							this.typeSens = SENS.GAUCHE;
							// si le sens est différent, on possitionne les indice d'animation
							if(this.backupSens != this.typeSens)
								indAnim = 24;
							
							// on sauvegarde le sens
							this.backupSens = this.typeSens;

						}
					}
					
					// si le grappin est activé et que le joueur saute
					if(!this.isSpace && this.isGrapin && Keyboard.isKeyPressed(Keyboard.Key.SPACE))
					{
						body.applyLinearImpulse(new Vec2(0,-48), body.getWorldCenter());
						this.isSpace = true;
					}
					else if(!this.isSpace && this.isground && Keyboard.isKeyPressed(Keyboard.Key.SPACE))
					{
						
						body.applyLinearImpulse(new Vec2(0,-72), body.getWorldCenter());
						this.isground = false;
						this.isSpace = true;
					}
					else if(!Keyboard.isKeyPressed(Keyboard.Key.SPACE))
						this.isSpace = false;
				
				}
				
				// on récupère le vecteur velocity
				if(body.getLinearVelocity().lengthSquared() == 0f)
					this.typeSens = SENS.PAUSE;
		
	
				// animaiton de pause
				if(this.typeSens == SENS.PAUSE)
				{
					if(this.backupSens == SENS.DROITE)
					{
						if(timeAnim.asSeconds() > 1f/24f)
						{
							indAnim++;
							timeAnim = Time.ZERO;
							
							if(indAnim < 48)
								indAnim = 48;
							
							if(indAnim > 57)
							{
								indAnim = 48;
							}
						}
					}
					else if(this.backupSens == SENS.GAUCHE)
					{
						if(timeAnim.asSeconds() > 1f/24f)
						{
							indAnim++;
							timeAnim = Time.ZERO;
							
							if(indAnim < 60)
								indAnim = 60;
							
							if(indAnim > 69)
							{
								indAnim = 60;
							}
						}
					}
				}
				
		// VIEW ET ANIMATION
				
				if(this.typeSens == RobotBase.SENS.DROITE)
				{
					if(timeAnim.asSeconds() > 1f/24f)
					{
						
						
						indAnim++;
						timeAnim = Time.ZERO;
						if(indAnim > 17)
						{
							indAnim = 0;
						}
					}
				}
				else if(this.typeSens == RobotBase.SENS.GAUCHE)
				{
					if(timeAnim.asSeconds() > 1f/24f)
					{
						indAnim++;
						timeAnim = Time.ZERO;
						if(indAnim > 41)
						{
							indAnim = 24;
						}
					}
				}
				
				// le perso meurt
				if(this.isDead && this.typeSens == RobotBase.SENS.DROITE)
				{
					if(timeAnim.asSeconds() > 1f/24f)
					{
						indAnim++;
						timeAnim = Time.ZERO;
						if(indAnim > 113)
						{
							indAnim = 96;
						}
					}
				}
				
				// le perso meurt
				if(this.isDead && this.typeSens == RobotBase.SENS.GAUCHE)
				{
					if(timeAnim.asSeconds() > 1f/24f)
					{
						indAnim++;
						timeAnim = Time.ZERO;
						if(indAnim > 140)
						{
							indAnim = 120;
						}
					}
				}
				
		// update de l'animation
		spritePlayer.setTextureRect(vectorAnim[indAnim]);
		// choix de l'anim
		timeAnim = Time.add(deltaTime, timeAnim);
		
		// update grapnel
		if(grapnel != null)
			grapnel.update(deltaTime);
		
		
	}
	
	public void searchAttachGrapnel()
	{
		
	
	}

	/* (non-Javadoc)
	 * @see CorePlayer.RobotBase#loadContent()
	 */
	
	public void loadContent()
	{
		// TODO Auto-generated method stub
		super.loadContent();
		
		spritePlayer = new Sprite(TexturesManager.GetTextureByName("playerSmallRobot"));
		spritePlayer.setTextureRect(new IntRect(0,0,64,64));
		
		// taille de l'image
		Vector2i size = TexturesManager.GetTextureByName("playerSmallRobot").getSize();
		
		// initialisation du vecteur d'animation
		vectorAnim = new IntRect[144]; // 12 étant le nombre d'animation pour le player
		// on crée les floatrect
		int x = 0;
		int y = 0;
		for(int i=0;i<vectorAnim.length;i++)
		{
			vectorAnim[i] = new IntRect(x,y,64,64);
			x+=64;
			if(x>=size.x - 64)
			{
				x=0;
				y+=64;
				if(y >= size.y-64)
				{
					break;
				}
			}
			
		}
		
		// on spécifie l'indice maximal de l'animation
		indMaxAnim = vectorAnim.length;
	}

	/* (non-Javadoc)
	 * @see CorePlayer.RobotBase#draw(org.jsfml.graphics.RenderTarget, org.jsfml.graphics.RenderStates)
	 */
	
	public void draw(RenderTarget render, RenderStates state) {
		// TODO Auto-generated method stub
		super.draw(render, state);
		
		// render du grapnel
		if(grapnel != null)
			grapnel.draw(render, state);
	}
	
	
	
	
	
}
