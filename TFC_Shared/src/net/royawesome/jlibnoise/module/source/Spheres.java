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
package net.royawesome.jlibnoise.module.source;

import net.royawesome.jlibnoise.MathHelper;
import net.royawesome.jlibnoise.Utils;
import net.royawesome.jlibnoise.module.Module;

public class Spheres extends Module {
	/// Default frequency value for the noise::module::Spheres noise module.
	public static final double DEFAULT_SPHERES_FREQUENCY = 1.0;

	/// Frequency of the concentric spheres.
	double frequency = DEFAULT_SPHERES_FREQUENCY;

	public Spheres() {
		super(0);
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	@Override
	public int GetSourceModuleCount() {
		return 0;
	}

    @Override
    public double GetValue(double x, double y, double z) {
        double x1 = x;
        double y1 = y;
        double z1 = z;
        x1 *= frequency;
        y1 *= frequency;
        z1 *= frequency;

        double distFromCenter = MathHelper.sqrt(x1 * x1 + y1 * y1 + z1 * z1);
        double distFromSmallerSphere = distFromCenter - MathHelper.floor(distFromCenter);
        double distFromLargerSphere = 1.0 - distFromSmallerSphere;
        double nearestDist = Utils.GetMin(distFromSmallerSphere, distFromLargerSphere);
        return 1.0 - (nearestDist * 4.0); // Puts it in the -1.0 to +1.0 range.

    }

}
