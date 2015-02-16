// Vertex position (in model space)
attribute vec4 vPosition;

// Normal vector at vertex (in model space)
attribute vec3 vNormal;

// Light position is given in world space
uniform vec4 lightPosition;

// Light color
uniform vec4 lightColor;

// Diffuse reflection color
uniform vec4 diffuseColor, specularColor;

//specular exponent
uniform int specExp;

// Color to pass to fragment shader for interpolation
varying vec4 color;


void main()
{
    // View and projection matricies. Set to identity for now
    // FOr non-default, this, or parameters to form these, would be
    // passed in as uniform variables.
    mat4 modelMatrix = mat4 (1.0, 0.0, 0.0, 0.0, 
                            0.0,  1.0,  0.0, 0.0,
                            0.0,  0.0,  1.0, 0.0,
                            0.0,  0.0, -3.0, 1.0);
                            
    mat4 viewMatrix = mat4 (1.0,  0.0,  0.0,  0.0, 
                            0.0,  1.0,  0.0,  0.0,
                            0.0,  0.0,  1.0,  0.0,
                            0.0,  0.0,  0.0,  1.0);
                            
    mat4 projMatrix = mat4 (1.0,  0.0,  0.0,  0.0, 
                            0.0,  1.0,  0.0,  0.0,
                            0.0,  0.0,  0.11, 0.0,
                            0.0,  0.0,  0.0,  1.0);
                            
    mat4 modelViewMatrix = viewMatrix * modelMatrix;
    
    //mat4 normalMatrix = transpose (inverse (modelViewMatrix));

    // All vectors need to be converted to "eye" space
    // All vectors should also be normalized
    vec4 vertexInEye = modelViewMatrix * vPosition;
    vec4 lightInEye = viewMatrix * lightPosition;
    vec4 normalInEye = normalize(modelViewMatrix * vec4(vNormal, 0.0));
    
    vec3 L = normalize ((lightInEye - vertexInEye).xyz);
    vec3 N = normalize (normalInEye.xyz);
    
    vec4 viewCoord = vec4(0, 0, 0, 0);
    vec3 viewVector = normalize ((viewCoord - vertexInEye).xyz);
    vec3 reflectVector = reflect( normalize( (vertexInEye - lightInEye).xyz ) , N); 
    
    // calculate components
    vec4 diffuse = lightColor * ( diffuseColor * dot(N, L) + 
    			   specularColor * pow( max( dot( viewVector, reflectVector ),0.0 ), specExp) );
          
    // convert to clip space (like a vertex shader should)
    gl_Position =  vPosition;
    
    // Calculate color and pass to fragment shader
    color = diffuse;

}
