package com.Thomas107500.adaptiveagriculture.events;

import com.Thomas107500.adaptiveagriculture.AdaptiveAgriculture;
import com.Thomas107500.adaptiveagriculture.init.BlockInit;

import net.minecraft.block.BeetrootBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.CropGrowEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = AdaptiveAgriculture.MOD_ID, bus = Bus.FORGE)
public class BonusGrowthHandler {
//LETS DO INCREASE IN GROWTH SPEED INSTEAD OF BONUS LOOT :P
	@SubscribeEvent
	public static void HarvestDropListener(CropGrowEvent.Pre event) 
	{
		Block plantedOnBlock = event.getWorld().getBlockState(event.getPos().down()).getBlock();
		Block targetCropBlock = event.getState().getBlock();
		
		if (targetCropBlock instanceof CropsBlock && plantedOnBlock == BlockInit.nutrient_rich_dirt) 
		{
			event.getWorld().setBlockState(event.getPos(), event.getState().with(CropsBlock.AGE, event.getState().get(CropsBlock.AGE) + 1), 2);
			event.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), 0.1f, 0.1f, 0.1f);
		}
		else if(targetCropBlock instanceof BeetrootBlock && plantedOnBlock == BlockInit.nutrient_rich_dirt) 
		{
			event.getWorld().setBlockState(event.getPos(), event.getState().with(BeetrootBlock.AGE, event.getState().get(BeetrootBlock.AGE) + 1), 2);	
			event.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), 0.1f, 0.1f, 0.1f);
		}
		else if(targetCropBlock instanceof SweetBerryBushBlock && plantedOnBlock == BlockInit.nutrient_rich_dirt)
		{
			event.getWorld().setBlockState(event.getPos(), event.getState().with(SweetBerryBushBlock.AGE, event.getState().get(SweetBerryBushBlock.AGE) + 1), 2);
			event.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), 0.1f, 0.1f, 0.1f);
		}
	}

	
	




}
