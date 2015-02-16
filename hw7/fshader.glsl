// color passed in from vertex shader
varying vec4 color;


void main()
{
    // set the final color
    gl_FragColor = color;

}
