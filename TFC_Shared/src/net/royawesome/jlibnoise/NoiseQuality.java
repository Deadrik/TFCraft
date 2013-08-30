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

package net.royawesome.jlibnoise;

public enum NoiseQuality {
	/**
	 *  Generates coherent noise quickly.  When a coherent-noise function with
	 *	this quality setting is used to generate a bump-map image, there are
	 *  noticeable "creasing" artifacts in the resulting image.  This is
	 *  because the derivative of that function is discontinuous at integer 
	 *  boundaries.
	 */   
	FAST,
	/**
	 * Generates standard-quality coherent noise. When a coherent-noise function
	 * with this quality setting is used to generate a bump-map image, there are
	 * some minor "creasing" artifacts in the resulting image. This is because
	 * the second derivative of that function is discontinuous at integer
	 * boundaries.
	 */
	STANDARD,
	/**
	 * Generates the best-quality coherent noise. When a coherent-noise function
	 * with this quality setting is used to generate a bump-map image, there are
	 * no "creasing" artifacts in the resulting image. This is because the first
	 * and second derivatives of that function are continuous at integer
	 * boundaries.
	 */
	BEST
}
