package com.github.thesuddenchutton.earthandbonesmod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;

public class SpikeTrap extends Block{

	public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
	
	
	public SpikeTrap() {
		super(Properties.of(Material.STONE).destroyTime(2).requiresCorrectToolForDrops());

	      this.registerDefaultState(this.defaultBlockState().setValue(TRIGGERED, Boolean.valueOf(false)));
	}
	
	@Override
	public boolean removedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest,
			FluidState fluid) {
		if(world.getBlockState(pos.above()) == Registration.SPIKES.get().defaultBlockState()) world.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), UPDATE_ALL);
		return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
	}
	@Override
	public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
		if(world.getBlockState(pos.above()) == Registration.SPIKES.get().defaultBlockState()) world.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), UPDATE_ALL);
		super.onBlockExploded(state, world, pos, explosion);
	}
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random rand) {
		if(level.getBlockState(pos.above()) == Registration.SPIKES.get().defaultBlockState()) {
			level.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), UPDATE_ALL);
			level.setBlock(pos, state.setValue(TRIGGERED, false), 3);
		}
	}	
	public void stepOn(Level p_154299_, BlockPos p_154300_, BlockState p_154301_, Entity p_154302_) {
	   interact(p_154301_, p_154299_, p_154300_);
	   super.stepOn(p_154299_, p_154300_, p_154301_, p_154302_);
	}
	
	private static void interact(BlockState p_55493_, Level p_55494_, BlockPos p_55495_) {
	   if(!p_55493_.getValue(TRIGGERED)) {
		   p_55494_.setBlock(p_55495_.above(), Registration.SPIKES.get().defaultBlockState(), 3);
		   p_55494_.setBlock(p_55495_, p_55493_.setValue(TRIGGERED, true), 3);
	   }
	   
	
	}
	  
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55484_) {
		p_55484_.add(TRIGGERED);
	}
	
	public boolean isRandomlyTicking(BlockState p_55486_) {
		return p_55486_.getValue(TRIGGERED);
	}
}
