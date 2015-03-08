package CoreLoader;

import java.util.ArrayList;
import java.util.List;

public class TiledLayerTiles 
{
	// data map
	private List<Integer> dataMap;
	
	// name
	
	private String name;
	
	// constructeur par defaut
	public TiledLayerTiles()
	{
		// instance de la list 
		this.dataMap = new ArrayList<Integer>();
	}

	/**
	 * @return the dataMap
	 */
	public List<Integer> getDataMap() {
		return dataMap;
	}

	/**
	 * @param dataMap the dataMap to set
	 */
	public void setDataMap(List<Integer> dataMap) {
		this.dataMap = dataMap;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public void InsertIndice(int indice)
	{
		// ajout de l'indice dans le vecteur
		this.dataMap.add(indice);
	}
	
	
	
}
