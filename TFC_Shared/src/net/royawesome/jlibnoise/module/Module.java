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

package net.royawesome.jlibnoise.module;

import net.royawesome.jlibnoise.exception.NoModuleException;

public abstract class Module {
	protected Module[] SourceModule;

	public Module(int sourceModuleCount) {
		SourceModule = null;

		// Create an array of pointers to all source modules required by this
		// noise module.  Set these pointers to NULL.
		if (sourceModuleCount > 0) {
			SourceModule = new Module[sourceModuleCount];
			for (int i = 0; i < sourceModuleCount; i++) {
				SourceModule[i] = null;
			}
		} else {
			SourceModule = null;
		}

	}

	public Module getSourceModule(int index) {
		if (index >= GetSourceModuleCount() || index < 0 || SourceModule[index] == null) {
			throw new NoModuleException();
		}
		return (SourceModule[index]);

	}

	public void setSourceModule(int index, Module sourceModule) {
		if (SourceModule == null)
			return;
		if (index >= GetSourceModuleCount() || index < 0) {
			throw new IllegalArgumentException("Index must be between 0 and GetSourceMoudleCount()");
		}
		SourceModule[index] = sourceModule;
	}

	public abstract int GetSourceModuleCount();

	public abstract double GetValue(double x, double y, double z);
}
