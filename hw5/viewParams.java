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


import javax.media.opengl.*;
import javax.media.opengl.fixedfunc.*; 

public class viewParams
{
    
	/**
	 * constructor
	 */
	public viewParams()
	{
        
	}
    
    
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
        // Add your code here.
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
    public void setUpOrtho (int program, GL2 gl2)
    {
        // Add your code here.
    }

	
}
