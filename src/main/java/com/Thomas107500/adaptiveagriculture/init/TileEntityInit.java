package com.Thomas107500.adaptiveagriculture.init;

import com.Thomas107500.adaptiveagriculture.AdaptiveAgriculture;
import com.Thomas107500.adaptiveagriculture.modclass.block.tileentity.PlantedFarmlandTE;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(AdaptiveAgriculture.MOD_ID)
@Mod.EventBusSubscriber(modid = AdaptiveAgriculture.MOD_ID, bus = Bus.MOD)
public class TileEntityInit {
	
	public static TileEntityType<PlantedFarmlandTE> PLANTED_FARMLAND_TE;
	
	@SubscribeEvent
	public static void registerTileEntity(RegistryEvent.Register<TileEntityType<?>> event) 
	{
		//event.getRegistry().register(TileEntityType.Builder.create(PlantedFarmlandTE::new, BlockInit.planted_farmland).build(null).setRegistryName("planted_farmland_te"));
	}


}
