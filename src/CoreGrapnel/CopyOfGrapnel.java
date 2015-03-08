package CoreGrapnel;


import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.JointEdge;
import org.jbox2d.dynamics.joints.JointType;
import org.jbox2d.dynamics.joints.PulleyJointDef;
import org.jbox2d.dynamics.joints.WeldJointDef;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Vertex;
import org.jsfml.graphics.VertexArray;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

import bilou.ICoreBase;
import bilou.PhysicWorld;

public class CopyOfGrapnel implements ICoreBase, Drawable 
{
	// class de création du grappin
	
	// class body alpha (accroche du debut)
	private Body 	alphaBody;
	// class body bravo
	private Body 	braboBody;
	// Nombre de noeuds dans le grappin
	private int 	nbNode;
	// list contenant l'ensemble des nodes
	private List<Vec2> listNodes;
	// VertexArray
	private VertexArray vectors;
	
	
	public CopyOfGrapnel(Body alpha,Body bravo,Vec2 point, int nbNode)
	{
		
		// on instancie le vertex array
		vectors = new VertexArray(PrimitiveType.LINE_STRIP);
		// affectation des variable
		this.alphaBody = alpha;
		this.braboBody = bravo;
		// affectation du nombre de noeuds
		this.nbNode = nbNode;
		if(this.nbNode < 1)this.nbNode = 1; // on évite ainsi de diviser ensuite par 0
		// instance de la liste
		listNodes = new ArrayList<Vec2>(this.nbNode);
		// on ajoute le node de départ
		listNodes.add(this.alphaBody.getPosition());
		// création de la liste des node virtuel
		// on détermine le vecteur qui relie le alpha et le bravo
		Vec2 posAlpha = this.alphaBody.getPosition();
		Vec2 posBravo = point;//this.braboBody.getPosition();
		Vec2  diff = posBravo.sub(posAlpha);
		// obtention de la longueur entre l'alpha et le bravo
		float length = diff.length();
		// on normalise pour obtenir le vecteur direction
		diff.normalize();
		// on connait la distance et le nombre de noeuds voulu, on divise pour obtenir l'intervale
		// entre chaque noeud virtuel
		float lengthNode = length / this.nbNode;
		
		// on crée la position de départ
		Vec2 pos = posAlpha;
		// on place le premier body node
		Body Bodynode = this.alphaBody;
		
		// on boucle au nombre de node voulu
		/*for(int i=1;i<=this.nbNode;i++)
		{
			// on crée la position du node
			pos = pos.add(diff.mul(lengthNode * i));
			// on crée le node intermédiaire et on le récupère
			Bodynode = this.createNode(pos,lengthNode,Bodynode);
			// on ajoute dans la liste
			listNodes.add(Bodynode.getPosition());
		}*/
		// on ajoute dans la liste le dernier Node
		listNodes.add(posBravo);
		// on relie les tout au Bravo final
		
		// on éer le body final qui sera attaché 
		WeldJointDef wd = new WeldJointDef();
		wd.bodyA = Bodynode;
		wd.bodyB = this.braboBody;
		wd.initialize(Bodynode, braboBody, point);
		PhysicWorld.getWorldPhysic().createJoint(wd);
		
		
		
		DistanceJointDef distanceDef = new DistanceJointDef();
		distanceDef.initialize(Bodynode, this.braboBody, Bodynode.getWorldCenter(), point);
		distanceDef.length = lengthNode;
		distanceDef.collideConnected = false;
		distanceDef.frequencyHz = 10;
		distanceDef.dampingRatio = 10f;
		distanceDef.type = JointType.DISTANCE;

		// création du joint
		DistanceJoint distance = (DistanceJoint) PhysicWorld.getWorldPhysic().createJoint(distanceDef);
		
	}
	
	private Body createNode(Vec2 pos,float lenght,Body bodyNodePrevious)
	{
		// creatin du body def
		BodyDef def = new BodyDef();
		def.active = true;
		def.bullet = false;
		def.fixedRotation = true;
		def.position = pos;
		def.type = BodyType.DYNAMIC;
		
		// création du body
		Body body = PhysicWorld.getWorldPhysic().createBody(def);
		// création du shape
		CircleShape circle = new CircleShape();
		circle.m_radius = 0.1f;
		// Fixture definition
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1f;
		fixtureDef.friction = 1f;
		fixtureDef.restitution = 0f;
		fixtureDef.isSensor = true;
		fixtureDef.shape = circle;
		// creation du fixture
		body.createFixture(fixtureDef);
		// on retourne le body créé
		
				
		// il faut accroche le nouveau body avec le body previous
		DistanceJointDef distanceDef = new DistanceJointDef();
		distanceDef.initialize(bodyNodePrevious,body,bodyNodePrevious.getWorldCenter(),body.getWorldCenter());
		distanceDef.length = lenght;
		distanceDef.collideConnected = false;
		distanceDef.frequencyHz = 10;
		distanceDef.dampingRatio = 10f;
		distanceDef.type = JointType.DISTANCE;
		// création du joint
		DistanceJoint distance = (DistanceJoint) PhysicWorld.getWorldPhysic().createJoint(distanceDef);
		// retour du body
		return body;
		
	}
	
	public void destroyGrapnel()
	{
		// on boucle dans la liste des nodes pour détruire les jointure
		/*for(Vec2 b : this.listNodes)
		{
			// on détruit le body également
			if(b.getJointList() != null)
				PhysicWorld.getWorldPhysic().destroyJoint(b.getJointList().joint);
			if(b != this.alphaBody)
				PhysicWorld.getWorldPhysic().destroyBody(b);
		}*/
		
		if(this.alphaBody != null && this.braboBody != null)
		{
		
			if(this.alphaBody.getJointList() != null)
				PhysicWorld.getWorldPhysic().destroyJoint(this.alphaBody.getJointList().joint);
			if(this.braboBody.getJointList() != null)
				PhysicWorld.getWorldPhysic().destroyJoint(this.braboBody.getJointList().joint);
			
			PhysicWorld.getWorldPhysic().destroyBody(this.braboBody);
		}
		// on vide la liste
		this.listNodes.clear();
	}
	
	
	public void draw(RenderTarget render, RenderStates state) 
	{
		// on vide le vectors
		vectors.clear();
		// on boucle dans la liste des nodes pour crée les point de la ligne
		for(Vec2 b : listNodes)
		{
			// on crée un vertex
			Vertex v = new Vertex(new Vector2f(b.x * PhysicWorld.getRatioPixelMeter(),b.y * PhysicWorld.getRatioPixelMeter()),Color.WHITE);
			// on ajoute dans le vectors
			vectors.add(v);
		}
		
		// render
		render.draw(vectors,state);
		
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
