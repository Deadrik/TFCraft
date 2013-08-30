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

public class RidgedMulti extends Module {

	/// Default frequency for the noise::module::RidgedMulti noise module.
	public static final double DEFAULT_RIDGED_FREQUENCY = 1.0;

	/// Default lacunarity for the noise::module::RidgedMulti noise module.
	public static final double DEFAULT_RIDGED_LACUNARITY = 2.0;

	/// Default number of octaves for the noise::module::RidgedMulti noise
	/// module.
	public static final int DEFAULT_RIDGED_OCTAVE_COUNT = 6;

	/// Default noise quality for the noise::module::RidgedMulti noise
	/// module.
	public static final NoiseQuality DEFAULT_RIDGED_QUALITY = NoiseQuality.STANDARD;

	/// Default noise seed for the noise::module::RidgedMulti noise module.
	public static final int DEFAULT_RIDGED_SEED = 0;

	/// Maximum number of octaves for the noise::module::RidgedMulti noise
	/// module.
	public static final int RIDGED_MAX_OCTAVE = 30;

	double frequency = DEFAULT_RIDGED_FREQUENCY;

	/// Frequency multiplier between successive octaves.
	double lacunarity = DEFAULT_RIDGED_LACUNARITY;

	/// Quality of the ridged-multifractal noise.
	NoiseQuality noiseQuality = DEFAULT_RIDGED_QUALITY;

	/// Total number of octaves that generate the ridged-multifractal
	/// noise.
	int octaveCount = DEFAULT_RIDGED_OCTAVE_COUNT;

	/// Contains the spectral weights for each octave.
	double[] SpectralWeights;

	/// Seed value used by the ridged-multfractal-noise function.
	int seed = DEFAULT_RIDGED_SEED;

	public RidgedMulti() {
		super(0);
		CalcSpectralWeights();
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
		this.octaveCount = Utils.GetMin(octaveCount, RIDGED_MAX_OCTAVE);
	}

	public int getSeed() {
		return seed;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}

	protected void CalcSpectralWeights() {
		// This exponent parameter should be user-defined; it may be exposed in a
		// future version of libnoise.
		double h = 1.0;

		double frequency = 1.0;
		SpectralWeights = new double[RIDGED_MAX_OCTAVE];
		for (int i = 0; i < RIDGED_MAX_OCTAVE; i++) {
			// Compute weight for each frequency.
			SpectralWeights[i] = Math.pow(frequency, -h);
			frequency *= lacunarity;
		}

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

        double signal;
        double value = 0.0;
        double weight = 1.0;

        // These parameters should be user-defined; they may be exposed in a
        // future version of libnoise.
        double offset = 1.0;
        double gain = 2.0;

        for (int curOctave = 0; curOctave < octaveCount; curOctave++) {

            // Make sure that these floating-point values have the same range as a 32-
            // bit integer so that we can pass them to the coherent-noise functions.
            double nx, ny, nz;
            nx = Utils.MakeInt32Range(x1);
            ny = Utils.MakeInt32Range(y1);
            nz = Utils.MakeInt32Range(z1);

            // Get the coherent-noise value.
            int seed = (this.seed + curOctave) & 0x7fffffff;
            signal = Noise.GradientCoherentNoise3D(nx, ny, nz, seed, noiseQuality);

            // Make the ridges.
            signal = Math.abs(signal);
            signal = offset - signal;

            // Square the signal to increase the sharpness of the ridges.
            //noinspection UnusedAssignment
            signal *= signal;

            // The weighting from the previous octave is applied to the signal.
            // Larger values have higher weights, producing sharp points along the
            // ridges.
            signal *= weight;

            // Weight successive contributions by the previous signal.
            weight = signal * gain;
            if (weight > 1.0) {
                weight = 1.0;
            }
            if (weight < 0.0) {
                weight = 0.0;
            }

            // Add the signal to the output value.
            value += (signal * SpectralWeights[curOctave]);

            // Go to the next octave.
            x1 *= lacunarity;
            y1 *= lacunarity;
            z1 *= lacunarity;
        }

        return (value * 1.25) - 1.0;

    }

}
