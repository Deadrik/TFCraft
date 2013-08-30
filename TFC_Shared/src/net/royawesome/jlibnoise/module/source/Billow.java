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

import net.royawesome.jlibnoise.Noise;
import net.royawesome.jlibnoise.NoiseQuality;
import net.royawesome.jlibnoise.Utils;
import net.royawesome.jlibnoise.module.Module;

public class Billow extends Module {

	public static final double DEFAULT_BILLOW_FREQUENCY = 1.0;

	public static final double DEFAULT_BILLOW_LACUNARITY = 2.0;

	public static final int DEFAULT_BILLOW_OCTAVE_COUNT = 6;

	public static final double DEFAULT_BILLOW_PERSISTENCE = 0.5;

	public static final NoiseQuality DEFAULT_BILLOW_QUALITY = NoiseQuality.STANDARD;

	public static final int DEFAULT_BILLOW_SEED = 0;

	public static final int BILLOW_MAX_OCTAVE = 30;

	protected double frequency = DEFAULT_BILLOW_FREQUENCY;
	protected double lacunarity = DEFAULT_BILLOW_LACUNARITY;
	protected NoiseQuality quality = DEFAULT_BILLOW_QUALITY;
	protected double persistence = DEFAULT_BILLOW_PERSISTENCE;
	protected int seed = DEFAULT_BILLOW_SEED;
	protected int octaveCount = DEFAULT_BILLOW_OCTAVE_COUNT;

	public Billow() {
		super(0);
	}

	public int getOctaveCount() {
		return octaveCount;
	}

	public void setOctaveCount(int octaveCount) {
		if (octaveCount < 1 || octaveCount > BILLOW_MAX_OCTAVE) {
			throw new IllegalArgumentException("octaveCount must be between 1 and BILLOW_MAX_OCTAVE: " + BILLOW_MAX_OCTAVE);
		}
		this.octaveCount = octaveCount;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	public double getLacunarity() {
		return lacunarity;
	}

	public void setLacunarity(double lacunarity) {
		this.lacunarity = lacunarity;
	}

	public NoiseQuality getQuality() {
		return quality;
	}

	public void setQuality(NoiseQuality quality) {
		this.quality = quality;
	}

	public double getPersistence() {
		return persistence;
	}

	public void setPersistence(double persistance) {
		this.persistence = persistance;
	}

	public int getSeed() {
		return seed;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}

	@Override
	public int GetSourceModuleCount() {
		return 0;
	}

    @Override
    public double GetValue(double x, double y, double z) {
        double z1 = z;
        double y1 = y;
        double x1 = x;
        double value = 0.0;
        double signal;
        double curPersistence = 1.0;
        double nx, ny, nz;
        int seed;

        x1 *= frequency;
        y1 *= frequency;
        z1 *= frequency;

        for (int curOctave = 0; curOctave < octaveCount; curOctave++) {

            // Make sure that these floating-point values have the same range as a 32-
            // bit integer so that we can pass them to the coherent-noise functions.
            nx = Utils.MakeInt32Range(x1);
            ny = Utils.MakeInt32Range(y1);
            nz = Utils.MakeInt32Range(z1);

            // Get the coherent-noise value from the input value and add it to the
            // final result.
            seed = (this.seed + curOctave);
            signal = Noise.GradientCoherentNoise3D(nx, ny, nz, seed, quality);
            signal = 2.0 * Math.abs(signal) - 1.0;
            value += signal * curPersistence;

            // Prepare the next octave.
            x1 *= lacunarity;
            y1 *= lacunarity;
            z1 *= lacunarity;
            curPersistence *= persistence;
        }
        value += 0.5;

        return value;
    }

}
