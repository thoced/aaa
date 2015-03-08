package bilou;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

import CorePlayer.RobotBase;


public class MyContactListener implements ContactListener {

	
	public void beginContact(Contact contact) 
	{
	// TODO Auto-generated method stub

			do
			{
				Object tempA = contact.m_fixtureA.m_body.getUserData();
				Object tempB = contact.m_fixtureB.m_body.getUserData();
					
				if(tempA != null && tempA.getClass().getSuperclass() == RobotBase.class)
				{
					((RobotBase)tempA).setIsground(true);
					
				}
				
	
				if(tempB != null && tempB.getClass().getSuperclass() == RobotBase.class)
				{
					((RobotBase)tempB).setIsground(true);
				}
				
			contact = contact.getNext();
			
			}while(contact != null);
			
	}

	
	public void endContact(Contact contact)
	{
		// TODO Auto-generated method stub
		do
		{
		
			Object tempA = contact.m_fixtureA.m_body.getUserData();
			Object tempB = contact.m_fixtureB.m_body.getUserData();
				
			if(tempA != null && tempA.getClass().getSuperclass() == RobotBase.class)
			{
				((RobotBase)tempA).setIsground(false);

			}
			

			if(tempB != null && tempB.getClass().getSuperclass() == RobotBase.class)
			{
				((RobotBase)tempB).setIsground(false);
			}
			
		contact = contact.getNext();
		
		}while(contact != null);
		
	}

	
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
