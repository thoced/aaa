package CoreLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.json.*;
import javax.json.JsonValue.ValueType;

import org.jsfml.graphics.Texture;

import bilou.DrawableMap;

public class LoaderTiled 
{
	// taille de la map
	private int mapWidth,mapHeight;
	// path de l'image du tileset
	private String image;
	// taille du tileset
	private int imageWidth,imageHeight;
	// taille des tiles
	private int tileWidth,tileHeight;
	// marge et spacing entre les tiles
	private int margin,parcing;
	// firstgid
	private int firstgid;
	// nom de la map
	private String nameMap;
	// liste des layers tiles
	private List<TiledLayerTiles> listLayersTiles = new ArrayList<TiledLayerTiles>();
	// liste des layers objects
	private List<TiledLayerObjects> listLayersObjects = new ArrayList<TiledLayerObjects>();
	// list des Layers d'images
	private List<TiledLayerImages> listLayersImages = new ArrayList<TiledLayerImages>();
	public String toString()
	{
		return nameMap + String.valueOf(mapWidth) + " , " + String.valueOf(mapHeight); 
	}
	
	public void Load(InputStream nameFile) throws LoaderTiledException
	{
		// si l'input stream est null, on lance l'exception
		if(nameFile==null)
			throw new LoaderTiledException();
				
		// ouverture du fichier
				JsonReader reader = Json.createReader(nameFile);
				if(reader==null)
					throw new LoaderTiledException();
				
				// lecture de l'objet principale json
				JsonObject obj = reader.readObject();
				
				// on récupère la taille de la map
				if(obj.containsKey("width"))
				{
					this.mapWidth = obj.getInt("width");
				}
				
				if(obj.containsKey("height"))
				{
					this.mapHeight = obj.getInt("height");
				}
				
				// ---------------- obtention du tableau tileset -------------
				// -----------------------------------------------------------
				// -----------------------------------------------------------
				
				JsonArray  tilesets = obj.getJsonArray("tilesets");
				// on récupère la liste des objets contenu danas le tableau tilesets		
				List<JsonObject> l = tilesets.getValuesAs(JsonObject.class);
				
				for(JsonObject o :  l)
				{
					// on récupère le path de l'image du tileset
					if(o.containsKey("image"))
					{
						this.image = o.getString("image");
					}
					
					if(o.containsKey("imagewidth"))
					{
						this.imageWidth = o.getInt("imagewidth");
					}
					
					if(o.containsKey("imageheight"))
					{
						this.imageHeight = o.getInt("imageheight");
					}
					if(o.containsKey("margin"))
					{
						this.margin = o.getInt("margin");
					}
					
					if(o.containsKey("spacing"))
					{
						this.parcing = o.getInt("spacing");
					}
					
					if(o.containsKey("tilewidth"))
					{
						this.tileWidth = o.getInt("tilewidth");
					}
					
					if(o.containsKey("tileheight"))
					{
						this.tileHeight = o.getInt("tileheight");
					}
					
					if(o.containsKey("firstgid"))
					{
						this.firstgid = o.getInt("firstgid");
					}
				}
				
				// ---------------------------------------------------------
				// ---------------------------------------------------------
				// ---------------- obtention du tableau layers -------------
				// -----------------------------------------------------------
				// -----------------------------------------------------------
				
				JsonArray  layers = obj.getJsonArray("layers");
				// on récupère la liste des objets contenu danas le tableau layers		
				List<JsonObject> listLayers = layers.getValuesAs(JsonObject.class);
				
				
				for(JsonObject o : listLayers)
				{
					if(o.containsKey("type"))
					{
						// reception du type
						String typeLayers = o.getString("type");
						
						// si le type est objectgroup
						if(typeLayers.equals("objectgroup"))
						{
							// parse des objets
							this.parseObjects(o);
						}
						
						// si le type est tile
						if(typeLayers.equals("tilelayer"))
						{
							// parse des tiles
							this.parseTile(o);
						}
						
						// si le type est une image
						if(typeLayers.equals("imagelayer"))
						{
							// parse du layer image
							this.parseImage(o);
						}
					}
	
				}
				
				// fermeture du reader
				reader.close();
	}
	
