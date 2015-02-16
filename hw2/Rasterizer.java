import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


//
//  Rasterizer.java
//  
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

/**
 * 
 * This is a class that performas rasterization algorithms
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
	 * Draw a filled polygon in the simpleCanvas C.
	 *
	 * The polygon has n distinct vertices. The 
	 * coordinates of the vertices making up the polygon are stored in the 
	 * x and y arrays.  The ith vertex will have coordinate  (x[i], y[i])
	 *
	 * You are to add the implementation here using only calls
	 * to C.setPixel()
	 */
	public void drawPolygon(int n, int x[], int y[], simpleCanvas C)
	{
		// YOUR IMPLEMENTATION GOES HERE
		List<EdgeBucket>[] edgeTable = createEdgeBucketsAndTable(n, x, y);
		List<EdgeBucket> activeEdgeList = new LinkedList<EdgeBucket>();

		for(int rasterIndex = 0; rasterIndex < edgeTable.length; rasterIndex++){
			//• Remove AET entries where y = ymax
			removeEBFromAEList(activeEdgeList, rasterIndex);

			if(edgeTable[rasterIndex]!=null){
				//• Move from ET[y] to AET when y = ymin
				activeEdgeList.addAll(edgeTable[rasterIndex]);

				//sort the edgeBuckets based on its currentX value 
				Collections.sort(activeEdgeList);
			}

			//fill pixels b/n a pair of x-cords. This pair of xCords are chosen from adjacent 
			// edgeBuckets' infoArray (bucket's 1st index has xCurr). The edgeBuckets are in Active Edge Buckets list
			for(int p = 0; p < activeEdgeList.size(); p+=2){
				int xLeft = activeEdgeList.get(p).infoArr[1];
				int xRight = activeEdgeList.get(p+1).infoArr[1];

				for(; xLeft < xRight; xLeft++){
					C.setPixel(xLeft, rasterIndex);
				}
			}

			//updating x for the next scan line, y is incremented by 1
			for(EdgeBucket eb : activeEdgeList){
				if(eb.infoArr[2]!=0){ // if its a non vertical edge , dx!=0 
					eb.infoArr[4]+= eb.infoArr[2];  //Nsum += dx
					if(Math.abs(eb.infoArr[4]) > Math.abs(eb.infoArr[3])){ //NSum > dy
						int quotient = quotient(eb.infoArr[4], eb.infoArr[3]); 
						eb.infoArr[1] += quotient;
						eb.infoArr[4] = eb.infoArr[4] - (eb.infoArr[3] * quotient);  
					}
				}
			}
		}
	}

	/***
	 * calculates the quotient for a given numerator and denominator
	 * @param numerator
	 * @param denominator
	 * @return the quotient
	 */
	private static int quotient(int numerator, int denominator){
		int quotient = 0, numAbs = Math.abs(numerator), 
				denAbs = Math.abs(denominator),	incr = denAbs;
		if(denAbs > numAbs || numAbs == 0)
			return 0;

		//calculate quotient for absolute values
		while(denAbs <= numAbs){
			denAbs+=incr;
			quotient++;
		}

		//sign adjustments
		if(numerator < 0)
			quotient = -quotient;
		if(denominator < 0)
			quotient = -quotient;

		return quotient;
	}

	/**
	 * removes EdgeBuckets from ActiveEdgeList whose yMax = rasterY   
	 * @param edgeBucketList : The activeEdgeList
	 * @param yMax : rasterY
	 */
	private void removeEBFromAEList(List<EdgeBucket> edgeBucketList, int yMax){
		Iterator<EdgeBucket> iterator = edgeBucketList.iterator();
		while(iterator.hasNext()){
			if(iterator.next().infoArr[0] == yMax){
				iterator.remove();
			}
		}
	}

	/**
	 * creates the edgeBuckets for each non-vertical edge and adds the edgebucket to EdgeTable 
	 * in an index which represents the edge's yminx   
	 *
	 * @param n : no of vertices
	 * @param x : array of x-coords of the polygon
	 * @param y : array of y-coords of the polygon
	 * @return The constructed edgeTable
	 */
	private List<EdgeBucket>[] createEdgeBucketsAndTable(int n, int x[], int y[]){
		List<EdgeBucket>[] edgeBucketListTable = (LinkedList<EdgeBucket>[]) new LinkedList[n_scanlines];

		for(int i=0; i<n; i++){
			int iNext = i+1;
			if(iNext == n){
				iNext = 0;
			}
			if(y[i] != y[iNext]){
				int maxY = y[iNext], minYIndex = i;
				if(y[i]>y[iNext]){
					maxY = y[i];
					minYIndex = iNext;
				} 
				EdgeBucket edgeBucket = new EdgeBucket(maxY, x[minYIndex], x[iNext] - x[i], y[iNext] - y[i]);
				if(edgeBucketListTable[y[minYIndex]] == null)	
					edgeBucketListTable[y[minYIndex]] = new LinkedList<EdgeBucket>();
				edgeBucketListTable[y[minYIndex]].add(edgeBucket);
			}
			//don't create an EdgeBucket for an horizontal edge
		}

		return edgeBucketListTable;
	}

	
	/**
	 * Custom class representing EdgeBucket
	 */
	class EdgeBucket implements Comparable<EdgeBucket>{
		int infoArr[] = new int[5];
		EdgeBucket nextPointer;

		/**
		 * Constructor
		 * 
		 * @param yMax : ymax of the edge
		 * @param currX : currentX (x corresponding to yMin of the edge)
		 * @param dx : dx of the edge
		 * @param dy : dy of the edge
		 */
		EdgeBucket(int yMax, int currX, int dx, int dy) { //initially, nSum = 0 (the running numerator sum) 
			infoArr[0] = yMax;
			infoArr[1] = currX;
			if(dy<0){ //sign adjustment, mainly for supporting the sorting of EdgeBuckets
				dx=-dx;
				dy=-dy;
			}
			infoArr[2] = dx;
			infoArr[3] = dy;
			infoArr[4] = 0;
		}

		@Override
		/**
		 * toString override
		 */
		public String toString() {
			return infoArr[0] + "; " + infoArr[1] + "; " + infoArr[2] +  "; " +infoArr[3] + "; " +  infoArr[4]; 
		}

		@Override
		/**
		 * The equals method override
		 */
		public boolean equals(Object object) {
			if(super.equals(object)) // super.equals() means '=='
				return true;

			// test if object is instance of the class Person
			if (object instanceof EdgeBucket){
				EdgeBucket edgeBucket = (EdgeBucket)object;
				if(edgeBucket.infoArr[1] == this.infoArr[1])	//compare the current Xs
					return true;
			}
			return false;

		}

		@Override
		/**
		 * natural ordering is defined here
		 * compares the buckets' currX values, if equal compare the dx's
		 */
		public int compareTo(EdgeBucket target) {
			if(this.infoArr[1] < target.infoArr[1])
				return -1;
			else if(this.infoArr[1] > target.infoArr[1])
				return 1;
			else{
				if(this.infoArr[2] < target.infoArr[2])
					return -1;
				else
					return 1;
			} 
		}

	}

	/*private static void sortVerticesInScanLineOrder(int x[], int y[]){
		int temp = 0; 
		for(int i=0; i < y.length; i++){
			for(int j=i+1; j<y.length; j++ ){
				if(y[i] >= y[j]){ // then swap
					temp = y[i]; y[i] = y[j]; y[j] = temp; //swap y-cords
					temp = x[i]; x[i] = x[j]; x[j] = temp; // and corresponding x-cords
				} else if (y[i] == y[j] && x[i] > x[j] ){
					temp = x[i]; x[i] = x[j]; x[j] = temp;
				}
			}
		}
	}*/

}
