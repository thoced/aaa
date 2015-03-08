package bilou;

import java.io.IOException;
import java.util.List;

import org.jsfml.graphics.BlendMode;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Shader;
import org.jsfml.graphics.ShaderSourceException;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.Transform;
import org.jsfml.graphics.Transformable;
import org.jsfml.graphics.Vertex;
import org.jsfml.graphics.VertexArray;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

public  class DrawableMap implements Drawable, Transformable
{

	private Texture textureTileSets;
	
	private VertexArray listVertex;
	
	private int mapWidth,mapHeight,wTile,hTile,margin,parcing,firstgid;
		
	private List<Integer> map;
	
	// Shader
	private Shader shader;
	
	public DrawableMap() throws IOException, ShaderSourceException
	{
		// initialisationdu vertexarray
		listVertex = new VertexArray(PrimitiveType.QUADS);
		// initialisation du shader
		shader = new Shader();
		shader.loadFromStream(DrawableMap.class.getResourceAsStream("/Shaders/VertexShaderMap.vert"),Shader.Type.VERTEX);
		shader.loadFromStream(DrawableMap.class.getResourceAsStream("/Shaders/FragmentShaderMap.frag"),Shader.Type.FRAGMENT);
	}
	
	public void LoadMap(List<Integer> map,Texture text,int mapWidth,int mapHeight,int wTile,int hTile,int margin,int parcing,int firstgid)
	{
		this.textureTileSets = text;
		
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.wTile = wTile;
		this.hTile = hTile;
		this.margin = margin;
		this.parcing = parcing;
		this.firstgid = firstgid;
		
		this.map = map;
		
		// creation des vertex
		this.createVertex();
		
	}
	
	private void createVertex()
	{
		// indice dans le vecteur de la map
		int i = 0;
		
		for(int posY = 0; posY < this.getMapHeightInPixels(); posY += this.hTile)
		{
			for(int posX = 0; posX < this.getMapWidthInPixels(); posX += this.wTile)
			{
				//creation des TextureRects
				
				
				int ind = this.map.get(i) - this.firstgid;
				if(ind > -1) // si il n'y a pas de texture (-1) alors on ne créer par de quads
				{
				
					// obtention des valeurs x,y de la texture par rapport à l'indice
					int x = (ind % (this.textureTileSets.getSize().x / (this.wTile)) * this.wTile) ;
					int y = (ind / (this.textureTileSets.getSize().x / (this.wTile)) * this.hTile) ;
					
					Vector2f t0 = new Vector2f(x,y);
					Vector2f t1 = new Vector2f(x + this.wTile,y);
					Vector2f t2 = new Vector2f(x + this.wTile,y + this.hTile);
					Vector2f t3 = new Vector2f(x,y + this.hTile);
					
					// creation des vertex
					Vertex v0 = new Vertex(new Vector2f(posX,posY),t0);
					Vertex v1 = new Vertex(new Vector2f(posX + this.wTile,posY),t1);
					Vertex v2 = new Vertex(new Vector2f(posX + this.wTile, posY + this.hTile),t2);
					Vertex v3 = new Vertex(new Vector2f(posX,posY + this.hTile),t3);
					
					// ajout des vertex dans le tableau de vertex
					listVertex.add(v0);
					listVertex.add(v1);
					listVertex.add(v2);
					listVertex.add(v3);
				
				}
				
				// incrementation de l'indice du map
				i++;
			}
		}
	
		
		
	}
	
	public int getMapWidthInPixels()
	{
		//retourne la taille de la map en longueur multiplié par la taille d'un tiles
		return this.mapWidth * this.wTile;
	}
	
	public int getMapHeightInPixels()
	{
		//retourne la taille de la map en hauteur multiplié par la hauteur d'un tiles
		return this.mapHeight * this.hTile;
	}
	

	
	public Vector2f getOrigin() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Vector2f getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public float getRotation() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public Vector2f getScale() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return Transform.IDENTITY;
	}

	
	public void move(Vector2f arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void move(float arg0, float arg1) {
		// TODO Auto-generated method stub
		
	}

	
	public void rotate(float arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void scale(Vector2f arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void scale(float arg0, float arg1) {
		// TODO Auto-generated method stub
		
	}

	
	public void setOrigin(Vector2f arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void setOrigin(float arg0, float arg1) {
		// TODO Auto-generated method stub
		
	}

	
	public void setPosition(Vector2f arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void setPosition(float arg0, float arg1) {
		// TODO Auto-generated method stub
		
	}

	
	public void setRotation(float arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void setScale(Vector2f arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void setScale(float arg0, float arg1) {
		// TODO Auto-generated method stu,camera.getView().getViewport()b
		
	}

	
	public void draw(RenderTarget renderTarget,RenderStates states) 
	{
		RenderStates newStates = new RenderStates(BlendMode.ALPHA,
		        Transform.combine(states.transform, this.getTransform()),this.textureTileSets,null);

		shader.setParameter("maTexture", Shader.CURRENT_TEXTURE);

		renderTarget.draw(listVertex,newStates);
		
		
	}

	
	public Transform getInverseTransform() {
		// TODO Auto-generated method stub
		return null;
	}

}
