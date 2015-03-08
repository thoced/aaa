package CoreGrapnel;

import org.jbox2d.common.Vec2;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;

import CoreManager.Manager;
import CoreManager.ManagerBase;
import bilou.ICoreBase;
import bilou.PhysicWorld;

public class ManagerGrapnel extends ManagerBase
{
	// position de départ
	private Vec2 positionStart;
	// rayCastGrapnel
	private rayCastGrapnel rayCast;
	
	
	
	/**
	 * @return the rayCast
	 */
	public rayCastGrapnel getRayCast() {
		return rayCast;
	}

	/**
	 * @param rayCast the rayCast to set
	 */
	public void setRayCast(rayCastGrapnel rayCast) {
		this.rayCast = rayCast;
	}

	public ManagerGrapnel(Vec2 posStart)
	{
		// copie de la position de départ
		this.positionStart = posStart;
		// instnace du raycast
		this.rayCast = new rayCastGrapnel();
	}
	
	
	public void draw(RenderTarget render, RenderStates state) 
	{
		// affichage du raycast
		this.rayCast.draw(render, state);
		
	}

	
	public void update(Time deltaTime) 
	{
		// on récupère la position de la souris
		Vector2i posMouse = Mouse.getPosition();
		// on transforme les coordonnées de la souris en coordonnées de monde
		Vector2f posWorld = Manager.getRenderTexture().mapPixelToCoords(posMouse);
		// on lance un rayon depuis la source et on test les intersection
		// on cré un vecteur de start et un vecteur de fin
		Vec2 vStart = this.positionStart;
		// on crée le vecteur direction (soustraction entre la souris et la source)
		Vec2 dir = PhysicWorld.convertToM2VEC(posWorld);
		dir = dir.sub(vStart);
		dir.normalize();
		// on projete pour obtenir le vecteur de destination
		dir = dir.mul(32.0f);
		// on lance un rayon
		PhysicWorld.getWorldPhysic().raycast(rayCast, vStart, vStart.add(dir));
		
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

}
