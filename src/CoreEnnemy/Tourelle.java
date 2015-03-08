package CoreEnnemy;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Rot;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import CoreFx.BaseFx;
import CorePlayer.PlayerManager;
import CoreTexturesManager.TexturesManager;
import bilou.PhysicWorld;

public class Tourelle extends BaseFx
{ 
	// Body de la tourelle
	private Body bodyTourelle;
	// sprite socle tourelle
	private Sprite socleSprite;
	// sprite tourelle
	private Sprite tourelleSprite;
	// textu
	private Texture tourelleTexture;
	// sprite lazer
	private Texture laserTexture;
	private Sprite laserSprite;
	// lenght laser
	private float lenghtLaser;
	// private lenght2
	private float lenghtLaser2;
	// tir ?
	private boolean isFire = false;
	// private tourelle target raycasting
	private TourelleTargetCallBack targetContact;
	// angle de la tourelle
	private float angleTourelle;
	// intrect des impacte
	private IntRect[] textRect = new IntRect[20];
	// indice d'animaiton
	private int indAnimation = 0;
	// sprite de l'impact
	private Sprite impactSprite;
	// temps d'animation
	private Time timeAnimation = Time.ZERO;
	
	// en charge
	private boolean isCharging = false;
	
	
	
	public Tourelle(Vector2f pos)
	{
		// création du targetcontact
		this.targetContact = new TourelleTargetCallBack();
		// création du bodydef
		BodyDef bdef = new BodyDef();
		bdef.active = true;
		bdef.bullet = false;
		bdef.fixedRotation = false;
		bdef.position = PhysicWorld.convertToM2VEC(pos);
		// shape
		CircleShape shape = new CircleShape();
		shape.m_radius = 1.5f;
		// Création du body
		bodyTourelle = PhysicWorld.getWorldPhysic().createBody(bdef);
		// fixturedef
		FixtureDef fDef = new FixtureDef();
		fDef.density = 1f;
		fDef.friction = 1f;
		fDef.restitution = 0f;
		fDef.shape = shape;
		// fixture
		bodyTourelle.createFixture(fDef);
		// création des sprites
		tourelleTexture = TexturesManager.GetTextureByName("Tourelle_01v3.png");
		laserTexture = TexturesManager.GetTextureByName("laser02.png");
		// création des sprites
		socleSprite = new Sprite(tourelleTexture);
		socleSprite.setTextureRect(new IntRect(0,135,230,182));
		tourelleSprite = new Sprite(tourelleTexture);
		tourelleSprite.setTextureRect(new IntRect(0,0,180,134));
		laserSprite = new Sprite(laserTexture);
		// origine
		Vector2f ori = new Vector2f(115f,95f);
		socleSprite.setOrigin(ori);
		Vector2f ori2 = new Vector2f(34f,tourelleSprite.getTextureRect().height / 2);
		tourelleSprite.setOrigin(ori2);
		laserSprite.setOrigin(new Vector2f(0,7));
		// création des intrect pour l'animation du contacdt
		int i=0;
		for(int y=0;y<10;y++)
		{
			for(int x=0;x<2;x++)
			{
				textRect[i] = new IntRect(x * 167,314 + (y * 167),167,167);
				i++;
			}
		}
		// impacte sprite
		impactSprite = new Sprite(tourelleTexture);
		impactSprite.setOrigin(new Vector2f(83f,83f));
		impactSprite.setTextureRect(textRect[0]);
	
	}

	
	