	private void parseImage(JsonObject obj)
	{
		// instance de la class TiledLayerImages
		TiledLayerImages layerImages = new TiledLayerImages();
		
		// reception du nom 
		layerImages.setName(obj.getString("name"));
		
		// reception du nom du fichier
		layerImages.setPathImage(obj.getString("image"));
		
		// reception de la position de l'image 
		layerImages.setPosx((float)obj.getJsonNumber("x").doubleValue());
		layerImages.setPosy((float)obj.getJsonNumber("y").doubleValue());
		
		// reception de la taille de l'image
		layerImages.setWidth(obj.getInt("width"));
		layerImages.setHeight(obj.getInt("height"));
		
		// reception du type d'image si il y en a
		
		// si l'objet properties existe (facultatif)
		if(obj.containsKey("properties"))
		{
			JsonObject prop = obj.getJsonObject("properties");
			
			if(prop.containsKey("type_calque"))
			{
				layerImages.setTypeCalque(prop.getString("type_calque"));
				
			}
			// reception du targetX targetY si existe
			if(prop.containsKey("targetX") && prop.containsKey("targetY"))
			{
				layerImages.setTargetX(Integer.parseInt(prop.getString("targetX")));
				layerImages.setTargetY(Integer.parseInt(prop.getString("targetY")));
			}
			
			if(prop.containsKey("speed"))
			{
				layerImages.setSpeed(Integer.parseInt(prop.getString("speed")));
			}
			
			// valeur danger
			if(prop.containsKey("danger"))
			{
				String danger = prop.getString("danger");
				if(danger != null)
				{
					switch(danger)
					{
					case "true" :layerImages.setDanger(true);break;
					
					case "false": layerImages.setDanger(false);break;
					
					default :  layerImages.setDanger(false);break;
					
					}
				}
			}
			
			// valeur de masse
			if(prop.containsKey("masse"))
			{
				String masse = prop.getString("masse");
				if(masse!=null)
				{
					float mf = Float.parseFloat(masse);
					layerImages.setMasse(mf);
				}
			}
			
			// valeur de sens de rotation
			if(prop.containsKey("sens_rotation"))
			{
				String sens = prop.getString("sens_rotation");
				if(sens!=null)
				{
					switch(sens)
					{
					case "true" : layerImages.setSensRotation(true);break;
					
					case "false" : layerImages.setSensRotation(false);break;
					
					default : layerImages.setSensRotation(true);break;
					}
				}
				
			}
			
			// valeur de taille d'animation
			if(prop.containsKey("width_animation") && prop.containsKey("height_animation"))
			{
				String wa = prop.getString("width_animation");
				String ha = prop.getString("height_animation");
				
				int iwa = Integer.parseInt(wa);
				int iha = Integer.parseInt(ha);
				
				layerImages.setWidthAnimation(iwa);
				layerImages.setHeightAnimation(iha);
			}
			
			// valeur du nombre de tiles d'animation
			if(prop.containsKey("nombre_animation"))
			{
				String nombreAnimation = prop.getString("nombre_animation");
				if(nombreAnimation!=null)
				{
					int ina = Integer.parseInt(nombreAnimation);
					layerImages.setNombreAnimation(ina);
				}
			}
		}
		
		
	
		
				
		
		// ajout du tiledlayerimage dans la liste
		this.listLayersImages.add(layerImages);
		
	}
	
	private void parseTile(JsonObject obj)
	{
		// instance de la class TiledLayerTiles
		TiledLayerTiles layerTiles = new TiledLayerTiles();
	
		// reception du nom de la tile
		layerTiles.setName(obj.getString("name"));
		
		// parse de la map
		if(obj.containsKey("data"))
		{
			// ajout de l'indice dans le layerTiles
			JsonArray data = obj.getJsonArray("data");
			for(int ind=0;ind<data.size();ind++)
				layerTiles.InsertIndice(data.getInt(ind));
		}
		
		// ajout du layer Tiles dans la liste
		this.listLayersTiles.add(layerTiles);
	}
	
