//
//  Rasterizer.java
//  
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

/**
 * 
 * A simple class for performing rasterization algorithms.
 *
 */
public class Rasterizer {
    
    /**
     * number of scanlines
     */
    int n_scanlines;
    
    /**
     * Constructor
     *
     * @param n - number of scanlines
     *
     */
    Rasterizer (int n)
    {
        n_scanlines = n;
    }
    
    /**
     * Draw a line from (x0,y0) to (x1, y1) on the simpleCanvas C.
     *
     * Implementation should be using the Midpoint Method
     *
	 * You are to add the implementation here using only calls
	 * to C.setPixel()
     *
     * @param x0 - x coord of first endpoint
     * @param y0 - y coord of first endpoint
     * @param x1 - x coord of second endpoint
     * @param y1 - y coord of second endpoint
     * @param C - The canvas on which to apply the draw command.
	 */
    
    public void drawLine (int x0, int y0, int x1, int y1, simpleCanvas canvas)
    {	
    	int dy = y1 - y0, dx = x1 - x0;
    	
    	if(dx < 0){ //swap end-points and reverse signs of dy and dx, if p0 is on the right of p1
    		int t=x0; x0=x1; x1=t; t=y0; y0=y1; y1=t; dx=-dx;dy=-dy;
    	}
    	
    	if(dx == 0){ // slope infinity, vertical line
    		drawVertLine(x0, y0, x1, y1, canvas);
    		
    	} else if(dy == 0){ // slope zero, horizontal line
    		drawHorzLine(x0, y0, x1, y1, canvas);
    		
    	} else if (dx == dy){ // +ve diagonal, slope = 1
    		drawPostiveDiag(x0, y0, x1, y1, canvas);
    	
    	} else if( (dx*-1) == dy){ // -ve diagonal, slope = -1
    		drawNegDiag(x0, y0, x1, y1, canvas);
    	
    	} else if(dx > dy && dy > 0){ // +ve slope < 1 
    		drawShallowPostiveSlopeLine(x0, y0, x1, y1, canvas);
    		
    	} else if(dy > dx && dx > 0 ){ // +ve slope > 1
    		drawSteepPostiveSlopeLine(x0, y0, x1, y1, canvas);
    		
    	} else if(dx>0 && dy<0 && dy+dx < 0) { // 2nd quadrant , -oo < slope < -1
    		drawSteepNegSlopeLine(x0, y0, x1, y1, canvas);
    		
    	} else { // 2nd quadrant , -1 < slope < 0
    		drawShallowNegSlopeLine(x0, y0, x1, y1, canvas);
    	}
    }    
    
    private void drawHorzLine(int x0, int y0, int x1, int y1, simpleCanvas canvas){
    	int x = x0, y = y0; 
    	while(x <= x1){
			canvas.setPixel(x, y);
			x++;
		}
    }
    
    private void drawVertLine (int x0, int y0, int x1, int y1, simpleCanvas canvas){
    	int x=x0, y=y0;
    	
		if(y1-y0 < 0){ //swap
			int t=x0; x0=x1; x1=t; t = y0; y0=y1; y1=t; x=x0; y=y0;
		}
		
		while(y <= y1){
			canvas.setPixel(x, y);
			y++;
		}
    }
    
    private void drawPostiveDiag(int x0, int y0, int x1, int y1, simpleCanvas canvas){
    	int x=x0, y=y0;
    	while(x<=x1){
			canvas.setPixel(x, y);
			x++;y++;
		}
    }
    
    private void drawNegDiag(int x0, int y0, int x1, int y1, simpleCanvas canvas){
    	int x=x0, y=y0;
    	while(x<=x1){
			canvas.setPixel(x, y);
			x++;y--;
		}
    }
    
    private void drawShallowPostiveSlopeLine(int x0, int y0, int x1, int y1, simpleCanvas canvas){
    	int dy = y1 - y0, dx = x1 - x0, decisionVar = 2*dy-dx;
    	int x=x0; int y=y0;
    	
    	canvas.setPixel (x,y);
    	while(x<=x1){
			x++;
			canvas.setPixel(x, y);
			if(decisionVar <= 0){ // choose E
				decisionVar += 2*dy;
	
			} else { // chose NE
				y++;
				decisionVar += 2*(dy-dx);
			}
		}
    }
    
    private void drawSteepPostiveSlopeLine(int x0, int y0, int x1, int y1, simpleCanvas canvas){
    	int dy = y1 - y0, dx = x1 - x0, decisionVar = 2*dx-dy;
    	int x=x0; int y=y0;

    	canvas.setPixel (x,y);
    	while(y <= y1){
    		y++;
    		canvas.setPixel(x, y);
			if(decisionVar <= 0){ //choose NE
    			x++;
    			decisionVar += 2*dx;

    		} else {
    			decisionVar += 2*(dx-dy); //choose N
    		}
    	}
    }
    
    private void drawSteepNegSlopeLine(int x0, int y0, int x1, int y1, simpleCanvas canvas){
    	int dy = y1 - y0, dx = x1 - x0, decisionVar = 2*dx+dy;
    	int x=x0; int y=y0;

    	canvas.setPixel (x,y);
		while(y >= y1){
			y--;
			canvas.setPixel(x, y);
			if(decisionVar <= 0){ // if <= 0, choose S
				decisionVar += 2*dx;
			} else { // if > 0, choose SE
				x++;
    			decisionVar += 2*(dx+dy);
			}
		}
    }
    
    private void drawShallowNegSlopeLine(int x0, int y0, int x1, int y1, simpleCanvas canvas){
    	int dy = y1 - y0, dx = x1 - x0, decisionVar = 2*dy+dx;
    	int x=x0; int y=y0;

    	canvas.setPixel (x,y);
		while(x <= x1){
			x++;
			canvas.setPixel(x, y);
			if(decisionVar <= 0){ // if <= 0, choose SE
				y--;
    			decisionVar += 2*(dy+dx);
			} else { // if > 0, choose E
				decisionVar += 2*dy;
			}
		}
    }

}
