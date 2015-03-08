package CoreQuadTree;

import java.util.ArrayList;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

import bilou.ICoreBase;
import bilou.IGameBase;

public class QuadTreeNode implements ICoreBase
{
	// membre static LevelNodeMax
	public static int LevelNodeMax = 8;
	// nombre maximal d'éléments dans le node avant un split
	public static int NbMaxElement = 5;
	// bounds du noeud
	private FloatRect bounds;
	// liste des éléments présents dans le noeud
	private ArrayList<IGameBase> elements = null;
	// references vers les 4 sous noeuds
	private QuadTreeNode[] nodesFils;
	// node level (permet de déterminer la hauteur maximal de l'arbre
	private int levelNode;
	// le node est il splité ?
	private boolean iSSplited = false;

	
	
	public QuadTreeNode(int level,FloatRect bounds)
	{
		// initialisation du levelNode
		levelNode = level;
		// creation du bounds
		this.bounds = bounds;  
		// initialisation des elements
		this.elements = new ArrayList<IGameBase>();
		
	}
	
	public void DrawDebugBounds(RenderWindow window)
	{
		// permet d'afficher les bounds de l'arbre (mode debug)
		if(this.iSSplited)
		{
			nodesFils[0].DrawDebugBounds(window);
			nodesFils[1].DrawDebugBounds(window);
			nodesFils[2].DrawDebugBounds(window);
			nodesFils[3].DrawDebugBounds(window);
		}
		else
		{
			RectangleShape shape = new RectangleShape(new Vector2f(this.bounds.width,this.bounds.height));
			shape.setPosition(new Vector2f(this.bounds.left,this.bounds.top));
			shape.setFillColor(Color.TRANSPARENT);
			shape.setOutlineColor(Color.BLUE);
			shape.setOutlineThickness(window.getView().getSize().x * 0.0005f);
			window.draw(shape);
		}
	}
	
	public void Split()
	{
		// instantiation des nodes fils
		nodesFils = new QuadTreeNode[4];
		// initialisation
		// création des bounds des fils nodes
		
		// bounds /2
		float nw = bounds.width / 2;
		float nh = bounds.height /2;
		
		FloatRect b1 =  new FloatRect(bounds.left,bounds.top,nw,nh);
		FloatRect b2 =  new FloatRect(bounds.left + nw,bounds.top,nw,nh);
		FloatRect b3 =  new FloatRect(bounds.left,bounds.top + nh,nw,nh);
		FloatRect b4 =  new FloatRect(bounds.left + nw,bounds.top + nh,nw,nh);
				
		// instance des 4 nodes fils
		nodesFils[0] = new QuadTreeNode(levelNode + 1,b1);
		nodesFils[1] = new QuadTreeNode(levelNode + 1,b2);
		nodesFils[2] = new QuadTreeNode(levelNode + 1,b3);
		nodesFils[3] = new QuadTreeNode(levelNode + 1,b4);
		
		// on récupére les élements et on les place dans les nodes fils
		
			for(IGameBase elem : this.elements)
			{
				nodesFils[0].InsertElement(elem);
				nodesFils[1].InsertElement(elem);
				nodesFils[2].InsertElement(elem);
				nodesFils[3].InsertElement(elem);
			}
		
	}
	
	public void InsertElement(IGameBase element)
	{
		
			// l'element est-il dans le bounds ?
			FloatRect result = bounds.intersection(element.GetGlobalBounds());
			if(result != null)
			{
				
				if(this.iSSplited) // c'est déja splité alors on insère dans les nodes fils
				{
					nodesFils[0].InsertElement(element);
					nodesFils[1].InsertElement(element);
					nodesFils[2].InsertElement(element);
					nodesFils[3].InsertElement(element);
				}
				else
				{
					// ce n'est pas encore splité
					if(this.elements.size() <= QuadTreeNode.NbMaxElement || this.levelNode > QuadTreeNode.LevelNodeMax)
					{
						elements.add(element);
					}
					else
					{
						// on split le node
						elements.add(element);
						// on crée les 4 fils nodes
						this.Split();
						// on true la variable ISSplited
						this.iSSplited = true;
						// on supprime les élements
						this.elements.clear();
					}
				}
				
				
			}
		
	}
	
	public void GetElements(FloatRect zone,ArrayList<IGameBase> listes)
	{
		// recherche des élements de façon récursive
		FloatRect result = this.bounds.intersection(zone);
		if(result != null)
		{
			
			if(this.iSSplited)
			{
				// on descend dans les 4 nodes fils
				nodesFils[0].GetElements(zone,listes);
				nodesFils[1].GetElements(zone,listes);
				nodesFils[2].GetElements(zone,listes);
				nodesFils[3].GetElements(zone,listes);
			}
			else
			{	
				// le noeud ne possède pas de fils, on retourne la listes avec les élements supplémentaires
			    //listes.addAll(this.elements);
			    
			    for(IGameBase b : this.elements)
			    {
			    	if(!listes.contains(b))
			    		listes.add(b);
			    }
			}
			
		}
		
	}

	public FloatRect getBounds() {
		return bounds;
	}

	public void setBounds(FloatRect bounds) {
		this.bounds = bounds;
	}

	
	public void update(Time deltaTime) {
		// TODO Auto-generated method stub
		
	}

	

	
	public void loadContent() {
		// TODO Auto-generated method stub
		
	}

	
	public void reloadContent() {
		// TODO Auto-generated method stub
		
	}

	
	public void deleteContent() {
		// TODO Auto-generated method stub
		
	}

	
	public void init() {
		// TODO Auto-generated method stub
		
	}

	
	public void catchEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
