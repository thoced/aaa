varying vec2 vTexCoord;

void main(void)
{
  
   gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
   vTexCoord = gl_TexCoord[0].st;
}