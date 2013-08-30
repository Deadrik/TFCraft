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
package net.royawesome.jlibnoise.module.combiner;

import net.royawesome.jlibnoise.Utils;
import net.royawesome.jlibnoise.exception.NoModuleException;
import net.royawesome.jlibnoise.module.Module;

public class Select extends Module {
	/// Default edge-falloff value for the noise::module::Select noise module.
	public static final double DEFAULT_SELECT_EDGE_FALLOFF = 0.0;

	/// Default lower bound of the selection range for the
	/// noise::module::Select noise module.
	public static final double DEFAULT_SELECT_LOWER_BOUND = -1.0;

	/// Default upper bound of the selection range for the
	/// noise::module::Select noise module.
	public static final double DEFAULT_SELECT_UPPER_BOUND = 1.0;

	/// Edge-falloff value.
    double edgeFalloff = DEFAULT_SELECT_EDGE_FALLOFF;

	/// Lower bound of the selection range.
	double lowerBound = DEFAULT_SELECT_LOWER_BOUND;

	/// Upper bound of the selection range.
	double upperBound = DEFAULT_SELECT_UPPER_BOUND;

	public Select() {
		super(3);
	}

	public Module getControlModule() {
		if (SourceModule == null || SourceModule[2] == null) {
			throw new NoModuleException();
		}
		return SourceModule[2];
	}

	public void setControlModule(Module m) {
		if (m == null)
			throw new IllegalArgumentException("the module cannot be null");
		SourceModule[2] = m;
	}

	public double getEdgeFalloff() {
		return edgeFalloff;
	}

    public void setEdgeFalloff(double edgeFalloff) {
        // Make sure that the edge falloff curves do not overlap.
        double boundSize = upperBound - lowerBound;
        this.edgeFalloff = (edgeFalloff > boundSize / 2) ? boundSize / 2 : edgeFalloff;

    }

	public double getLowerBound() {
		return lowerBound;
	}

	public double getUpperBound() {
		return upperBound;
	}

	public void setBounds(double lower, double upper) {
		if (lower > upper)
			throw new IllegalArgumentException("lower must be less than upper");
		this.lowerBound = lower;
		this.upperBound = upper;

		setEdgeFalloff(edgeFalloff);
	}

	@Override
	public int GetSourceModuleCount() {
		return 3;
	}

	@Override
	public double GetValue(double x, double y, double z) {
		if (SourceModule[0] == null)
			throw new NoModuleException();
		if (SourceModule[1] == null)
			throw new NoModuleException();
		if (SourceModule[2] == null)
			throw new NoModuleException();

		double controlValue = SourceModule[2].GetValue(x, y, z);
		double alpha;
		if (edgeFalloff > 0.0) {
			if (controlValue < (lowerBound - edgeFalloff)) {
				// The output value from the control module is below the selector
				// threshold; return the output value from the first source module.
				return SourceModule[0].GetValue(x, y, z);

			} else if (controlValue < (lowerBound + edgeFalloff)) {
				// The output value from the control module is near the lower end of the
				// selector threshold and within the smooth curve. Interpolate between
				// the output values from the first and second source modules.
				double lowerCurve = (lowerBound - edgeFalloff);
				double upperCurve = (lowerBound + edgeFalloff);
				alpha = Utils.SCurve3((controlValue - lowerCurve) / (upperCurve - lowerCurve));
				return Utils.LinearInterp(SourceModule[0].GetValue(x, y, z), SourceModule[1].GetValue(x, y, z), alpha);

			} else if (controlValue < (upperBound - edgeFalloff)) {
				// The output value from the control module is within the selector
				// threshold; return the output value from the second source module.
				return SourceModule[1].GetValue(x, y, z);

			} else if (controlValue < (upperBound + edgeFalloff)) {
				// The output value from the control module is near the upper end of the
				// selector threshold and within the smooth curve. Interpolate between
				// the output values from the first and second source modules.
				double lowerCurve = (upperBound - edgeFalloff);
				double upperCurve = (upperBound + edgeFalloff);
				alpha = Utils.SCurve3((controlValue - lowerCurve) / (upperCurve - lowerCurve));
				return Utils.LinearInterp(SourceModule[1].GetValue(x, y, z), SourceModule[0].GetValue(x, y, z), alpha);

			} else {
				// Output value from the control module is above the selector threshold;
				// return the output value from the first source module.
				return SourceModule[0].GetValue(x, y, z);
			}
		} else {
			if (controlValue < lowerBound || controlValue > upperBound) {
				return SourceModule[0].GetValue(x, y, z);
			} else {
				return SourceModule[1].GetValue(x, y, z);
			}
		}

	}

}
