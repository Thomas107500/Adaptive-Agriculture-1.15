package com.Thomas107500.adaptiveagriculture.events;

import com.Thomas107500.adaptiveagriculture.AdaptiveAgriculture;
import com.Thomas107500.adaptiveagriculture.config.Config;
import com.Thomas107500.adaptiveagriculture.init.BlockInit;
import com.Thomas107500.adaptiveagriculture.modclass.block.CoverCrop;
import com.Thomas107500.adaptiveagriculture.modclass.block.InfertileFarmland;
import com.Thomas107500.adaptiveagriculture.util.RandomHelper;

import net.minecraft.block.BeetrootBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.CropGrowEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.VersionChecker.CheckResult;
import net.minecraftforge.fml.VersionChecker.Status;

public class StaticEventHandler {
	@SubscribeEvent
    public static void HoeUseListener(RightClickBlock event) {
		//AdaptiveAgriculture.LOGGER.debug("DEBUG:RightClickBlock Fired !!!");
		
		BlockPos hitBlockPos = event.getPos();
		BlockState hitBlock = event.getWorld().getBlockState(hitBlockPos);
		ItemStack heldItemStack = event.getItemStack();
		
		if(heldItemStack.getItem() instanceof HoeItem && hitBlock == BlockInit.infertile_dirt.getDefaultState()) 
		{
			//AdaptiveAgriculture.LOGGER.debug("DEBUG:Hoe attempt to tilt infertile dirt detected !!!");
			turnBlockToFarmland(event, hitBlockPos, heldItemStack, BlockInit.infertile_farmland.getDefaultState());
		}else if(heldItemStack.getItem() instanceof HoeItem && hitBlock == BlockInit.nourished_dirt.getDefaultState()) 
		{
			turnBlockToFarmland(event, hitBlockPos, heldItemStack, BlockInit.nourished_farmland.getDefaultState());
		}else if(heldItemStack.getItem() instanceof HoeItem && hitBlock == BlockInit.nutrient_rich_dirt.getDefaultState()) 
		{
			turnBlockToFarmland(event, hitBlockPos, heldItemStack, BlockInit.nutrient_rich_farmland.getDefaultState());
		}
	}
	
	protected static void turnBlockToFarmland(RightClickBlock event, BlockPos hitBlockPos, ItemStack heldItemStack, BlockState farmlandState) 
	{
		event.getWorld().setBlockState(hitBlockPos, farmlandState);
		event.getPlayer().playSound(SoundEvents.ITEM_HOE_TILL, 2f, 1.2f);
		event.getPlayer().swingArm(Hand.MAIN_HAND);
		heldItemStack.damageItem(1, event.getPlayer(), (Player) -> {event.getPlayer().sendBreakAnimation(Hand.MAIN_HAND);});
	}
	
	
	@SubscribeEvent
	public static void onHarvestDrops(BreakEvent event) 
	{
		@SuppressWarnings("unused")
		BreakEvent eventReference = event;
		int typeflag = 0;
		//AdaptiveAgriculture.LOGGER.debug("CropGrowEventPOST*** Fired !!!"+"BlockPos: "+ event.getPos()+"BlockState: "+ event.getState().toString());
		//First check to see if it is fully grown (BeetrootBlock and Berry Bush have different state property then CropsBlock)
		//Might do enum later 
		if (event.getState().getBlock() instanceof CropsBlock && !(event.getState().getBlock() instanceof CoverCrop)) {typeflag = 1;}
		if (event.getState().getBlock() instanceof CropsBlock && event.getState().getBlock() instanceof CoverCrop) {typeflag = 5;}
		if (event.getState().getBlock() instanceof BeetrootBlock) {typeflag = 2;}	
		if (event.getState().getBlock() instanceof SweetBerryBushBlock) {typeflag = 3;}
		if (event.getState().getBlock() instanceof StemBlock) {typeflag = 1;}
		//So we use switch to prevent game throwing exception when trying to access Blockstate property
		switch(typeflag) 
		{
		case 1:
			if(event.getState().get(CropsBlock.AGE) == 7) 
			{
				lowerFarmFertility(event);
			}
			break;
		case 2:
			if(event.getState().get(BeetrootBlock.BEETROOT_AGE) == 3) 
			{
				lowerFarmFertility(event);
			}
			break;
		case 3:
			if(event.getState().get(SweetBerryBushBlock.AGE) == 3) 
			{
				lowerFarmFertility(event);
			}
			break;
		case 5:
			if(event.getState().get(CoverCrop.AGE) == 7) 
			{
				raiseFarmFertility(event);
			}
			break;
		default:
			event.setResult(Result.DEFAULT);
		}
	}

