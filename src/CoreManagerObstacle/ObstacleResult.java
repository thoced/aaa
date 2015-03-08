package CoreManagerObstacle;

public class ObstacleResult 
{
	private ObstacleBase result = null;

	// constructeur avec passage de l'obstacle
	public ObstacleResult(ObstacleBase base)
	{
		result = base;
	}
	/**
	 * @return the result
	 */
	public ObstacleBase getResult() {
		return result;
	}

}
 