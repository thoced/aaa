package CorePlayer;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

import bilou.Camera;
import bilou.ICoreBase;
import bilou.PhysicWorld;

public class RobotBase implements Drawable, ICoreBase
{

	// VIEW
		// class Sprite
	protected Sprite spritePlayer;
			// vecteur des FloatRect d'animation
	protected IntRect[] vectorAnim;
			// indice d'animation
	protected int indAnim = 0;
			// indicemax d'anim
	protected int indMaxAnim = 0;
			// Time Anim
	protected Time timeAnim = Time.ZERO;
	
	
	// est il sur le ground
		protected boolean isground = false;
		// est il selectionné
		protected boolean isSelected = false;
		// Vecteur de position
		protected Vector2f positionPlayer;
		// enum sens
		public enum SENS {PAUSE,GAUCHE,DROITE}; 
			// direction choisie (pour animation)
		protected SENS typeSens = SENS.PAUSE;
		// backup du SENS
		protected SENS backupSens = SENS.PAUSE;
		// body physic
		protected Body body;
			// bodydef
		protected BodyDef bodyDef;
			
		protected FixtureDef fixture;
			
		protected Fixture ff;
	
	// PHYSIQUE - CONTROL
		
		// bounds
		protected FloatRect bounds;
		// Vecteur velocité
		protected Vector2f velocity = Vector2f.ZERO;
		//  vitesse
		protected float speed = 256.0f;
		// acceleration
		protected float acceleration = 1.0f;
		// vecteur de direction
		protected Vector2f direction = Vector2f.ZERO;
		// vecteur du jump
		protected Vector2f jumpVector = new Vector2f(0,-1);
		
		// la touche space est elle enfoncée
		protected boolean isSpace = false;
		
		
		
	/**
		 * @return the spritePlayer
		 */
		public Sprite getSpritePlayer() {
			return spritePlayer;
		}

		/**
		 * @param spritePlayer the spritePlayer to set
		 */
		public void setSpritePlayer(Sprite spritePlayer) {
			this.spritePlayer = spritePlayer;
		}

		/**
		 * @return the vectorAnim
		 */
		public IntRect[] getVectorAnim() {
			return vectorAnim;
		}

		/**
		 * @param vectorAnim the vectorAnim to set
		 */
		public void setVectorAnim(IntRect[] vectorAnim) {
			this.vectorAnim = vectorAnim;
		}

		/**
		 * @return the indAnim
		 */
		public int getIndAnim() {
			return indAnim;
		}

		/**
		 * @param indAnim the indAnim to set
		 */
		public void setIndAnim(int indAnim) {
			this.indAnim = indAnim;
		}

		/**
		 * @return the indMaxAnim
		 */
		public int getIndMaxAnim() {
			return indMaxAnim;
		}

		/**
		 * @param indMaxAnim the indMaxAnim to set
		 */
		public void setIndMaxAnim(int indMaxAnim) {
			this.indMaxAnim = indMaxAnim;
		}

		/**
		 * @return the timeAnim
		 */
		public Time getTimeAnim() {
			return timeAnim;
		}

		/**
		 * @param timeAnim the timeAnim to set
		 */
		public void setTimeAnim(Time timeAnim) {
			this.timeAnim = timeAnim;
		}

		/**
		 * @return the bounds
		 */
		public FloatRect getBounds() {
			return bounds;
		}

		/**
		 * @param bounds the bounds to set
		 */
		public void setBounds(FloatRect bounds) {
			this.bounds = bounds;
		}

		/**
		 * @return the velocity
		 */
		public Vector2f getVelocity() {
			return velocity;
		}

		/**
		 * @param velocity the velocity to set
		 */
		public void setVelocity(Vector2f velocity) {
			this.velocity = velocity;
		}

		/**
		 * @return the speed
		 */
		public float getSpeed() {
			return speed;
		}

		/**
		 * @param speed the speed to set
		 */
		public void setSpeed(float speed) {
			this.speed = speed;
		}

		/**
		 * @return the acceleration
		 */
		public float getAcceleration() {
			return acceleration;
		}

		/**
		 * @param acceleration the acceleration to set
		 */
		public void setAcceleration(float acceleration) {
			this.acceleration = acceleration;
		}

		/**
		 * @return the direction
		 */
		public Vector2f getDirection() {
			return direction;
		}

		/**
		 * @param direction the direction to set
		 */
		public void setDirection(Vector2f direction) {
			this.direction = direction;
		}

		/**
		 * @return the jumpVector
		 */
		public Vector2f getJumpVector() {
			return jumpVector;
		}

		/**
		 * @param jumpVector the jumpVector to set
		 */
		public void setJumpVector(Vector2f jumpVector) {
			this.jumpVector = jumpVector;
		}

		/**
		 * @return the isSpace
		 */
		public boolean isSpace() {
			return isSpace;
		}

		/**
		 * @param isSpace the isSpace to set
		 */
		public void setSpace(boolean isSpace) {
			this.isSpace = isSpace;
		}

	/**
		 * @return the isground
		 */
		public boolean isIsground() {
			return isground;
		}

		/**
		 * @param isground the isground to set
		 */
		public void setIsground(boolean isground) {
			this.isground = isground;
		}

		/**
		 * @return the isSelected
		 */
		public boolean isSelected() {
			return isSelected;
		}

		/**
		 * @param isSelected the isSelected to set
		 */
		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;
		}

		/**
		 * @return the positionPlayer
		 */
		public Vector2f getPositionPlayer() {
			return positionPlayer;
		}
	
		/**
		 * @param positionPlayer the positionPlayer to set
		 */
		public void setPositionPlayer(Vector2f positionPlayer) {
			this.positionPlayer = positionPlayer;
		}

		/**
		 * @return the typeSens
		 */
		public SENS getTypeSens() {
			return typeSens;
		}

		/**
		 * @param typeSens the typeSens to set
		 */
		public void setTypeSens(SENS typeSens) {
			this.typeSens = typeSens;
		}

		/**
		 * @return the body
		 */
		public Body getBody() {
			return body;
		}

		/**
		 * @param body the body to set
		 */
		public void setBody(Body body) {
			this.body = body;
		}

		/**
		 * @return the bodyDef
		 */
		public BodyDef getBodyDef() {
			return bodyDef;
		}

		/**
		 * @param bodyDef the bodyDef to set
		 */
		public void setBodyDef(BodyDef bodyDef) {
			this.bodyDef = bodyDef;
		}

		/**
		 * @return the fixture
		 */
		public FixtureDef getFixture() {
			return fixture;
		}

		/**
		 * @param fixture the fixture to set
		 */
		public void setFixture(FixtureDef fixture) {
			this.fixture = fixture;
		}

		/**
		 * @return the ff
		 */
		public Fixture getFf() {
			return ff;
		}

		/**
		 * @param ff the ff to set
		 */
		public void setFf(Fixture ff) {
			this.ff = ff;
		}

	
	public void update(Time deltaTime) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		Vector2f pos = new Vector2f(body.getPosition().x,body.getPosition().y);
		spritePlayer.setRotation((float) ((body.getAngle() * 180) / Math.PI) % 360);
		
		Vector2f tpos = Vector2f.mul(pos, PhysicWorld.getRatioPixelMeter());
		
		spritePlayer.setPosition(tpos);
		
		spritePlayer.setOrigin(new Vector2f(32,32));
		
		// test update camera
		if(this.isSelected())
		Camera.SetCenter(spritePlayer.getPosition());
		
		// affichage
		render.draw(spritePlayer);
		
	}

}
