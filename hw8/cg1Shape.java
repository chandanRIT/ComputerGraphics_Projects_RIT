/**
 * cg1Shape.java
 *
 * Class that includes routines for tessellating a number of basic shapes
 *
 * Students are to supply their implementations for the
 * functions in this file using the function "addTriangle()" to do the 
 * tessellation.
 *
 */

import java.awt.*;
import java.nio.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import java.io.*;


public class cg1Shape extends simpleShape
{     
    /**
     * makeDefaultShape - creates a "unit" shape of your choice using your tesselation routines.
     * 
     *
     */
    public void makeDefaultShape ()
    {
        // tessellate your favorite unit shape here.
    	makeSphere(0.5f, 50, 50);
    }

    /**
     * makeStdSphere - makes a sphere of radius 0.5 and 50 subdivisions in
     * both directions....For use in case you don't have working tesselation code.
     */
	public void makeSphere (float radius, int slices, int stacks)
    {
	  float fractionSlices=(float)(2*Math.PI)/slices;
	  float fractionStacks=(float)Math.PI/stacks;
	  for(int i=0;i<slices;i++){
	   int secondIndex=0;
	   if(i+1 == slices){
	    secondIndex=0;
	   }
	   else{
	    secondIndex=i+1;
	   }
	   float xtheta=(float)(radius*Math.cos(fractionSlices*i));
	   float xthetanext=(float)(radius*Math.cos(fractionSlices*(secondIndex)));
	   float ztheta=(float)(radius*Math.sin(fractionSlices*i));
	   float zthetanext=(float)(radius*Math.sin(fractionSlices*(secondIndex)));
	   for(int j=0;j<stacks;j++){
	    float xphi=(float)(xtheta*Math.sin(fractionStacks*j));
	    float xphiTop=(float)(xtheta*Math.sin(fractionStacks*(j+1)));
	    float xphiNext=(float)(xthetanext*Math.sin(fractionStacks*j));
	    float xphiNextTop=(float)(xthetanext*Math.sin(fractionStacks*(j+1)));
	    float zphi=(float)(ztheta*Math.sin(fractionStacks*j));
	    float zphiTop=(float)(ztheta*Math.sin(fractionStacks*(j+1)));
	    float zphiNext=(float)(zthetanext*Math.sin(fractionStacks*j));
	    float zphiNextTop=(float)(zthetanext*Math.sin(fractionStacks*(j+1)));
	    float yphi=(float)(radius*Math.cos(fractionStacks*j));
	    float yphiNext=(float)(radius*Math.cos(fractionStacks*(j+1)));
	    this.addTriangle(xphi,yphi,zphi, i*1.0f/slices, j*1.0f/stacks,
	    		xphiNext,yphi,zphiNext ,(i+1)*1.0f/slices, j*1.0f/stacks,
	    		xphiTop,yphiNext,zphiTop, i*1.0f/slices, (j+1)*1.0f/stacks);
	    
	    this.addTriangle(xphiNextTop,yphiNext,zphiNextTop, (i+1)*1.0f/slices,(j+1)*1.0f/stacks,
	    		xphiTop,yphiNext,zphiTop, i*1.0f/slices,(j+1)*1.0f/stacks,
	    		xphiNext,yphi,zphiNext, (i+1)*1.0f/slices,j*1.0f/stacks);
	    
	   }
	  }
	 }
}
