package Entities;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

import bilou.Camera;
import bilou.PhysicWorld;
import CoreTexturesManager.TexturesManager;

public class SmallRobotView extends RobotViewBase 
{
	
	// class Sprite
	private Sprite spritePlayer;
	// vecteur des FloatRect d'animation
	private IntRect[] vectorAnim;
	// indice d'animation
	private int indAnim = 0;
	// indicemax d'anim
	private int indMaxAnim = 0;
	// Time Anim
	private Time timeAnim = Time.ZERO;
	
	
	public SmallRobotView()
	{
		// instance du PlayerControl
		pControl = new SmallRobotControl();
		// attachement au model MVC
		pControl.Attach(this);
	
	}

	
	public void Update(Time elapsedTime) 
	{
		// TODO Auto-generated method stub
		pControl.Update(elapsedTime);
		
		// update de l'animation
		spritePlayer.setTextureRect(vectorAnim[indAnim]);
		
		// choix de l'anim
		timeAnim = Time.add(elapsedTime, timeAnim);
		
		if(pControl.getTypeSens() == SmallRobotControl.SENS.DROITE)
		{
			if(timeAnim.asSeconds() > 1f/24f)
			{
				indAnim++;
				timeAnim = Time.ZERO;
				if(indAnim > 11)
				{
					indAnim = 0;
				}
			}
		}
		else if(pControl.getTypeSens() == SmallRobotControl.SENS.GAUCHE)
		{
			if(timeAnim.asSeconds() > 1f/24f)
			{
				indAnim++;
				timeAnim = Time.ZERO;
				if(indAnim > 23)
				{
					indAnim = 12;
				}
			}
		}
		
	}

	
	public void UpdateMVC() 
	{
		// appel callback venant du model mvc
		
		// on met à jour l'affichage du sprite
		Vector2f pos = new Vector2f(this.pControl.getBody().getPosition().x,this.pControl.getBody().getPosition().y);
		spritePlayer.setRotation((float) ((this.pControl.getBody().getAngle() * 180) / Math.PI) % 360);
		
		Vector2f tpos = Vector2f.mul(pos, PhysicWorld.getRatioPixelMeter());
		
		spritePlayer.setPosition(tpos);
		

		spritePlayer.setOrigin(new Vector2f(32,32));
		
		// test update camera
		if(this.pControl.isSelected)
		Camera.SetCenter(spritePlayer.getPosition());
	}

	
	public void LoadContent() 
	{
		// TODO Auto-generated method stub
		spritePlayer = new Sprite(TexturesManager.GetTextureByName("playerSmallRobot"));
		spritePlayer.setTextureRect(new IntRect(0,0,64,64));
		
		// taille de l'image
		Vector2i size = TexturesManager.GetTextureByName("playerSmallRobot").getSize();
		
		// initialisation du vecteur d'animation
		vectorAnim = new IntRect[24]; // 12 étant le nombre d'animation pour le player
		// on crée les floatrect
		int x = 0;
		int y = 0;
		for(int i=0;i<vectorAnim.length;i++)
		{
			vectorAnim[i] = new IntRect(x,y,64,64);
			x+=64;
			if(x>=size.x)
			{
				x=0;
				y+=64;
				if(y >= size.y)
				{
					break;
				}
			}
			
		}
		
		
		// on spécifie l'indice maximal de l'animation
		indMaxAnim = vectorAnim.length;
	}
	
	public void SetPosition(Vector2f pos)
	{
		this.pControl.setPositionPlayer(pos);
	}

	
	public void SetEvent(Event e) 
	{
		// TODO Auto-generated method stub
		pControl.SetEvent(e);
	}

	/**
	 * @return the spritePlayer
	 */
	public Sprite getSpritePlayer() {
		return spritePlayer;
	}

	/**
	 * @param spritePlayer the spritePlayer to set
	 */
	public void setSpritePlayer(Sprite spritePlayer) {
		this.spritePlayer = spritePlayer;
	}
	
	

}
