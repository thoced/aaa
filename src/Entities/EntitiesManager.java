package Entities;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

import bilou.Camera;
import bilou.ICoreBase;

public class EntitiesManager implements ICoreBase,Drawable
{
	// small robot
	private static SmallRobotView playerSmallRobot;
	// big robot
	private static BigRobotView playerBigRobot;
	
	// reférence vers le robot selectioné
	private EntitieBase playerSelected;
	
	
	public EntitiesManager()
	{
		
	}
	
	
	
	/**
	 * @return the playerBigRobot
	 */
	public static BigRobotView getPlayerBigRobot() {
		return playerBigRobot;
	}



	/**
	 * @param playerBigRobot the playerBigRobot to set
	 */
	public static void setPlayerBigRobot(BigRobotView playerBigRobot) {
		EntitiesManager.playerBigRobot = playerBigRobot;
	}



	/**
	 * @return the playerSmallRobot
	 */
	public static SmallRobotView getPlayerSmallRobot() {
		return playerSmallRobot;
	}



	/**
	 * @param playerSmallRobot the playerSmallRobot to set
	 */
	public static void setPlayerSmallRobot(SmallRobotView playerSmallRobot) {
		EntitiesManager.playerSmallRobot = playerSmallRobot;
	}



	/**
	 * @return the playerSelected
	 */
	public EntitieBase getPlayerSelected() {
		return playerSelected;
	}



	/**
	 * @param playerSelected the playerSelected to set
	 */
	public void setPlayerSelected(EntitieBase playerSelected) {
		this.playerSelected = playerSelected;
	}



	public void SwitchPlayer()
	{
		// inverse la selection
		if(playerSmallRobot.pControl.isSelected)
		{
			playerSmallRobot.pControl.setSelected(false);
			playerBigRobot.pControl.setSelected(true);
			
		}
		else
		{
			playerSmallRobot.pControl.setSelected(true);
			playerBigRobot.pControl.setSelected(false);
			
		}

		
	}
	

	
	public void update(Time deltaTime)
	{
		// TODO Auto-generated method stub
		playerSmallRobot.Update(deltaTime);
		playerBigRobot.Update(deltaTime);
	}

	

	
	public void loadContent() 
	{
		// TODO Auto-generated method stub
		//Load content du TextureManager
		playerSmallRobot = new SmallRobotView();
		playerSmallRobot.LoadContent();
		playerSmallRobot.SetPosition(new Vector2f(1000,-400));
		
		playerBigRobot = new BigRobotView();
		playerBigRobot.LoadContent();
		playerBigRobot.SetPosition(new Vector2f(30,-400));
		
		// initialisation du robot selectionné
		playerSelected = playerSmallRobot;
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

	
	public void catchEvent(Event e) 
	{
		// TODO Auto-generated method stub
		this.playerSelected.SetEvent(e);
		
	}


	
	public void draw(RenderTarget render, RenderStates state) 
	{
		render.draw(playerSmallRobot.getSpritePlayer());
		
		render.draw(playerBigRobot.getSpritePlayer());
		
	}
	
}