	@SubscribeEvent
	public static void onCropGrowPre(CropGrowEvent.Pre event) 
	{
		
		Block plantBlock = event.getState().getBlock();
		
		if(event.getWorld().getBlockState(event.getPos().down()).getBlock() == BlockInit.infertile_farmland) 
		{
			//Not allowing normal plants to grow on infertile farmland
			if(!(event.getState().getBlock() instanceof CoverCrop)) 
			{
				event.setResult(Result.DENY);
			}
			else 
			{
				event.setResult(Result.DEFAULT);
			}
		}
		//Weed System
		if(plantBlock instanceof CropsBlock && !(plantBlock instanceof CoverCrop)|| plantBlock instanceof BeetrootBlock || plantBlock instanceof SweetBerryBushBlock || plantBlock instanceof StemBlock) 
		{
			if(!(event.getWorld().getBlockState(event.getPos().north()).getBlock() instanceof CoverCrop) && 
			   !(event.getWorld().getBlockState(event.getPos().east()).getBlock() instanceof CoverCrop) && 
			   !(event.getWorld().getBlockState(event.getPos().south()).getBlock() instanceof CoverCrop) &&
			   !(event.getWorld().getBlockState(event.getPos().west()).getBlock() instanceof CoverCrop) && 
			   event.getWorld().getBlockState(event.getPos().down()).getBlock() == BlockInit.nutrient_rich_farmland.getBlock()) 
			{
				if(RandomHelper.getWeightedRoll(Config.COMMON.weedProbability.get())) 
				{
					event.getWorld().setBlockState(event.getPos(), BlockInit.weed_crop.getDefaultState().with(CropsBlock.AGE, 7), 2);
				}
			}
		}
	
		//Apply Bonus Growth
		//AdaptiveAgriculture.LOGGER.debug("BonusGrowthHandler Fired !!!");
		Block plantedOnBlock = event.getWorld().getBlockState(event.getPos().down()).getBlock();
		Block targetCropBlock = event.getState().getBlock();
		
		if(RandomHelper.getWeightedRoll(Config.COMMON.bonusGrowthProbability.get())) 
		{
			if (targetCropBlock instanceof CropsBlock && plantedOnBlock == BlockInit.nutrient_rich_farmland) 
			{
				event.setResult(Result.ALLOW);
				event.getWorld().playEvent(2005, event.getPos(), 0);
			}
			else if(targetCropBlock instanceof BeetrootBlock && plantedOnBlock == BlockInit.nutrient_rich_farmland) 
			{
				event.setResult(Result.ALLOW);
				event.getWorld().playEvent(2005, event.getPos(), 0);
			}
			else if(targetCropBlock instanceof SweetBerryBushBlock && plantedOnBlock == BlockInit.nutrient_rich_farmland)
			{
				event.setResult(Result.ALLOW);
				event.getWorld().playEvent(2005, event.getPos(), 0);
			}
		}
		else if (plantedOnBlock != BlockInit.infertile_farmland)
		{
			event.setResult(Result.DEFAULT);
		}
	}
	
	protected static void lowerFarmFertility(BreakEvent eventReference) 
	{
		if(eventReference.getWorld().getBlockState(eventReference.getPos().down()).getBlock() == Blocks.FARMLAND.getBlock()) 
		{
			//Set the state of infertile farmland to the replaced farmland block
			Integer farmStateProperty = eventReference.getWorld().getBlockState(eventReference.getPos().down()).get(FarmlandBlock.MOISTURE);
			eventReference.getWorld().setBlockState(eventReference.getPos().down(), BlockInit.infertile_farmland.getDefaultState().with(InfertileFarmland.MOISTURE, farmStateProperty), 2);
		}else if(eventReference.getWorld().getBlockState(eventReference.getPos().down()).getBlock() == BlockInit.nourished_farmland.getBlock()) 
		{
			updateFarmState(eventReference, Blocks.FARMLAND.getDefaultState());
		}else if(eventReference.getWorld().getBlockState(eventReference.getPos().down()).getBlock() == BlockInit.nutrient_rich_farmland.getBlock()) 
		{
			updateFarmState(eventReference, BlockInit.nourished_farmland.getDefaultState());
		}
	}

	protected static void raiseFarmFertility(BreakEvent eventReference) 
	{
		//Check if the block under is Infertile farmland
		if(eventReference.getWorld().getBlockState(eventReference.getPos().down()).getBlock() == BlockInit.infertile_farmland.getBlock()) 
		{
			//Set the state of farmland to the replaced infertile farmland block
			updateFarmState(eventReference, Blocks.FARMLAND.getDefaultState());
		}else if(eventReference.getWorld().getBlockState(eventReference.getPos().down()).getBlock() == Blocks.FARMLAND.getBlock()) 
		{
			updateFarmState(eventReference, BlockInit.nourished_farmland.getDefaultState());
		}else if(eventReference.getWorld().getBlockState(eventReference.getPos().down()).getBlock() == BlockInit.nourished_farmland.getBlock()) 
		{
			updateFarmState(eventReference, BlockInit.nutrient_rich_farmland.getDefaultState());
		}
	}

	protected static void updateFarmState(BreakEvent eventReference, BlockState targetFarmState) 
	{
		Integer farmStateProperty = eventReference.getWorld().getBlockState(eventReference.getPos().down()).get(InfertileFarmland.MOISTURE);
		eventReference.getWorld().setBlockState(eventReference.getPos().down(), targetFarmState.with(InfertileFarmland.MOISTURE, farmStateProperty), 2);
	}

	@SubscribeEvent
	public static void modUpdateTracker(PlayerLoggedInEvent event) 
	{
		if(AdaptiveAgriculture.result.status == Status.OUTDATED) 
		{
			event.getPlayer().sendMessage(new StringTextComponent("A new version of Adaptive Agriculture: "+ AdaptiveAgriculture.result.target + " is now available at Curseforge!"));
		}
		else if(AdaptiveAgriculture.result.status == Status.FAILED) 
		{
			event.getPlayer().sendMessage(new StringTextComponent("Adaptive Agriculture update tracker failed to retreive update information..."));
		}
	}

}

