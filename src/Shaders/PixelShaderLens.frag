uniform sampler2D texture;
uniform sampler2D backtexture;

void main()
{
    // récupère le pixel dans la texture

	//vec2 coordPixel = gl_Position;

    vec4 pixel = texture2D(texture,gl_TexCoord[0].xy);
    
    vec4 pixelback = texture2D(backtexture,gl_TexCoord[0].xy);

    // et multiplication avec la couleur pour obtenir le pixel final
    gl_FragColor = (gl_Color * pixel) * pixelback;
}
