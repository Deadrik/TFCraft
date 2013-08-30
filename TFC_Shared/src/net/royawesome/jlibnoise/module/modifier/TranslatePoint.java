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

public class TranslatePoint extends Module {
	/// Default translation factor applied to the @a x coordinate for the
	/// noise::module::TranslatePoint noise module.
	public static final double DEFAULT_TRANSLATE_POINT_X = 0.0;

	/// Default translation factor applied to the @a y coordinate for the
	/// noise::module::TranslatePoint noise module.
	public static final double DEFAULT_TRANSLATE_POINT_Y = 0.0;

	/// Default translation factor applied to the @a z coordinate for the
	/// noise::module::TranslatePoint noise module.
	public static final double DEFAULT_TRANSLATE_POINT_Z = 0.0;

	/// Translation amount applied to the @a x coordinate of the input
	/// value.
	double xTranslation = DEFAULT_TRANSLATE_POINT_X;

	/// Translation amount applied to the @a y coordinate of the input
	/// value.
	double yTranslation = DEFAULT_TRANSLATE_POINT_Y;

	/// Translation amount applied to the @a z coordinate of the input
	/// value.
	double zTranslation = DEFAULT_TRANSLATE_POINT_Z;

	public TranslatePoint() {
		super(1);
	}

	public double getXTranslation() {
		return xTranslation;
	}

	public void setXTranslation(double xTranslation) {
		this.xTranslation = xTranslation;
	}

	public double getYTranslation() {
		return yTranslation;
	}

	public void setYTranslation(double yTranslation) {
		this.yTranslation = yTranslation;
	}

	public double getZTranslation() {
		return zTranslation;
	}

	public void setZTranslation(double zTranslation) {
		this.zTranslation = zTranslation;
	}

	public void setTranslations(double x, double y, double z) {
		setXTranslation(x);
		setYTranslation(y);
		setZTranslation(z);
	}

	@Override
	public int GetSourceModuleCount() {
		return 1;
	}

	@Override
	public double GetValue(double x, double y, double z) {
		if (SourceModule[0] == null)
			throw new NoModuleException();

		return SourceModule[0].GetValue(x + xTranslation, y + yTranslation, z + zTranslation);
	}

}
