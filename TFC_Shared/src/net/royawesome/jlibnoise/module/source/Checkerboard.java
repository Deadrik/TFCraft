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

public class Checkerboard extends Module {

	public Checkerboard() {
		super(0);

	}

	@Override
	public int GetSourceModuleCount() {

		return 0;
	}

	@Override
	public double GetValue(double x, double y, double z) {
		int ix = (int) (MathHelper.floor(Utils.MakeInt32Range(x)));
		int iy = (int) (MathHelper.floor(Utils.MakeInt32Range(y)));
		int iz = (int) (MathHelper.floor(Utils.MakeInt32Range(z)));
		return ((ix & 1 ^ iy & 1 ^ iz & 1) != 0) ? -1.0 : 1.0;
	}

}
