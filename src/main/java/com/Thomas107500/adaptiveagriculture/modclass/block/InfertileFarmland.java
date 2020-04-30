package com.Thomas107500.adaptiveagriculture.modclass.block;

import java.util.Random;

import com.Thomas107500.adaptiveagriculture.init.BlockInit;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class InfertileFarmland extends FarmlandBlock {

	//public static final IntegerProperty MOISTURE = BlockStateProperties.MOISTURE_0_7;
	
	public InfertileFarmland(Properties builder) {
		super(builder);
	}
	
	//from FarmlandBlock
	private static boolean hasWater(IWorldReader worldIn, BlockPos pos) {
		for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
			if (worldIn.getFluidState(blockpos).isTagged(FluidTags.WATER)) {
				return true;
			}
		}
		return net.minecraftforge.common.FarmlandWaterManager.hasBlockWaterTicket(worldIn, pos);
	}
	
	//from FarmlandBlock
	public boolean hasCrops(IBlockReader worldIn, BlockPos pos) {
	   BlockState state = worldIn.getBlockState(pos.up());
	   return state.getBlock() instanceof net.minecraftforge.common.IPlantable && canSustainPlant(state, worldIn, pos, Direction.UP, (net.minecraftforge.common.IPlantable)state.getBlock());
	}
	
	public static void turnToInfertileDirt(BlockState state, World worldIn, BlockPos pos) 
	{
		worldIn.setBlockState(pos, nudgeEntitiesWithNewState(state, BlockInit.infertile_dirt.getDefaultState(), worldIn, pos));
	}
	
	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
		if (!worldIn.isRemote && net.minecraftforge.common.ForgeHooks.onFarmlandTrample(worldIn, pos, Blocks.DIRT.getDefaultState(), fallDistance, entityIn)) {
	         turnToInfertileDirt(worldIn.getBlockState(pos), worldIn, pos);
	    }
	}
	
	
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) 
	{
		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos); 
	}
	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, net.minecraftforge.common.IPlantable plantable) 
	{
		return true; 
	}
	
	@Override
	public boolean ticksRandomly(BlockState state) {
	      return true;
	}
	
	
	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) 
	{
		//AdaptiveAgriculture.LOGGER.debug("DEBUG:farmland tick called !!!");
		breakCrop(state, worldIn, pos);
		
		if (!state.isValidPosition(worldIn, pos)) {
	         turnToInfertileDirt(state, worldIn, pos);
	      } else {
	         int i = state.get(MOISTURE);
	         if (!hasWater(worldIn, pos) && !worldIn.isRainingAt(pos.up())) {
	            if (i > 0) {
	               worldIn.setBlockState(pos, state.with(MOISTURE, Integer.valueOf(i - 1)), 2);
	            } else if (!hasCrops(worldIn, pos)) {
	               turnToInfertileDirt(state, worldIn, pos);
	            }
	         } else if (i < 7) {
	            worldIn.setBlockState(pos, state.with(MOISTURE, Integer.valueOf(7)), 2);
	         }

	      }
	}
	
	
	public void breakCrop(BlockState state, ServerWorld worldIn, BlockPos pos) 
	{
		
		BlockState possibleplantState = worldIn.getBlockState(pos.up());
		
		if(possibleplantState.getBlock() instanceof net.minecraftforge.common.IPlantable)
		{
			if(!(possibleplantState.getBlock() instanceof CoverCrop)) 
			{
				//Crop will break and left no item if fail to harvest on time
				worldIn.destroyBlock(pos.up(), false);
			}
			
		}
	} 
}
