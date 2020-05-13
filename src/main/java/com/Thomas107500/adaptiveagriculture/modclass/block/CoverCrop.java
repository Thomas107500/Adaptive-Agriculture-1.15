package com.Thomas107500.adaptiveagriculture.modclass.block;

import java.util.Random;

import com.Thomas107500.adaptiveagriculture.init.BlockInit;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.util.Direction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class CoverCrop extends CropsBlock implements IGrowable{

	public CoverCrop(Properties builder) {
		super(builder);
	}
	
	@Override
	protected IItemProvider getSeedsItem() 
	{
		return BlockInit.cover_crop_seed;
		
	}
	
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
	    return false;
	}
	
	@Override
	public boolean ticksRandomly(BlockState state) {
		return true;
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) 
	{
		worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1); 
		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}
	
	@Override
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
	   return super.isValidGround(state, worldIn, pos);
	}
	
	
	/*@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		//Have to let light hit on the VoxelShape in order to render properly
		if(context.getEntity() instanceof Entity) 
		{
			return VoxelShapes.empty();
		}else 
		{
			return super.getCollisionShape(state, worldIn, pos, context);
		}
	} */
	
	
	
	
	
	
	
}
