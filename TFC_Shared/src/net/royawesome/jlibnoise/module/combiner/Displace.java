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

import net.royawesome.jlibnoise.exception.NoModuleException;
import net.royawesome.jlibnoise.module.Module;

public class Displace extends Module {

	public Displace() {
		super(4);
	}

	@Override
	public int GetSourceModuleCount() {
		return 4;
	}

	public Module GetXDisplaceModule() {
		if (SourceModule == null || SourceModule[1] == null) {
			throw new NoModuleException();
		}
		return SourceModule[1];
	}

	public Module GetYDisplaceModule() {
		if (SourceModule == null || SourceModule[2] == null) {
			throw new NoModuleException();
		}
		return SourceModule[2];
	}

	public Module GetZDisplaceModule() {
		if (SourceModule == null || SourceModule[3] == null) {
			throw new NoModuleException();
		}
		return SourceModule[3];
	}

	public void SetXDisplaceModule(Module x) {
		if (x == null)
			throw new IllegalArgumentException("x cannot be null");
		SourceModule[1] = x;
	}

	public void SetYDisplaceModule(Module y) {
		if (y == null)
			throw new IllegalArgumentException("y cannot be null");
		SourceModule[2] = y;
	}

	public void SetZDisplaceModule(Module z) {
		if (z == null)
			throw new IllegalArgumentException("z cannot be null");
		SourceModule[3] = z;
	}

	public void SetDisplaceModules(Module x, Module y, Module z) {
		SetXDisplaceModule(x);
		SetYDisplaceModule(y);
		SetZDisplaceModule(z);
	}

	@Override
	public double GetValue(double x, double y, double z) {
		if (SourceModule[0] == null)
			throw new NoModuleException();
		if (SourceModule[1] == null)
			throw new NoModuleException();
		if (SourceModule[2] == null)
			throw new NoModuleException();
		if (SourceModule[3] == null)
			throw new NoModuleException();

		// Get the output values from the three displacement modules.  Add each
		// value to the corresponding coordinate in the input value.
		double xDisplace = x + (SourceModule[1].GetValue(x, y, z));
		double yDisplace = y + (SourceModule[2].GetValue(x, y, z));
		double zDisplace = z + (SourceModule[3].GetValue(x, y, z));

		// Retrieve the output value using the offsetted input value instead of
		// the original input value.
		return SourceModule[0].GetValue(xDisplace, yDisplace, zDisplace);

	}

}
