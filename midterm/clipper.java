

import java.util.ArrayList;
import java.util.List;

//
//  Clipper.java
//  
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

/**
 * Class for performing clipping
 */
public class clipper {
    
    /**
     * clipPolygon
     * 
     * Clip the polygon with vertex count in and vertices inx/iny
     * against the rectangular clipping region specified by lower-left corner
     * (x0,y0) and upper-right corner (x1,y1). The resulting vertices are
     * placed in outx/outy.  
     * 
     * The routine should return the with the vertex count of polygon
     * resulting from the clipping.
     *
     * @param numOfVertices the number of vertices in the polygon to be clipped
     * @param inx - x coords of vertices of polygon to be clipped.
     * @param int - y coords of vertices of polygon to be clipped.
     * @param outx - x coords of vertices of polygon resulting after clipping.
     * @param outy - y coords of vertices of polygon resulting after clipping.
     * @param x0 - x coord of lower left of clipping rectangle.
     * @param y0 - y coord of lower left of clipping rectangle.
     * @param x1 - x coord of upper right of clipping rectangle.
     * @param y1 - y coord of upper right of clipping rectangle.
     *
     * @return number of vertices in the polygon resulting after clipping
     */
    public int clipPolygon(int numOfVertices, float inx[], float iny[], float outx[], 
                    float outy[], float x0, float y0, float x1, float y1)
    {
    	// Your implementation goes here
    	float[] arrOfClipEdges = {x0, y0, x1, y1};
    	boolean[] isFirstEdgeArr = {true, true, false, false};
    	boolean isVertical = true;
		List<Point> outputListOfVertices = new ArrayList<Point>();
		
        for(int i = 0; i < arrOfClipEdges.length; i++){
    		for(int j=0; j<numOfVertices; j++){
    			int jNext = j+1;
    			if(jNext == numOfVertices){
    				jNext = 0;
    			}
    			Point p1 = new Point(inx[j], iny[j]), 
    				   p2 = new Point(inx[jNext], iny[jNext]);
    			
    			switch(checkCase(p1, p2, arrOfClipEdges[i], isVertical, isFirstEdgeArr[i])){
    				case 1: //add the second point (both inside)
    					outputListOfVertices.add(p2);
    					break;
    				
    				case 2: //just add the intersection point (since first inside , second out)
    					Point ipointToAdd = findIntersection(p1, p2, arrOfClipEdges[i], isVertical);
    					outputListOfVertices.add(ipointToAdd);
    					break;
    				
    				case 3: //nothing to add (since both outside)
    					break;
    				
    				case 4: //add the intersection and the second point 
    					Point ipointToAdd4 = findIntersection(p1, p2, arrOfClipEdges[i], isVertical);
    					if(!p2.equals(ipointToAdd4))
    						outputListOfVertices.add(ipointToAdd4);
    					outputListOfVertices.add(p2);
    					break;
    			}
    		}
    		
    		if(i != arrOfClipEdges.length-1){
	        	numOfVertices = outputListOfVertices.size();
	    		//reset the input array with the intermde
	    		inx = new float[numOfVertices];
	    		iny = new float[numOfVertices];
	    		for(int k = 0; k<numOfVertices; k++){
	    			inx[k] = outputListOfVertices.get(k).x;
	    			iny[k] = outputListOfVertices.get(k).y;
	    		}
	    		outputListOfVertices.clear();
	    		isVertical = !isVertical;
    		}
    	}
    	
        for(int i=0; i<outputListOfVertices.size(); i++){
        	outx[i] = outputListOfVertices.get(i).x;
        	outy[i] = outputListOfVertices.get(i).y;
        }
        return outputListOfVertices.size(); // should return number of vertices in clipped polygon.
    }
    
    /**
     * Determines into which case the two points under consideration fall into wrt the infinite edge.
     * case 1 : both inside, 
     * case 2 : first inside, second outside,
     * case 3 : both outside
     * case 4 : first outside, second inside
     *  
     * @param p1 : point1
     * @param p2 : point2
     * @param infiniteWindowEdge : the equation of the infinite clip edge
     * @param isVertical : tells if the infinite edge passed is either vertical or horizontal
     * @param firstEdge : tells if its left/bottom or right/top edge
     * @return returns the caseNumber
     */
    int checkCase(Point p1, Point p2, float infiniteWindowEdge, boolean isVertical, boolean firstEdge){
    	float[] coordsToCheck = {p1.y, p2.y};
    	if(isVertical) {//if the window edge is a vertical edge, then check on the x-coords
    		coordsToCheck[0] = p1.x;
    		coordsToCheck[1] = p2.x;
    	} 
    	
    	int caseNum = 0;
    	if(firstEdge){ //left or bottom edge
	    	if(infiniteWindowEdge <= coordsToCheck[0]){ // x1/y1 to the right/top of left/bottom edge (inside)
	    		caseNum = 1;
	    		if(infiniteWindowEdge > coordsToCheck[1]){
	    			caseNum++;
	    		}
	    	} else {
	    		caseNum = 3;
	    		if(infiniteWindowEdge <= coordsToCheck[1]){ // first out , second in
	    			caseNum++;
	    		}
	    	}
    	} else { //right or top edge
    		if(infiniteWindowEdge >= coordsToCheck[0]){
	    		caseNum = 1;
	    		if(infiniteWindowEdge < coordsToCheck[1]){
	    			caseNum++;
	    		}
	    	} else {
	    		caseNum = 3;
	    		if(infiniteWindowEdge >= coordsToCheck[1]){ // first out , second in
	    			caseNum++;
	    		}
	    	}
    	}
    	
    	return caseNum;
    }
    
    /**
     * This method finds out the intersection of the line formed with the passed 2 points and 
     * the passed infinite edge. This infinite edge can be either vertical or horizontal.
     *  
     * @return the intersection point
     */
    Point findIntersection(Point p1, Point p2, float infiniteWindowEdge, boolean vertical){
    	if(p2.x == p1.x){ //if vertical line
    		if(vertical) // intersected by vertical window clip line
    			return p2; //just return the 2nd point
    		else
    			return new Point(p2.x,infiniteWindowEdge);
    	}
    		
    	float m = (p2.y-p1.y)/(p2.x-p1.x); //slope
    	float c = p1.y - m * p1.x;
    	
    	Point iPoint = new Point(infiniteWindowEdge , infiniteWindowEdge);  
    	if(vertical)
    		iPoint.y = m*infiniteWindowEdge + c;
    	else{
    		if(m==0)
        		return new Point(p2.x, p2.y);
        	iPoint.x =(infiniteWindowEdge - c) / m; // if intersection is with a horizontal line
        }
    	return iPoint;
    }
    
    /**
     * This resembles a Point in 2D space
     * @author Kumar
     */
    class Point{
    	float x, y;
    	
    	/**
    	 * constructor
    	 * @param x : x coordinate
    	 * @param y : y coordinate
    	 */
    	Point(float x, float y) {
			this.x = x;
			this.y = y;
		}
    	
    	/**
    	 * overridden equals method. defines when are 2 points are said to be equal.
    	 */
    	public boolean equals(Object object) {
    		if(super.equals(object)) // super.equals() means '=='
				return true;

			if (object instanceof Point){
				Point p2 = (Point)object;
				if(p2.x == this.x && p2.y == this.y)//compare the current Xs and Ys
					return true;
			}
			return false;
    	}
    }
}
