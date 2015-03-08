package CoreEnnemy;

import java.util.ArrayList;
import java.util.List;

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

public class TourelleBiCanon extends BaseFx
{ 
	// Body de la tourelle
	private Body bodyTourelle;
	// sprite tourelle
	private Sprite tourelleSprite;
	// textu
	private Texture tourelleTexture;
	// sprite lazer
	private Texture fireBallTexture;
	private Sprite laserSprite;
	// lenght laser
	// tir ?
	private boolean isFire = false;
	// private tourelle target raycasting
	private TourelleTargetCallBack targetContact;
	// angle de la tourelle
	private float angleTourelle;
	// temps d'animation
	private Time timeAnimation = Time.ZERO;
	
	// en charge
	private boolean isCharging = false;
	
	// liste des boules de feu
	private List<FireBall> listBalls;
	// liste des ball à supprimer
	private List<FireBall> listRemoveBalls;
	
	
	
	public TourelleBiCanon(Vector2f pos)
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
		tourelleTexture = TexturesManager.GetTextureByName("Tourelle_02.png");
		fireBallTexture = TexturesManager.GetTextureByName("projectile01.png");
		// création des sprites
		tourelleSprite = new Sprite(tourelleTexture);
		tourelleSprite.setTextureRect(new IntRect(0,0,140,138));
		
		// origine
		Vector2f ori = new Vector2f(70f,69f);
		tourelleSprite.setOrigin(ori);
	
		// création de la list des fireball
		this.listBalls = new ArrayList<FireBall>();
		// création de la list à supprimer
		this.listRemoveBalls = new ArrayList<FireBall>();
		
		
	}
		
	public void destroyBall(FireBall ball)
	{
		// suppression
		this.listRemoveBalls.add(ball);
	}
	
	
	public void update(Time deltaTime) 
	{
		// TODO Auto-generated method stub
		
		super.update(deltaTime);
		// on détermine le vecteur de difference
		Vec2 diff = PlayerManager.getSmallRobot().getBody().getPosition().sub(this.bodyTourelle.getPosition());
		// distance entre la tourelle et le robot
		float lenght = diff.length();
		// normalisation du vecteur de différence
		diff.normalize();
		// on détermine l'angle de rotation
		Rot rot = new Rot();
		rot.c = diff.x;
		rot.s = diff.y;
		float angle = rot.getAngle();
		// création de l'angle lerp
		angleTourelle = lerp(0.02f,angleTourelle,angle);
		
		if(Math.abs(this.angleTourelle - angle) < 0.1f) // la tourelle vise en direction du robo
		{
			// on lance un rayon
			this.bodyTourelle.getWorld().raycast(this.targetContact, this.bodyTourelle.getPosition(), diff.mul(32f));
			// si le robot est touché par le rayon, on tir
			if(this.targetContact.getContact() != null && this.targetContact.getContact() == PlayerManager.getSmallRobot().getBody())
			{
				// on tir
				this.isFire = true;
				// on tire une ball
				Sprite sb = new Sprite(fireBallTexture);
				sb.setOrigin(new Vector2f(44f,44f));
				FireBall ball = new FireBall(this,sb,this.bodyTourelle.getPosition().add(diff.mul(4f)),diff.mul(32f));
				this.listBalls.add(ball);
			}
		}
		
		// transform du body
		this.bodyTourelle.setTransform(this.bodyTourelle.getPosition(), this.angleTourelle);
		
		// appel au update de la liste
		for(FireBall ball : this.listBalls)
			ball.update(deltaTime);
		
		// appel a la methode update de la liste ball
		this.listBalls.removeAll(this.listRemoveBalls);
		// clear de la liste de suppression
		this.listRemoveBalls.clear();
			
	}

	
	
	public void draw(RenderTarget render, RenderStates state) 
	{
		// TODO Auto-generated method stub
		super.draw(render, state);
		
		tourelleSprite.setRotation((float)((bodyTourelle.getAngle()  * 180f) / Math.PI) % 360f);
		// affichage de la tourelle

		//tourelleSprite.setRotation(arg0);
		//tourelleSprite.setRotation(bodyTourelle.getAngl());
		tourelleSprite.setPosition(PhysicWorld.convertToPixels(bodyTourelle.getPosition()));
		render.draw(tourelleSprite);
	
		// on boucle dans le rendu des 
		for(FireBall b : this.listBalls)
			b.draw(render, state);

		
	}
	
	private float lerp(float value, float start, float end)
	{
	    return start + (end - start) * value;
	}
	
	
	
	
	
}
