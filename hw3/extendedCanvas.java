//
//  extendedCanvas.java
//  
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

/**
 * This is a special subclass of simpleCanvas with functionality
 * for testing out the clipping assignment.
 *
 * Note, this class should only be used for clipping assignment and
 * only for testing purposes!!!
 */
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.util.*;

public class extendedCanvas extends simpleCanvas {
		
	private LinkedList<Polygon> poly_fill;
	private LinkedList<Polygon> poly_draw;
	private LinkedList<Color> poly_fill_color;
	private LinkedList<Color> poly_draw_color;

	extendedCanvas (int w, int h) 
	{
		super (w,h);
        
        poly_fill = new LinkedList<Polygon>();
		poly_draw = new LinkedList<Polygon>();
		poly_fill_color  = new LinkedList<Color>();
		poly_draw_color = new LinkedList<Color>();
	}
    
    public void printLoop (int n, float x[], float y[] )
	{
		// Need to swap the y component
		Polygon P = new Polygon();
		for (int i=0; i < n; i++) 
            P.addPoint (Math.round(x[i]), Math.round(height - y[i]));
		poly_draw.add (P);
		poly_draw_color.add (curColor);
	}
	
	public void printPoly (int n, float x[], float y[] )
	{
		Polygon P = new Polygon();
		for (int i=0; i < n; i++) 
            P.addPoint (Math.round(x[i]), Math.round(height - y[i]));
		poly_fill.add (P);
		poly_fill_color.add (curColor);
	}

	
	public void paint(Graphics g)
    {
        // do normal painting first
		super.paint (g);
        
		// draw polys
		ListIterator<Polygon> II = poly_draw.listIterator(0);
		ListIterator<Color> CC = poly_draw_color.listIterator(0);
		while (II.hasNext()) {
			Polygon P = II.next();
			Color C = CC.next();
			g.setColor (C);
			g.drawPolygon (P);
		}
		
		// fill polys
		II = poly_fill.listIterator(0);
		CC = poly_fill_color.listIterator(0);
		while (II.hasNext()) {
			Polygon P = II.next();
			Color C = CC.next();
			g.setColor (C);
			g.fillPolygon (P);
		}
		
    }	

}
