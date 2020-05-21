package com.Thomas107500.adaptiveagriculture.integration;

import com.Thomas107500.adaptiveagriculture.AdaptiveAgriculture;

import net.minecraftforge.fml.ModList;

public class IntergrationManager {
	
	public static boolean isphc2loaded = false;
	
	public static void processIntegration() 
	{
		if(ModList.get().isLoaded("pamhc2crops")) 
		{
			AdaptiveAgriculture.LOGGER.debug("Pam Harvestcraft 2-Crops detected! performing mod integration...");
			isphc2loaded = true;
		
		}
	}
}
