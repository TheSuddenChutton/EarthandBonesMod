package com.github.thesuddenchutton.earthandbonesmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;

public class TrapAlertAir extends AbstractTrapBlock{
	public static DirectionProperty ALERTSTOWARD = BlockStateProperties.FACING;
	
	public TrapAlertAir() {
		super(Properties.of(Material.STONE).noCollission().air());
		this.registerDefaultState(this.defaultBlockState().setValue(ALERTSTOWARD,Direction.UP));
		
	}
	@Override
	protected boolean isAir(BlockState state) {
		return true;
	}
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		alerted(pos, level, state);
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55484_) {
		p_55484_.add(ALERTSTOWARD);
	}
	public static void alerted(BlockPos pos, Level level, BlockState state) {
		if(state.getValue(ALERTSTOWARD) == Direction.UP && level.getBlockState(pos.above()).getBlock() instanceof AbstractTrapBlock) { ((AbstractTrapBlock)level.getBlockState(pos.above()).getBlock()).alerted(pos.above(), level); return; }
		if(state.getValue(ALERTSTOWARD) == Direction.DOWN && level.getBlockState(pos.below()).getBlock() instanceof AbstractTrapBlock) { ((AbstractTrapBlock)level.getBlockState(pos.below()).getBlock()).alerted(pos.below(), level); return; }
		if(state.getValue(ALERTSTOWARD) == Direction.EAST && level.getBlockState(pos.east()).getBlock() instanceof AbstractTrapBlock) { ((AbstractTrapBlock)level.getBlockState(pos.east()).getBlock()).alerted(pos.east(), level); return; }
		if(state.getValue(ALERTSTOWARD) == Direction.WEST && level.getBlockState(pos.west()).getBlock() instanceof AbstractTrapBlock) { ((AbstractTrapBlock)level.getBlockState(pos.west()).getBlock()).alerted(pos.west(), level); return; }
		if(state.getValue(ALERTSTOWARD) == Direction.NORTH && level.getBlockState(pos.north()).getBlock() instanceof AbstractTrapBlock) { ((AbstractTrapBlock)level.getBlockState(pos.north()).getBlock()).alerted(pos.north(), level); return; }
		if(state.getValue(ALERTSTOWARD) == Direction.SOUTH && level.getBlockState(pos.south()).getBlock() instanceof AbstractTrapBlock) { ((AbstractTrapBlock)level.getBlockState(pos.south()).getBlock()).alerted(pos.south(), level); return; }
		
		//none of the above
		level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
	}
}
