package CorePlayer;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.system.Time;
import org.jsfml.window.event.Event;

import bilou.ICoreBase;

public class PlayerManager implements Drawable,ICoreBase 
{

	private static RobotBase smallRobot;
	
	private static RobotBase bigRobot;
	
	
	
	/**
	 * @return the smallRobot
	 */
	public static RobotBase getSmallRobot() {
		return smallRobot;
	}

	/**
	 * @param smallRobot the smallRobot to set
	 */
	public static void setSmallRobot(RobotBase smallRobot) {
		PlayerManager.smallRobot = smallRobot;
	}

	/**
	 * @return the bigRobot
	 */
	public static RobotBase getBigRobot() {
		return bigRobot;
	}

	/**
	 * @param bigRobot the bigRobot to set
	 */
	public static void setBigRobot(RobotBase bigRobot) {
		PlayerManager.bigRobot = bigRobot;
	}

	public static void createSmallRobot(RobotBase base)
	{
		smallRobot = base;
		// load coent
		smallRobot.loadContent();
	}
	
	public static void createBigRobot(RobotBase base)
	{
		bigRobot = base;
		// load content
		bigRobot.loadContent();
	}

	public void update(Time deltaTime) 
	{
		// TODO Auto-generated method stub
		if(this.smallRobot != null)
			this.smallRobot.update(deltaTime);
		
	}

	
	public void draw(RenderTarget render, RenderStates state) 
	{
		// TODO Auto-generated method stub
		if(this.smallRobot != null)
		render.draw(this.smallRobot,state);
		//render.draw(this.bigRobot,state);
		
	}

	

	
	public void loadContent()
	{
	
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
