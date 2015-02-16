

import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import java.io.*;


public class shaderProgram
{
	/**
	 * constructor
	 */
	public shaderProgram()
	{
	}
    
    /**
     * reads in text from a file and returns as a string.
     */
    private String textFileRead (String filePath) throws IOException
    {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(
                                                   new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }
    
    public void printShaderInfoLog(GL2 gl2, int obj)
    {
        int infologLength[] = new int[1];
        int charsWritten[] = new int[1];
        
        
        gl2.glGetShaderiv(obj, GL2.GL_INFO_LOG_LENGTH, infologLength, 0);
        
        if (infologLength[0] > 0)
        {
            byte infoLog[] = new byte[infologLength[0]];
            gl2.glGetShaderInfoLog(obj, infologLength[0], charsWritten, 0, infoLog, 0);
            System.err.println (new String(infoLog));
        }
    }

    
    public void printProgramInfoLog(GL2 gl2, int obj)
    {
        int infologLength[] = new int[1];
        int charsWritten[] = new int[1];
        

        gl2.glGetShaderiv(obj, GL2.GL_INFO_LOG_LENGTH, infologLength, 0);
        
        if (infologLength[0] > 0)
        {
            byte infoLog[] = new byte[infologLength[0]];
            gl2.glGetProgramInfoLog(obj, infologLength[0], charsWritten, 0, infoLog, 0);
            System.err.println (new String(infoLog));
        }
    }
    
	
	/**
	 * readAndCompileShaders
	 */
	public int readAndCompile (GL2 gl2, String vert, String frag)
	{
        // read in shader source
        String vs, fs;
        
        // create the shader
        int the_vert = gl2.glCreateShader (GL2ES2.GL_VERTEX_SHADER);
        int the_frag = gl2.glCreateShader (GL2ES2.GL_FRAGMENT_SHADER);
        
        // read in shader source
        try {
            vs = textFileRead (vert);
        }
        catch (IOException E) {
            System.err.println ("Error reading vertex shader file " + vert);
            return 0;
        }
        try {
            fs = textFileRead (frag);
        }
        catch (IOException E) {
            System.err.println ("Error reading fragment shader file " + vert);
            return 0;
        }
        
        
        // fill in the shader source
        String source[] = new String[1];
        int len[] = new int[1];
        source[0] = vs;
        len[0] = vs.length();
        gl2.glShaderSource (the_vert, 1, source, len, 0);
        source[0] = fs;
        len[0] = fs.length();
        gl2.glShaderSource (the_frag, 1, source, len, 0);
        
        // Compile the shader
        gl2.glCompileShader (the_vert);
        printShaderInfoLog (gl2, the_vert);
        gl2.glCompileShader (the_frag);
        printShaderInfoLog (gl2, the_frag);
        
        // Create the program and attach your shader
        int the_program = gl2.glCreateProgram();
        gl2.glAttachShader(the_program, the_vert);
        gl2.glAttachShader(the_program, the_frag);
        printProgramInfoLog(gl2, the_program);
        
        // Link the program
        gl2.glLinkProgram(the_program);
        printProgramInfoLog(gl2, the_program);
        
		return the_program;
	}
}
