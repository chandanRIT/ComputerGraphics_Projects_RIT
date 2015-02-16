//
//  clipTest.java
//  
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

import java.awt.*;

public class clipTest {
	
	public clipTest () {}
	
	static public void main(String[] args){
		
		extendedCanvas T = new extendedCanvas(300, 300);
        clipper C = new clipper();
        T.setColor (1.0f, 1.0f, 1.0f);
        T.clear();
		
		float quad1x[] = new float[4];
		float quad1y[] = new float[4];
		quad1x[0] = 20; quad1x[1] = 20; quad1x[2] = 40; quad1x[3] = 40;
		quad1y[0] = 20; quad1y[1] = 40; quad1y[2] = 40; quad1y[3] = 20;
		
		float quad2x[] = new float[4];
		float quad2y[] = new float[4];
		quad2x[0] = 80; quad2x[1] = 80; quad2x[2] = 60; quad2x[3] = 60;
		quad2y[0] = 60; quad2y[1] = 100; quad2y[2] = 100; quad2y[3] = 60;
		
		float quad3x[] = new float[4];
		float quad3y[] = new float[4];
		quad3x[0] = 20; quad3x[1] = 50; quad3x[2] = 50; quad3x[3] = 20;
		quad3y[0] = 60; quad3y[1] = 60; quad3y[2] = 50; quad3y[3] = 50;
		
		float quad4x[] = new float[4];
		float quad4y[] = new float[4];
		quad4x[0] = 44; quad4x[1] = 60; quad4x[2] = 60; quad4x[3] = 44;
		quad4y[0] = 22; quad4y[1] = 22; quad4y[2] = 46; quad4y[3] = 46;

		float pent1x[] = new float[5];
		float pent1y[] = new float[5];
		pent1x[0] = 80; pent1x[1] = 90; pent1x[2] = 110; pent1x[3] = 100; pent1x[4] = 80;
		pent1y[0] = 20; pent1y[1] = 10; pent1y[2] = 20; pent1y[3] = 50; pent1y[4] = 40;

		float hept1x[] = new float[7];
		float hept1y[] = new float[7];
		hept1x[0] = 120; hept1x[1] = 140; hept1x[2] = 160; hept1x[3] = 160; hept1x[4] = 140; hept1x[5] = 120; hept1x[6] = 110;
		hept1y[0] = 70; hept1y[1] = 70; hept1y[2] = 80; hept1y[3] = 100; hept1y[4] = 110; hept1y[5] = 100; hept1y[6] = 90;

		float wx[] = new float [50];
		float wy[] = new float [50];
		int wl;

	/* 
	* first polygon:  entirely within region
    */
		wl = 0;
		wl = C.clipPolygon( 4, quad1x, quad1y,  wx, wy, 10, 10, 50, 50 );
		T.setColor ( 1.0f, 0.0f, 0.0f );		/* red */
		T.printLoop( 4, quad1x, quad1y );
		T.printPoly( wl, wx, wy );

   /*
    * second polygon:  entirely outside region
    */
		wl = 0;
		wl = C.clipPolygon( 4, quad2x, quad2y, wx, wy, 10, 10, 50, 50 );
		/* shouldn't draw anything! */
		if( wl > 0 ) {
			T.setColor ( 0.0f, 1.0f, 0.0f );		/* green */
			T.printLoop( 4, quad2x, quad2y );
			T.printPoly( wl, wx, wy );
		}

   /*
    * third polygon:  halfway outside on left
    */

		wl = 0;
		wl = C.clipPolygon( 4, quad3x, quad3y, wx, wy, 30, 10, 70, 80 );
		T.setColor ( 0.0f, 0.0f, 1.0f );		/* blue */
		T.printLoop( 4, quad3x, quad3y );
		T.printPoly( wl, wx, wy );

   /*
    * fourth polygon:  part outside on right
    */

		wl = 0;
		wl = C.clipPolygon( 4, quad4x, quad4y, wx, wy, 10, 10, 50, 50 );
		T.setColor ( 1.0f, 0.0f, 1.0f );		/* magenta */
		T.printLoop( 4, quad4x, quad4y );
		T.printPoly( wl, wx, wy );

   /*
    * fifth polygon:  enclosing
    */

		wl = 0;
		wl = C.clipPolygon( 5, pent1x, pent1y, wx, wy, 90, 20, 100, 30 );
		T.setColor ( 0.5f, 0.5f, 1.0f );		/* reddish-greenish-blue ? */
		T.printLoop( 5, pent1x, pent1y );
		T.printPoly( wl, wx, wy );

   /*
    * sixth polygon:  outside on left and bottom
    */

		wl = 0;
		wl = C.clipPolygon( 5, pent1x, pent1y, wx, wy, 90, 34, 120, 60 );
		T.setColor ( 1.0f, 0.5f, 1.0f );		/* red-greenish-blue ? */
		T.printLoop( 5, pent1x, pent1y );
		T.printPoly( wl, wx, wy );

   /*
    * seventh polygon:  outside on top, right, and bottom
    */

		wl = 0;
		wl = C.clipPolygon( 7, hept1x, hept1y, wx, wy, 90, 80, 130, 110 );
		T.setColor ( 0.0f, 0.0f, 0.0f );		/* black */
		T.printLoop( 7, hept1x, hept1y );
		T.printPoly( wl, wx, wy );

		
		Frame f = new Frame( "clip Test" );
        f.add("Center", T);
		f.pack();
		f.setResizable (false);
        f.setVisible(true);
	}
        
}
