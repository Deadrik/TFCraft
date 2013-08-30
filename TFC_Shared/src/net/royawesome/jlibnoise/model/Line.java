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

package net.royawesome.jlibnoise.model;

import net.royawesome.jlibnoise.exception.NoModuleException;
import net.royawesome.jlibnoise.module.Module;

/**
 * Model that defines the displacement of a line segment.
 * 
 * This model returns an output value from a noise module given the
 * one-dimensional coordinate of an input value located on a line segment, which
 * can be used as displacements.
 * 
 * This class is useful for creating: - roads and rivers - disaffected college
 * students
 * 
 * To generate an output value, pass an input value between 0.0 and 1.0 to the
 * GetValue() method. 0.0 represents the start position of the line segment and
 * 1.0 represents the end position of the line segment.
 */
public class Line {
	// A flag that specifies whether the value is to be attenuated
	// (moved toward 0.0) as the ends of the line segment are approached.
	boolean attenuate = false;

	// A pointer to the noise module used to generate the output values.
	Module module;

	// @a x coordinate of the start of the line segment.
	double x0 = 0;

	// @a x coordinate of the end of the line segment.
	double x1 = 1;

	// @a y coordinate of the start of the line segment.
	double y0 = 0;

	// @a y coordinate of the end of the line segment.
	double y1 = 1;

	// @a z coordinate of the start of the line segment.
	double z0 = 0;

	// @a z coordinate of the end of the line segment.
	double z1 = 1;

	/**
	 * @param module The noise module that is used to generate the output
	 *            values.
	 */
	public Line(Module module) {
		if (module == null)
			throw new IllegalArgumentException("module cannot be null");
		this.module = module;
	}

	/**
	 * Returns a flag indicating whether the output value is to be attenuated
	 * (moved toward 0.0) as the ends of the line segment are approached by the
	 * input value.
	 * 
	 * @return true if the value is to be attenuated false if not.
	 */
	public boolean attenuate() {
		return this.attenuate;
	}

	/**
	 * Sets a flag indicating that the output value is to be attenuated (moved
	 * toward 0.0) as the ends of the line segment are approached.
	 * 
	 * @param att A flag that specifies whether the output value is to be
	 *            attenuated.
	 */
	public void setAttenuate(boolean att) {
		this.attenuate = att;
	}

	/**
	 * Sets the position ( @a x, @a y, @a z ) of the start of the line segment
	 * to choose values along.
	 * 
	 * @param x x coordinate of the start position.
	 * @param y y coordinate of the start position.
	 * @param z z coordinate of the start position.
	 */
	public void setStartPoint(double x, double y, double z) {
		this.x0 = x;
		this.y0 = y;
		this.z0 = z;
	}

	/**
	 * Sets the position ( @a x, @a y, @a z ) of the end of the line segment to
	 * choose values along.
	 * 
	 * @param x x coordinate of the end position.
	 * @param y y coordinate of the end position.
	 * @param z z coordinate of the end position.
	 */
	public void setEndPoint(double x, double y, double z) {
		this.x1 = x;
		this.y1 = y;
		this.z1 = z;
	}

	/**
	 * Returns the noise module that is used to generate the output values.
	 * 
	 * @returns A reference to the noise module.
	 * @pre A noise module was passed to the SetModule() method.
	 */
	public Module getModule() {
		return module;
	}

	/**
	 * Sets the noise module that is used to generate the output values.
	 * 
	 * @param module The noise module that is used to generate the output
	 *            values.
	 * 
	 *            This noise module must exist for the lifetime of this object,
	 *            until you pass a new noise module to this method.
	 */
	public void setModule(Module module) {
		if (module == null)
			throw new IllegalArgumentException("module cannot be null");
		this.module = module;
	}

	/**
	 * Returns the output value from the noise module given the one-dimensional
	 * coordinate of the specified input value located on the line segment.
	 * 
	 * @param p The distance along the line segment (ranges from 0.0 to 1.0)
	 * @return The output value from the noise module.
	 * @pre A noise module was passed to the SetModule() method.
	 * @pre The start and end points of the line segment were specified.
	 * 
	 *      The output value is generated by the noise module passed to the
	 *      SetModule() method. This value may be attenuated (moved toward 0.0)
	 *      as @a p approaches either end of the line segment; this is the
	 *      default behavior.
	 * 
	 *      If the value is not to be attenuated, @a p can safely range outside
	 *      the 0.0 to 1.0 range; the output value will be extrapolated along
	 *      the line that this segment is part of.
	 */
	public double getValue(double p) {
		if (module == null)
			throw new NoModuleException();

		double x = (x1 - x0) * p + x0;
		double y = (y1 - y0) * p + y0;
		double z = (z1 - z0) * p + z0;
		double value = module.GetValue(x, y, z);

		if (attenuate) {
			return p * (1.0 - p) * 4 * value;
		} else {
			return value;
		}

	}

}
