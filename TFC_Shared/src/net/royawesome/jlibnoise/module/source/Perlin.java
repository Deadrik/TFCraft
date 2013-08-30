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

public class Perlin extends Module {

	/// Default frequency for the noise::module::Perlin noise module.
	public static final double DEFAULT_PERLIN_FREQUENCY = 1.0;

	/// Default lacunarity for the noise::module::Perlin noise module.
	public static final double DEFAULT_PERLIN_LACUNARITY = 2.0;

	/// Default number of octaves for the noise::module::Perlin noise module.
	public static final int DEFAULT_PERLIN_OCTAVE_COUNT = 6;

	/// Default persistence value for the noise::module::Perlin noise module.
	public static final double DEFAULT_PERLIN_PERSISTENCE = 0.5;

	/// Default noise quality for the noise::module::Perlin noise module.
	public static final NoiseQuality DEFAULT_PERLIN_QUALITY = NoiseQuality.STANDARD;

	/// Default noise seed for the noise::module::Perlin noise module.
	public static final int DEFAULT_PERLIN_SEED = 0;

	/// Maximum number of octaves for the noise::module::Perlin noise module.
	public static final int PERLIN_MAX_OCTAVE = 30;

	/// Frequency of the first octave.
	double frequency = DEFAULT_PERLIN_FREQUENCY;

	/// Frequency multiplier between successive octaves.
	double lacunarity = DEFAULT_PERLIN_LACUNARITY;

	/// Quality of the Perlin noise.
	NoiseQuality noiseQuality = DEFAULT_PERLIN_QUALITY;

	/// Total number of octaves that generate the Perlin noise.
	int octaveCount = DEFAULT_PERLIN_OCTAVE_COUNT;

	/// Persistence of the Perlin noise.
	double persistence = DEFAULT_PERLIN_PERSISTENCE;

	/// Seed value used by the Perlin-noise function.
	int seed = DEFAULT_PERLIN_SEED;

	public Perlin() {
		super(0);
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

	public NoiseQuality getNoiseQuality() {
		return noiseQuality;
	}

	public void setNoiseQuality(NoiseQuality noiseQuality) {
		this.noiseQuality = noiseQuality;
	}

	public int getOctaveCount() {
		return octaveCount;
	}

	public void setOctaveCount(int octaveCount) {
		if (octaveCount < 1 || octaveCount > PERLIN_MAX_OCTAVE) {
			throw new IllegalArgumentException("octaveCount must be between 1 and MAX OCTAVE: " + PERLIN_MAX_OCTAVE);
		}

		this.octaveCount = octaveCount;
	}

	public double getPersistence() {
		return persistence;
	}

	public void setPersistence(double persistence) {
		this.persistence = persistence;
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
        double x1 = x;
        double y1 = y;
        double z1 = z;
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
            signal = Noise.GradientCoherentNoise3D(nx, ny, nz, seed, noiseQuality);
            value += signal * curPersistence;

            // Prepare the next octave.
            x1 *= lacunarity;
            y1 *= lacunarity;
            z1 *= lacunarity;
            curPersistence *= persistence;
        }

        return value;

    }

}
