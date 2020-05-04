package com.Thomas107500.adaptiveagriculture.init;

import com.Thomas107500.adaptiveagriculture.AdaptiveAgriculture;
import com.Thomas107500.adaptiveagriculture.modclass.block.CoverCrop;
import com.Thomas107500.adaptiveagriculture.modclass.block.CropBase;
import com.Thomas107500.adaptiveagriculture.modclass.block.FarmlandBase;
import com.Thomas107500.adaptiveagriculture.modclass.block.InfertileFarmland;

import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(AdaptiveAgriculture.MOD_ID)
@Mod.EventBusSubscriber(modid = AdaptiveAgriculture.MOD_ID, bus = Bus.MOD)
public class BlockInit {

	public static final InfertileFarmland infertile_farmland = null;
	public static final Block infertile_dirt = null;
	public static final CoverCrop cover_crop = null;
	public static final BlockNamedItem cover_crop_seed = null;
	public static final FarmlandBase nourished_farmland = null;
	public static final Block nourished_dirt = null;
	public static final FarmlandBase nutrient_rich_farmland = null;
	public static final Block nutrient_rich_dirt = null;
	public static final CropBase weed_crop = null;
	
	@SubscribeEvent
	public static final void registerBlocks(RegistryEvent.Register<Block> event) 
	{
		event.getRegistry().register(new InfertileFarmland(FarmlandBlock.Properties.create(Material.EARTH)
				.hardnessAndResistance(0.6f, 6.0f)
				.sound(SoundType.GROUND)
				.harvestTool(ToolType.SHOVEL))
				.setRegistryName("infertile_farmland"));
	
		event.getRegistry().register(new Block(Block.Properties.create(Material.EARTH)
				.hardnessAndResistance(0.6f, 6.0f)
				.sound(SoundType.GROUND)
				.harvestTool(ToolType.SHOVEL))
				.setRegistryName("infertile_dirt"));
	
		event.getRegistry().register(new CoverCrop(CoverCrop.Properties.create(Material.PLANTS)
				.doesNotBlockMovement()
				.hardnessAndResistance(0f)
				.sound(SoundType.PLANT))
				.setRegistryName("cover_crop"));
	
		event.getRegistry().register(new FarmlandBase(FarmlandBase.Properties.create(Material.EARTH)
				.hardnessAndResistance(0.6f, 0.6f)
				.sound(SoundType.GROUND)
				.harvestTool(ToolType.SHOVEL))
				.setRegistryName("nourished_farmland"));
	
		event.getRegistry().register(new Block(Block.Properties.create(Material.EARTH)
				.hardnessAndResistance(0.6f, 6.0f)
				.sound(SoundType.GROUND)
				.harvestTool(ToolType.SHOVEL))
				.setRegistryName("nourished_dirt"));
	
		event.getRegistry().register(new FarmlandBase(FarmlandBase.Properties.create(Material.EARTH)
				.hardnessAndResistance(0.6f, 0.6f)
				.sound(SoundType.GROUND)
				.harvestTool(ToolType.SHOVEL))
				.setRegistryName("nutrient_rich_farmland"));
	
		event.getRegistry().register(new Block(Block.Properties.create(Material.EARTH)
				.hardnessAndResistance(0.6f, 0.6f)
				.sound(SoundType.GROUND)
				.harvestTool(ToolType.SHOVEL))
				.setRegistryName("nutrient_rich_dirt"));
	
		event.getRegistry().register(new CropBase(CropBase.Properties.create(Material.PLANTS)
				.doesNotBlockMovement()
				.hardnessAndResistance(0f)
				.sound(SoundType.PLANT))
				.setRegistryName("weed_crop"));
	}

	@SubscribeEvent
	public static final void registerBlockItems(RegistryEvent.Register<Item> event)
	{
		
		event.getRegistry().register(new BlockItem(infertile_farmland, new Item.Properties())
				.setRegistryName("infertile_farmland"));
	
		event.getRegistry().register(new BlockItem(infertile_dirt, new Item.Properties()
				.group(AdaptiveAgriculture.CREATIVE_TAB))
				.setRegistryName("infertile_dirt"));
	
		event.getRegistry().register(new BlockNamedItem(BlockInit.cover_crop, new BlockNamedItem.Properties()
				.group(AdaptiveAgriculture.CREATIVE_TAB))
				.setRegistryName("cover_crop_seed"));
		
		event.getRegistry().register(new BlockItem(nourished_farmland, new Item.Properties()
				.group(AdaptiveAgriculture.CREATIVE_TAB))
				.setRegistryName("nourished_farmland"));
	
		event.getRegistry().register(new BlockItem(nourished_dirt, new Item.Properties()
				.group(AdaptiveAgriculture.CREATIVE_TAB))
				.setRegistryName("nourished_dirt"));
	
		event.getRegistry().register(new BlockItem(nutrient_rich_farmland, new Item.Properties()
				.group(AdaptiveAgriculture.CREATIVE_TAB))
				.setRegistryName("nutrient_rich_farmland"));
		
		event.getRegistry().register(new BlockItem(nutrient_rich_dirt, new Item.Properties()
				.group(AdaptiveAgriculture.CREATIVE_TAB))
				.setRegistryName("nutrient_rich_dirt"));
	
	
	}


}