	private void parseObjects(JsonObject obj)
	{
		// on instancie l'objet dataObject
		//this.dataObjects = new ArrayList<TiledObjectBase>();
		// instance de la class TiledLayerObjects
		TiledLayerObjects layerObject = new TiledLayerObjects();
		
		// récupération du tableau des objets 
		JsonArray arrayObjects = obj.getJsonArray("objects");
		
		for(JsonValue value : arrayObjects)
		{
			// Pour chaque objet je récupère les values
			if(value.getValueType() == ValueType.OBJECT)
			{
				JsonObject o = (JsonObject) value;
				
				// polyline
				if(o.containsKey("polyline"))
				{
					// c'est un objet rectangle, on instantie
					TiledObjectPolyline poly = new TiledObjectPolyline();
					// si c'est un objet polyline
					poly.setType(o.getString("type"));
					poly.setX(o.getInt("x"));
					poly.setY(o.getInt("y"));
					// on récupère l'array polyline
					JsonArray arrayPolyline = o.getJsonArray("polyline");
					for(JsonValue valuePoly : arrayPolyline)
					{
						if(valuePoly.getValueType() == ValueType.OBJECT)
						{
							// on récupère les couples x,y pour les polyline
							JsonObject opoly = (JsonObject) valuePoly;
							
							
							int xpoly = opoly.getInt("x");
							int ypoly = opoly.getInt("y");
							// on ajoute un point
							poly.InsertPoint(xpoly, ypoly);
						}
					}
					
					// on insère dans le tableau
					//this.dataObjects.add(poly);
					// on insère dans le layerobject
					layerObject.InsertObject(poly);
					
				}
				else
				{
					// c'est un objet rectangle, on instantie
					TiledObjectRectangle rect = new TiledObjectRectangle();
					// on récupère les x et y du rectangle plus le type
					rect.setType(o.getString("type"));
					rect.setX(o.getInt("x"));
					rect.setY(o.getInt("y"));
					// on récupère la taille du rectangle
					rect.setWidth(o.getInt("width"));
					rect.setHeight(o.getInt("height"));
					// on insère dans le tableau
					//this.dataObjects.add(rect);
					// on insert l'objet rect dans le layerobject
					layerObject.InsertObject(rect);
				}
				
				
				
			}
		}
		
		// insertion du layer
		this.listLayersObjects.add(layerObject);
	}
	
	
	
	/**
						this.tileWidth = o.getInt("ti
	 * @return the mapWidth
	 */
	public int getMapWidth() {
		return mapWidth;
	}

	/**
	 * @param mapWidth the mapWidth to set
	 */
	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}

	/**
	 * @return the mapHeight
	 */
	public int getMapHeight() {
		return mapHeight;
	}

	/**
	 * @param mapHeight the mapHeight to set
	 */
	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the imageWidth
	 */
	public int getImageWidth() {
		return imageWidth;
	}

	/**
	 * @param imageWidth the imageWidth to set
	 */
	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	/**
	 * @return the imageHeight
	 */
	public int getImageHeight() {
		return imageHeight;
	}

	/**
	 * @param imageHeight the imageHeight to set
	 */
	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	/**
	 * @return the tileWidth
	 */
	public int getTileWidth() {
		return tileWidth;
	}

	/**
	 * @param tileWidth the tileWidth to set
	 */
	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	/**
	 * @return the tileHeight
	 */
	public int getTileHeight() {
		return tileHeight;
	}

	/**
	 * @param tileHeight the tileHeight to set
	 */
	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}

	/**
	 * @return the margin
	 */
	public int getMargin() {
		return margin;
	}

	/**
	 * @param margin the margin to set
	 */
	public void setMargin(int margin) {
		this.margin = margin;
	}

	/**
	 * @return the spacing
	 */
	public int getParcing() {
		return parcing;
	}

	/**
	 * @param spacing the spacing to set
	 */
	public void setParcing(int parcing) {
		this.parcing = parcing;
	}

	/**
	 * @return the firstgid
	 */
	public int getFirstgid() {
		return firstgid;
	}

	/**
	 * @param firstgid the firstgid to set
	 */
	public void setFirstgid(int firstgid) {
		this.firstgid = firstgid;
	}

	/**
	 * @return the nameMap
	 */
	public String getNameMap() {
		return nameMap;
	}

	/**
	 * @param nameMap the nameMap to set
	 */
	public void setNameMap(String nameMap) {
		this.nameMap = nameMap;
	}

	/**
	 * @return the listLayersTiles
	 */
	public List<TiledLayerTiles> getListLayersTiles() {
		return listLayersTiles;
	}

	/**
	 * @param listLayersTiles the listLayersTiles to set
	 */
	public void setListLayersTiles(List<TiledLayerTiles> listLayersTiles) {
		this.listLayersTiles = listLayersTiles;
	}

	/**
	 * @return the listLayersObjects
	 */
	public List<TiledLayerObjects> getListLayersObjects() {
		return listLayersObjects;
	}

	/**
	 * @param listLayersObjects the listLayersObjects to set
	 */
	public void setListLayersObjects(List<TiledLayerObjects> listLayersObjects) {
		this.listLayersObjects = listLayersObjects;
	}

	/**
	 * @return the listLayersImages
	 */
	public List<TiledLayerImages> getListLayersImages() {
		return listLayersImages;
	}

	/**
	 * @param listLayersImages the listLayersImages to set
	 */
	public void setListLayersImages(List<TiledLayerImages> listLayersImages) {
		this.listLayersImages = listLayersImages;
	}

	

	

}
