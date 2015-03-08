package CoreManagerObstacle;

public abstract class ObstacleBase 
{
	public ObstacleBase()
	{
		
	}
	
	public abstract ObstacleResult IsPointCollision(int x,int y);
	
	public abstract ObstacleResult IsRectangleCollision(int x,int y,int width,int height);
	
	public abstract ObstacleResult IsLineCollision(int x1,int y1,int x2,int y2);

	
}
