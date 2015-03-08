package CoreLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.Transform;
import org.jsfml.system.Time;
import org.jsfml.window.event.Event;

import bilou.ElementBase;
import bilou.ICoreBase;
import bilou.IGameBase;

public class LoaderMap implements ICoreBase
{
	// document JDOM2 XML
	private static Document document;
	// element racine du fixhier xml
	private static Element racine;
	// List des elements chargés
	private List<IGameBase> listElement;
	
	public LoaderMap()
	{
		listElement = new ArrayList<IGameBase>();
	}
	
	
	public void update(Time deltaTime) 
	{
		// TODO Auto-generated method stub

	}
	
	public void LoadContent()
	{
		// instance du builder
		SAXBuilder sxb = new SAXBuilder();
		 
		try
		  {
		// chargement du fichier   
			InputStream stream = LoaderMap.class.getResourceAsStream("/Maps/level01.svg");
			document = sxb.build(stream);
		// on récupère la racine du fichier xml
			racine = document.getRootElement();
		// clear de la liste des élements
			listElement.clear();
		// Appel à la méthode ChargementElements
			this.ChargementElements(racine);
		  }
		  catch(IOException | JDOMException e)
		  {
			  
		  }
		
		
		
	}
	
	private void ChargementElements(Element racine)
	{
		   // Liste contenant tous les élements "g"
		   List<Element> listElements = racine.getChildren();
		   
		   int a = listElements.size();
		   
		  for(Element e : listElements)
		  {
			  if(e.getName().equals("g"))
			  {
				  // on descend par récurcivité 
				  this.ChargementElements(e);
			  }
			  
			  if(e.getName().equals("rect"))
			  {
				  // on récupère l'id de l'élement
				  String id = e.getAttributeValue("id");
				  // on récupère les position x et y
				  float x = Float.parseFloat(e.getAttributeValue("x"));
				  float y = Float.parseFloat(e.getAttributeValue("y"));
				  // on récupère la largeur et la hauteur
				  float width  = Float.parseFloat(e.getAttributeValue("width"));
				  float height = Float.parseFloat(e.getAttributeValue("height"));
				  // récupération de la transformation
				  String transform = e.getAttributeValue("transform");
				  Transform trans = Transform.IDENTITY;
				  if(transform != null && transform.contains("matrix"))
				  {
					  // la transformation est une matrice
					  String[] splitA = transform.split("\\(");
					  String temp = splitA[1].replace(")", "");
					  String[] values = temp.split(",");
					  float a00 = Float.parseFloat(values[0]);
					  float a01 = Float.parseFloat(values[1]);
					  float a02 = Float.parseFloat(values[2]);
					  float a10 = Float.parseFloat(values[3]);
					  float a11 = Float.parseFloat(values[4]);
					  float a12 = Float.parseFloat(values[5]);
					  float a20 = 0.0f;
					  float a21 = 0.0f;
					  float a22 = 1.0f;
					 // trans = new Transform(a00,a01,a02,a10,a11,a12,a20,a21,a22);
					  //trans = Transform.rotate(Transform.IDENTITY, 33.0f);
					  
				  }
				  
				  // création de l'élement
				  ElementBase b = new ElementBase(width,height,x,y);
				  // update de la matrice de transformation
				  b.setTrans(trans);
				  // ajout dans la liste
				  listElement.add(b);
			  }
				  
		  }

	}

	public List<IGameBase> getListElement() {
		return listElement;
	}

	public void setListElement(List listElement) {
		this.listElement = listElement;
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


	
	public void loadContent() {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