	public void update(Time deltaTime) 
	{
		// TODO Auto-generated method stub
		
		super.update(deltaTime);
		
		this.isFire = false;
		// reception du body du robot
		Vec2 actorPos = PlayerManager.getSmallRobot().getBody().getPosition().clone();
		Vec2 posT = this.bodyTourelle.getPosition().clone();
	
		// création du vecteur de direction
		Vec2 dir = actorPos.sub(posT);
	    lenghtLaser = dir.length();
	    if(this.lenghtLaser < 24f) // si la distance entre la tourelle et le rbot est inférieur à 32
	    {
		//	dir.normalize();		
			// création de l'objet rotation	// indice d'animation
			Rot r = new Rot();
			r.s = dir.y;
			r.c = dir.x;
			float angle = r.getAngle(); // angle à approcher
			this.angleTourelle = lerp(0.015f,this.angleTourelle,angle); // angle de la tourelle déterminé
			
			// compute
			this.bodyTourelle.setTransform(this.bodyTourelle.getPosition(), this.angleTourelle);
				// création de vecteur de direction
				Rot rd = new Rot();
				rd.set(this.angleTourelle);
				Vec2 d = new Vec2(rd.c,rd.s);		
				// on défini le vecteur de direction
				d = d.mul(24f);
				// on test les collisio
				this.bodyTourelle.getWorld().raycast(this.targetContact,this.bodyTourelle.getPosition(), this.bodyTourelle.getPosition().add(d));
				if(this.targetContact.getContact() != null && this.targetContact.getContact() == PlayerManager.getSmallRobot().getBody())
				{
					// on applique de la force sur le robot
					d.normalize();
					this.targetContact.getContact().applyForceToCenter(d.mul(192));
					
					this.isFire = true;
					// il y a un contact on détermine la distnace avec le contact
					this.lenghtLaser = this.targetContact.getPos().sub(this.bodyTourelle.getPosition()).length(); 
					
					this.targetContact.setContact(null);
					
				if(timeAnimation.asSeconds() > 1f/24f)
				{
					this.indAnimation ++;
					timeAnimation = Time.ZERO;
				}
				
				if(this.indAnimation > 18)
					this.indAnimation = 0;
				
				// ajout du time pour l'animation
				timeAnimation = Time.add(timeAnimation, deltaTime);
				// choix de l'animation
				impactSprite.setTextureRect(this.textRect[this.indAnimation]);
					
				}
				else
					this.isFire = false;
		}
		else
		{
			this.isFire = false; // on ne tire plus
			// on positionne la tourelle en position neutre
			this.angleTourelle = lerp(0.02f,this.angleTourelle,0f);
			this.bodyTourelle.setTransform(this.bodyTourelle.getPosition(), this.angleTourelle);
		}
	}

	
	
	public void draw(RenderTarget render, RenderStates state) 
	{
		// TODO Auto-generated method stub
		super.draw(render, state);
		
		tourelleSprite.setRotation((float)((bodyTourelle.getAngle()  * 180f) / Math.PI) % 360f);
		
		// laser
		if(this.isFire /* && this.lenghtLaser < 20f*/) 
		{// on tir
		
			laserSprite.setScale(this.lenghtLaser * (PhysicWorld.getRatioPixelMeter() / 16f),1f);
			laserSprite.setPosition(PhysicWorld.convertToPixels(bodyTourelle.getPosition()));
			laserSprite.setRotation(tourelleSprite.getRotation());// appel a la methode draw de l'entites manager
			render.draw(laserSprite);
			
			// affichage de l'impacte
			impactSprite.setPosition(PhysicWorld.convertToPixels(this.targetContact.getPos()));
			render.draw(impactSprite);
		}
		
		// affichage de la tourelle

		//tourelleSprite.setRotation(arg0);
		//tourelleSprite.setRotation(bodyTourelle.getAngle());
		tourelleSprite.setPosition(PhysicWorld.convertToPixels(bodyTourelle.getPosition()));
		render.draw(tourelleSprite);
		// affichage du socle
		socleSprite.setPosition(PhysicWorld.convertToPixels(bodyTourelle.getPosition()));
		render.draw(socleSprite);
		

		
	}
	
	private float lerp(float value, float start, float end)
	{
	    return start + (end - start) * value;
	}
	
	
	
	
	
}
