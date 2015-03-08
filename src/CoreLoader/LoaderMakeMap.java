package CoreLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.json.*;
import javax.json.JsonValue.ValueType;

import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import CorePlayer.PlayerManager;
import CorePlayer.SmallRobot;
import CoreTexturesManager.TexturesManager;
import Entities.EntitiesManager;
import bilou.DrawableMap;

public class LoaderMakeMap 
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
				if(obj.containsKey("width_map"))
				{
					this.mapWidth = obj.getInt("width_map");
				}
				
				if(obj.containsKey("height_map"))
				{
					this.mapHeight = obj.getInt("height_map");
				}
				
				// chargement des calques
				if(obj.containsKey("calques"))
				{
					this.parseImage(obj);
				}
				
				if(obj.containsKey("obstacles"))
				{
					this.parseObstacles(obj);
				}
				
				if(obj.containsKey("entities"))
				{
					this.parseEntities(obj);
				}
				
				// ---------------- obtention du tableau tileset -------------
				// -----------------------------------------------------------
				// -----------------------------------------------------------
				
				/*JsonArray  tilesets = obj.getJsonArray("tilesets");
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
				*/
				// ---------------------------------------------------------
				// ---------------------------------------------------------
				// ---------------- obtention du tableau layers -------------
				// -----------------------------------------------------------
				// -----------------------------------------------------------
				/*
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
				*/
				// fermeture du reader
				reader.close();
	}
	
	private void parseEntities(JsonObject obj)
	{
		// on récupère le tableau des entities
		JsonArray arrayEntities = obj.getJsonArray("entities");
		
		for(int i=0;i<arrayEntities.size();i++)
		{
			// on récupère l'entité current
			JsonObject ent = arrayEntities.getJsonObject(i);
			// on vérifie le type d'entitié
			if(ent.containsKey("type_entities"))
			{
				switch(ent.getString("type_entities"))
				{
					case "PLAYERSTART":
					{
						// réception des positions
						if(ent.containsKey("x") && ent.containsKey("y") && ent.containsKey("type"))
						{
							float x = (float) ent.getJsonNumber("x").doubleValue();
							float y = (float) ent.getJsonNumber("y").doubleValue();
							SmallRobot robot = new SmallRobot(new Vector2f(x,y));
							PlayerManager.createSmallRobot(robot);
							break;
						}
					}
				}
			}
		}
	}
	
	private void parseImage(JsonObject obj)
	{
		// récupération de la liste array des calques
		JsonArray arrayCalques = obj.getJsonArray("calques");
		
		for(int i=0;i<arrayCalques.size();i++)
		{
			JsonObject calque = arrayCalques.getJsonObject(i);
			
			// on récupère un calque

			String virtualName = null;
			String path = null;
			String typeCalque = null;
			float x = 0,y = 0;
			int layer = 0;
			float speed = 0;
			float masse = 0;
			float targetX = 0,targetY = 0;
			boolean danger = false;
			boolean lightmap = false;
			int alpha = 255;
			boolean rotation = false;
			String sens_rotation = null;
			
			// virtual name
			if(calque.containsKey("virtual_name"))
				virtualName = calque.getString("virtual_name");
			// path
			if(calque.containsKey("path"))
				path = calque.getString("path");
			// type de calque
			if(calque.containsKey("type_calque"))
				typeCalque = calque.getString("type_calque");
			// x et y
			if(calque.containsKey("x") && calque.containsKey("y"))
			{
				x = (float) calque.getJsonNumber("x").doubleValue();
				y = (float) calque.getJsonNumber("y").doubleValue();
			}
			// layer
			if(calque.containsKey("layer"))
				layer = calque.getInt("layer");
			// vitesse
			if(calque.containsKey("speed"))
				speed = (float) calque.getJsonNumber("speed").doubleValue();
			// masse
			if(calque.containsKey("masse"))
				masse = (float) calque.getJsonNumber("masse").doubleValue();
			// targetX et targetY
			if(calque.containsKey("targetX") && calque.containsKey("targetY"))
			{
				targetX = (float) calque.getJsonNumber("targetX").doubleValue();
				targetY = (float) calque.getJsonNumber("targetY").doubleValue();
			}
			// danger
			if(calque.containsKey("danger"))
				danger = calque.getBoolean("danger");
			// lightmap
			if(calque.containsKey("lightmap"))
				lightmap = calque.getBoolean("lightmap");
			// alpha
			if(calque.containsKey("alpha"))
				alpha = calque.getInt("alpha");
			// rotation
			if(calque.containsKey("rotation"))
				rotation = calque.getBoolean("rotation");
			// sens_rotation
			if(calque.containsKey("sens_rotation"))
				sens_rotation = calque.getString("sens_rotation");
				
			

			TiledLayerImages layerImages = new TiledLayerImages();
			
			layerImages.setPathImage(virtualName);
			layerImages.setName(path);
			layerImages.setMasse(masse);
			layerImages.setSpeed(speed);
			layerImages.setTypeCalque(typeCalque);
			layerImages.setTargetX(targetX);
			layerImages.setTargetY(targetY);
			layerImages.setPosx(x);
			layerImages.setPosy(y);
			layerImages.setDanger(danger);
			layerImages.setLightmap(lightmap);
			layerImages.setAlpha(alpha);
			layerImages.setRotation(rotation);
			layerImages.setSensRotation(sens_rotation == "positif" ? true : false);
	
		
			// ajout du tiledlayerimage dans la liste
			this.listLayersImages.add(layerImages);
		
		}
		
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
	
	private void parseObstacles(JsonObject obj)
	{
		// on instancie l'objet dataObject
		//this.dataObjects = new ArrayList<TiledObjectBase>();
		// instance de la class TiledLayerObjects
		TiledLayerObjects layerObject = new TiledLayerObjects();
		
		
		JsonArray arrayObstacle = obj.getJsonArray("obstacles");
		
		for(int i=0;i<arrayObstacle.size();i++)
		{
			// oh récupère un obsacle
			JsonObject obstacle = arrayObstacle.getJsonObject(i);
			
			String name = obstacle.getString("name_obstacle");
		
			// on instance un objet polyline 
			TiledObjectPolyline poly = new TiledObjectPolyline();
			// si c'est un objet polyline
			//poly.setType(o.getString("type"))
			// on récupère l'array polyline
			JsonArray arrayPolyline = obstacle.getJsonArray("list_points");
			
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
