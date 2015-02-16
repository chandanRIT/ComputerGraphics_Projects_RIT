

//
//  cg1Canvas.java
//  
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

/**
 * This is a simple canvas class for adding functionality for the
 * 2D portion of Computer Graphics I.
 *
 */

import Jama.*;
import java.util.*;

public class cg1Canvas extends simpleCanvas {

	Matrix currTransMatrix;
    List<XY> polygonsList; 
    float[] arrOfClipEdges; 
    int[] viewPortInfo;
    clipper clipper;
    Rasterizer rasterizer;
    
	/**
     * Constructor
     *
     * @param w width of canvas
     * @param h height of canvas
     */
    cg1Canvas (int w, int h)
    {
        super (w, h);
        arrOfClipEdges = new float[4];
        viewPortInfo = new int[4];
        polygonsList = new ArrayList<XY>();
        clipper = new clipper();
        rasterizer = new Rasterizer(this.getWidth());
    }
    
    /**
     *
     * addPoly - Adds and stores a polygon to the canvas.  Note that this method does not
     *           draw the polygon, but merely stores it for later draw.  Drawing is 
     *           initiated by the draw() method.
     *
     *           Returns a unique integer id for the polygon.
     *
     * @param x - Array of x coords of the vertices of the polygon to be added.
     * @param y - Array of y coords of the vertices of the polygin to be added.
     * @param n - Number of verticies in polygon
     *
     * @return a unique integer identifier for the polygon
     */
    public int addPoly (float x[], float y[], int n)
    {
    	polygonsList.add( new XY(Arrays.copyOf(x, n), Arrays.copyOf(y, n)) ); 
        return polygonsList.size()-1;
    }
    
    /**
     *
     * clearTransform - sets the current transformation to be the identity 
     *
     */
    public void clearTransform()
    {
    	currTransMatrix = Matrix.identity(3, 3);
    }
    
    /**
     *
     * draw - Draw the polygon with the given id.  Draw should draw the polygon after applying the 
     *        current transformation on the vertices of the polygon.
     *
     * @param polyID - the ID of the polygin to be drawn.
     */
    public void draw (int polyID)
    {
    	float[] outX = new float[40], outY = new float[40];
    	XY xyArr = polygonsList.get(polyID);
    	//clipping
    	int nAfterClip = clipper.clipPolygon(xyArr.xArr.length, xyArr.xArr, xyArr.yArr, 
    			outX, outY, arrOfClipEdges[0], arrOfClipEdges[1], arrOfClipEdges[2], arrOfClipEdges[3]);
    	
    	int[] outXInt = new int[nAfterClip], outYInt = new int[nAfterClip];
    	
    	//View Transform
    	Matrix m1 = new Matrix(new double[][]{
    			{1,0,viewPortInfo[0]},
    			{0,1,viewPortInfo[1]}, 
    			{0,0,1}
    	}), 

    	m2 = new Matrix(new double[][]{
    		{(viewPortInfo[2] - viewPortInfo[0])/(arrOfClipEdges[2]-arrOfClipEdges[0]),0,0},
    		{0,(viewPortInfo[3] - viewPortInfo[1])/(arrOfClipEdges[3]-arrOfClipEdges[1]),0},
    		{0,0,1}
    	}), 

    	m3 = new Matrix(new double[][]{
    		{1,0,-arrOfClipEdges[0]},
    		{0,1,-arrOfClipEdges[1]},
    		{0,0,1}
    	});
   
    	/*translate(-arrOfClipEdges[0], -arrOfClipEdges[1]);
    	scale( (viewPortInfo[2] - viewPortInfo[0])/(arrOfClipEdges[2]-arrOfClipEdges[0]), 
    		(viewPortInfo[3] - viewPortInfo[1])/(arrOfClipEdges[3]-arrOfClipEdges[1]) );
    	translate(viewPortInfo[0], viewPortInfo[1]);*/
    	
    	for(int i = 0; i<nAfterClip; i++){
    		Matrix pointMatrix = new Matrix(3,1);
    	
    		pointMatrix.set(0, 0, outX[i]);	pointMatrix.set(1, 0, outY[i]);	pointMatrix.set(2, 0, 1);
    		pointMatrix = m1.times(m2.times(m3.times(pointMatrix)));
    		pointMatrix = currTransMatrix.times(pointMatrix);
    		
    		outXInt[i] = (int)pointMatrix.get(0,0);
    		outYInt[i] = (int)pointMatrix.get(1,0);
    	}
    	//rasterizing
    	rasterizer.drawPolygon(nAfterClip, outXInt, outYInt, this);
    }

    /**
    *
    * translate - Add a translation to the current transformation by premultiplying the appropriate
    *          translation matrix to the current transformtion matrix.
    *
    * @param x - Amount of translation in x.
    * @param y - Amount of translation in y.
    *
    */
   public void translate (float x, float y)
   {
   	Matrix tranlationMatrix = new Matrix(new double[][]{
   			{1,0,x},
   			{0,1,y},
   			{0,0,1}
   	}); 
   	currTransMatrix = tranlationMatrix.times(currTransMatrix);
   }
    
    /**
     *
     * rotate - Add a rotation to the current transformation by premultiplying the appropriate
     *          rotation matrix to the current transformtion matrix.
     *
     * @param degrees - Amount of rotation in degrees.
     *
     */
    public void rotate (float degrees)
    {
    	double radians = Math.toRadians(degrees),
    			sinValue = Math.sin(radians),
    			cosValue = Math.cos(radians);
    	
    	Matrix rotationMatrix = new Matrix(new double[][]{
    			{cosValue, -sinValue, 0},
    			{sinValue,  cosValue, 0},
    			{       0,         0, 1}
    	}); 
    	currTransMatrix = rotationMatrix.times(currTransMatrix);
    }
  
    /**
     *
     * scale - Add a scale to the current transformation by premultiplying the appropriate
     *          scaling matrix to the current transformtion matrix.
     *
     * @param x - Amount of scaling in x.
     * @param y - Amount of scaling in y.
     *
     */
    public void scale (float x, float y)
    {
    	Matrix scalingMatrix = new Matrix(new double[][]{
    			{x,0,0},
    			{0,y,0},
    			{0,0,1}
    	}); 
    	currTransMatrix = scalingMatrix.times(currTransMatrix); 
    }
    
    /**
     *
     * setClipWindow - defines the clip window
     *
     * @param bottom - y coord of bottom edge of clip window (in world coords)
     * @param top - y coord of top edge of clip window (in world coords)
     * @param left - x coord of left edge of clip window (in world coords)
     * @param right - x coord of right edge of clip window (in world coords)
     *
     */
    public void setClipWindow (float bottom, float top, float left, float right)
    {
    	arrOfClipEdges[0] = left;
    	arrOfClipEdges[1] = bottom;
    	arrOfClipEdges[2] = right;
    	arrOfClipEdges[3] = top;
    }
    
    /**
     *
     * setViewport - defines the viewport
     *
     * @param xmin - x coord of lower left of view window (in screen coords)
     * @param ymin - y coord of lower left of view window (in screen coords)
     * @param width - width of view window (in world coords)
     * @param height - width of view window (in world coords)
     *
     */
    public void setViewport (int x, int y, int width, int height)
    {
    	viewPortInfo[0] = x; 
    	viewPortInfo[1] = y;
    	viewPortInfo[2] = x+width;
    	viewPortInfo[3] = y+height;
    }
    
    class XY{
    	//int numOfVertices;
    	float xArr[], yArr[];
    	XY(float x[], float y[]){
    		xArr=x;
    		yArr=y;
    		//numOfVertices = n;
    	}
    }
}
