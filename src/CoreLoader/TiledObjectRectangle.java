package CoreLoader;

public class TiledObjectRectangle extends TiledObjectBase 
{

	// taille du rectangle
	private int width,height;
		
	public TiledObjectRectangle()
	{
		this.setTypeObjects(Type.RECTANGLE);
	}
	
	public TiledObjectRectangle(Type t)
	{
		super(t);
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
