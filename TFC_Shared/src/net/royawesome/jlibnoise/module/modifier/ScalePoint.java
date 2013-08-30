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

import net.royawesome.jlibnoise.exception.NoModuleException;
import net.royawesome.jlibnoise.module.Module;

public class ScalePoint extends Module {

	/// Default scaling factor applied to the @a x coordinate for the
	/// noise::module::ScalePoint noise module.
	public static final double DEFAULT_SCALE_POINT_X = 1.0;

	/// Default scaling factor applied to the @a y coordinate for the
	/// noise::module::ScalePoint noise module.
	public static final double DEFAULT_SCALE_POINT_Y = 1.0;

	/// Default scaling factor applied to the @a z coordinate for the
	/// noise::module::ScalePoint noise module.
	public static final double DEFAULT_SCALE_POINT_Z = 1.0;

	/// Scaling factor applied to the @a x coordinate of the input value.
	double xScale = DEFAULT_SCALE_POINT_X;

	/// Scaling factor applied to the @a y coordinate of the input value.
	double yScale = DEFAULT_SCALE_POINT_Y;

	/// Scaling factor applied to the @a z coordinate of the input value.
	double zScale = DEFAULT_SCALE_POINT_Z;

	public ScalePoint() {
		super(1);
	}

	public double getxScale() {
		return xScale;
	}

	public void setxScale(double xScale) {
		this.xScale = xScale;
	}

	public double getyScale() {
		return yScale;
	}

	public void setyScale(double yScale) {
		this.yScale = yScale;
	}

	public double getzScale() {
		return zScale;
	}

	public void setzScale(double zScale) {
		this.zScale = zScale;
	}

	@Override
	public int GetSourceModuleCount() {
		return 1;
	}

	@Override
	public double GetValue(double x, double y, double z) {
		if (SourceModule[0] == null)
			throw new NoModuleException();

		return SourceModule[0].GetValue(x * xScale, y * yScale, z * zScale);
	}

}
