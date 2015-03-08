package CoreLoader;

import org.jsfml.graphics.Sprite;

public class TiledLayerImages 
{
	// nom du layer image
		private String name = null;
		
	// coordonnée d'affichage de l'image
		private float posx,posy;
		
	// taille de l'image
		private int width,height;
	
	// chemin absolu de l'image
		private String pathImage = null;
		
	// type 
		private String type = null;
		
	// typeCalque
		private String typeCalque = null;
		
	// targetX et targetY (pour les déplacements dynamiques)
		private float targetX,targetY;
		
	// speed
		private float speed;  // m/s
	// danger -> détermine si l'objet en question est un danger pour le player
		private boolean danger;
	// masse
		private float masse;
	// sens (de rotation)  true -> sens horlogé, false -> sens anti-horlogé
		private boolean sensRotation; 
	// taille de la tiles d'animation
		private int widthAnimation,heightAnimation;
	// nombre de tiles pour l'animation
		private int nombreAnimation;
	// ligthmap
		private boolean  lightmap;
	// alpha
		private float alpha;
	// rotation
		private boolean rotation;
	

		
	
		
		/**
		 * @return the lightmap
		 */
		public boolean isLightmap() {
			return lightmap;
		}




		/**
		 * @param lightmap the lightmap to set
		 */
		public void setLightmap(boolean lightmap) {
			this.lightmap = lightmap;
		}




		/**
		 * @return the alpha
		 */
		public float getAlpha() {
			return alpha;
		}




		/**
		 * @param alpha the alpha to set
		 */
		public void setAlpha(float alpha) {
			this.alpha = alpha;
		}




		/**
		 * @return the rotation
		 */
		public boolean isRotation() {
			return rotation;
		}




		/**
		 * @param rotation the rotation to set
		 */
		public void setRotation(boolean rotation) {
			this.rotation = rotation;
		}



		public float getPosx() {
			return posx;
		}

		
	

		/**
		 * @return the widthAnimation
		 */
		public int getWidthAnimation() {
			return widthAnimation;
		}




		/**
		 * @param widthAnimation the widthAnimation to set
		 */
		public void setWidthAnimation(int widthAnimation) {
			this.widthAnimation = widthAnimation;
		}




		/**
		 * @return the heightAnimation
		 */
		public int getHeightAnimation() {
			return heightAnimation;
		}




		/**
		 * @param heightAnimation the heightAnimation to set
		 */
		public void setHeightAnimation(int heightAnimation) {
			this.heightAnimation = heightAnimation;
		}



		/**
		 * @return the nombreAnimation
		 */
		public int getNombreAnimation() {
			return nombreAnimation;
		}




		/**
		 * @param nombreAnimation the nombreAnimation to set
		 */
		public void setNombreAnimation(int nombreAnimation) {
			this.nombreAnimation = nombreAnimation;
		}




		/**
		 * @return the masse
		 */
		public float getMasse() {
			return masse;
		}

		/**
		 * @param masse the masse to set
		 */
		public void setMasse(float masse) {
			this.masse = masse;
		}

		/**
		 * @return the sensRotation
		 */
		public boolean isSensRotation() {
			return sensRotation;
		}

		/**
		 * @param sensRotation the sensRotation to set
		 */
		public void setSensRotation(boolean sensRotation) {
			this.sensRotation = sensRotation;
		}

		/**
		 * @return the danger
		 */
		public boolean isDanger() {
			return danger;
		}

		/**
		 * @param danger the danger to set
		 */
		public void setDanger(boolean danger) {
			this.danger = danger;
		}

		/**
		 * @return the typeCalque
		 */
		public String getTypeCalque() {
			return typeCalque;
		}

		/**
		 * @param typeCalque the typeCalque to set
		 */
		public void setTypeCalque(String typeCalque) {
			this.typeCalque = typeCalque;
		}

		/**
		 * @return the speed
		 */
		public float getSpeed() {
			return speed;
		}

		/**
		 * @param speed the speed to set
		 */
		public void setSpeed(float speed) {
			this.speed = speed;
		}

		/**
		 * @return the targetX
		 */
		public float getTargetX() {
			return targetX;
		}

		/**
		 * @param targetX the targetX to set
		 */
		public void setTargetX(float targetX) {
			this.targetX = targetX;
		}

		/**
		 * @return the targetY
		 */
		public float getTargetY() {
			return targetY;
		}

		/**
		 * @param targetY the targetY to set
		 */
		public void setTargetY(float targetY) {
			this.targetY = targetY;
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

		/**
		 * @param f the posx to set
		 */
		public void setPosx(float f) {
			this.posx = f;
		}

		/**
		 * @return the posy
		 */
		public float getPosy() {
			return posy;
		}

		/**
		 * @param f the posy to set
		 */
		public void setPosy(float f) {
			this.posy = f;
		}

		/**
		 * @return the width
		 */
		public int getWidth() {
			return width;
		}

		/**
		 * @param width the width to set
		 */
		public void setWidth(int width) {
			this.width = width;
		}

		/**
		 * @return the height
		 */
		public int getHeight() {
			return height;
		}

		/**
		 * @param height the height to set
		 */
		public void setHeight(int height) {
			this.height = height;
		}

		/**
		 * @return the pathImage
		 */
		public String getPathImage() {
			return pathImage;
		}

		/**
		 * @param pathImage the pathImage to set
		 */
		public void setPathImage(String pathImage) {
			this.pathImage = pathImage;
		}

		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}

		/**
		 * @param type the type to set
		 */
		public void setType(String type) {
			this.type = type;
		}
		
		
}
