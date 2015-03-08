package Entities;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;


public class RobotBase extends EntitieBase 
{
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
	// body physic
	protected Body body;
		// bodydef
	protected BodyDef bodyDef;
		
	protected FixtureDef fixture;
		
	protected Fixture ff;
	
	
	public void Update(Time elapsedTime) {
		// TODO Auto-generated method stub
		
	}

	
	public void LoadContent() {
		// TODO Auto-generated method stub
		
	}

	
	public void SetEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

	
	public void UpdateMVC() {
		// TODO Auto-generated method stub
		
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
	 * @return the typeSens
	 */
	public SENS getTypeSens() {
		return typeSens;
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
	 * @param positionPlayer the positionPlayer to set
	 */
	public void setPositionPlayer(Vector2f positionPlayer) 
	{
		this.positionPlayer = positionPlayer;
		this.UpdateAttachMVC();
	}
	/**
	 * @return the positionPlayer
	 */
	public Vector2f getPositionPlayer() {
		return positionPlayer;
	}


	
	
	

}
