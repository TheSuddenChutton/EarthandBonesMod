package com.github.thesuddenchutton.earthandbonesmod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.thesuddenchutton.earthandbonesmod.EarthandBonesMod;
import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;

import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraftforge.client.event.sound.PlaySoundEvent;

public class SpikeTrap extends AbstractTrapBlock{

	public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
	
	
	public SpikeTrap() {
		super(Properties.of(Material.STONE).destroyTime(2).requiresCorrectToolForDrops());

	      this.registerDefaultState(this.defaultBlockState().setValue(TRIGGERED, Boolean.valueOf(false)));
	}	
	public SpikeTrap(Properties p) {
		super(p);
	    this.registerDefaultState(this.defaultBlockState().setValue(TRIGGERED, Boolean.valueOf(false)));
	}
	@Override
	public boolean removedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest,
			FluidState fluid) {
		if(world.getBlockState(pos.above()).is(Registration.SPIKES.get())) world.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), UPDATE_ALL);
		return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
	}
	@Override
	public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
		if(world.getBlockState(pos.above()).is(Registration.SPIKES.get())) world.setBlock(pos.above(), Registration.ACACIALOGTRAP.get().defaultBlockState(), UPDATE_ALL);
		super.onBlockExploded(state, world, pos, explosion);
	}
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random rand) {
		if(level.getBlockState(pos.above()).is(Registration.SPIKES.get())) {
			//level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents., null, friction, explosionResistance, dynamicShape);
			// TODO add a sound event for spikes.
			
			level.setBlock(pos.above(), Registration.TRAP_ALERT_AIR.get().defaultBlockState().setValue(TrapAlertAir.ALERTSTOWARD, Direction.DOWN), UPDATE_ALL);
			level.setBlock(pos, state.setValue(TRIGGERED, false), 3);
		}
	}
	
	public void stepOn(Level p_154299_, BlockPos p_154300_, BlockState p_154301_, Entity p_154302_) {
	   interact(p_154301_, p_154299_, p_154300_);
	   super.stepOn(p_154299_, p_154300_, p_154301_, p_154302_);
	}
	
	public static void interact(BlockState state, Level level, BlockPos pos) {
		if(!state.getValue(TRIGGERED) && !level.getBlockState(pos.above()).is(Blocks.OBSIDIAN) && !level.getBlockState(pos.above()).is(Blocks.BEDROCK)) {
			   if(!level.getBlockState(pos.above()).isAir()) {
				   level.getBlockState(pos.above()).getBlock().asItem();
			   }
			   level.setBlock(pos.above(), Registration.SPIKES.get().defaultBlockState(), 3);
			   level.setBlock(pos, state.setValue(TRIGGERED, true), 3);			
		}
	}
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55484_) {
		p_55484_.add(TRIGGERED);
	}
	
	public boolean isRandomlyTicking(BlockState p_55486_) {
		return p_55486_.getValue(TRIGGERED);
	}
	public void alerted(BlockPos pos, Level level, BlockState state) {
		interact(state, level, pos);
	}
}
