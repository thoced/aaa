package CoreManager;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Time;
import org.jsfml.window.event.Event;

import bilou.ICoreBase;
import CoreFx.ManagerFx;

public class Manager implements ICoreBase,Drawable
{
	// class manager static
	private static RenderTexture renderTexture;
	
	private static RenderWindow renderWindow;
	
	private static ManagerFx managerFx;
	
	private static List<ManagerBase> listManager;


	public Manager()
	{
		// instance du list manager
		Manager.listManager = new ArrayList<ManagerBase>();
	}
		
	/**
	 * @return the listManager
	 */
	public static List<ManagerBase> getListManager() {
		return listManager;
	}

	/**
	 * @param listManager the listManager to set
	 */
	public static void setListManager(List<ManagerBase> listManager) {
		Manager.listManager = listManager;
	}



	/**
	 * @return the managerFx
	 */
	public static ManagerFx getManagerFx() {
		return managerFx;
	}

	/**
	 * @param managerFx the managerFx to set
	 */
	public static void setManagerFx(ManagerFx managerFx) {
		Manager.managerFx = managerFx;
	}

	/**
	 * @return the renderTexture
	 */
	public static RenderTexture getRenderTexture() {
		return renderTexture;
	}

	/**
	 * @param renderTexture the renderTexture to set
	 */
	public static void setRenderTexture(RenderTexture renderTexture) {
		Manager.renderTexture = renderTexture;
	}

	/**
	 * @return the renderWindow
	 */
	public static RenderWindow getRenderWindow() {
		return renderWindow;
	}

	/**
	 * @param renderWindow the renderWindow to set
	 */
	public static void setRenderWindow(RenderWindow renderWindow) {
		Manager.renderWindow = renderWindow;
	}

	
	public void update(Time deltaTime)
	{
		// on boucle sur les managers
		for(ManagerBase m : Manager.listManager)
			m.update(deltaTime);
		
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
		// on boucle sur les managers
		for(ManagerBase m : Manager.listManager)
		{
			m.draw(render, state);
		}
	}
	
}
