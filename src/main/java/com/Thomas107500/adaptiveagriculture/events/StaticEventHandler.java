package com.Thomas107500.adaptiveagriculture.events;

import com.Thomas107500.adaptiveagriculture.init.BlockInit;
import com.Thomas107500.adaptiveagriculture.modclass.block.CoverCrop;
import com.Thomas107500.adaptiveagriculture.modclass.block.InfertileFarmland;

import net.minecraft.block.BeetrootBlock;
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
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.CropGrowEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StaticEventHandler {
	@SubscribeEvent
    public static void HoeUseListener(RightClickBlock event) {
		//AdaptiveAgriculture.LOGGER.debug("DEBUG:RightClickBlock Fired !!!");
		
		BlockPos hitBlockpos = event.getPos();
		BlockState hitBlock = event.getWorld().getBlockState(hitBlockpos);
		ItemStack heldItemStack = event.getItemStack();
		
		if(heldItemStack.getItem() instanceof HoeItem && hitBlock == BlockInit.infertile_dirt.getDefaultState()) 
		{
			//AdaptiveAgriculture.LOGGER.debug("DEBUG:Hoe attempt to tilt infertile dirt detected !!!");
			event.getWorld().setBlockState(hitBlockpos, BlockInit.infertile_farmland.getDefaultState());
			event.getPlayer().playSound(SoundEvents.ITEM_HOE_TILL, 2f, 1.2f);
			event.getPlayer().swingArm(Hand.MAIN_HAND);
			heldItemStack.damageItem(1, event.getPlayer(), (Player) -> {event.getPlayer().sendBreakAnimation(Hand.MAIN_HAND);});
		}
	}
	@SubscribeEvent
	public static void onHarvestDrops(HarvestDropsEvent event) 
	{
		int typeflag = 0;
		//AdaptiveAgriculture.LOGGER.debug("CropGrowEventPOST Fired !!!"+"BlockPos: "+ event.getPos()+"BlockState: "+ event.getState().toString());
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
				updateBlockstate(event);
			}
			break;
		case 2:
			if(event.getState().get(BeetrootBlock.BEETROOT_AGE) == 3) 
			{
				updateBlockstate(event);
			}
			break;
		case 3:
			if(event.getState().get(SweetBerryBushBlock.AGE) == 3) 
			{
				updateBlockstate(event);
			}
			break;
		case 5:
			if(event.getState().get(CoverCrop.AGE) == 7) 
			{
				fertileSoil(event);
			}
			break;
		default:
			event.setResult(Result.DEFAULT);
		}
	}

	@SubscribeEvent
	public static void onCropGrowPre(CropGrowEvent.Pre event) 
	{
		if(event.getWorld().getBlockState(event.getPos().down()).getBlock() == BlockInit.infertile_farmland.getBlock()) 
		{
			//Not allowing normal plants to grow on infertile farmland
			if(!(event.getState().getBlock() instanceof CoverCrop)) 
			{
				event.setResult(Result.DENY);
			}else 
			{
				event.setResult(Result.DEFAULT);
			}
		}
	}

	
	
	protected static void updateBlockstate(HarvestDropsEvent event) 
	{
		//Check if the block under is FarmlandBlock
		if(event.getWorld().getBlockState(event.getPos().down()).getBlock() == Blocks.FARMLAND.getBlock()) 
		{
			//Set the state of infertile farmland to the replaced farmland block
			Integer farmStateProperty = event.getWorld().getBlockState(event.getPos().down()).get(FarmlandBlock.MOISTURE);
			event.getWorld().setBlockState(event.getPos().down(), BlockInit.infertile_farmland.getDefaultState().with(InfertileFarmland.MOISTURE, farmStateProperty), 2);
	
		}
	}

	protected static void fertileSoil(HarvestDropsEvent event) 
	{
		//Check if the block under is Infertile farmland
		if(event.getWorld().getBlockState(event.getPos().down()).getBlock() == BlockInit.infertile_farmland.getBlock()) 
		{
			//Set the state of farmland to the replaced infertile farmland block
			Integer farmStateProperty = event.getWorld().getBlockState(event.getPos().down()).get(InfertileFarmland.MOISTURE);
			event.getWorld().setBlockState(event.getPos().down(), Blocks.FARMLAND.getDefaultState().with(InfertileFarmland.MOISTURE, farmStateProperty), 2);
		}
	}
}

