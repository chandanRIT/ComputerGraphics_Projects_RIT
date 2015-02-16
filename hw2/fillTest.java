//
//  Test.java
//  
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

import java.awt.*;

public class fillTest {
	
	public fillTest () {}	
    
	static public void main(String[] args){
		
		simpleCanvas T = new simpleCanvas(300, 300);
        Rasterizer R = new Rasterizer (300);
        
        T.setColor (0.0f, 0.0f, 0.0f);
		T.clear();
		T.setColor (1.0f, 1.0f, 1.0f);
        
        int x[] = new int[7];
		int y[] = new int[7];
        
        /* 
		 * Use Student's drawPolygon 
		 */
		x[0] = 10; y[0] = 10;
		x[1] = 20; y[1] = 10;
		x[2] = 20; y[2] = 20;
		x[3] = 10; y[3] = 20;
		
		/* 
		 * LL => Lower Left Start, CCW => Vertices entered in 
		 * counter clockwise progression 
		 */
		R.drawPolygon( 4, x, y, T );	/* Square, LL, CCW */
		
		x[0] = 40; y[0] = 30;
		x[1] = 40; y[1] = 50;
		x[2] = 30; y[2] = 50;
		x[3] = 30; y[3] = 30;
		
		R.drawPolygon( 4, x, y, T );	/* Rectangle, LR, CCW */
		
		x[0] = 40; y[0] = 90;
		x[1] = 40; y[1] = 70;
		x[2] = 10; y[2] = 70;
		x[3] = 10; y[3] = 90;
		
		R.drawPolygon( 4, x, y, T );	/* Rectangle, UR, CW */
		
		x[0] = 10; y[0] = 230;
		x[1] = 40; y[1] = 230;
		x[2] = 40; y[2] = 210;
		x[3] = 10; y[3] = 210;
		
		R.drawPolygon( 4, x, y, T );	/* Rectangle, UL, CW */
		
		x[0] = 100; y[0] = 10;
		x[1] = 150; y[1] = 10;
		x[2] = 125; y[2] = 20;
		
		R.drawPolygon( 3, x, y, T );	/* Isosceles, flat bottom */
		
		x[0] = 100; y[0] = 30;
		x[1] = 140; y[1] = 50;
		x[2] = 175; y[2] = 50;
		
		R.drawPolygon( 3, x, y, T );	/* flat top - tail to left */
		
		x[0] = 120; y[0] = 40;
		x[1] = 80;  y[1] = 60;
		x[2] = 45;  y[2] = 60;
		
		R.drawPolygon( 3, x, y, T );	/* flat top - tail to right */
		
		x[0] = 10; y[0] = 100;
		x[1] = 10; y[1] = 120;
		x[2] = 25; y[2] = 100;
		
		R.drawPolygon( 3, x, y, T );	/* Right */
		
		x[0] = 10; y[0] = 130;
		x[1] = 20; y[1] = 130;
		x[2] = 20; y[2] = 140;
		
		R.drawPolygon( 3, x, y, T );	/* Right */
		
		x[0] = 10; y[0] = 170;
		x[1] = 20; y[1] = 170;
		x[2] = 10; y[2] = 150;
		
		R.drawPolygon( 3, x, y, T );	/* Right */
		
		x[0] = 100; y[0] = 70;
		x[1] = 150; y[1] = 70;
		x[2] = 75;  y[2] = 90;
		
		R.drawPolygon( 3, x, y, T );	/* flat bottom - top left */
		
		x[0] = 100; y[0] = 100;
		x[1] = 150; y[1] = 100;
		x[2] = 195; y[2] = 120;
		
		R.drawPolygon( 3, x, y, T );	/* flat bottom - top right */
		
		x[0] = 100; y[0] = 170;
		x[1] = 150; y[1] = 150;
		x[2] = 175; y[2] = 130;
		
		R.drawPolygon( 3, x, y, T );	/* scalene */
		
		x[0] = 200; y[0] =  50;
		x[1] = 225; y[1] =  90;
		x[2] = 250; y[2] =  50;
		x[3] = 225; y[3] =  10;
		
		R.drawPolygon ( 4, x, y, T );     /* diamond */
		
		x[0] = 200; y[0] = 125;
		x[1] = 210; y[1] = 150;
		x[2] = 260; y[2] = 145;
		x[3] = 275; y[3] = 120;
		x[4] = 250; y[4] = 100;
		x[5] = 225; y[5] = 100;
		
		R.drawPolygon ( 6, x, y, T );     /* hexagon */
		
		x[0] = 215; y[0] = 225;
		x[1] = 200; y[1] = 250;
		x[2] = 215; y[2] = 250;
		x[3] = 225; y[3] = 275;
		x[4] = 235; y[4] = 250;
		x[5] = 250; y[5] = 250;
		x[6] = 235; y[6] = 225;
		
		R.drawPolygon ( 7, x, y, T );     /* star top */
        
        Frame f = new Frame( "polyfill Test" );
        f.add("Center", T);
		f.pack();
		f.setResizable (false);
        f.setVisible(true);
		
	}
        
}
