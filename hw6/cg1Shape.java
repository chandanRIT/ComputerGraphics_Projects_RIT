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

public class cg1Shape extends simpleShape
{
	/**
	 * constructor
	 */
	public cg1Shape()
	{
	}

	/****
	 *
	 *  Feel free to cut / paste, and use working tesselation code here
	 */

	/**
	 * makeLameCube - creates triangles for a pretty lame cube...Only use if you don't have working tesselation code
	 *
	 */
	private void makeLameCube()
	{
		float faceLength = 1.0f;
		int subdivisions = 2;
		float x = -(float)(faceLength/2), y = (float)(faceLength/2), z = (float)(faceLength/2), 
				currX = x, currY = y;
		float eachSubDiv = faceLength/subdivisions;
		
		for(int i = 0; i<subdivisions; i++){
			float yNext = currY - eachSubDiv;

			for(int j = 0; j<subdivisions; j++){
				float xNext = currX + eachSubDiv;

				//front and back
				addTriangle(currX, currY, z, currX, yNext, z, xNext, currY, z); //123
				addTriangle(xNext, currY, z, currX, yNext, z, xNext, yNext, z); //324

				addTriangle(currX, currY, -z, xNext, currY, -z, currX, yNext, -z); //123
				addTriangle(xNext, currY, -z, xNext, yNext, -z, currX, yNext, -z); //324

				//sides
				addTriangle(x, currY, currX, x, yNext, currX, x, currY, xNext); //123
				addTriangle(x, currY, xNext, x, yNext, currX, x, yNext, xNext); //324

				addTriangle(-x, currY, currX, -x, currY, xNext, -x, yNext, currX); //123
				addTriangle(-x, currY, xNext, -x, yNext, xNext, -x, yNext, currX); //324

				//top and bottom
				addTriangle(currX, y, -currY, currX, y, -yNext, xNext, y, -currY); //123
				addTriangle(xNext, y, -currY, currX, y, -yNext, xNext, y, -yNext); //324

				addTriangle(currX, -y, -currY, xNext, -y, -currY, currX, -y, -yNext); //123
				addTriangle(xNext, -y, -currY, xNext, -y, -yNext, currX, -y, -yNext); //243

				currX = xNext; 
			}

			currX = x;
			currY = yNext;
		}

	}


	/**
	 * makeDefaultShape - creates a "unit" shape of your choice using your tesselation routines.
	 *  If you don't have working tessellation code for any of the shapes, you can use the supplied
	 *  makeLameCube routine.
	 *
	 */
	public void makeDefaultShape ()
	{
		makeLameCube();
	}


}
