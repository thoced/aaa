package CoreEnnemy;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

public class TourelleTargetCallBack implements RayCastCallback
{
	// position du contact
	private Vec2 pos;
	// normal du contact
	private Vec2 normal;
	// body de contact
	private Body contact;
	// fraction
	private float fraction;
	
	/**
	 * @return the pos
	 */
	public Vec2 getPos() {
		return pos;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(Vec2 pos) {
		this.pos = pos;
	}

	/**
	 * @return the normal
	 */
	public Vec2 getNormal() {
		return normal;
	}

	/**
	 * @param normal the normal to set
	 */
	public void setNormal(Vec2 normal) {
		this.normal = normal;
	}

	/**
	 * @return the contact
	 */
	public Body getContact() {
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(Body contact) {
		this.contact = contact;
	}
	
	/**
	 * @return the fraction
	 */
	public float getFraction() {
		return fraction;
	}

	/**
	 * @param fraction the fraction to set
	 */
	public void setFraction(float fraction) {
		this.fraction = fraction;
	}

	
	public float reportFixture(Fixture fix, Vec2 pos, Vec2 normal, float fraction) 
	{
		// position de l'impact
		this.pos = pos;
		// normla de l'impact
		this.normal = normal;
		// body de contact
		this.contact = fix.getBody();
		// fraction
		this.fraction = fraction;
		
		return fraction;
	}

}
