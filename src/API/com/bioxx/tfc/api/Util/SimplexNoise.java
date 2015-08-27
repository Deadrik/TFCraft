package com.bioxx.tfc.api.Util;

import java.util.Random;


/*
 * A speed-improved simplex noise algorithm for 2D, 3D and 4D in Java.
 *
 * Based on example code by Stefan Gustavson (stegu@itn.liu.se).
 * Optimisations by Peter Eastman (peastman@drizzle.stanford.edu).
 * Better rank ordering method by Stefan Gustavson in 2012.
 *
 * This could be speeded up even further, but it's useful as it is.
 *
 * Version 2012-03-09
 *
 * This code was placed in the public domain by its original author,
 * Stefan Gustavson. You may use it as you see fit, but
 * attribution is appreciated.
 *
 */

public class SimplexNoise {

	public SimplexNoise_Octave[] octaves;
	public double[] frequencys;
	public double[] amplitudes;

	public int largestFeature;
	public double persistence;
	public long seed;

	public SimplexNoise(int largestFeature,double persistence, long seed){
		this.largestFeature=largestFeature;
		this.persistence=persistence;
		this.seed=seed;

		//recieves a number (eg 128) and calculates what power of 2 it is (eg 2^7)
		int numberOfOctaves=(int)Math.ceil(Math.log10(largestFeature)/Math.log10(2));

		octaves=new SimplexNoise_Octave[numberOfOctaves];
		frequencys=new double[numberOfOctaves];
		amplitudes=new double[numberOfOctaves];

		Random rnd=new Random(seed);

		for(int i=0;i<numberOfOctaves;i++){
			octaves[i]=new SimplexNoise_Octave(rnd.nextInt());

			frequencys[i] = Math.pow(2,i);
			amplitudes[i] = Math.pow(persistence,octaves.length-i);

		}

	}


	public double getNoise(int x, int y){

		double result=0;

		for(int i=0;i<octaves.length;i++){
			//double frequency = Math.pow(2,i);
			//double amplitude = Math.pow(persistence,octaves.length-i);

			result=result+octaves[i].noise(x/frequencys[i], y/frequencys[i])* amplitudes[i];
		}


		return result;

	}

	public double getNoise(int x,int y, int z){

		double result=0;

		for(int i=0;i<octaves.length;i++){
			double frequency = Math.pow(2,i);
			double amplitude = Math.pow(persistence,octaves.length-i);

			result=result+octaves[i].noise(x/frequency, y/frequency,z/frequency)* amplitude;
		}


		return result;

	}

	public double[] getNoiseArray(int xSize, int zSize)
	{
		double[] outNoise = new double[xSize*zSize];
		for(int x = 0; x < 16; x++)
		{
			for(int z = 0; z < 16; z++)
			{
				outNoise[x+z*16] = getNoise(x, z);
			}
		}
		return outNoise;
	}
} 
