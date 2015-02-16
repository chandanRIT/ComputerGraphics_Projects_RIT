attribute vec4 vPosition;

uniform vec3 eyePoint, lookAt, upVector, projNF;
uniform int scaleFactor;
uniform ivec2 translateZX;
uniform vec2 rotZY;
uniform vec4 projLRTB;

vec3 u , v, n;
mat4 tMatrix, rMatrix1, rMatrix2, translateMatrix, scaleMatrix, orthoMatrix;

void main()
{
	n = normalize(eyePoint-lookAt);
	u = normalize(cross(upVector, n));
	v = normalize(cross(n, u)); 
	
	tMatrix = mat4(	u.x, v.x, n.x, 0,
			 	  	u.y, v.y, n.y, 0,	
			   		u.z, v.z, n.z, 0,
	-dot(u, eyePoint), -dot(v, eyePoint), -dot(n, eyePoint), 1);
	
	scaleMatrix = mat4(1,0,0,0,
					   0,scaleFactor,0,0,
					   0,0,1,0,
					   0,0,0,1);
	
	rMatrix1 = mat4(cos(rotZY.x), sin(rotZY.x), 0, 0,
					-sin(rotZY.x), cos(rotZY.x), 0, 0,
					0, 0, 1, 0,
					0, 0, 0, 1);
	
	rMatrix2 = mat4(cos(rotZY.y), 0, -sin(rotZY.y), 0,
					0, 1, 0, 0,
					sin(rotZY.y), 0, cos(rotZY.y), 0,
					0, 0, 0, 1);
	
	translateMatrix = mat4(1, 0, 0, 0,
						   0, 1, 0, 0,
						   0, 0, 1, 0,	
						   translateZX.y, 0, translateZX.x, 1 );
	if(projNF.z == 0.0){
		orthoMatrix = mat4(2.0*projNF.x/(projLRTB.y-projLRTB.x), 0.0, 0.0, 0.0,
						0.0, 2.0*projNF.x/(projLRTB.z-projLRTB.w), 0.0, 0.0,
						(projLRTB.y+projLRTB.x)/(projLRTB.y-projLRTB.x),(projLRTB.z+projLRTB.w)/(projLRTB.z-projLRTB.w),-(projNF.y+projNF.x)/(projNF.y-projNF.x),-1.0,
						0.0, 0.0, -2.0*projNF.y*projNF.x/(projNF.y-projNF.x), 0.0);
	}
	else {
		orthoMatrix = mat4(2.0/(projLRTB.y-projLRTB.x), 0.0, 0.0, 0.0,
						0.0, 2.0/(projLRTB.z-projLRTB.w), 0.0, 0.0,
						0.0, 0.0, -2.0/(projNF.y-projNF.x),0.0,
						-(projLRTB.y+projLRTB.x)/(projLRTB.y-projLRTB.x), -(projLRTB.z+projLRTB.w)/(projLRTB.z-projLRTB.w), -(projNF.y+projNF.x)/(projNF.y-projNF.x), 1.0);	
	}
	
	gl_Position =  orthoMatrix * tMatrix * translateMatrix * rMatrix2 * rMatrix1  * scaleMatrix * vPosition;
}
