package CoreLoader;

import java.util.ArrayList;
import java.util.List;

public class TiledLayerObjects 
{
	// data objets
	private List<TiledObjectBase> dataObjects;
	
	public TiledLayerObjects()
	{
		// instance de la liste des tiledobject
		this.dataObjects = new ArrayList<TiledObjectBase>();
	}

	/**
	 * @return the dataObjects
	 */
	public List<TiledObjectBase> getDataObjects() {
		return dataObjects;
	}

	/**
	 * @param dataObjects the dataObjects to set
	 */
	public void setDataObjects(List<TiledObjectBase> dataObjects) {
		this.dataObjects = dataObjects;
	}
	
	public void InsertObject(TiledObjectBase ob)
	{
		this.dataObjects.add(ob);
	}
	
	
}
