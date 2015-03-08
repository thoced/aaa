varying vec2 texCoords;
uniform sampler2D maTexture;

void main()
{
	gl_FragColor = texture2D(maTexture, texCoords);
	//gl_FragColor = vec4(1.0,0.5,1.0,1.0);
}
