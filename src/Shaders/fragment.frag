uniform sampler2D maTexture;
uniform float deltaTime;
varying vec2 vTexCoord;


void main()
{
 // gl_FragColor = gl_Color - (((gl_FragCoord.x ) / 16.0) * vec4(0.01,0.05,0.05,0.0));
 
 gl_FragColor =  texture2D(maTexture,gl_TexCoord[0].st).rgba * deltaTime;
 
   //gl_FragColor = texture2D(backTexture,gl_TexCoord[0].st).rgba + texture2D(maTexture,gl_TexCoord[0].st).rgba * 0.5 * deltaTime;
 
 }
