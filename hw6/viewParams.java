/**
 *
 * viewParams.java
 *
 * Simple class for setting up the viewing and projection transforms for the Transformation
 * Assignment.
 *
 * Students are to complete this class.
 *
 */

import javax.media.opengl.GL2;

public class viewParams
{
    /**
     * This functions sets up the view and projection parameter for a frustrum
     * projection of the scene. See the assignment description for the values
     * for the projection parameters.
     *
     * You will need to write this function, and maintain all of the values needed
     * to be sent to the vertex shader.
     *
     * @param program - The ID of an OpenGL (GLSL) program to which parameter values
     *    are to be sent
     *
     * @param gl2 - GL2 object on which all OpenGL calls are to be made
     *
     */
    public void setUpFrustrum (int program, GL2 gl2)
    {
       setup(program, gl2, false);
    }
    
    /**
     * This functions sets up the view and projection parameter for an orthographic
     * projection of the scene. See the assignment description for the values
     * for the projection parameters.
     *
     * You will need to write this function, and maintain all of the values needed
     * to be sent to the vertex shader.
     *
     * @param program - The ID of an OpenGL (GLSL) program to which parameter values
     *    are to be sent
     *
     * @param gl2 - GL2 object on which all OpenGL calls are to be made
     *
     */
    public void setUpOrtho(int program, GL2 gl2)
    {
    	setup(program, gl2, true);
    }

    /**
     * 
     * @param program - The ID of an OpenGL (GLSL) program to which parameter values
     *    are to be sent
     * 
     * @param gl2 - GL2 object on which all OpenGL calls are to be made
     * 
     * @param isOrtho : set true for ortho-projection and false for frustrum-projection 
     */
    void setup(int program, GL2 gl2, boolean isOrtho){
    	//setting up the ids
    	int eyePointId = gl2.glGetUniformLocation( program, "eyePoint"),
        	lookAtId = gl2.glGetUniformLocation( program, "lookAt"),
        	upVectorId = gl2.glGetUniformLocation( program, "upVector"),
        	scaleFactorId = gl2.glGetUniformLocation( program, "scaleFactor"),
        	rotZY_Id = gl2.glGetUniformLocation( program, "rotZY"),
        	translateZX_Id = gl2.glGetUniformLocation( program, "translateZX"),
        	projLRTB_Id = gl2.glGetUniformLocation( program, "projLRTB"),
        	projNF_Id = gl2.glGetUniformLocation( program, "projNF");
    		//projType_Id = gl2.glGetUniformLocation( program, "projType");
    	
        //sending View transform data	
    	gl2.glUniform3f(eyePointId, 0, 3, 3);
    	gl2.glUniform3f(lookAtId, 1, 0, 0);
    	gl2.glUniform3f(upVectorId, 0, 1, 0);
    	
    	//sending Model transform data
    	gl2.glUniform1i(scaleFactorId, 5); //scaling factor
    	gl2.glUniform2f(rotZY_Id,  (float)Math.toRadians(90), (float)Math.toRadians(30)); //rotation angles around z and y
    	gl2.glUniform2i(translateZX_Id, -1, 1); // translation along z and x
    	
    	//sending Projection Data
    	gl2.glUniform4f(projLRTB_Id, -1.5f, 1.0f, 1.5f, -1.5f); //Left, Right, Top, Bottom : in the respective order
    	gl2.glUniform3f(projNF_Id, 1.0f, 8.5f, isOrtho ? 1.0f : 0.0f); //Near, Far, Ortho/Frustrum 
    }
	
}
