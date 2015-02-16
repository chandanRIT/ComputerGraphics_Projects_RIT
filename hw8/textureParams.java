/**
 *
 * textureParams.java
 *
 * Simple class for setting up the textures for the textures
 * Assignment.
 *
 * Students are to complete this class.
 *
 */

import java.io.IOException;
import java.io.InputStream;

import javax.media.opengl.GL2;
import javax.media.opengl.GLProfile;

import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class textureParams
{
    /**
     * This functions loads texture data to the GPU.
     *
     * You will need to write this function, and maintain all of the values needed
     * to be sent to the various shaders.
     *
     * @param filename - The name of the texture file.
     *
     */
    public void loadTexture (String filename)
    {
    	try {
    	InputStream stream = getClass().getResourceAsStream("/texture.jpg");
    	TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "jpg");
    	TextureIO.newTexture(data);
    	}
    	catch (IOException exc) {
    	// Do error handling here!
    		System.out.println("error reading tex-image");
    		return;
    	}
    }

    
    /**
     * This functions sets up the parameters
     * for texture use.
     *
     * You will need to write this function, and maintain all of the values needed
     * to be sent to the various shaders.
     *
     * @param program - The ID of an OpenGL (GLSL) program to which parameter values
     *    are to be sent
     *
     * @param gl2 - GL2 object on which all OpenGL calls are to be made
     *
     */
    public void setUpTextures (int program, GL2 gl2)
    {
    	gl2.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
    	gl2.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
    }
}
