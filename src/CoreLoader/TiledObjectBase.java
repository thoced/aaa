package CoreLoader;



public abstract class TiledObjectBase 
{
	// creation de l'enum type
	public static enum Type {RECTANGLE,POLYLINE};
	
	// posx et posy
	protected int x,y;
	// value type
	protected Type typeObjects;
	// type donné dans les attibuts du logiciel Tiled
	protected String type;

	public TiledObjectBase()
	{
		
	}
	
	public TiledObjectBase(Type t)
	{
		this.typeObjects = t;
	}
	
	/**
	 * @return the typeObjects
	 */
	public Type getTypeObjects() {
		return typeObjects;
	}

	/**
	 * @param typeObjects the typeObjects to set
	 */
	public void setTypeObjects(Type typeObjects) {
		this.typeObjects = typeObjects;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	

}
