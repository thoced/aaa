package CoreManagerObstacle;

import org.jsfml.graphics.IntRect;
import org.jsfml.system.Vector2i;

public class ObstacleRectangle extends ObstacleBase
{
	private int posX,posY;
	
	private int width,height;
	
	// utilisation de la gesion de SFML pour le test
	private IntRect obs;
	
	public ObstacleRectangle(int x,int y, int width, int height)
	{
		// création de l'obstacle rectangle
		this.posX = x;
		this.posY = y;
		this.width = width;
		this.height = height;
		
		this.obs = new IntRect(posX,posY,this.width,this.height);
	}

	
	public ObstacleResult IsPointCollision(int x, int y) 
	{
		// si le point est dans le rectangle
		if((x > posX && x < posX + width) && (y > posY && y < posY + height))
			return new ObstacleResult(this);
		else
			return null;
	}

	
	public ObstacleResult IsRectangleCollision(int x, int y, int width,
			int height) {
		// si la collision se fait avec deux rectangles
		IntRect objTest = new IntRect(x,y,width,height);
		
		if(this.obs.intersection(objTest) != null)
			return new ObstacleResult(this);
		else
			return null;
	}

	
	public ObstacleResult IsLineCollision(int x1, int y1, int x2, int y2) 
	{
		// TODO Auto-generated method stub
		// creation des 4 vecteurs formant l'obstacle rectangle

		return null;
	}

	

	
	
	
}
