

import java.awt.*;
import java.nio.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.*; 


public class HelloOGL implements GLEventListener, KeyListener
{	
	/**
	 * shader info
	 */
	private shaderProgram myShaders;
	private int shaderProgID = 0;
    private boolean updateNeeded = true;
    
    
	/**
	 * buffer info
	 */
    private int vbuffer;
    private int ebuffer;
    
    /**
     * geometry data
     */
    private float datapoints[] = {  0.25f, -0.75f, 0.0f, 1.0f,
                                    0.50f, -0.75f, 0.0f, 1.0f,
                                    0.25f,  0.15f, 0.0f, 1.0f,
    
                                    0.50f, -0.75f, 0.0f, 1.0f,
                                    0.50f,  0.15f, 0.0f, 1.0f,
                                    0.25f,  0.15f, 0.0f, 1.0f,
    
                                    0.25f,  0.25f, 0.0f, 1.0f,
                                    0.50f,  0.25f, 0.0f, 1.0f,
                                    0.25f,  0.50f, 0.0f, 1.0f,
    
                                    0.50f,  0.25f, 0.0f, 1.0f,
                                    0.50f,  0.50f, 0.0f, 1.0f,
                                    0.25f,  0.50f, 0.0f, 1.0f,
    
                                    -0.25f, -0.75f, 0.0f, 1.0f,
                                     0.00f, -0.75f, 0.0f, 1.0f,
                                    -0.25f,  0.75f, 0.0f, 1.0f,
    
                                    0.00f, -0.75f, 0.0f, 1.0f,
                                    0.00f,  0.75f, 0.0f, 1.0f,
                                    -0.25f,  0.75f, 0.0f, 1.0f,
    
                                    -0.75f, -0.75f, 0.0f, 1.0f,
                                    -0.50f, -0.75f, 0.0f, 1.0f,
                                    -0.75f,  0.75f, 0.0f, 1.0f,
    
                                    -0.50f, -0.75f, 0.0f, 1.0f,
                                    -0.50f,  0.75f, 0.0f, 1.0f,
                                    -0.75f,  0.75f, 0.0f, 1.0f,
    
                                    -0.50f, -0.12f, 0.0f, 1.0f,
                                    -0.25f, -0.12f, 0.0f, 1.0f,
                                    -0.50f,  0.12f, 0.0f, 1.0f,
    
                                    -0.25f, -0.12f, 0.0f, 1.0f,
                                    -0.25f,  0.12f, 0.0f, 1.0f,
                                    -0.50f,  0.12f, 0.0f, 1.0f
    };
    
    short elems[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                        12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
                        24, 25, 26, 27, 28, 29};
    
    int nverts = 30;

    
    /**
     * my canvas
     */
    GLCanvas myCanvas;
	
	/**
	 * constructor
	 */
	public HelloOGL (GLCanvas G)
	{
		myShaders = new shaderProgram();
        myCanvas = G;
        
        G.addGLEventListener (this);
        G.addKeyListener (this);
	}
    
    private void errorCheck (GL2 gl2)
    {
        int code = gl2.glGetError();
        if (code == GL.GL_NO_ERROR)
            System.err.println ("All is well");
        else
            System.err.println ("Problem - error code : " + code);
            
    }
    
    
	/**
	 * Called by the drawable to initiate OpenGL rendering by the client. 
	 */
	public void display(GLAutoDrawable drawable)
	{
        // get GL
		GL2 gl2 = (drawable.getGL()).getGL2();
        
        // This should all probably be in createNewShape...However, 
        // since we can only get access to the GL2 during display, we'll have
        // to include it here.
        if (updateNeeded) {
            
            // get your verticies and elements
            Buffer points = FloatBuffer.wrap (datapoints);
            Buffer elements = ShortBuffer.wrap (elems);
            
            // set up the vertex buffer
            int bf[] = new int[1];
            gl2.glGenBuffers (1, bf, 0);
            vbuffer = bf[0];
            long vertBsize = nverts * 4l * 4l;
            gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vbuffer);
            gl2.glBufferData ( GL.GL_ARRAY_BUFFER, vertBsize , points, GL.GL_STATIC_DRAW);
            
            // set up element buffer.
            gl2.glGenBuffers (1, bf, 0);
            ebuffer = bf[0];
            long eBuffSize = nverts * 2l;
            gl2.glBindBuffer ( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer);
            gl2.glBufferData ( GL.GL_ELEMENT_ARRAY_BUFFER, eBuffSize,elements, 
                              GL.GL_STATIC_DRAW);
            
            // we're all done
            updateNeeded = false;
        }
        
        // clear your frame buffers
        gl2.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
        
        
        // bind your vertex buffer
        gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vbuffer);
        
        // bind your element array buffer
        gl2.glBindBuffer ( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer);
        
        // set up your attribute variables
        gl2.glUseProgram (shaderProgID);
        int  vPosition = gl2.glGetAttribLocation (shaderProgID, "vPosition");
        gl2.glEnableVertexAttribArray ( vPosition );
        gl2.glVertexAttribPointer (vPosition, 4, GL.GL_FLOAT, false,
                                   0, 0l);

        
		// draw your shapes
        gl2.glDrawElements ( GL.GL_TRIANGLES, nverts,  GL.GL_UNSIGNED_SHORT, 0l);
                
    }
        
	
	/**
	 * Notifies the listener to perform the release of all OpenGL 
	 * resources per GLContext, such as memory buffers and GLSL 
	 * programs.
	 */
	public void dispose(GLAutoDrawable drawable)
	{
            
	}
        
	/**
	 * Called by the drawable immediately after the OpenGL context is
	 * initialized. 
	 */
	public void init(GLAutoDrawable drawable)
	{
		// get the gl object
		GL2 gl2 = drawable.getGL().getGL2();
			
		// Load shaders
		shaderProgID = myShaders.readAndCompile (gl2, "vshader.glsl", "fshader.glsl");
		if (shaderProgID == 0) {
			System.err.println ("Error setting up shaders");
			System.exit (1);
		}
			
		// Other GL initialization
		gl2.glEnable (GL.GL_DEPTH_TEST);
		gl2.glEnable (GL.GL_CULL_FACE);
        gl2.glCullFace ( GL.GL_BACK );
        gl2.glFrontFace(GL.GL_CCW);
		gl2.glClearColor (1.0f, 1.0f, 1.0f, 1.0f);
        gl2.glDepthFunc (GL.GL_LEQUAL);
        gl2.glClearDepth (1.0f);
			
	}
        
	/**
	 * Called by the drawable during the first repaint after the component
	 * has been resized. 
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
                     int height)
	{
            
	}
	

    /**
     * Because I am a Key Listener...we'll only respond to key presses
     */
    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){}

    /** 
     * Invoked when a key has been pressed.
     */
    public void keyPressed(KeyEvent e)
    {
        // Get the key that was pressed
        char key = e.getKeyChar();
        
        // Respond appropriately
        switch( key ) {
            case 'q': case 'Q':
                System.exit( 0 );
                break;
        }
        
        // do a redraw
        myCanvas.display();
    }
    
        
    /**
     * main program
     */
    public static void main(String [] args)
    {
        // GL setup
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);
		
		// create your tessMain
		HelloOGL myMain = new HelloOGL(canvas);
        
        
        Frame frame = new Frame("CG1 - Hello OpenGL");
        frame.setSize(512, 512);
        frame.add(canvas);
        frame.setVisible(true);
        
        // by default, an AWT Frame doesn't do anything when you click
        // the close button; this bit of code will terminate the program when
        // the window is asked to close
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
