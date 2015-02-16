varying	vec2 texCoord;
uniform	sampler2D texture;

void main() 
{ 
  // replace with proper texture function
  	gl_FragColor = texture2D(texture, texCoord);			
} 
