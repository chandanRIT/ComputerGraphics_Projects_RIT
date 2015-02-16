#Sutherland-Hodgman Clipping Algorithm

The programming environment is a set of simple object-oriented classes with implementations in Java. The classes include:
simpleCanvas – a simple 2D canvas that allows the ability to set a pixel.
extenderCanvas - a special subclass ofsimpleCanvas with functionality for testing out the clipping. 
clipper – a simple class that performs clipping operations.
clipTest – the main function for the application.

In this project, you will need to complete the method clipPolygon() , implementing the Sutherland-Hodgman Clipping Algorithm. 

The signature for the clipPolygon method is:
int clipPolygon(int in, float inx[ ] , float iny[ ] , float outx[ ] , float outy[ ] , float x0, float y0, float x1, float y1)

Where inx and iny are arrays holding the coords ofthe vertices ofthe polygon before clipping. n is the number
ofvertices in this polygon. outx and outy are arrays that will hold the coords ofthe verticies ofthe polygon after
clipping. The function shoyld return the number ofverticies in the polygon after clipping
