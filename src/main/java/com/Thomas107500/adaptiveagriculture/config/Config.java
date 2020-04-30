package com.Thomas107500.adaptiveagriculture.config;

import org.apache.commons.lang3.tuple.Pair;

import com.Thomas107500.adaptiveagriculture.AdaptiveAgriculture;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = AdaptiveAgriculture.MOD_ID, bus = Bus.MOD)
public class Config {
	
	public static class Common {
		
		public final BooleanValue breakCropOnInfertileFarmland;
		
		
		public Common(ForgeConfigSpec.Builder builder) {
			builder.comment("Adaptive Agriculture Configurations")
				   .push("adaptive_agriculture");
			
			breakCropOnInfertileFarmland = builder
					.comment("This is set to true to enable infertile farmland breaks normal crop when they are on top of it and false to disable this feature." + "\n" +
							 "#Value: true/false  DEFAULT: false")
					.translation("adaptive_agriculture.configgui.breakCropOnInfertileFarmland")
					.worldRestart()
					.define("breakCropOnInfertileFarmland", false);
			
			builder.pop();
		}
	}
	
	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;
	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}
	
	@SubscribeEvent
	public static void onConfigLoad(final ModConfig.Loading event) {
		
	}
	
	@SubscribeEvent
	public static void onConfigReload(final ModConfig.Reloading event) {
		
	}
}
