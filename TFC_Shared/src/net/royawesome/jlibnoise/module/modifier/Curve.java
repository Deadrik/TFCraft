/* Copyright (C) 2011 Garrett Fleenor

 This library is free software; you can redistribute it and/or modify it
 under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation; either version 3.0 of the License, or (at
 your option) any later version.

 This library is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 License (COPYING.txt) for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with this library; if not, write to the Free Software Foundation,
 Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 This is a port of libnoise ( http://libnoise.sourceforge.net/index.html ).  Original implementation by Jason Bevins

*/

package net.royawesome.jlibnoise.module.modifier;

import java.util.ArrayList;

import net.royawesome.jlibnoise.Utils;
import net.royawesome.jlibnoise.exception.NoModuleException;
import net.royawesome.jlibnoise.module.Module;

public class Curve extends Module {
	public class ControlPoint {
		public double inputValue;

		public double outputValue;
	}

	ArrayList<ControlPoint> controlPoints = new ArrayList<ControlPoint>();

	public Curve() {
		super(1);

	}

	public void AddControlPoint(double inputValue, double outputValue) {
		int index = findInsertionPos(inputValue);
		InsertAtPos(index, inputValue, outputValue);
	}

	public ControlPoint[] getControlPoints() {
		return (ControlPoint[]) controlPoints.toArray();
	}

	public void ClearAllControlPoints() {
		controlPoints.clear();
	}

	protected int findInsertionPos(double inputValue) {
		int insertionPos;
		for (insertionPos = 0; insertionPos < controlPoints.size(); insertionPos++) {
			if (inputValue < controlPoints.get(insertionPos).inputValue) {
				// We found the array index in which to insert the new control point.
				// Exit now.
				break;
			} else if (inputValue == controlPoints.get(insertionPos).inputValue) {
				// Each control point is required to contain a unique input value, so
				// throw an exception.
				throw new IllegalArgumentException("inputValue must be unique");
			}
		}
		return insertionPos;

	}

	protected void InsertAtPos(int insertionPos, double inputValue, double outputValue) {
		ControlPoint newPoint = new ControlPoint();
		newPoint.inputValue = inputValue;
		newPoint.outputValue = outputValue;
		controlPoints.add(insertionPos, newPoint);
	}

	@Override
	public int GetSourceModuleCount() {
		return 1;
	}

	@Override
	public double GetValue(double x, double y, double z) {
		if (SourceModule[0] == null)
			throw new NoModuleException();
//		if (controlPoints.size() >= 4)
//			throw new RuntimeException("must have 4 or less control points");

		// Get the output value from the source module.
		double sourceModuleValue = SourceModule[0].GetValue(x, y, z);

		// Find the first element in the control point array that has an input value
		// larger than the output value from the source module.
		int indexPos;
		for (indexPos = 0; indexPos < controlPoints.size(); indexPos++) {
			if (sourceModuleValue < controlPoints.get(indexPos).inputValue) {
				break;
			}
		}

		// Find the four nearest control points so that we can perform cubic
		// interpolation.
		int index0 = Utils.ClampValue(indexPos - 2, 0, controlPoints.size() - 1);
		int index1 = Utils.ClampValue(indexPos - 1, 0, controlPoints.size() - 1);
		int index2 = Utils.ClampValue(indexPos, 0, controlPoints.size() - 1);
		int index3 = Utils.ClampValue(indexPos + 1, 0, controlPoints.size() - 1);

		// If some control points are missing (which occurs if the value from the
		// source module is greater than the largest input value or less than the
		// smallest input value of the control point array), get the corresponding
		// output value of the nearest control point and exit now.
		if (index1 == index2) 
		{
		if(indexPos == controlPoints.size())
		{
			return controlPoints.get(indexPos-1).outputValue;
		}
		else
		{
			return controlPoints.get(indexPos).outputValue;
		}
		}

		// Compute the alpha value used for cubic interpolation.
		double input0 = controlPoints.get(indexPos).inputValue;
		double input1 = controlPoints.get(indexPos).inputValue;
		double alpha = (sourceModuleValue - input0) / (input1 - input0);

		// Now perform the cubic interpolation given the alpha value.
		return Utils.CubicInterp(controlPoints.get(index0).outputValue, controlPoints.get(index1).outputValue, controlPoints.get(index2).outputValue, controlPoints.get(index3).outputValue, alpha);

	}

}
