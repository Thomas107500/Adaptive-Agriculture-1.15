package com.Thomas107500.adaptiveagriculture.util;

import java.util.Random;

public class RandomHelper {
	public static boolean getWeightedRoll(double prob) 
	{
		Random random = new Random();
		
		return random.ints(0, 100).findAny().getAsInt() < Math.round((float)prob*100) ? true : false;
				
	}
}
