package com.github.thesuddenchutton.earthandbonesmod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.thesuddenchutton.earthandbonesmod.handlers.EventHandler;
import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;
import com.mojang.realmsclient.client.Ping;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraftforge.common.IPlantable;

public class MultidirectionalWoodenSpikeTrap extends AbstractTrapBlock{

	public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
	
	public MultidirectionalWoodenSpikeTrap(boolean wooden) {
		super(Properties.of(Material.WOOD).destroyTime(1).requiresCorrectToolForDrops());

	    this.registerDefaultState(this.defaultBlockState().setValue(TRIGGERED, Boolean.valueOf(false)));
	}
	@Override
	public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing,
			IPlantable plantable) {
		return state.is(Blocks.JUNGLE_LEAVES) || state.is(Blocks.SPRUCE_LEAVES) || state.is(Blocks.ACACIA_LEAVES);
	}
	@Override
	public void onPlace(BlockState p_60566_, Level world, BlockPos pos, BlockState p_60569_, boolean p_60570_) {
		if(world.getBlockState(pos.above()).isAir() || world.getBlockState(pos.above()).canBeReplaced(Fluids.FLOWING_WATER) || world.getBlockState(pos.above()).is(Blocks.VINE)) world.setBlock(pos.above(), Registration.TRAP_ALERT_AIR.get().defaultBlockState().setValue(Spikes.POINTING, Direction.DOWN), UPDATE_ALL);
		if(world.getBlockState(pos.below()).isAir() || world.getBlockState(pos.below()).canBeReplaced(Fluids.FLOWING_WATER) || world.getBlockState(pos.below()).is(Blocks.VINE)) world.setBlock(pos.below(), Registration.TRAP_ALERT_AIR.get().defaultBlockState().setValue(Spikes.POINTING, Direction.UP), UPDATE_ALL);
		if(world.getBlockState(pos.north()).isAir() || world.getBlockState(pos.north()).canBeReplaced(Fluids.FLOWING_WATER) || world.getBlockState(pos.north()).is(Blocks.VINE)) world.setBlock(pos.north(), Registration.TRAP_ALERT_AIR.get().defaultBlockState().setValue(Spikes.POINTING, Direction.SOUTH), UPDATE_ALL);
		if(world.getBlockState(pos.south()).isAir() || world.getBlockState(pos.south()).canBeReplaced(Fluids.FLOWING_WATER) || world.getBlockState(pos.south()).is(Blocks.VINE)) world.setBlock(pos.south(), Registration.TRAP_ALERT_AIR.get().defaultBlockState().setValue(Spikes.POINTING, Direction.NORTH), UPDATE_ALL);
		if(world.getBlockState(pos.east()).isAir() || world.getBlockState(pos.east()).canBeReplaced(Fluids.FLOWING_WATER) || world.getBlockState(pos.east()).is(Blocks.VINE)) world.setBlock(pos.west(), Registration.TRAP_ALERT_AIR.get().defaultBlockState().setValue(Spikes.POINTING, Direction.WEST), UPDATE_ALL);
		if(world.getBlockState(pos.west()).isAir() || world.getBlockState(pos.west()).canBeReplaced(Fluids.FLOWING_WATER) || world.getBlockState(pos.west()).is(Blocks.VINE)) world.setBlock(pos.east(), Registration.TRAP_ALERT_AIR.get().defaultBlockState().setValue(Spikes.POINTING, Direction.EAST), UPDATE_ALL);
	}
	
	@Override
	public boolean removedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest,
			FluidState fluid) {
		if(world.getBlockState(pos.above()) == Registration.SPIKES.get().defaultBlockState()) world.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), UPDATE_ALL);
		return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
	}
	@Override
	public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
		retract(world, pos);
		super.onBlockExploded(state, world, pos, explosion);
	}
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random rand) {
		retract(level, pos);
	}
	
	
	public void stepOn(Level p_154299_, BlockPos p_154300_, BlockState p_154301_, Entity p_154302_) {
		extend(p_154299_, p_154300_);
	   	super.stepOn(p_154299_, p_154300_, p_154301_, p_154302_);
	}
	@Override
	public void attack(BlockState p_60499_, Level p_60500_, BlockPos p_60501_, Player p_60502_) {
		if(EventHandler.isWithinDistance(p_60501_, p_60502_.blockPosition(), 3) && !p_60499_.getValue(TRIGGERED)) extend(p_60500_,p_60501_);
	}
	public static void retract(Level world, BlockPos pos){
		world.setBlock(pos, world.getBlockState(pos).setValue(TRIGGERED, false), 3);
		if(world.getBlockState(pos.above()).is(Registration.SPIKES.get())) world.setBlock(pos.above(), Registration.TRAP_ALERT_AIR.get().defaultBlockState().setValue(TrapAlertAir.ALERTSTOWARD, Direction.DOWN), UPDATE_ALL);
		if(world.getBlockState(pos.below()).is(Registration.SPIKES.get())) world.setBlock(pos.below(), Registration.TRAP_ALERT_AIR.get().defaultBlockState().setValue(TrapAlertAir.ALERTSTOWARD, Direction.UP), UPDATE_ALL);			
		if(world.getBlockState(pos.north()).is(Registration.SPIKES.get())) world.setBlock(pos.north(), Registration.TRAP_ALERT_AIR.get().defaultBlockState().setValue(TrapAlertAir.ALERTSTOWARD, Direction.SOUTH), UPDATE_ALL);
		if(world.getBlockState(pos.south()).is(Registration.SPIKES.get())) world.setBlock(pos.south(), Registration.TRAP_ALERT_AIR.get().defaultBlockState().setValue(TrapAlertAir.ALERTSTOWARD, Direction.NORTH), UPDATE_ALL);
		if(world.getBlockState(pos.east()).is(Registration.SPIKES.get())) world.setBlock(pos.east(), Registration.TRAP_ALERT_AIR.get().defaultBlockState().setValue(TrapAlertAir.ALERTSTOWARD, Direction.WEST), UPDATE_ALL);
		if(world.getBlockState(pos.west()).is(Registration.SPIKES.get())) world.setBlock(pos.west(), Registration.TRAP_ALERT_AIR.get().defaultBlockState().setValue(TrapAlertAir.ALERTSTOWARD, Direction.EAST), UPDATE_ALL);		

	}
	public static void extend(Level world, BlockPos pos){
		if(!world.getBlockState(pos).getValue(TRIGGERED)) {
			world.setBlock(pos, world.getBlockState(pos).setValue(TRIGGERED, true), 3);
			if(world.getBlockState(pos.above()).isAir() || world.getBlockState(pos.above()).canBeReplaced(Fluids.FLOWING_WATER)) world.setBlock(pos.above(), Registration.SPIKES.get().defaultBlockState().setValue(Spikes.POINTING, Direction.UP), UPDATE_ALL);
			if(world.getBlockState(pos.below()).isAir() || world.getBlockState(pos.above()).canBeReplaced(Fluids.FLOWING_WATER)) world.setBlock(pos.below(), Registration.SPIKES.get().defaultBlockState().setValue(Spikes.POINTING, Direction.DOWN), UPDATE_ALL);
			if(world.getBlockState(pos.north()).isAir() || world.getBlockState(pos.north()).canBeReplaced(Fluids.FLOWING_WATER)) world.setBlock(pos.north(), Registration.SPIKES.get().defaultBlockState().setValue(Spikes.POINTING, Direction.NORTH), UPDATE_ALL);
			if(world.getBlockState(pos.south()).isAir() || world.getBlockState(pos.north()).canBeReplaced(Fluids.FLOWING_WATER)) world.setBlock(pos.south(), Registration.SPIKES.get().defaultBlockState().setValue(Spikes.POINTING, Direction.SOUTH), UPDATE_ALL);
			if(world.getBlockState(pos.east()).isAir() || world.getBlockState(pos.north()).canBeReplaced(Fluids.FLOWING_WATER)) world.setBlock(pos.east(), Registration.SPIKES.get().defaultBlockState().setValue(Spikes.POINTING, Direction.EAST), UPDATE_ALL);
			if(world.getBlockState(pos.west()).isAir() || world.getBlockState(pos.north()).canBeReplaced(Fluids.FLOWING_WATER)) world.setBlock(pos.west(), Registration.SPIKES.get().defaultBlockState().setValue(Spikes.POINTING, Direction.WEST), UPDATE_ALL);
		}
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55484_) {
		p_55484_.add(TRIGGERED);
	}
	
	public boolean isRandomlyTicking(BlockState p_55486_) {
		return p_55486_.getValue(TRIGGERED);
	}
	

	public void alerted(BlockPos pos, Level level, BlockState state) { extend(level, pos); }
}
