package bilou;

import javax.xml.parsers.DocumentBuilderFactory;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

public class Camera implements ICoreBase
{
	// View
	private static View view;
	// Constante
	public static Vector2f Right = 	new Vector2f(1,0);
	public static Vector2f Left = 	new Vector2f(-1,0);
	public static Vector2f Up = 	new Vector2f(0,1);
	public static Vector2f Down = 	new Vector2f(0,-1);
	public static Vector2f Zero = Vector2f.ZERO;
	// CurrentAdd
	private Vector2f currentAdd = Vector2f.ZERO;
	// Speed camera
	private float speed = 512.0f;
	// ZoomLevel camera
	private int zoomLevel = 1;
	// private parent
	private static Camera cam;
   

//Fill the win = 0;
	
	public static FloatRect GetBoundsVisible()
	{
		Vector2f  size = cam.getView().getSize();
		Vector2f centre = cam.getView().getCenter();
		Vector2f source = Vector2f.sub(centre, Vector2f.div(size,2));
		return  new FloatRect(source,size);
	}
	
	
	
	public Camera(RenderTexture window)
	{
		view = (View) window.getView();
		cam = this;
		//view = new View();
	}
	
	public void update(Time deltaTime) 
	{

		// Modification de la vue de la camera
		//Vector2f newcenter = Vector2f.add(view.getCenter(), Vector2f.mul(currentAdd, speed * deltaTime.asSeconds()));
		//view.setCenter(newcenter);
		
		
	}
	
	public static void SetCenter(Vector2f center)
	{
		cam.getView().setCenter(center);
	}
	
//Fill the win
	
	public static View getView()
	{
		return Camera.view;
	}

	public static void  setView(View view)
	{
		view = view;
	}
	
	public void Move(Vector2f move)
	{
		currentAdd = move;
	}


//Fill the win
	public void ZoomIn()
	{
		Vector2f size = view.getSize();
		size = Vector2f.div(size, 2);
		//view.setSize(size);
		view.zoom(2);
		this.zoomLevel ++;
	}
	public void ZoomOut()
	{
		Vector2f size = view.getSize();
		size = Vector2f.mul(size, 2);
		//view.setSize(size);
		view.zoom(0.5f);
		this.zoomLevel --;
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
